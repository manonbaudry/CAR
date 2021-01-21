package main;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.net.Socket;
import java.io.InputStreamReader;


/**
 * Thread to a ftp server. Called when a client connect to the server.
 * @author Brice & Manon
 *
 */
public class FtpServerThread extends Thread {
	private Socket cliSocket;
	private boolean cliThreadRunning = true;
	
	private enum userStatus {
		NOTLOGGEDIN, ENTEREDUSERNAME, LOGGEDIN
	}
	
	private userStatus currentUserStatus = userStatus.NOTLOGGEDIN;
	
	/**
	 * Construct a thread by default
	 */
	public FtpServerThread() {
		super();
	}

	/**
	 * Initiate the client to our class
	 * @param client - the client to map
	 */
	public FtpServerThread(Socket client) {
		cliSocket = client;
	}

	/**
	 * Run the exe of a thread.
	 */
	public void run() {
		DataOutputStream controlOutWriter;
		BufferedReader controlIn;

		//System.out.println("Accepted Client Address - " + cliSocket.getInetAddress().getHostName());

		try {
			// Input from client
			controlIn = new BufferedReader(new InputStreamReader(cliSocket.getInputStream()));

			// Output to client, automatically flushed after each print
			controlOutWriter = new DataOutputStream(cliSocket.getOutputStream());

			// Greeting
			controlOutWriter.writeBytes("220 service ready\r\n");
			controlOutWriter.flush();

			while(cliThreadRunning) {
				interpreteCommand(controlIn.readLine());
			}
			
			
			// Authenticates the user
			String userName = controlIn.readLine();
			if(!userName.equals("USER toto")) {
				System.out.println("Wrong username");
				System.exit(-1);
			}

			controlOutWriter.writeBytes("331 User name ok, need password\r\n");
			String pwd = controlIn.readLine();

			if(!pwd.equals("PASS toto")){
				System.out.println("Wrong password");
				System.exit(-1);
			}
			controlOutWriter.writeBytes("230 User logged in\r\n");

		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Switch/case of the user entry. Send to the corresponding command.
	 * @param readLine - the user's entry
	 */
	private void interpreteCommand(String readLine) {
		// TODO Auto-generated method stub
		String[] entireLine = readLine.split(" ");
		String command = entireLine[0];
		
		String[] args = entireLine; //!remove [0]
		
		//System.out.println(sortie[0]);
	}
	
	public static void main(String[] args) {
		FtpServerThread st = new FtpServerThread();
		st.interpreteCommand("bonjour brice");
	}
}
