package login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class Update_Profile_Controller implements Initializable {

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
    TextField Name, Email, Phone, Address, OrgName, AboutMe, New_Password;

    @FXML
    Button Update;

    //Variables to contain user information.
    String name, email, phone, address, orgName, aboutMe, new_password;

    private int userID = SessionManager.getCurrentUser();

    //All the methods for Buttons and Menu bar.

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    //All the methods for Buttons and Menu bar.

    //Method 1: Updates the user profile.
    public void updateProfile(ActionEvent actionEvent) throws IOException {

        //Extracting text from the Text fields.
        name = Name.getText();
        email = Email.getText();
        phone = Phone.getText();
        address = Address.getText();
        orgName = OrgName.getText();
        aboutMe = AboutMe.getText();
        new_password = New_Password.getText();

        //Variables for the connection to a database.
        Connection connection = null;
        PreparedStatement psInsertValue = null;

        String url = Constants.DATABASE_URL;
        String user = Constants.DATABASE_USERNAME;
        String pass = Constants.DATABASE_PASSWORD;

        if (name.equals("") || email.equals("") || phone.equals("") || address.equals("") || orgName.equals("") || aboutMe.equals("") || new_password.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Text Field Empty!");
            alert.setContentText("Please fill up the text fields!");
            alert.show();
        }
        else {

            try {

                //Establishing the database connection.
                connection = DriverManager.getConnection(url, user, pass);

                psInsertValue = connection.prepareStatement("UPDATE userdetails SET fullName = ?, email = ?, phone = ?, address = ?, orgName = ?, aboutMe = ?, password = ? WHERE userID = ?");
                psInsertValue.setString(1, name);
                psInsertValue.setString(2, email);
                psInsertValue.setString(3, phone);
                psInsertValue.setString(4, address);
                psInsertValue.setString(5, orgName);
                psInsertValue.setString(6, aboutMe);
                psInsertValue.setString(7, new_password);
                psInsertValue.setString(8, String.valueOf(userID));
                psInsertValue.executeUpdate();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Update Successful!");
                alert.setContentText("Your account has been updated successfully!");
                alert.show();

                //Proceeds to the homepage scene.
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("homepage.fxml")));
                stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
                scene = new Scene(root);
                stage.setTitle("Homepage");
                stage.setScene(scene);
                stage.show();
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }
            finally {

                //Closing all the connections to the database.
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

    //Method 2: Takes to the Profile Page.
    public void goToProfile() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("profile.fxml"));
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

}
