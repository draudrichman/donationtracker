package login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Profile_Controller implements Initializable {

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
    MenuItem Home, Explore, YourCampaign, DonatedCampaign, MyProfile, UpdateProfile, HelpAndSupport, LogOut, Exit;

    @FXML
    Button NewCampaign;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    //All the methods for Buttons and Menu bar.

    //Method 1: Takes to the Home Page.
    public void goToHome() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("homepage.fxml"));
        root = loader.load();
        scene = new Scene(root);
        stage = (Stage)anchorPane.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    //Method 2: Takes to the Update Profile Page.
    public void goToUpdateProfile() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("update_profile.fxml"));
        root = loader.load();
        scene = new Scene(root);
        stage = (Stage)anchorPane.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    //Method 3: Takes to the Help & Support Page.
    public void goToHelpAndSupport() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("help_and_support.fxml"));
        root = loader.load();
        scene = new Scene(root);
        stage = (Stage)anchorPane.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    //Method 4: Logs out from the user account.
    public void logout() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("login.fxml"));
        root = loader.load();
        scene = new Scene(root);
        stage = (Stage)anchorPane.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    //Method 5: Exits the Program.
    public void exit() throws IOException {

        stage = (Stage) anchorPane.getScene().getWindow();
        stage.close();
    }

    //Method 6: Takes to the New Campaign Page.
    public void goToNewCampaign(ActionEvent actionEvent) throws IOException {

        root = FXMLLoader.load(getClass().getResource("new_campaign.fxml"));
        stage = (Stage) ((Node)(actionEvent.getSource())).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Log In");
        stage.setScene(scene);
        stage.show();
    }

}