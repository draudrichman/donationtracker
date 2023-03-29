package login;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Donation_Confirmation_Controller implements Initializable {

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
    MenuItem LogOut, HelpAndSupport;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    //All the methods for Buttons and Menu bar.

    //Method 1: Takes to the Help & Support Page.
    public void goToHelpAndSupport() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("help_and_support.fxml"));
        root = loader.load();
        scene = new Scene(root);
        stage = (Stage)anchorPane.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    //Method 2: Logs out from the user account.
    public void logout() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("login.fxml"));
        root = loader.load();
        scene = new Scene(root);
        stage = (Stage)anchorPane.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
