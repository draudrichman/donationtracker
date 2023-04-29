package login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
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

    @FXML
    Label selectImageLabel;

    @FXML
    Button createCampaign, SelectImageButton;

    //Variables to contain campaign information.
    String campaign_name, description, category, status;

    String goal_amount, current_amount;

    static int campaignPicture;

    String campaignImagePath;

    private int userID;

    public New_Campaign_Controller() throws IOException {

        userID = SessionManager.getCurrentUser();
    }

    //Methods for the buttons.

    @FXML
    BorderPane borderPane;

    String url = Constants.DATABASE_URL;
    String username = Constants.DATABASE_USERNAME;
    String password = Constants.DATABASE_PASSWORD;


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

    public void selectCampaignImage2() {

        try {

            FileChooser fileChooser = new FileChooser();
            File chosenFile = fileChooser.showOpenDialog(null);
            String imagePath;

            if (chosenFile != null) {
                imagePath = chosenFile.getAbsolutePath();
                System.out.println(imagePath);

                try {
                    InputStream is = new FileInputStream(imagePath);
                    OutputStream os = new FileOutputStream("D:\\Intelli J\\donationtracker-master2\\src\\main\\resources\\login\\campaignpictures\\" + String.valueOf(campaignPicture) + ".png");
                    campaignPicture++;
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = is.read(buffer)) > 0) {
                        os.write(buffer, 0, length);
                    }
                    is.close();
                    os.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                campaignImagePath = "D:\\Intelli J\\donationtracker-master2\\src\\main\\resources\\login\\campaignpictures\\" + campaignPicture + ".png";
                selectImageLabel.setText(campaignImagePath);
            }
            else {

                campaignImagePath = "D:\\Intelli J\\donationtracker-master2\\src\\main\\resources\\login\\campaignpictures\\defaultBackground.jpg";
                selectImageLabel.setText(campaignImagePath);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void selectCampaignImage() {
        try {
            FileChooser fileChooser = new FileChooser();
            File chosenFile = fileChooser.showOpenDialog(null);

            if (chosenFile != null) {
                campaignImagePath = chosenFile.getAbsolutePath();
                System.out.println(campaignImagePath);
                selectImageLabel.setText(campaignImagePath);
            } else {
                campaignImagePath = "D:\\zzzfinalprojectdstination\\src\\main\\resources\\login\\resources\\campaignpictures\\defaultBackground.jpg";
                selectImageLabel.setText(campaignImagePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //Method 8: Crates New Campaign.
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
            alert.setContentText("Please select the type of campaign you want to create!");
            alert.show();
        }

        //Variables for the connection to a database.
        Connection connection = null;
        PreparedStatement psInsertValue = null;


        String url = Constants.DATABASE_URL;
        String user = Constants.DATABASE_USERNAME;
        String pass = Constants.DATABASE_PASSWORD;

        if (campaign_name.equals("") || goal_amount.equals("") || description.equals("") || category.equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Empty fields!");
            alert.setContentText("Please fill up all the information");
            alert.show();
        }
        else {

            connection = DriverManager.getConnection(url, user, pass);

            psInsertValue = connection.prepareStatement("INSERT INTO campaign (title, userID, description, goalAmount, currentAmount, status, category, image) VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
            psInsertValue.setString(1, campaign_name);
            psInsertValue.setInt(2, userID);
            psInsertValue.setString(3, description);
            psInsertValue.setDouble(4, Double.parseDouble(goal_amount));
            psInsertValue.setDouble(5, 0);
            psInsertValue.setString(6, status);
            psInsertValue.setString(7, category);
            psInsertValue.setString(8, campaignImagePath);
            psInsertValue.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Campaign Creation Successful!");
            alert.setContentText("Your campaign has been created successfully!");
            alert.show();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("homepage.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage = (Stage)borderPane.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }

    }

}
