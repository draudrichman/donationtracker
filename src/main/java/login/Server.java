package login;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Server {

    private ServerSocket serverSocket;
    public HashMap<String, NetworkUtil> clientMap; // HashMap of client's name and socket information

    Server() {
        clientMap = new HashMap<>();
        try {

            serverSocket = new ServerSocket(33333);
            new WriteThreadServer(clientMap, "Admin");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                serve(clientSocket);
            }
        } catch (Exception e) {
            System.out.println("Server starts:" + e);
        }
    }

    public void serve(Socket clientSocket) throws IOException, ClassNotFoundException {
        NetworkUtil networkUtil = new NetworkUtil(clientSocket);

        String clientName = (String) networkUtil.read();

        clientMap.put(clientName, networkUtil);
        new ReadThread(networkUtil);
    }

}
