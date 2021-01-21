package main;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * FTP Server class. Launch a server instance on port 1010 and listen for client to connect.
 * @author Brice & Manon
 * 
 */
public class FtpServer {
    private int serverPort = 1010;
    private ServerSocket serverSocket;
    boolean running = false;
    
    /**
     * Constructor.
     * Create and run a multithreaded ftp server on port 1010 by default.
     * Listen for client to connect and wrap them to a FTP server thread.
     */
    public FtpServer() {
    	try {
    		serverSocket = new ServerSocket(serverPort);
    	}catch (Exception e) {
			System.out.println("Error launch server :" + e);
			System.exit(-1);
		}
    	
    	running = true;
    	System.out.println("FTP server running. Listening on port " + serverPort);
    	
    	int nbThreads = 0 ;
    	
    	while(running) {
    		try {
    			Socket client = serverSocket.accept();
    			int dataPort = serverPort + nbThreads + 1 ;
    			FtpServerThread cliThread = new FtpServerThread(client, dataPort);
    			cliThread.start();
    			nbThreads++;
    			System.out.println("New client listening");
    		}catch (Exception e) {
    			System.out.println("Error listening :" + e);
			}
    	}
    	
    	try {
			serverSocket.close();
			System.out.println("Server stopped.");
    	} catch (Exception e) {
			System.out.println("Error stop server :" + e);
			System.exit(-1);
		}
    }
}
