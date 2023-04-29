package login;

import javafx.scene.layout.VBox;

import java.io.*;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Client {

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    final public int userID = SessionManager.getCurrentUser();

    private String clientName;

    public Client(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultSet;

            String url1 = Constants.DATABASE_URL;
            String user = Constants.DATABASE_USERNAME;
            String pass = Constants.DATABASE_PASSWORD;

            try{

                //Establishing the database connection.
                connection = DriverManager.getConnection(url1, user, pass);

                preparedStatement = connection.prepareStatement("SELECT userName FROM userdetails WHERE userID = ?");
                preparedStatement.setInt(1, userID);
                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()){
                    this.clientName = resultSet.getString("userName");
                }

            }
            catch (Exception e){
                e.printStackTrace();
            }
            finally {

                //Closing all the connections to the database.
                if (preparedStatement != null) {
                    try {
                        preparedStatement.close();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (connection != null) {
                    try {
                        connection.close();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            bufferedWriter.write(this.clientName);
            bufferedWriter.newLine();
            bufferedWriter.flush();

        }
        catch (IOException e){
            e.printStackTrace();
            closeEveryThing(socket, bufferedReader, bufferedWriter);
        }
    }

    public void receiveMsgFromServer(VBox vBox){

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (socket.isConnected()) {
                    try {
                        String messageFromClient = bufferedReader.readLine();
                        Admin_Server_Controller.addLabel(messageFromClient, vBox);
                    } catch (IOException e) {
                        e.printStackTrace();
                        closeEveryThing(socket, bufferedReader, bufferedWriter);
                        break;
                    }
                }
            }
        }).start();
    }

    public void sendMsgToServer(String messageToServer){
        try {
            bufferedWriter.write(messageToServer);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }
        catch (IOException e){
            e.printStackTrace();
            closeEveryThing(socket, bufferedReader, bufferedWriter);
        }
    }

    public void closeEveryThing(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        try {
            if(bufferedReader != null){
                bufferedReader.close();
            }
            if(bufferedWriter != null){
                bufferedWriter.close();
            }
            if(socket != null){
                socket.close();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
