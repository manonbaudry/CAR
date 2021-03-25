package actors;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import akka.actor.AbstractActor;
import akka.actor.UntypedActor;

/**
 * Reducer actor that counts the number of occurrences of a word
 *
 * @author Brice Despelchin, Manon Baudry
 */
public class Reducer extends UntypedActor {

	public HashMap<String, Integer> occurences;

	public Reducer() {
		this.occurences = new HashMap<>();
	}
	
	/**
	 * Add a word to our occurences hashmap.
	 * @param word
	 */
	private void addWord(String word) {
		if (!occurences.containsKey(word)) {
			occurences.put(word, 1);
		} else {
			occurences.put(word, occurences.get(word) + 1);
		}
	}

	/**
	 * Print occurences hashmap to normal output system.
	 */
	private void printOccurences() {
		for (Map.Entry<String, Integer> e : occurences.entrySet()) {
			System.out.println(e.getKey() + " -> " + e.getValue());
		}
	}

	@Override
	public void onReceive(Object message) throws Exception {
		if(message instanceof Message) {
			printOccurences();
		}else if (message instanceof String) {
			addWord((String)message);
		}else {
			unhandled(message);
		}		
	}
}
