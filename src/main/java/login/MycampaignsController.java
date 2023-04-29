package login;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class MycampaignsController implements Initializable {

    @FXML
    Stage stage;

    @FXML
    Scene scene;

    @FXML
    Parent root;

    @FXML
    BorderPane borderPane;

    @FXML
    private TableView<Campaign> campaignTable;
    @FXML
    private TableColumn<Campaign, String> titleColumn;
    @FXML
    private TableColumn<Campaign, String> categoryColumn;
    @FXML
    private TableColumn<Campaign, Double> goalAmountColumn;
    @FXML
    private TableColumn<Campaign, Double> currentAmountColumn;
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
            load();
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

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

    public void load() throws IOException {


        //Variables for the connection to a database.
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        //String driver = "com.mysql.jdbc.Driver";
        String url = Constants.DATABASE_URL;
        String user = Constants.DATABASE_USERNAME;
        String pass = Constants.DATABASE_PASSWORD;

        ArrayList<Campaign> campaigns = null;
        try {
            //Establishing the database connection.
            connection = DriverManager.getConnection(url, user, pass);

            //Storing the password in the resultPassword variable.
            preparedStatement = connection.prepareStatement("SELECT * FROM campaign WHERE userID = ?");
            preparedStatement.setInt(1, SessionManager.getCurrentUser());
            resultSet = preparedStatement.executeQuery();

            campaigns = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("campaignID");
                String name = resultSet.getString("title");
                String description = resultSet.getString("description");
                double goalAmount = resultSet.getDouble("goalAmount");
                double currentAmount = resultSet.getDouble("currentAmount");
                String status = resultSet.getString("status");
                String category = resultSet.getString("category");

                Campaign campaign = new Campaign(id, name, description, goalAmount, currentAmount, status, category);
                campaigns.add(campaign);
            }
//            for (Campaign campaign : campaigns) {
//                System.out.println(campaign.toString());
//            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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
        assert campaigns != null;
        ObservableList<Campaign> campaignList = FXCollections.observableArrayList(campaigns);

        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        goalAmountColumn.setCellValueFactory(new PropertyValueFactory<>("goalAmount"));
        currentAmountColumn.setCellValueFactory(new PropertyValueFactory<>("currentAmount"));


        campaignTable.setItems(campaignList);

        campaignTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) { // check for single-click
                Campaign campaign = campaignTable.getSelectionModel().getSelectedItem();
                int campaignID = campaign.getCampaignID();
                try {
                    loadCampaignDetails(event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        System.out.println(SessionManager.getCurrentUser());

    }

    void loadCampaignDetails(MouseEvent event) throws IOException {
        Campaign selectedCampaign = campaignTable.getSelectionModel().getSelectedItem();
        if (selectedCampaign != null) {
            int campaignID = selectedCampaign.getCampaignID();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("campaign_details.fxml"));
            Campaign_Details_Controller controller = new Campaign_Details_Controller(campaignID);
            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) campaignTable.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

}
