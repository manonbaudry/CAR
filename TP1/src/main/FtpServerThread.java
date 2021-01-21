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

		System.out.println("Accepted Client Address - " + cliSocket.getInetAddress().getHostName());

		try {
			// Input from client
			controlIn = new BufferedReader(new InputStreamReader(cliSocket.getInputStream()));

			// Output to client, automatically flushed after each print
			controlOutWriter = new DataOutputStream(cliSocket.getOutputStream());

			// Greeting
			controlOutWriter.writeBytes("220 service ready\r\n");
			controlOutWriter.flush();


			// Authenticates the user
			String userName = controlIn.readLine();
			System.out.println(userName);
			if(!userName.equals("USER toto"))
				throw new Exception("Wrong username");

			controlOutWriter.writeBytes("331 User name ok, need password\r\n");
			String pwd = controlIn.readLine();
			System.out.println(pwd);

			if(!pwd.equals("PASS toto"))
				throw new Exception("Wrong password");

			controlOutWriter.writeBytes("230 User logged in\r\n");

		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
