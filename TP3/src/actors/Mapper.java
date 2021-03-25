package actors;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import main.Main;
/**
 * The Mapper actor identifies each word in the line and passes the word to one of the actors
 *
 * @author Brice Despelchin, Manon Baudry
 */
public class Mapper extends UntypedActor {
	public Mapper() {}

	/**
	 * Partition our string to reducers
	 * @param key -the key to partition
	 * @return the Reducer to use
	 */
	private ActorRef partition(String key) {
		return Main.paths.get("reducer" + Math.abs(key.hashCode() % Main.NBREDUCER));
	}

	@Override
	public void onReceive(Object message) throws Exception {
		if(message instanceof Message) {
			String[] splittedMessage = ((Message) message).message.split("[ |.|,|:|!|\\\\s|\\\\n]");
			for (String word : splittedMessage) {
				ActorRef reducer = partition(word);
				reducer.tell(word, self());
			}
		}else
			unhandled(message);
	}
}
