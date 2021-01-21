package main;

import utils.CSVReader;

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
	private userStatus currentUserStatus = userStatus.NOTLOGGEDIN;
	private String username = "";
	
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
	
	private void printMsg(String msg) {
		try {
			controlOutWriter.writeBytes(msg+"\r\n");
			controlOutWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Run the exe of a thread.
	 */
	public void run() {
		try {
			// Input from client
			controlIn = new BufferedReader(new InputStreamReader(cliSocket.getInputStream()));

			// Output to client, automatically flushed after each print
			controlOutWriter = new DataOutputStream(cliSocket.getOutputStream());

			// Greeting
			printMsg("220 service ready");

			USER(controlIn.readLine());
			PASS(controlIn.readLine());

			while(cliThreadRunning) {
				interpreteCommand(controlIn.readLine());
			}
						
		} catch (Exception e) {
			System.out.println(e);
		}finally{
			try {
				controlIn.close();
				controlOutWriter.close();
				cliSocket.close();
				System.out.println("Cli stopped.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * Switch/case of the user entry. Send to the corresponding command.
	 * @param readLine - the user's entry
	 */
	private void interpreteCommand(String readLine) {
		String[] entireLine = readLine.split(" ");
		String command = entireLine[0];
		String[] args =  Arrays.copyOfRange(entireLine, 1, entireLine.length);
				
		switch (command.toLowerCase()) {
		case "username":
			USER(args[0]);
			break;
		case "password":
			PASS(args[0]);
			break;
		case "dir":
			if (args[0].isEmpty()) {
				DIR("");
			}else {
				DIR(args[0]);
			}			
			break;
		case "get":
			break;
		case "put":
			break;
		case "cd":
			break;
		case "quit":
			QUIT();
			break;

		default:
			printMsg("501 Unknown command");
		}
	}
	
	/**
	 * DIR method. Get a list of the given directory
	 * @param path
	 */
	private void DIR(String path) {
		
		
	}

	/**
	 * USER method. Log the user if included in csv file
	 * @param username - username
	 */
	private void USER(String username) {
		CSVReader csvReader = new CSVReader("TP1/src/data/users.csv");
		username = username.split(" ")[1];
		if(csvReader.checkUserName(username)) {
			printMsg("331 User name ok, need password");
			this.username = username;
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
		CSVReader csvReader = new CSVReader("TP1/src/data/users.csv");
		password = password.split(" ")[1];
		if(currentUserStatus == userStatus.ENTEREDUSERNAME && csvReader.checkPassword(this.username, password)) {
			currentUserStatus = userStatus.LOGGEDIN;
			printMsg("230 User logged in");
		}else if (currentUserStatus == userStatus.LOGGEDIN) {
			printMsg("530 User already logged in");
		}else {
			printMsg("530 Not logged in");
		}
	}
	
	private void QUIT() {
		printMsg("221 Closing connection");
		cliThreadRunning = false;
	}
}
