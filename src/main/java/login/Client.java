package login;

import java.util.Scanner;

public class Client {

    public Client(String serverAddress, int serverPort) {
        try {
            System.out.print("Enter name of the client: ");
            Scanner scanner = new Scanner(System.in);
            String clientName = scanner.nextLine();
            NetworkUtil networkUtil = new NetworkUtil(serverAddress, serverPort);

            networkUtil.write(clientName);

            new ReadThread(networkUtil);
            new WriteThreadClient(networkUtil, clientName);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
