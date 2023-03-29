package login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Reset_Password_Controller {

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
    TextField UserName, Reset_Code;

    @FXML
    PasswordField New_Password;

    @FXML
    Button Login, Confirm;

    //Variables to contain user information.
    String username, reset_code, new_password;

    //Methods For the Buttons.

    //Method 1: Resets the Password.
    public void resetPassword(ActionEvent actionEvent) throws IOException {

        //Extracting text from the text fields.
        username = UserName.getText();
        reset_code = Reset_Code.getText();
        new_password = New_Password.getText();

        //Variables for the connection to a database.
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement psInsertValue = null;
        ResultSet resultSet = null;

        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/donation_tracker";
        String user = "saad";
        String pass = "123@saad";

        if (username.equals("") || reset_code.equals("") || new_password.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Empty text fields!");
            alert.setContentText("Please fill up all the information!");
            alert.show();
        } else {
            try {
                Class.forName(driver);

                //Establishing the database connection.
                connection = DriverManager.getConnection(url, user, pass);

                //Storing the password in the resultPassword variable.
                preparedStatement = connection.prepareStatement("SELECT reset_code FROM signup WHERE username = ?");
                preparedStatement.setString(1, username);
                resultSet = preparedStatement.executeQuery();

                //Checks if the user exist or not. Returns false if the user does not exist.
                if (!resultSet.isBeforeFirst()) {

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("User not Found!");
                    alert.setContentText("This user does not exist! Use another username.");
                    alert.show();
                } else {

                    while (resultSet.next()) {
                        String retrievedResetCode = resultSet.getString("reset_code");

                        if (retrievedResetCode.equals(reset_code)) {

                            psInsertValue = connection.prepareStatement("UPDATE signup SET password = ? WHERE username = ?");
                            psInsertValue.setString(1, new_password);
                            psInsertValue.setString(2, username);
                            psInsertValue.executeUpdate();

                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setHeaderText("Password Changed!");
                            alert.setContentText("Your Password has been changed successfully!");
                            alert.show();
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setHeaderText("Wrong Reset Code!");
                            alert.setContentText("Provided Reset Code for the username is incorrect!");
                            alert.show();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //Closing all the connections to the database.
                if (resultSet != null) {
                    try {
                        resultSet.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (preparedStatement != null) {
                    try {
                        preparedStatement.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (psInsertValue != null) {
                    try {
                        psInsertValue.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    //Method 2: Take backs to the Login Page.
    public void backToLoginPage(ActionEvent actionEvent) throws IOException {

        root = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage = (Stage) ((Node)(actionEvent.getSource())).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Log In");
        stage.setScene(scene);
        stage.show();
    }
}
