package login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class New_Campaign_Controller implements Initializable {

    //Fx components for the fx:id.
    @FXML
    Stage stage;

    @FXML
    Scene scene;

    @FXML
    Parent root;

    @FXML
    RadioButton other;

    @FXML
    RadioButton Emergencies;
    @FXML
    RadioButton Funeral;
    @FXML
    RadioButton ArtandCulture;
    @FXML
    RadioButton Sports;
    @FXML
    RadioButton Environment;
    @FXML
    RadioButton Hunger;
    @FXML
    RadioButton Community;
    @FXML
    RadioButton Medical;
    @FXML
    RadioButton Disaster;
    @FXML
    RadioButton AnimalWelfare;

    @FXML
    RadioButton Education;

    @FXML
    AnchorPane anchorPane;

    @FXML
    MenuItem Home, Explore, YourCampaign, DonatedCampaign, MyProfile, UpdateProfile, HelpAndSupport, LogOut, Exit;

    @FXML
    TextField campaignTitle;

    @FXML
    TextField goalAmount;

    @FXML
    TextArea campaignDescription;

//    @FXML
//    PasswordField Confirm_Password;

    @FXML
    Button createCampaign;

    //Variables to contain campaign information.
    String campaign_name, description, category, status;

    String goal_amount, current_amount;

    //Methods for the buttons.

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    private int userID;
    public New_Campaign_Controller() throws IOException {
        userID = SessionManager.getCurrentUser();
    }




    public void createCampaign(ActionEvent actionEvent) throws IOException, SQLException {

        //Extracting text from the Text fields.
        campaign_name = campaignTitle.getText();
        goal_amount = goalAmount.getText();
        description = campaignDescription.getText();
        status = "Active";

        ToggleGroup toggleGroup = new ToggleGroup();
        other.setToggleGroup(toggleGroup);
        Emergencies.setToggleGroup(toggleGroup);
        Funeral.setToggleGroup(toggleGroup);
        ArtandCulture.setToggleGroup(toggleGroup);
        Sports.setToggleGroup(toggleGroup);
        Environment.setToggleGroup(toggleGroup);
        Hunger.setToggleGroup(toggleGroup);
        Community.setToggleGroup(toggleGroup);
        Medical.setToggleGroup(toggleGroup);
        Disaster.setToggleGroup(toggleGroup);
        AnimalWelfare.setToggleGroup(toggleGroup);
        Education.setToggleGroup(toggleGroup);


        if (toggleGroup.getSelectedToggle() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Category not selected!");
            alert.setContentText("Please select the type of newUser you want to signup as!");
            alert.show();
        }
        else if(other.isSelected()){
            category = other.getText();
        }
        else if(AnimalWelfare.isSelected()){
            category = AnimalWelfare.getText();
        }
        else if(Hunger.isSelected()){
            category = Hunger.getText();
        }
        else if(Education.isSelected()){
            category = Education.getText();
        }
        else if(Emergencies.isSelected()){
            category = Emergencies.getText();
        }
        else if(Environment.isSelected()){
            category = Environment.getText();
        }
        else if(ArtandCulture.isSelected()){
            category = ArtandCulture.getText();
        }
        else if(Funeral.isSelected()){
            category = Funeral.getText();
        }
        else if(Sports.isSelected()){
            category = Sports.getText();
        }
        else if(Community.isSelected()){
            category = Community.getText();
        }
        else if(Disaster.isSelected()){
            category = Disaster.getText();
        }
        else if(Medical.isSelected()){
            category = Medical.getText();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Category not selected!");
            alert.setContentText("Please select the type of newUser you want to signup as!");
            alert.show();
        }

        //Variables for the connection to a database.
        Connection connection = null;
        PreparedStatement psInsertValue = null;


        String url = "jdbc:mysql://localhost:3306/donation_tracker";
        String user = "root";
        String pass = "112358abc";

        if (campaign_name.equals("") || goal_amount.equals("") || description.equals("") || category.equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Empty fields!");
            alert.setContentText("Please fill up all the information");
            alert.show();
        }
        else {


                connection = DriverManager.getConnection(url, user, pass);

                psInsertValue = connection.prepareStatement("INSERT INTO campaign (title, userID, description, goalAmount, currentAmount, status, category) VALUES(?, ?, ?, ?, ?, ?, ?)");
                psInsertValue.setString(1, campaign_name);
                psInsertValue.setInt(2, userID);
                psInsertValue.setString(3, description);
                psInsertValue.setDouble(4, Double.parseDouble(goal_amount));
                psInsertValue.setDouble(5, 0);
                psInsertValue.setString(6, status);
                psInsertValue.setString(7, category);
                psInsertValue.executeUpdate();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Signup Successful!");
                alert.setContentText("Your account has been created successfully!");
                alert.show();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("homepage.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage = (Stage)anchorPane.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }

    }





    //All the methods for Buttons and Menu bar.

    //Method 1: Takes to the Profile Page.
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

    //Method 3: Takes to the Update Profile Page.
    public void goToUpdateProfile() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("update_profile.fxml"));
        root = loader.load();
        scene = new Scene(root);
        stage = (Stage)anchorPane.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    //Method 4: Takes to the Help & Support Page.
    public void goToHelpAndSupport() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("help_and_support.fxml"));
        root = loader.load();
        scene = new Scene(root);
        stage = (Stage)anchorPane.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    //Method 5: Logs out from the user account.
    public void logout() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("login.fxml"));
        root = loader.load();
        scene = new Scene(root);
        stage = (Stage)anchorPane.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    //Method 6: Exits the Program.
    public void exit() throws IOException {

        stage = (Stage) anchorPane.getScene().getWindow();
        stage.close();
    }

}
