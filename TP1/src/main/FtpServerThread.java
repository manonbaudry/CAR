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


/**
 * Thread to a ftp server. Called when a client connect to the server.
 * @author Brice & Manon
 *
 */
public class FtpServerThread extends Thread {

	//Thread properties
	private Socket cliSocket;

	//Control loop, changed by QUIT command to stop the thread
	private boolean cliThreadRunning = true;

	//User properties
	private DataOutputStream controlOutWriter;
	private BufferedReader controlIn;
	private UserStatus userStatus = UserStatus.NotLoggedIn;
	private String username = "";

	private final String CSV_PATH = "TP1/src/data/users.csv";

	//Data properties 
	private ServerSocket dataSocket;
	private Socket dataConnection;
	private int dataPort;
	private DataOutputStream dataOutWriter;

	//Paths properties
	private String currentDIR;
	private String ROOT;
	private final String FILE_SEPARATOR = "/";

	/**
	 * Initiate the client to our class
	 * @param client - the client to map
	 */
	public FtpServerThread(Socket client, int dataPort) {
		super();
		this.cliSocket = client;
		this.dataPort = dataPort;
		this.ROOT = System.getProperty("user.dir");
		this.currentDIR = ROOT;
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
			System.out.println("error while printing message");;
		}
	}

	/**
	 * Print a message via the data connection
	 * @param msg - the message to send
	 */
	private void printDataMsg(String msg) {
		try {
			dataOutWriter.writeBytes(msg);
			dataOutWriter.flush();
		} catch (IOException e) {
			System.out.println("error while printing data message");;
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
			printMsg(Messages.MSG_220);
			String command;

			user(controlIn.readLine().split(" ")[1]);
			pass(controlIn.readLine().split(" ")[1]);

			if(System.getProperty("os.name").equals("Mac OS X")){
				String commandLine = controlIn.readLine();
				String[] req = commandLine.split("\\|");
				int port = Integer.parseInt(req[req.length-1]);
				String addr = req[req.length-2];
				printMsg(Messages.MSG_227.replace("port", req[req.length-1]));
				this.dataConnection = new Socket(addr, port);
				this.dataPort = port;
				dataOutWriter = new DataOutputStream(dataConnection.getOutputStream());
			}
			while(cliThreadRunning) {
				command = controlIn.readLine();
				System.out.println(command);
				interpreteCommand(command);
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
		String args = null;
		if(entireLine.length > 0)
			args =  entireLine[1];

		switch (command.toLowerCase()) {
			case "user":
				user(args);
				break;
			case "pass":
				pass(args);
				break;
			case "list":
				dir(args);
				break;
			case "get":
				get(args);
				break;
			case "put":
				put(args);
				break;
			case "cd":
				cd(args);
				break;
			case "quit":
				quit();
				break;

			default:
				printMsg(Messages.MSG_501);
		}
	}

	/**
	 * CD Method. Change working directory
	 * @param dir - the dir to move to.
	 */
	private void cd(String dir) {
		String filename = currentDIR;

		if(dir.equals("..")) {
			int ind = filename.lastIndexOf(FILE_SEPARATOR);
			if (ind > 0) {
				filename = filename.substring(0, ind);
			}
		}else if(dir!=null && !dir.equals(".")) {
			filename = filename + FILE_SEPARATOR + dir;
		}

		File f = new File(filename);
		if(f.exists() && f.isDirectory() && (filename.length() >= ROOT.length())) {
			currentDIR = filename;
			printMsg(Messages.MSG_250);
		}else {
			printMsg(Messages.MSG_550_UNAVAILABLE);
		}
	}

	/**
	 * GET method. Get a file from FTP to client
	 * @param file - the file to get
	 */
	private void get(String file) {
		try {
			if (dataConnection == null || dataConnection.isClosed()) {
				openDataConnection(dataPort);
			}
			File f = new File(currentDIR + FILE_SEPARATOR + file);
			if (!f.exists()) {
				printMsg(Messages.MSG_550_NOT);
			} else {
				printMsg(Messages.MSG_150 + f.getName());
				BufferedReader in = null;
				DataOutputStream out = null;

				in = new BufferedReader(new FileReader(f));
				out = new DataOutputStream(dataConnection.getOutputStream());

				String curr;

				while ((curr = in.readLine()) != null) {
					out.writeBytes(curr);
				}
				out.close();
				in.close();
				printMsg(Messages.MSG_226_CLOSE);
			}
		}catch(IOException e){
			e.printStackTrace();
			System.out.println("error GET");
		}
		closeDataConnection();
	}

	/**
	 * PUT method. Save the given file to the FTP server 
	 * @param file - the file to save on the server
	 */
	private void put(String file) {
		try{
			if (dataConnection == null || dataConnection.isClosed()){
				openDataConnection(dataPort);
			}
			if (file == null) {
				printMsg(Messages.MSG_501_NO_FILENAME);
			}else {
				File f = new File(currentDIR + FILE_SEPARATOR + file);
				if(f.exists()) {
					printMsg(Messages.MSG_550_ALREADY );
				}else{
					printMsg(Messages.MSG_150 + f.getName());
					BufferedReader in = new BufferedReader(new InputStreamReader(dataConnection.getInputStream()));
					DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
					String curr;
					while((curr = in.readLine()) !=null) {
						out.writeBytes(curr);
					}
					out.close();
					in.close();
					printMsg(Messages.MSG_226_CLOSE);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error PUT");
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

		if (path != null) {
			filename = filename + FILE_SEPARATOR + path;
		}
		String content[] = getContent(filename);
		if(content == null) {
			printMsg(Messages.MSG_550_NOT);
		}else {
			printMsg(Messages.MSG_125);
			for (String e : content) {
				printDataMsg(e);
			}
			closeDataConnection();
			printMsg(Messages.MSG_226_COMPLETE);
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
		CSVReader csvReader = new CSVReader(CSV_PATH);
		if (userStatus == UserStatus.LoggedIn) {
			printMsg(Messages.MSG_530_ALREADY);
		} else if(csvReader.checkUserName(username)) {
			printMsg(Messages.MSG_331);
			this.username = username;
			userStatus = UserStatus.EnteredUserName;
		}else {
			printMsg(Messages.MSG_530_NOT);
		}
	}

	/**
	 * PASS method. Check if the given password correspond to the past given user in CSV file
	 * @param password - password
	 */
	private void pass(String password) {
		CSVReader csvReader = new CSVReader(CSV_PATH);
		if (userStatus == UserStatus.LoggedIn) {
			printMsg(Messages.MSG_530_ALREADY);
		}else if(userStatus == UserStatus.EnteredUserName && csvReader.checkPassword(this.username, password)) {
			userStatus = UserStatus.LoggedIn;
			printMsg(Messages.MSG_230);
		}else {
			printMsg(Messages.MSG_530_NOT);
		}
	}

	private void quit() {
		printMsg(Messages.MSG_221);
		cliThreadRunning = false;
	}
}
