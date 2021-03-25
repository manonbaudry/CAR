package main;

import java.io.File;
import java.util.HashMap;

import com.typesafe.config.ConfigFactory;

import akka.actor.ActorPath;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import actors.*;


/**
 * Main application program. Launch an AKKA system in 2 differents ports in local.
 *
 * @author Brice Despelchin, Manon Baudry
 */
public class Main {

	public static final int NBMASTER= 1;
	public static final int NBMAPPER = 3;
	public static final int NBREDUCER = 2;
	public static HashMap<String,ActorRef> paths;
	public static ActorSystem system;

	public static final String HOST_REDUCERS = "akka.tcp://Server@localhost:2001";
	public static final String HOST_MAPPERS= "akka.tcp://Server@localhost:2002";
	
	public static void main(String[] args) {		
			startNormalSystem();
	}
	
	/**
	 * Handle a normal double configuration system. Read a file, creates actors systems, create actor props and references it in a hashmap. 
	 * Then send files data to actors bus.
	 */
	private static void startNormalSystem() {
		HashMap<String, ActorRef> map = new HashMap<>();
		try {
			File fontaine = new File("TP3/resources/lafontaine.txt");
			ActorSystem mapperSystem = ActorSystem.create("mapperSystem", ConfigFactory.load("mapper"));
			ActorSystem reducerSystem = ActorSystem.create("reducerSystem", ConfigFactory.load("reducer"));
			
			ActorRef master = reducerSystem.actorOf(Props.create(Master.class), "master");
			for (int i=0; i < NBREDUCER ; i++) {
				ActorRef a = reducerSystem.actorOf(Props.create(Reducer.class), "reducer" + i);
				map.put("reducer" + i, a);
			}
			for (int i=0; i < NBMAPPER ; i++) {
				ActorRef a =mapperSystem.actorOf(Props.create(Mapper.class), "mapper" + i);
				map.put("mapper" + i, a);
			}
			paths = map;
			master.tell(fontaine, ActorRef.noSender());
			master.tell(new Message("JobDone"), ActorRef.noSender());
		} catch (Exception e) {
			System.out.println(e);
		}
	}	
}