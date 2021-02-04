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
    boolean running;
    
    /**
     * Constructor.
     * Create and run a multithreaded ftp server on port 1010 by default.
     * Listen for client to connect and wrap them to a FTP server thread.
     */
    public FtpServer() {
		try {
			serverSocket = new ServerSocket(serverPort);

			running = true;
			System.out.println("FTP server running. Listening on port " + serverPort);

			int nbThreads = 0 ;

			while(running) {
				Socket client = serverSocket.accept();
				int dataPort = serverPort + nbThreads + 1 ;
				FtpServerThread cliThread = new FtpServerThread(client, dataPort);
				cliThread.start();
				nbThreads++;
				System.out.println("New client listening");
			}
			serverSocket.close();
			System.out.println("Server stopped.");
		} catch (Exception e) {
			System.out.println("Error server :" + e);
			System.exit(-1);
		}
	}
}
