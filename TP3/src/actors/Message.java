package actors;

import java.io.Serializable;

/**
 * Message class.
 * @author brice & manon
 *
 */
public class Message implements Serializable {

		public String message;
		public Message (String message) { this.message = message;}
}
