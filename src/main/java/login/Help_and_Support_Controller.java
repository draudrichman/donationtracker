package login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.EventObject;
import java.util.Objects;
import java.util.ResourceBundle;

public class Help_and_Support_Controller implements Initializable {

    //Fx components for the fx:id.
    @FXML
    Stage stage;

    @FXML
    Scene scene;

    @FXML
    Parent root;

    @FXML
    BorderPane borderPane;

    String url = Constants.DATABASE_URL;
    String username = Constants.DATABASE_USERNAME;
    String password = Constants.DATABASE_PASSWORD;

    @FXML
    MenuItem Home, Explore, YourCampaign, DonatedCampaign, MyProfile, UpdateProfile, HelpAndSupport, LogOut, Exit;

    @FXML
    Menu profile;

    @FXML
    Button helpDesk;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            gettingUsername();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Help_and_Support_Controller() throws IOException {
        System.out.println(SessionManager.getCurrentUser());
    }

    //All the methods for Buttons and Menu bar.

    //Method 1: Takes to the Profile Page.
    public void goToProfile() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("profile.fxml"));
        root = loader.load();
        scene = new Scene(root);
        stage = (Stage)borderPane.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void goToHomepage() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("homepage.fxml"));
        root = loader.load();
        scene = new Scene(root);
        stage = (Stage)borderPane.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    //Method 2: Takes to the Update Profile Page.
    public void goToUpdateProfile() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("update_profile.fxml"));
        root = loader.load();
        scene = new Scene(root);
        stage = (Stage)borderPane.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    //Method 3: Takes to the Help & Support Page.
    public void goToHelpAndSupport() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("help_and_support.fxml"));
        root = loader.load();
        scene = new Scene(root);
        stage = (Stage)borderPane.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void goTomycampaign() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("mycampaigns.fxml"));
        root = loader.load();
        scene = new Scene(root);
        stage = (Stage)borderPane.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void goTomydonatedcampaigns() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("mydonatedcampaigns.fxml"));
        root = loader.load();
        scene = new Scene(root);
        stage = (Stage)borderPane.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    //Method 4: Logs out from the user account.
    public void logout() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("login.fxml"));
        root = loader.load();
        scene = new Scene(root);
        stage = (Stage)borderPane.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void explore() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("discover_campaigns.fxml"));
        root = loader.load();
        scene = new Scene(root);
        stage = (Stage)borderPane.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void goToNewCampaign(ActionEvent actionEvent) throws IOException {

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("new_campaign.fxml")));
        stage = (Stage) ((Node)(actionEvent.getSource())).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Log In");
        stage.setScene(scene);
        stage.show();
    }

    public void gettingUsername() throws SQLException {
        String query = "SELECT fullName FROM userdetails WHERE userID = ?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, SessionManager.getCurrentUser());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String username = resultSet.getString("fullName");
                System.out.println("Username: " + username);
                profile.setText("Hello, " + username);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void contactAdmin(ActionEvent actionEvent) throws IOException {

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("user_msg_and_inbox.fxml")));
        stage = (Stage) ((Node)(actionEvent.getSource())).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Client Message");
        stage.setScene(scene);
        stage.show();
    }
}
