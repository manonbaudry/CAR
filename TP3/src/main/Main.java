package main;

import java.io.File;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import actors.*;


/**
 * Main application program
 *
 * @author Brice Despelchin, Manon Baudry
 */
public class Main {

	public static final int NBMASTER= 1;
	public static final int NBMAPPER =3;
	public static final int NBREDUCER = 2;

	public static final String HOST	= "akka.tcp://MySystemServer@localhost:2001";

	public static void main(String[] args) {

		try {
			File fontaine = new File("lafontaine.txt");
			System.out.println(fontaine.getAbsolutePath());
			ActorSystem system = ActorSystem.create("MySystemServer");
			ActorRef master = system.actorOf(Props.create(Master.class), "master");

			for (int i=0; i < NBREDUCER ; i++) {
				system.actorOf(Props.create(Reducer.class), "reducer" + i);
			}

			for (int i=0; i < NBMAPPER ; i++) {
				system.actorOf(Props.create(Mapper.class), "mapper" + i);
			}

			master.tell(fontaine, ActorRef.noSender());
			master.tell(new Message("JobDone"), ActorRef.noSender());
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}