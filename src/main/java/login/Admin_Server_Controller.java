package login;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class Admin_Server_Controller implements Initializable {

    //Fx components for the fx:id.
    @FXML
    Stage stage;

    @FXML
    Scene scene;

    @FXML
    Parent root;

    @FXML
    AnchorPane anchorPane;

    @FXML
    ScrollPane scrollPane;

    @FXML
    VBox vBox_message;

    @FXML
    TextField messageField;

    @FXML
    Button send;

    @FXML
    Label usernameLabel;

    private int userID;

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private Server server;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            server = new Server(new ServerSocket(33333));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String url1 = Constants.DATABASE_URL;
        String user = Constants.DATABASE_USERNAME;
        String pass = Constants.DATABASE_PASSWORD;

        try {

            usernameLabel.setText(server.getUsername());

            //Establishing the database connection.
            connection = DriverManager.getConnection(url1, user, pass);

            //Storing the newUser info of given username in the resultName variable.
            preparedStatement = connection.prepareStatement("SELECT userID FROM userdetails WHERE userName = ?");
            preparedStatement.setString(1, server.getUsername());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                userID = resultSet.getInt("userID");
            }

            preparedStatement = connection.prepareStatement("SELECT * FROM msg_details WHERE user_id = ? ORDER BY createdAt ASC");
            preparedStatement.setInt(1, userID);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){

                String message = resultSet.getString("message");
                int messageFrom = resultSet.getInt("userORadmin");

                if(messageFrom == 0){

                    HBox hBox = new HBox();
                    hBox.setAlignment(Pos.CENTER_RIGHT);
                    hBox.setPadding(new Insets(5, 5, 5, 10));

                    Text text = new Text(message);
                    TextFlow textFlow = new TextFlow(text);

                    textFlow.setStyle("-fx-color: rgb(239, 242, 255);" + "-fx-background-color: rgb(10, 68, 194);" + "-fx-background-radius: 20px;");
                    textFlow.setPadding(new Insets(5, 10 , 5, 10));
                    text.setFill(Color.color(0.934, 0.945, 0.996));

                    hBox.getChildren().add(textFlow);
                    vBox_message.getChildren().add(hBox);
                }
                else {

                    HBox hBox = new HBox();
                    hBox.setAlignment(Pos.CENTER_LEFT);
                    hBox.setPadding(new Insets(5, 5, 5, 10));

                    Text text = new Text(message);
                    TextFlow textFlow = new TextFlow(text);

                    textFlow.setStyle("-fx-background-color: rgb(233, 233, 235);" + "-fx-background-radius: 20px;");
                    textFlow.setPadding(new Insets(5, 10 , 5, 10));

                    hBox.getChildren().add(textFlow);

                    vBox_message.getChildren().add(hBox);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {

            //Closing all the connections to the database.
            if (resultSet != null) {
                try {
                    resultSet.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
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

        vBox_message.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                scrollPane.setVvalue((Double) oldValue);
            }
        });

        server.receiveMsgFromClient(vBox_message);

        send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                String messageToSend = messageField.getText();

                Connection connection = null;
                PreparedStatement preparedStatement = null;

                String url1 = Constants.DATABASE_URL;
                String user = Constants.DATABASE_USERNAME;
                String pass = Constants.DATABASE_PASSWORD;

                try{
                    //Establishing the database connection.
                    connection = DriverManager.getConnection(url1, user, pass);

                    preparedStatement = connection.prepareStatement("INSERT INTO msg_details (message, userORadmin, user_id) VALUES(?, ?, ?)");
                    preparedStatement.setString(1, messageToSend);
                    preparedStatement.setInt(2, 0);
                    preparedStatement.setInt(3, userID);
                    preparedStatement.executeUpdate();

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

                if(!messageToSend.isEmpty()){

                    HBox hBox = new HBox();
                    hBox.setAlignment(Pos.CENTER_RIGHT);
                    hBox.setPadding(new Insets(5, 5, 5, 10));

                    Text text = new Text(messageToSend);
                    TextFlow textFlow = new TextFlow(text);

                    textFlow.setStyle("-fx-color: rgb(239, 242, 255);" + "-fx-background-color: rgb(15, 125, 242);" + "-fx-background-radius: 20px;");
                    textFlow.setPadding(new Insets(5, 10 , 5, 10));
                    text.setFill(Color.color(0.934, 0.945, 0.996));

                    hBox.getChildren().add(textFlow);
                    vBox_message.getChildren().add(hBox);

                    server.sendMsgToClient(messageToSend);
                    messageField.clear();
                }
            }
        });

    }

    public static void addLabel(String messageFromClient, VBox vBox){

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5, 5, 5, 10));

        Text text = new Text(messageFromClient);
        TextFlow textFlow = new TextFlow(text);

        textFlow.setStyle("-fx-background-color: rgb(233, 233, 235);" + "-fx-background-radius: 20px;");
        textFlow.setPadding(new Insets(5, 10 , 5, 10));

        hBox.getChildren().add(textFlow);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vBox.getChildren().add(hBox);
            }
        });
    }

}
