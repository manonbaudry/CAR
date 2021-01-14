package main;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;


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
	            controlOutWriter.write("220 service ready\r\n200 OPTS command UTF8 ON ok.\r\n".getBytes());
	            controlOutWriter.flush();
	            
	            
//	            // Get new command from client
	            while (true)
	            {
	            	String t = controlIn.readLine();
		            System.out.println(t);
	            }
			
			
			
//			//Create in and out to communicate with client
//			in = new BufferedReader(new InputStreamReader(cliSocket.getInputStream()));
//			out = cliSocket.getOutputStream();			
//			out.write("220 service ready\r\n".getBytes());
//			out.flush();
//			while(cliThreadRunning) {
//				String cliCommand = in.readLine();
//				System.out.println(cliCommand + " test");
//			}
//			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
