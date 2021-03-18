package actors;

import java.io.File;

import akka.actor.ActorSelection;
import akka.actor.UntypedActor;
import main.Main;
/**
 * the Mapper actor identifies each word in the line and passes the word to one of the actors
 * Reducer
 *
 * @author Brice Despelchin, Manon Baudry
 */
public class Mapper extends UntypedActor {
	public Mapper() { }

	private ActorSelection partition(String key) {
		return getContext().actorSelection(Main.HOST + "/user/reducer" + Math.abs(key.hashCode() % Main.NBREDUCER));
	}


	@Override
	public void onReceive(Object message) throws Exception {
		if(message instanceof Message) {
			String[] splittedMessage = ((Message) message).message.split("[.|,|:|!|\\\\s|\\\\n]");
			for (String word : splittedMessage) {
				ActorSelection reducer = partition(word);
				reducer.tell(word, self());
			}
		}else
			unhandled(message);
	}
}
