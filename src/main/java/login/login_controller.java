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
import java.sql.*;

public class login_controller {

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
    TextField UserName;

    @FXML
    PasswordField Password;

    @FXML
    Button LOGIN, SIGNUP, Reset_Password, Exit;

    //Variables to contain user information.
    String username, password;


    //Methods For the Buttons.

    //Method 1: Logs into the user account.
    public void logIn(ActionEvent actionEvent) throws IOException {

        //Extracting text from the text fields.
        username = UserName.getText();
        password = Password.getText();

        //Variables for the connection to a database.
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Establish a connection to the database
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/donation_tracker", "root", "112358abc");

            // Prepare the SQL query
            String sql = "SELECT * FROM userdetails WHERE username=? AND password=?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);

            // Execute the query and retrieve the result set
            resultSet = statement.executeQuery();

            // Check if the result set has any rows
            if (resultSet.next()) {
                System.out.println("Login successful!");
            } else {
                System.out.println("Invalid username or password.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the result set, statement, and connection
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //Method 2: Takes to the SignUp page.
    public void signUp(ActionEvent actionEvent) throws IOException {

        root = FXMLLoader.load(getClass().getResource("signup.fxml"));
        stage = (Stage) ((Node)(actionEvent.getSource())).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Sign Up");
        stage.setScene(scene);
        stage.show();
    }

    //Method 3: Takes to the Reset_Password page.
    public void resetPassword(ActionEvent actionEvent) throws IOException {

        root = FXMLLoader.load(getClass().getResource("resetpassword.fxml"));
        stage = (Stage) ((Node)(actionEvent.getSource())).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Reset Password");
        stage.setScene(scene);
        stage.show();
    }

    //Method 4: Exits the program.
    public void exit(ActionEvent actionEvent) throws IOException {

        stage = (Stage) anchorPane.getScene().getWindow();
        stage.close();
    }
}
