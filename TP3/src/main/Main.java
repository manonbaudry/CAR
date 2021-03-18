package main;

import java.io.File;
import java.util.HashMap;

import akka.actor.ActorPath;
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
	public static final int NBMAPPER = 3;
	public static final int NBREDUCER = 2;
	public static HashMap<String,ActorPath> paths;
	public static ActorSystem system;

	public static final String HOST_REDUCERS = "akka.tcp://MySystemServer@localhost:2001";
	  
	public static void main(String[] args) {

		HashMap<String, ActorPath> map = new HashMap<>();
		try {
			File fontaine = new File("TP3/resources/lafontaine.txt");
			system = ActorSystem.create("MySystemServer");
			ActorRef master = system.actorOf(Props.create(Master.class), "master");
			for (int i=0; i < NBREDUCER ; i++) {
				ActorRef a = system.actorOf(Props.create(Reducer.class), "reducer" + i);
				map.put("reducer" + i, a.path());
			}
			for (int i=0; i < NBMAPPER ; i++) {
				ActorRef a =system.actorOf(Props.create(Mapper.class), "mapper" + i);
				map.put("mapper" + i, a.path());
			}
			paths = map;
			master.tell(fontaine, ActorRef.noSender());
			master.tell(new Message("JobDone"), ActorRef.noSender());

		} catch (Exception e) {
			System.out.println(e);
		}
	}
}