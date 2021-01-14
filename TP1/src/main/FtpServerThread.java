package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


/**
 * Thread to a ftp server. Called when a client connect to the server.
 * @author Brice & Manon
 *
 */
public class FtpServerThread extends Thread {
	private Socket cliSocket;
	private boolean cliThreadRunning;
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
		cliThreadRunning = true;
	}

	/**
	 * Run the exe of a thread.
	 */
	public void run() {
		System.out.println("Accepted Client Address - " + cliSocket.getInetAddress().getHostName());
		BufferedReader in = null;
		PrintWriter out = null;
		try {
			System.out.println("test");
			//Create in and out to communicate with client
			in = new BufferedReader(new InputStreamReader(cliSocket.getInputStream()));
			out = new PrintWriter(new OutputStreamWriter(cliSocket.getOutputStream()));			

			out.println("220 Service ready\r\n");
			while(cliThreadRunning) {
				String cliCommand = in.readLine();
				System.out.println(cliCommand);
			}
			
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
