package actors;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.UntypedActor;
import main.Main;
import scala.Option;
/**
 * The Master actor distributes the lines of the file alternately to each Mapper object
 *
 * @author Brice Despelchin, Manon Baudry
 */
public class Master extends UntypedActor {

	public Master() {}

	private void distributeRows(File file) {
		int i = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = br.readLine()) != null) {
				Main.system.actorSelection(Main.paths.get("mapper" + (i% Main.NBMAPPER))).tell(new Message(line.toUpperCase()), getSelf());
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void callReducer(Message message) {
		for (int i=0; i < Main.NBREDUCER; i++) {
			Main.system.actorSelection(Main.paths.get("reducer" + i%Main.NBREDUCER)).tell(message, getSelf());
		}
	}

	@Override
	public void onReceive(Object message) throws Exception {
		if(message instanceof Message) {
			callReducer((Message)message);
		}else if (message instanceof File) {
			distributeRows((File)message);
		}else {
			unhandled(message);
		}
	}
}
