package actors;

import java.io.Serializable;

public class Message implements Serializable {

		public String message;
		public Message (String message) { this.message = message;}
}
