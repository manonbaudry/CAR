package main;

import java.net.Socket;

/**
 * Thread to a ftp server. Called when a client connect to the server.
 * @author Brice & Manon
 *
 */
public class FtpServerThread extends Thread {
	private Socket cliSocket;
	
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
		System.out.println("wesh");
	}
}
