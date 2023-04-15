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
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
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
    Button NewCampaign, ProfilePicture;;

    @FXML
    Label userIdLabel, resetCodeLabel, usernameLabel, nameLabel, emailLabel, roleLabel, phoneLabel, addressLabel, orgNameLabel, aboutMeLabel;

    @FXML
    ImageView profileView;
    @FXML
    Image profilePicture;

    private final int userID = SessionManager.getCurrentUser();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(userID);
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String url2 = Constants.DATABASE_URL;
        String user = Constants.DATABASE_USERNAME;
        String pass = Constants.DATABASE_PASSWORD;

        try {

            connection = DriverManager.getConnection(url2, user, pass);

            //Storing the password in the resultPassword variable.
            preparedStatement = connection.prepareStatement("SELECT * FROM userdetails WHERE userID = ?");
            preparedStatement.setInt(1, userID);
            resultSet = preparedStatement.executeQuery();

            //Checks if the user exist or not. Returns false if the user does not exist.

            while (resultSet.next()) {
                String username = resultSet.getString("userName");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String role = resultSet.getString("userType");
                String name = resultSet.getString("fullName");
                String orgName = resultSet.getString("orgName");
                String address = resultSet.getString("address");
                int resetCode = resultSet.getInt("resetCode");
                String aboutMe = resultSet.getString("aboutMe");
                String phone = resultSet.getString("phone");
                String image = resultSet.getString("image");

                User user1 = new User(username, name, email, password);
                user1.setUser_id(userID);
                user1.setUser_type(role);
                user1.setOrgName(orgName);
                user1.setAddress(address);
                user1.setReset_code(resetCode);
                user1.setPhone(phone);
                user1.setAboutMe(aboutMe);
                user1.setImage(image);

                userIdLabel.setText(String.valueOf(user1.getUser_id()));
                resetCodeLabel.setText(String.valueOf(user1.getReset_code()));
                usernameLabel.setText(user1.getUsername());
                nameLabel.setText(user1.getName());
                emailLabel.setText(user1.getEmail());
                roleLabel.setText(user1.getUser_type());
                phoneLabel.setText(user1.getPhone());
                addressLabel.setText(user1.getAddress());
                orgNameLabel.setText(user1.getOrgName());
                aboutMeLabel.setText(user1.getAboutMe());

                if(user1.getImage().equals("")){
                    profilePicture = new Image("D:\\Intelli J\\donationtracker-master2\\src\\main\\resources\\login\\imagefiles\\defaultAvatar.png");
                }
                else {
                    profilePicture = new Image("D:\\Intelli J\\donationtracker-master2\\src\\main\\resources\\login\\imagefiles\\defaultAvatar.png");
                }

                profileView.setImage(profilePicture);

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
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
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
