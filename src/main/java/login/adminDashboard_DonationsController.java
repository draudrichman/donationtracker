package login;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
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
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class adminDashboard_DonationsController implements Initializable {


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
    private TableView<Donation> donationTable;
    @FXML
    private TableColumn<Donation, Integer> donationIDcolumn;
    @FXML
    private TableColumn<Donation, Integer> campaignIDcolumn;
    @FXML
    private TableColumn<Donation, Integer> userIDcolumn;
    @FXML
    private TableColumn<Donation, Double> amountcolumn;
    @FXML
    private TableColumn<Donation, String> methodcolumn;
    @FXML
    private TableColumn<Donation, String> datecolumn;
    @FXML
    private TableColumn<Donation, String> statuscolumn;



    public int currentCampaignID;
    public int currentdonationID;
    public double currentAmount;


    public void load() throws IOException {
        //Variables for the connection to a database.
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        //String driver = "com.mysql.jdbc.Driver";
        String url = Constants.DATABASE_URL;
        String user = Constants.DATABASE_USERNAME;
        String pass = Constants.DATABASE_PASSWORD;

        ArrayList<Donation> donations = null;
        try {
            //Establishing the database connection.
            connection = DriverManager.getConnection(url, user, pass);

            //Storing the password in the resultPassword variable.
            preparedStatement = connection.prepareStatement("SELECT * FROM donation");
            resultSet = preparedStatement.executeQuery();

            donations = new ArrayList<>();
            while (resultSet.next()) {
                int donationID = resultSet.getInt("donationID");
                int campaignID = resultSet.getInt("campaignID");
                int userID = resultSet.getInt("userID");
                double amount = resultSet.getDouble("amount");
                String method = resultSet.getString("method");
                Timestamp createdAt = resultSet.getTimestamp("createdAt");
                String status = resultSet.getString("paymentStatus");

                Donation donation = new Donation(donationID, campaignID, userID, amount, method, createdAt, status);
                donations.add(donation);
            }

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
        assert donations != null;
        ObservableList<Donation> donationList = FXCollections.observableArrayList(donations);

        donationIDcolumn.setCellValueFactory(new PropertyValueFactory<>("donationID"));
        campaignIDcolumn.setCellValueFactory(new PropertyValueFactory<>("campaignID"));
        userIDcolumn.setCellValueFactory(new PropertyValueFactory<>("userID"));
        amountcolumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        methodcolumn.setCellValueFactory(new PropertyValueFactory<>("method"));
        datecolumn.setCellValueFactory(p -> {
            // Format the createdAt timestamp as a string using the SimpleDateFormat class
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return new SimpleStringProperty(sdf.format(p.getValue().getCreatedAt()));
        });
        statuscolumn.setCellValueFactory(new PropertyValueFactory<>("status"));


        donationTable.setItems(donationList);

        donationTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) { // check for single-click
                Donation donation = donationTable.getSelectionModel().getSelectedItem();
                try {
                    loadCampaignDetails(event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    void loadCampaignDetails(MouseEvent event) throws IOException {
        Donation selectedDonation = donationTable.getSelectionModel().getSelectedItem();
        currentdonationID = donationTable.getSelectionModel().getSelectedItem().getDonationID();
        currentCampaignID = donationTable.getSelectionModel().getSelectedItem().getCampaignID();
        currentAmount = donationTable.getSelectionModel().getSelectedItem().getAmount();

        System.out.println(currentCampaignID);
        System.out.println(currentdonationID);
        System.out.println(currentAmount);
        if (selectedDonation != null) {
            int donationID = selectedDonation.getDonationID();
            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;

            String url2 = Constants.DATABASE_URL;
            String user = Constants.DATABASE_USERNAME;
            String pass = Constants.DATABASE_PASSWORD;

            try {
                connection = DriverManager.getConnection(url2, user, pass);

                preparedStatement = connection.prepareStatement("SELECT c.title AS campaignName, u.fullName AS userName, d.amount, d.method, d.createdAt, d.paymentStatus  FROM donation d JOIN campaign c ON d.campaignID = c.campaignID JOIN userdetails u ON d.userID = u.userID WHERE d.donationID = ?");
                preparedStatement.setInt(1, donationID); // set the value of the placeholder to 1
                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    String campaignName = resultSet.getString("campaignName");
                    String userName = resultSet.getString("userName");
                    double amount = resultSet.getDouble("amount");
                    String method = resultSet.getString("method");
                    Timestamp createdAt = resultSet.getTimestamp("createdAt");
                    String status = resultSet.getString("paymentStatus");

                    Title.setText(campaignName);


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
    public void UserBaseTransition(ActionEvent actionEvent) throws IOException {

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("admin-dashboard-userbase.fxml")));
        stage = (Stage) ((Node)(actionEvent.getSource())).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Admin - UserBase");
        stage.setScene(scene);
        stage.show();
    }

    public void Refresh() throws IOException {
        load();

    }

    @FXML
    public void setRefunded() throws IOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String url2 = Constants.DATABASE_URL;
        String user = Constants.DATABASE_USERNAME;
        String pass = Constants.DATABASE_PASSWORD;

        try {
            connection = DriverManager.getConnection(url2, user, pass);

            preparedStatement = connection.prepareStatement("UPDATE donation SET paymentStatus = 'Refunded' WHERE donationID = ?");
            preparedStatement.setInt(1, currentdonationID);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("UPDATE campaign SET currentAmount = currentAmount - ? WHERE campaignID = ?");
            preparedStatement.setDouble(1, currentAmount);
            preparedStatement.setInt(2, currentCampaignID);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();

            Refresh();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public void undoRefunded() throws IOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String url2 = Constants.DATABASE_URL;
        String user = Constants.DATABASE_USERNAME;
        String pass = Constants.DATABASE_PASSWORD;

        try {
            connection = DriverManager.getConnection(url2, user, pass);

            preparedStatement = connection.prepareStatement("UPDATE donation SET paymentStatus = 'Completed' WHERE donationID = ?");
            preparedStatement.setInt(1, currentdonationID);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("UPDATE campaign SET currentAmount = currentAmount + ? WHERE campaignID = ?");
            preparedStatement.setDouble(1, currentAmount);
            preparedStatement.setInt(2, currentCampaignID);
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
