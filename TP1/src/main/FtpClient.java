package main;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.HashMap;

public class FtpClient {
    private Socket socket;
    private HashMap<String, String> users;

    public void connect(String server, int port) {
//        try{
//            socket.connect();
//        }catch (IOException e){
//            e.printStackTrace();
//        }
    }

    public void login(String user, String pwd) {
    }

    public void closeConnection() throws IOException {
        try{
            socket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
