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

public class signup_controller {
    @FXML
    Stage stage;

    @FXML
    Scene scene;

    @FXML
    Parent root;

    @FXML
    AnchorPane anchorPane;

    @FXML
    TextField Username;

    @FXML
    TextField Name;

    @FXML
    PasswordField Password;

    @FXML
    PasswordField ConfirmPassword;

    @FXML
    private Button loginButton;

    @FXML
    private Button signupButton;

    String username, name, password, confirmPassword;

    @FXML
    public void backToLoginPage(ActionEvent actionEvent) throws IOException {

        root = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage = (Stage) ((Node)(actionEvent.getSource())).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Log In");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void signUp(ActionEvent actionEvent) throws IOException {
        username = Username.getText();
        name = Name.getText();
        password = Password.getText();
        confirmPassword = ConfirmPassword.getText();

        if (password.equals(confirmPassword)) {
            try {
                // Establish a connection to the database
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/donation_tracker", "root", "112358abc");

                // Prepare a SQL statement to insert the signup information into the database
                String sql = "INSERT INTO userdetails (username, name, password) VALUES (?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, username);
                stmt.setString(2, name);
                stmt.setString(3, password);

                // Execute the SQL statement
                int rowsAffected = stmt.executeUpdate();

                // Close the database connection
                conn.close();

                // Display a success message to the user
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Signup Successful");
                alert.setContentText("Your account has been created.");
                alert.showAndWait();

            } catch (SQLException e) {
                // Display an error message to the user if there was a problem with the database
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Signup Error");
                alert.setContentText("There was a problem creating your account. Please try again later.");
                alert.showAndWait();

                e.printStackTrace();
            }
        } else {
            // Display an error message to the user
        }
    }

}
