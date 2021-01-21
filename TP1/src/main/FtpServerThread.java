package main;

import utils.CSVReader;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
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

	//Thread properties
	private Socket cliSocket;
	private boolean cliThreadRunning = true;

	//User properties
	private DataOutputStream controlOutWriter;
	private BufferedReader controlIn;
	private UserStatus userStatus = UserStatus.NotLoggedIn;
	private String username = "";
	
	//Paths properties
	private String currentDIR;
	private String ROOT;
	private final String FILESEPARATOR = "/";

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
		ROOT = System.getProperty("user.dir");
		currentDIR = ROOT + "/CAR";
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

			user(controlIn.readLine());
			pass(controlIn.readLine());

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
		case "user":
			user(args[0]);
			break;
		case "pass":
			pass(args[0]);
			break;
		case "dir":
			if (args[0].isEmpty()) {
				dir("");
			}else {
				dir(args[0]);
			}			
			break;
		case "get":
			break;
		case "put":
			break;
		case "cd":
			break;
		case "quit":
			quit();
			break;

		default:
			printMsg("501 Unknown command");
		}
	}
	
	/**
	 * dir method. Get a list of the given directory
	 * @param path - path to the directory to list
	 */
	private void dir(String path) {
		String filename = currentDIR;
        if (path != null)
        {
            filename = filename + FILESEPARATOR + path;
        }

        String content[] = getContent(filename);

        if(content == null) {
        	printMsg("550 File does not exist");
        }else {
        	printMsg("125 Opening ASCII mode data connection for file list");
        	for (String e : content) {
				printMsg(e);
			}
        	printMsg("226 Transfer complete");
        }

	}

	/**
	 * Local Method. Used by (DIR) to get the content of the path
	 * @param filename -path to get the dir
	 * @return the list of given DIR
	 */
	private String[] getContent (String filename){
		File file = new File(filename);

		if(file.exists() && file.isFile()) {
        	return file.list();
        }else if (file.exists() && file.isDirectory()) {
        	String[] fileInfos = new String[1];
        	fileInfos[0] = file.getName();
        	return fileInfos;
        }else {
        	return null;
        }
	}
	/**
	 * USER method. Log the user if included in csv file
	 * @param username - username
	 */
	private void user(String username) {
		CSVReader csvReader = new CSVReader("TP1/src/data/users.csv");
		username = username.split(" ")[1];

		if (userStatus == userStatus.LoggedIn) {
			printMsg("530 User already logged in");
		} else if(csvReader.checkUserName(username)) {
			printMsg("331 User name ok, need password");
			this.username = username;
			userStatus = userStatus.EnteredUserName;
		}else {
			printMsg("530 Not logged in");
		}
	}
	
	/**
	 * PASS method. Check if the given password correspond to the past given user in CSV file
	 * @param password - password
	 */
	private void pass(String password) {
		CSVReader csvReader = new CSVReader("TP1/src/data/users.csv");
		password = password.split(" ")[1];

		if (userStatus == userStatus.LoggedIn) {
			printMsg("530 User already logged in");
		}else if(userStatus == userStatus.EnteredUserName && csvReader.checkPassword(this.username, password)) {
			userStatus = userStatus.LoggedIn;
			printMsg("230 User logged in");
		}else {
			printMsg("530 Not logged in");
		}
	}
	
	private void quit() {
		printMsg("221 Closing connection");
		cliThreadRunning = false;
	}
}
