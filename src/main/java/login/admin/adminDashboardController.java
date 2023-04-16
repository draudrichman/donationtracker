package login.admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import login.Constants;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;

public class adminDashboardController implements Initializable {

    @FXML
    Stage stage;

    @FXML
    Scene scene;

    @FXML
    Parent root;

    @FXML
    private HBox logout;

    @FXML
    private Label activeCampaignlabel, amountRaisedLabel, noofDonationlabel, avgDonationLabel;

    @FXML
    AnchorPane anchorPane;
    @FXML
    private LineChart<String, Integer> lineChart;

    @FXML
    private ImageView refresh;


    @FXML
    private PieChart pieChart;

    @FXML
    VBox activeCampaign, amountRaised, noOfDonations, avgDonation;

    String url = Constants.DATABASE_URL;
    String username = Constants.DATABASE_USERNAME;
    String password = Constants.DATABASE_PASSWORD;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Refresh();
        logout.setOnMouseClicked(event -> {
            try {
                Logout();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });



    }

    public void Refresh(){
            try {
                inlineChart();
                setPieChart();
                Connection connection = DriverManager.getConnection(url, username, password);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT SUM(amount) as total_donation_amount FROM donation WHERE DATE(createdAt) = CURDATE()");
                resultSet.next();
                int sum = resultSet.getInt(1);
                amountRaisedLabel.setText("৳" + String.valueOf(sum));

                resultSet = statement.executeQuery("SELECT COUNT(*) FROM donation WHERE DATE(createdAt) = CURDATE()");
                resultSet.next();
                int count = resultSet.getInt(1);
                noofDonationlabel.setText(String.valueOf(count));
                avgDonationLabel.setText(String.valueOf("৳" + sum / count));


                resultSet = statement.executeQuery("SELECT COUNT(*) FROM campaign");
                resultSet.next();
                count = resultSet.getInt(1);
                activeCampaignlabel.setText(String.valueOf(count));



                resultSet.close();
                statement.close();
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }





    }


    private void inlineChart() throws SQLException {
        // Connect to the database

        Connection connection = DriverManager.getConnection(url, username, password);

        // Create a new XYChart.Series and add the data to it
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        PreparedStatement ps = connection.prepareStatement("SELECT HOUR(createdAt) AS hour, COUNT(*) AS count FROM donation WHERE DATE(createdAt) = CURDATE() GROUP BY HOUR(createdAt) ORDER BY HOUR(createdAt)");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int hour = rs.getInt(1);
            int count = rs.getInt(2);
            series.getData().add(new XYChart.Data<>(String.valueOf(hour) + "a.m.", count));
        }


        lineChart.getData().clear();
        lineChart.getData().add(series);
        lineChart.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent;");
    }

    private void setPieChart() throws SQLException {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        Connection connection = DriverManager.getConnection(url, username, password);

        // Execute the SQL query to retrieve the data
        PreparedStatement ps = connection.prepareStatement("SELECT category, COUNT(*) AS count FROM campaign GROUP BY category");
        ResultSet rs = ps.executeQuery();

        // Loop through the ResultSet and create a new PieChart.Data object for each category
        while (rs.next()) {
            String category = rs.getString("category");
            int count = rs.getInt("count");
            pieChartData.add(new PieChart.Data(category, count));
        }

        // Set the data for the pie chart
        pieChart.setData(pieChartData);
    }

    @FXML
    public void Logout() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage)logout.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }





}
