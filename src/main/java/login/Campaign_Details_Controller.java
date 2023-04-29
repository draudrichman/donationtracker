package login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class Campaign_Details_Controller implements Initializable {

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
    Button DonationPayment;

    @FXML
    Label title, goalDisplay;

    @FXML
    TextFlow campdescription;

    @FXML
    BorderPane borderPane;

    @FXML
    ImageView campaignView;

    @FXML
    Image campaignPicture;

    @FXML
    Menu profile;


    String url = Constants.DATABASE_URL;
    String username = Constants.DATABASE_USERNAME;
    String password = Constants.DATABASE_PASSWORD;
    private final int campaignID;

    public Campaign_Details_Controller(int campaignID) {
        this.campaignID = campaignID;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            gettingUsername();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement psInsertValue = null;
        ResultSet resultSet = null;

        String url2 = Constants.DATABASE_URL;
        String user = Constants.DATABASE_USERNAME;
        String pass = Constants.DATABASE_PASSWORD;

        try {
            connection = DriverManager.getConnection(url2, user, pass);

            preparedStatement = connection.prepareStatement("SELECT * FROM campaign WHERE campaignID = ?");
            preparedStatement.setInt(1, campaignID); // set the value of the placeholder to 1
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("campaignID");
                String name = resultSet.getString("title");
                String description = resultSet.getString("description");
                double goalAmount = resultSet.getDouble("goalAmount");
                double currentAmount = resultSet.getDouble("currentAmount");
                String status = resultSet.getString("status");
                String category = resultSet.getString("category");
                String image = resultSet.getString("image");

                Campaign campaign = new Campaign(id, name, description, goalAmount, currentAmount, status, category);
                campaign.setImage(image);
                System.out.println(campaign.getTitle());
                System.out.println(campaign.getDescription());
                System.out.println(campaign.getGoalAmount());
                System.out.println(campaign.getCurrentAmount());

                title.setText(campaign.getTitle());
                Text text = new Text(campaign.getDescription());
                campdescription.getChildren().add(text);
                goalDisplay.setText(String.valueOf("৳" + campaign.getCurrentAmount()) + " raised of ৳" + String.valueOf(campaign.getGoalAmount()));

                campaignPicture = new Image(image);
                campaignView.setImage(campaignPicture);

            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
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

    public void back(ActionEvent actionEvent) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("discover_campaigns.fxml"));
        root = loader.load();
        scene = new Scene(root);
        stage = (Stage)anchorPane.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    //Method 2: Takes to the Profile Page.

    //Method 7: Takes to the Donation Payment Page.
    public void goToDonationPayment(ActionEvent actionEvent) throws IOException {

        Donation_Payment_Controller paymentController = new Donation_Payment_Controller(campaignID);
// load the FXML file and set the controller
        FXMLLoader loader = new FXMLLoader(getClass().getResource("donation_payment.fxml"));
        loader.setController(paymentController);

// load the scene and set it to the stage
        Parent root = loader.load();
        stage = (Stage) ((Node)(actionEvent.getSource())).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Donation Payment");
        stage.setScene(scene);
        stage.show();
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
