package main;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.io.InputStreamReader;


/**
 * Thread to a ftp server. Called when a client connect to the server.
 * @author Brice & Manon
 *
 */
public class FtpServerThread extends Thread {
	private Socket cliSocket;
	private boolean cliThreadRunning = true;
	private DataOutputStream controlOutWriter;
	private BufferedReader controlIn;
	
	
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


		//System.out.println("Accepted Client Address - " + cliSocket.getInetAddress().getHostName());

		try {
			// Input from client
			controlIn = new BufferedReader(new InputStreamReader(cliSocket.getInputStream()));

			// Output to client, automatically flushed after each print
			controlOutWriter = new DataOutputStream(cliSocket.getOutputStream());

			// Greeting
			printMsg("220 service ready");

			while(cliThreadRunning) {
				interpreteCommand(controlIn.readLine());
			}
			
			
			// Authenticates the user
			String userName = controlIn.readLine();
			if(!userName.equals("USER toto")) {
				System.out.println("Wrong username");
				System.exit(-1);
			}

			
			String pwd = controlIn.readLine();

			if(!pwd.equals("PASS toto")){
				System.out.println("Wrong password");
				System.exit(-1);
			}
			printMsg("230 User logged in");

		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * USER method. Log the user if included in csv file
	 * @param username - username
	 */
	private void USER(String username) {
		if(username) {
			printMsg("331 User name ok, need password");
			currentUserStatus = userStatus.ENTEREDUSERNAME;
		}else if (currentUserStatus == userStatus.LOGGEDIN) {
			printMsg("530 User already logged in");
		}else {
			printMsg("530 Not logged in");
		}
	}
	
	/**
	 * PASS method. Check if the given password correspond to the past given user in CSV file
	 * @param password - password
	 */
	private void PASS(String password) {
		if(currentUserStatus == userStatus.ENTEREDUSERNAME) {
			currentUserStatus = userStatus.LOGGEDIN;
			printMsg("230 User logged in");
		}else if (currentUserStatus == userStatus.LOGGEDIN) {
			printMsg("530 User already logged in");
		}else {
			printMsg("530 Not logged in");
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
		String[] args =  Arrays.copyOfRange(entireLine, 1, entireLine.length);
				
		switch (command.toUpperCase()) {
		case "USER":
			USER(args[0]);
			break;

		default:
			printMsg("501 Unknown command");
		}
	}
	
	private void printMsg(String msg) {
		try {
			controlOutWriter.writeBytes(msg+"\r\n");
			controlOutWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		FtpServerThread a= new FtpServerThread();
		a.interpreteCommand("bonjour le monde");
	}
}
