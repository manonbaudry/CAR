package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;

/***
 * @author Manon Baudry
 */
public class Main {

    public static void main(String[] args) {
        FtpServer ftpServer = new FtpServer();

        try{
            ServerSocket serverSocket = new ServerSocket();

        }catch (IOException e){
            e.printStackTrace();
        }

    }

}
