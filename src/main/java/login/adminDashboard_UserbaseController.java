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
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class adminDashboard_UserbaseController implements Initializable {


    //Fx components for the fx:id.
    @FXML
    Stage stage;

    @FXML
    Scene scene;

    @FXML
    Parent root;

    @FXML
    Label Title;

    @FXML
    TextFlow Description;


    @FXML
    Button active;

    @FXML
    Button suspended;


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

    @FXML
    private TableColumn<Campaign, Double> statusColumn;

    public int currentCampaignID;


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
            preparedStatement = connection.prepareStatement("SELECT * FROM campaign");
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
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

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

    }

    void loadCampaignDetails(MouseEvent event) throws IOException {
        Campaign selectedCampaign = campaignTable.getSelectionModel().getSelectedItem();
        if (selectedCampaign != null) {
            int campaignID = selectedCampaign.getCampaignID();
            Connection connection = null;
            PreparedStatement preparedStatement = null;
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

                    Campaign campaign = new Campaign(id, name, description, goalAmount, currentAmount, status, category);

                    Title.setText(campaign.getTitle());
                    Description.getChildren().clear();
                    Text text = new Text(campaign.getDescription());
                    Description.getChildren().add(text);
                    currentCampaignID = campaign.getCampaignID();
                }

                resultSet.close();
                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }



    //Method 3: Takes to the Reset_Password page.
    @FXML
    public void Logout(ActionEvent actionEvent) throws IOException {

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login.fxml")));
        stage = (Stage) ((Node)(actionEvent.getSource())).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Log In");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void AnalyticsTransition(ActionEvent actionEvent) throws IOException {

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("admin-dashboard.fxml")));
        stage = (Stage) ((Node)(actionEvent.getSource())).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Admin Analytics");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void CampaignTransition(ActionEvent actionEvent) throws IOException {

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("admin-dashboard-campaigns.fxml")));
        stage = (Stage) ((Node)(actionEvent.getSource())).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Admin - Campaigns");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void DonationsTransition(ActionEvent actionEvent) throws IOException {

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("admin-dashboard-donations.fxml")));
        stage = (Stage) ((Node)(actionEvent.getSource())).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Admin - Donations");
        stage.setScene(scene);
        stage.show();
    }

    public void Refresh() throws IOException {
        load();

    }

    @FXML
    public void setActive() throws IOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String url2 = Constants.DATABASE_URL;
        String user = Constants.DATABASE_USERNAME;
        String pass = Constants.DATABASE_PASSWORD;

        try {
            connection = DriverManager.getConnection(url2, user, pass);

            preparedStatement = connection.prepareStatement("UPDATE campaign SET status = 'Active' WHERE campaignID = ?");
            preparedStatement.setInt(1, currentCampaignID); // set the value of the placeholder to the campaignID
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();

            Refresh();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    @FXML
    public void setSuspended() throws IOException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String url2 = Constants.DATABASE_URL;
        String user = Constants.DATABASE_USERNAME;
        String pass = Constants.DATABASE_PASSWORD;

        try {
            connection = DriverManager.getConnection(url2, user, pass);

            preparedStatement = connection.prepareStatement("UPDATE campaign SET status = 'Suspended' WHERE campaignID = ?");
            preparedStatement.setInt(1, currentCampaignID); // set the value of the placeholder to the campaignID
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
            Refresh();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
