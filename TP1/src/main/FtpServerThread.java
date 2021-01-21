package main;

import utils.CSVReader;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.io.InputStreamReader;
import java.io.PrintWriter;


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
	
	//Data properties 
	private ServerSocket dataSocket;
	private Socket dataConnection;
	private int dataPort;
	private DataOutputStream dataOutWriter;
	
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
	public FtpServerThread(Socket client, int dataPort) {
		super();
		this.cliSocket = client;
		this.dataPort = dataPort;
		this.ROOT = System.getProperty("user.dir");
		this.currentDIR = ROOT + "/CAR";
	}
	
	/**
	 * Print a message via the control connection
	 * @param msg - the message to send 
	 */
	private void printMsg(String msg) {
		try {
			controlOutWriter.writeBytes(msg+"\r\n");
			controlOutWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Print a message via the data connection
	 * @param msg - the message to send
	 */
	private void printDataMsg(String msg) {
		try {
			if (dataConnection == null || dataConnection.isClosed())
	        {
	            printMsg("425 No data connection was established");
	        }else{
	        	dataOutWriter.writeBytes(msg+"\r\n");
	        	dataOutWriter.flush();
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Open a data connection socket 
	 * @param port - which port to listen for incoming connection
	 */
	private void openDataConnection(int port) {
		try {
			dataSocket = new ServerSocket(port);
			dataConnection = dataSocket.accept();
			dataOutWriter = new DataOutputStream(dataConnection.getOutputStream());
			System.out.println("data connecté"); //TODO remove
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/**
	 * Close the opened data connection
	 */
	private void closeDataConnection() {
		try {
			dataOutWriter.close();
			dataConnection.close();
			dataSocket.close();
			System.out.println("Data connection closed");
		} catch (Exception e) {
			System.out.println("Error, could not close data connection");
			System.out.println(e);
		}
		
		dataOutWriter = null;
		dataConnection = null;
		dataSocket = null;
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
			dir(args[0]);			
			break;
		case "get":
			get(args[0]);
			break;
		case "put":
			put(args[0]);
			break;
		case "cd":
			cd(args[0]);
			break;
		case "quit":
			quit();
			break;

		default:
			printMsg("501 Unknown command");
		}
	}
	
	/**
	 * CD Method. Change working directory
	 * @param dir - the dir to move to.
	 */
	private void cd(String dir) {
		String filename = currentDIR;
		
		if(dir.equals("..")) {
			int ind = filename.lastIndexOf(FILESEPARATOR);
			if (ind > 0) {
				filename = filename.substring(0, ind);
			}
		}else if(dir!=null && !dir.equals(".")) {
			filename = filename + FILESEPARATOR + dir;
		}
		
		File f = new File(filename);
		if(f.exists() && f.isDirectory() && (filename.length() >= ROOT.length())) {
			currentDIR = filename;
			printMsg("250 Requested file action okay, completed.");
		}else {
			printMsg("550 Requested action not taken. File unavailable");
		}
	}
	
	/**
	 * GET method. Get a file from FTP to client
	 * @param file - the file to get
	 */
	private void get(String file) {
		
		if (dataConnection == null || dataConnection.isClosed()){
            openDataConnection(dataPort);
		}
			
		File f = new File(currentDIR + FILESEPARATOR + file);
		if(!f.exists()) {
			printMsg("550 File does not exist");
		}else{
			
			 printMsg("150 Opening ASCII mode data connection for requested file " + f.getName());
		     BufferedReader in = null;
		     DataOutputStream out = null;
		     
		    try{
		    	 in = new BufferedReader(new FileReader(f));
		    	 out = new DataOutputStream(dataConnection.getOutputStream());
			} catch (Exception e) {
				System.out.println("Error create entry stream");
				System.out.println(e);
			}
		     
		    String curr;
		    try {
				while((curr = in.readLine()) !=null) {
					out.writeBytes(curr);
				}
			} catch (Exception e) {
				System.out.println("Error read entry stream");
				System.out.println(e);
			}
		    
		    try {
		    	out.close();
				in.close();
			} catch (Exception e) {
				System.out.println("Error close entry stream");
				System.out.println(e);
			}
		    printMsg("226 Closing data connection. Requested file action successful.");
		}
		closeDataConnection();
	}	
	
	/**
	 * PUT method. Save the given file to the FTP server 
	 * @param file - the file to save on the server
	 */
	private void put(String file) {
		
		if (dataConnection == null || dataConnection.isClosed()){
            openDataConnection(dataPort);
		}
				
		if (file == null) {
			printMsg("501 No filename given");
		}else {
			File f = new File(currentDIR + FILESEPARATOR + file);
			if(f.exists()) {
				printMsg("550 File already exists");
			}else{
					
				 printMsg("150 Opening ASCII mode data connection for requested file " + f.getName());
			     BufferedReader in = null;
			     DataOutputStream out = null;
			     
			     try {
			    	 in = new BufferedReader(new InputStreamReader(dataConnection.getInputStream()));
			    	 out = new DataOutputStream(new FileOutputStream(file));
				} catch (Exception e) {
					System.out.println("Error create entry stream");
					System.out.println(e);
				}
			     
			    String curr;
			    
			    try {
					while((curr = in.readLine()) !=null) {
						out.writeBytes(curr);
					}
				} catch (Exception e) {
					System.out.println("Error read entry stream");
					System.out.println(e);
				}
			    
			    try {
			    	out.close();
					in.close();
				} catch (Exception e) {
					System.out.println("Error close entry stream");
					System.out.println(e);
				}
			    printMsg("226 Closing data connection. Requested file action successful.");
			}
		}
		closeDataConnection();
	}
	
	/**
	 * DIR method. Get a list of the given directory
	 * @param path - path to the directory to list
	 */
	private void dir(String path) {
		
		if (dataConnection == null || dataConnection.isClosed()){
	            openDataConnection(dataPort);
	    }
		
		
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
				printDataMsg(e);
			}
        	printMsg("226 Transfer complete");
        }
        
        closeDataConnection();
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

		if (userStatus == UserStatus.LoggedIn) {
			printMsg("530 User already logged in");
		} else if(csvReader.checkUserName(username)) {
			printMsg("331 User name ok, need password");
			this.username = username;
			userStatus = UserStatus.EnteredUserName;
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

		if (userStatus == UserStatus.LoggedIn) {
			printMsg("530 User already logged in");
		}else if(userStatus == UserStatus.EnteredUserName && csvReader.checkPassword(this.username, password)) {
			userStatus = UserStatus.LoggedIn;
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
