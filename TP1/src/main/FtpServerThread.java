package main;

import java.net.Socket;

public class FtpServerThread extends Thread {
	private Socket cliSocket;
	
	public FtpServerThread() {
		super();
	}
	
	public FtpServerThread(Socket client) {
		cliSocket = client;
	}

	public void run() {
		System.out.println("wesh");
	}
}
