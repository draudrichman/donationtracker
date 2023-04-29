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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class Donation_Payment_Controller implements Initializable {

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
    BorderPane borderPane;

    @FXML
    MenuItem Home, Explore, YourCampaign, DonatedCampaign, MyProfile, UpdateProfile, HelpAndSupport, LogOut, Exit;

    @FXML
    Button PaymentConfirmation;

    @FXML
    TextField donationamount;


    String donationvalue, method;

    @FXML
    RadioButton MFS, Card, BankTransfer;

    String url = Constants.DATABASE_URL;
    String username = Constants.DATABASE_USERNAME;
    String password = Constants.DATABASE_PASSWORD;

    @FXML
    Menu profile;

    @FXML
    ImageView campaignView;

    @FXML
    Image campaignPicture;

    @FXML
    Label title;

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


    private int userID, campaignID;
    public Donation_Payment_Controller(int campaignID) throws IOException {
        userID = SessionManager.getCurrentUser();
        this.campaignID = campaignID;
    }

    public void goToDonationConfirmation(ActionEvent actionEvent) throws IOException, SQLException {

        donationvalue = donationamount.getText();

        ToggleGroup toggleGroup = new ToggleGroup();
        MFS.setToggleGroup(toggleGroup);
        Card.setToggleGroup(toggleGroup);
        BankTransfer.setToggleGroup(toggleGroup);


        if (toggleGroup.getSelectedToggle() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Payment method not selected!");
            alert.setContentText("Please select your payment method.");
            alert.show();
        } else if (MFS.isSelected()) {
            method = MFS.getText();
        } else if (Card.isSelected()) {
            method = Card.getText();
        } else if (BankTransfer.isSelected()) {
            method = BankTransfer.getText();
        }


        //Variables for the connection to a database.
        Connection connection = null;
        PreparedStatement psInsertValue = null;


        String url = Constants.DATABASE_URL;
        String user = Constants.DATABASE_USERNAME;
        String pass = Constants.DATABASE_PASSWORD;

        if (donationvalue.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Empty field!");
            alert.setContentText("Please enter your donation amount.");
            alert.show();
        } else {


            connection = DriverManager.getConnection(url, user, pass);

            psInsertValue = connection.prepareStatement("UPDATE campaign SET currentAmount = currentAmount + ? WHERE campaignID = ?");
            psInsertValue.setDouble(1, Double.parseDouble(donationvalue));
            psInsertValue.setInt(2, campaignID);
            psInsertValue.executeUpdate();

            psInsertValue = connection.prepareStatement("INSERT INTO donation (campaignID, userID, amount, method) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            psInsertValue.setInt(1, campaignID);
            psInsertValue.setInt(2, userID);
            psInsertValue.setDouble(3, Double.parseDouble(donationvalue));
            psInsertValue.setString(4, method);
            psInsertValue.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Payment Successful!");
            alert.setContentText("Your donation has been successfully pledged forward");
            alert.show();

            ResultSet generatedKeys = psInsertValue.getGeneratedKeys();

            if (generatedKeys.next()) {
                int donationID = generatedKeys.getInt(1);

                // Pass the donationID to the next scene or do any other necessary processing
                // For example, you can pass the donationID as a parameter to the constructor of the next scene:
                FXMLLoader loader = new FXMLLoader(getClass().getResource("donation_confirmation.fxml"));
                Donation_Confirmation_Controller controller= new Donation_Confirmation_Controller(donationID);
                loader.setController(controller);
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
                Stage currentStage = (Stage) borderPane.getScene().getWindow();
                currentStage.close();


            }
        }
    }

    //All the methods for Buttons and Menu bar.

    //Method 1: Takes to the Profile Page.
    public void goToHome() throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("homepage.fxml"));
        root = loader.load();
        scene = new Scene(root);
        stage = (Stage) anchorPane.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    //Method 2: Takes to the Profile Page.


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

