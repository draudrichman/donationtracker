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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import login.Constants;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.format.DateTimeFormatter;
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
    BorderPane borderPane;

    @FXML
    private HBox logouthbox;

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

    @FXML
    VBox activeCampaignsvbox, noOfDonationsvbox, avgDonationvbox, amountRaisedvbox;

    @FXML
    Label today, yesterday, last7days, alltime;

    @FXML
    Label linegraphLabel, piegraphLabel;

    public String timeState = "today";
    // timeStates = today, yesterday, last7days, last30days, alltime
    public String typeState = "amountraised";
    // typesStates = amountraised, campaignslaunched, nodonations


    String url = Constants.DATABASE_URL;
    String username = Constants.DATABASE_USERNAME;
    String password = Constants.DATABASE_PASSWORD;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Refresh();

        noOfDonationsvbox.setOnMouseClicked(event -> {
            typeState = "nodonations";
            Refresh();
        });

        amountRaisedvbox.setOnMouseClicked(event -> {
            typeState = "amountraised";
            Refresh();
        });

        activeCampaignsvbox.setOnMouseClicked(event -> {
            typeState = "campaignslaunched";
            Refresh();
        });



        today.setOnMouseClicked(event -> {
            timeState = "today";
            Refresh();
        });
        yesterday.setOnMouseClicked(event -> {
            timeState = "yesterday";
            Refresh();
        });
        last7days.setOnMouseClicked(event -> {
            timeState = "last7days";
            Refresh();
        });
        alltime.setOnMouseClicked(event -> {
            timeState = "alltime";
            Refresh();
        });

        logouthbox.setOnMouseClicked(event -> {
            System.out.println("HBox clicked!");
            Parent root = null;
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/login.fxml")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        });


    }

    public void Refresh(){
            try {
                inlineChart(timeState, typeState);
                setPieChart(timeState, typeState);
                Connection connection = DriverManager.getConnection(url, username, password);

                if (Objects.equals(timeState, "today")){
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT SUM(amount) as total_donation_amount FROM donation WHERE DATE(createdAt) = CURDATE()");
                    resultSet.next();
                    int sum = resultSet.getInt(1);
                    amountRaisedLabel.setText("৳" + String.valueOf(sum));

                    resultSet = statement.executeQuery("SELECT COUNT(*) FROM donation WHERE DATE(createdAt) = CURDATE()");
                    resultSet.next();
                    int count = resultSet.getInt(1);
                    noofDonationlabel.setText(String.valueOf(count));
                    if (count != 0) {
                        avgDonationLabel.setText(String.valueOf("৳" + sum / count));
                    }
                    else {
                        avgDonationLabel.setText("৳0");
                    }
                    resultSet = statement.executeQuery("SELECT COUNT(*) FROM campaign WHERE DATE(createdAt) = CURDATE()");
                    resultSet.next();
                    count = resultSet.getInt(1);
                    activeCampaignlabel.setText(String.valueOf(count));

                    resultSet.close();
                    statement.close();
                    connection.close();
                }
                else if (Objects.equals(timeState, "yesterday")){
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT SUM(amount) as total_donation_amount FROM donation WHERE DATE(createdAt) = DATE(NOW()) - INTERVAL 1 DAY");
                    resultSet.next();
                    int sum = resultSet.getInt(1);
                    amountRaisedLabel.setText("৳" + String.valueOf(sum));

                    resultSet = statement.executeQuery("SELECT COUNT(*) FROM donation WHERE DATE(createdAt) = DATE(NOW()) - INTERVAL 1 DAY");
                    resultSet.next();
                    int count = resultSet.getInt(1);
                    noofDonationlabel.setText(String.valueOf(count));
                    if (count != 0) {
                        avgDonationLabel.setText(String.valueOf("৳" + sum / count));
                    }
                    else {
                        avgDonationLabel.setText("৳0");
                    }
                    resultSet = statement.executeQuery("SELECT COUNT(*) FROM campaign WHERE DATE(createdAt) = DATE(NOW()) - INTERVAL 1 DAY");
                    resultSet.next();
                    count = resultSet.getInt(1);
                    activeCampaignlabel.setText(String.valueOf(count));

                    resultSet.close();
                    statement.close();
                    connection.close();
                }
                else if (Objects.equals(timeState, "last7days")){
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT SUM(amount) as total_donation_amount FROM donation WHERE createdAt >= DATE(NOW()) - INTERVAL 7 DAY\n");
                    resultSet.next();
                    int sum = resultSet.getInt(1);
                    amountRaisedLabel.setText("৳" + String.valueOf(sum));

                    resultSet = statement.executeQuery("SELECT COUNT(*) FROM donation WHERE createdAt >= DATE(NOW()) - INTERVAL 7 DAY;\n");
                    resultSet.next();
                    int count = resultSet.getInt(1);
                    noofDonationlabel.setText(String.valueOf(count));
                    if (count != 0) {
                        avgDonationLabel.setText(String.valueOf("৳" + sum / count));
                    }
                    else {
                        avgDonationLabel.setText("৳0");
                    }
                    resultSet = statement.executeQuery("SELECT COUNT(*) FROM campaign WHERE createdAt >= DATE(NOW()) - INTERVAL 7 DAY");
                    resultSet.next();
                    count = resultSet.getInt(1);
                    activeCampaignlabel.setText(String.valueOf(count));

                    resultSet.close();
                    statement.close();
                    connection.close();
                }
                else if (Objects.equals(timeState, "alltime")){
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT SUM(amount) as total_donation_amount FROM donation");
                    resultSet.next();
                    int sum = resultSet.getInt(1);
                    amountRaisedLabel.setText("৳" + String.valueOf(sum));

                    resultSet = statement.executeQuery("SELECT COUNT(*) FROM donation");
                    resultSet.next();
                    int count = resultSet.getInt(1);
                    noofDonationlabel.setText(String.valueOf(count));
                    if (count != 0) {
                        avgDonationLabel.setText(String.valueOf("৳" + sum / count));
                    }
                    else {
                        avgDonationLabel.setText("৳0");
                    }
                    resultSet = statement.executeQuery("SELECT COUNT(*) FROM campaign");
                    resultSet.next();
                    count = resultSet.getInt(1);
                    activeCampaignlabel.setText(String.valueOf(count));

                    resultSet.close();
                    statement.close();
                    connection.close();
                }


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }





    }


    private void inlineChart(String timeState, String typeState) throws SQLException {
        // Connect to the database

        Connection connection = DriverManager.getConnection(url, username, password);
        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        // Number of Donations
        if (Objects.equals(timeState, "today") && Objects.equals(typeState, "nodonations")){
            linegraphLabel.setText("No of Donations - Today");
            PreparedStatement ps = connection.prepareStatement("SELECT HOUR(createdAt) AS hour, COUNT(*) AS count FROM donation WHERE DATE(createdAt) = CURDATE() GROUP BY HOUR(createdAt) ORDER BY HOUR(createdAt)");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int hour = rs.getInt(1);
                int count = rs.getInt(2);
                series.getData().add(new XYChart.Data<>(String.valueOf(hour) + "a.m.", count));
            }
        }
        else if (Objects.equals(timeState, "yesterday") && Objects.equals(typeState, "nodonations")){
            linegraphLabel.setText("No of Donations - Yesterday");
            PreparedStatement ps = connection.prepareStatement("SELECT HOUR(createdAt) AS hour, COUNT(*) AS count FROM donation WHERE DATE(createdAt) = DATE_SUB(CURDATE(), INTERVAL 1 DAY) GROUP BY HOUR(createdAt) ORDER BY HOUR(createdAt)");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int hour = rs.getInt(1);
                int count = rs.getInt(2);
                series.getData().add(new XYChart.Data<>(String.valueOf(hour) + "a.m.", count));
            }
        }
        else if (Objects.equals(timeState, "last7days") && Objects.equals(typeState, "nodonations")){
            linegraphLabel.setText("No of Donations - Last 7 Days");
            PreparedStatement ps = connection.prepareStatement("SELECT DATE(createdAt) AS day, COUNT(*) AS count FROM donation WHERE createdAt >= DATE(NOW()) - INTERVAL 7 DAY GROUP BY day ORDER BY day");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String day = rs.getDate("day").toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yy"));
                int count = rs.getInt("count");
                series.getData().add(new XYChart.Data<>(day, count));
            }
        }
        else if (Objects.equals(timeState, "alltime") && Objects.equals(typeState, "nodonations")) {
            linegraphLabel.setText("No of Donations - All Time");
            PreparedStatement ps = connection.prepareStatement("SELECT DATE(createdAt) AS day, COUNT(*) AS count FROM donation GROUP BY day ORDER BY day");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String day = rs.getDate("day").toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yy"));
                int count = rs.getInt("count");
                series.getData().add(new XYChart.Data<>(day, count));
            }
        }

        // Number of Donations
        if (Objects.equals(timeState, "today") && Objects.equals(typeState, "amountraised")){
            linegraphLabel.setText("Amount Raised - Today");
            PreparedStatement ps = connection.prepareStatement("SELECT HOUR(createdAt) AS hour, SUM(amount) AS totalAmount FROM donation WHERE DATE(createdAt) = CURDATE() GROUP BY HOUR(createdAt) ORDER BY HOUR(createdAt)");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int hour = rs.getInt(1);
                double totalAmount = rs.getDouble(2);
                series.getData().add(new XYChart.Data<>(String.valueOf(hour) + "a.m.", (int)totalAmount));


            }
        }
        else if (Objects.equals(timeState, "yesterday") && Objects.equals(typeState, "amountraised")){
            linegraphLabel.setText("Amount Raised - Yesterday");
            PreparedStatement ps = connection.prepareStatement("SELECT HOUR(createdAt) AS hour, SUM(amount) AS totalAmount FROM donation WHERE DATE(createdAt) = DATE_SUB(CURDATE(), INTERVAL 1 DAY) GROUP BY HOUR(createdAt) ORDER BY HOUR(createdAt)");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int hour = rs.getInt(1);
                double totalAmount = rs.getDouble(2);
                series.getData().add(new XYChart.Data<>(String.valueOf(hour) + "a.m.", (int)totalAmount));
            }
        }
        else if (Objects.equals(timeState, "last7days") && Objects.equals(typeState, "amountraised")){
            linegraphLabel.setText("Amount Raised - Last 7 days");
            PreparedStatement ps = connection.prepareStatement("SELECT DATE(createdAt) AS day, SUM(amount) AS totalAmount FROM donation WHERE createdAt >= DATE(NOW()) - INTERVAL 7 DAY GROUP BY day ORDER BY day");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String day = rs.getDate("day").toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yy"));
                double totalAmount = rs.getDouble("totalAmount");
                series.getData().add(new XYChart.Data<>(day, (int)totalAmount));
            }
        }
        else if (Objects.equals(timeState, "alltime") && Objects.equals(typeState, "amountraised")) {
            linegraphLabel.setText("Amount Raised - All time");
            PreparedStatement ps = connection.prepareStatement("SELECT DATE(createdAt) AS day, SUM(amount) AS totalAmount FROM donation GROUP BY day ORDER BY day");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String day = rs.getDate("day").toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yy"));
                double totalAmount = rs.getDouble("totalAmount");
                series.getData().add(new XYChart.Data<>(day, (int)totalAmount));
            }

        }

        // Campaigns Launched
        if (Objects.equals(timeState, "today") && Objects.equals(typeState, "campaignslaunched")){
            linegraphLabel.setText("Campaigns Launched - Today");
            PreparedStatement ps = connection.prepareStatement("SELECT HOUR(createdAt) AS hour, COUNT(*) AS count FROM campaign WHERE DATE(createdAt) = CURDATE() GROUP BY HOUR(createdAt) ORDER BY HOUR(createdAt)");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int hour = rs.getInt(1);
                int count = rs.getInt(2);
                series.getData().add(new XYChart.Data<>(String.valueOf(hour) + "a.m.", count));
            }
        }
        else if (Objects.equals(timeState, "yesterday") && Objects.equals(typeState, "campaignslaunched")){
            linegraphLabel.setText("Campaigns Launched - Yesterday");
            PreparedStatement ps = connection.prepareStatement("SELECT HOUR(createdAt) AS hour, COUNT(*) AS count FROM campaign WHERE DATE(createdAt) = DATE_SUB(CURDATE(), INTERVAL 1 DAY) GROUP BY HOUR(createdAt) ORDER BY HOUR(createdAt)");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int hour = rs.getInt(1);
                int count = rs.getInt(2);
                series.getData().add(new XYChart.Data<>(String.valueOf(hour) + "a.m.", count));
            }
        }
        else if (Objects.equals(timeState, "last7days") && Objects.equals(typeState, "campaignslaunched")){
            linegraphLabel.setText("Campaigns Launched - Last 7 Days");
            PreparedStatement ps = connection.prepareStatement("SELECT DATE(createdAt) AS day, COUNT(*) AS count FROM campaign WHERE createdAt >= DATE(NOW()) - INTERVAL 7 DAY GROUP BY day ORDER BY day");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String day = rs.getDate("day").toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yy"));
                int count = rs.getInt("count");
                series.getData().add(new XYChart.Data<>(day, count));
            }
        }
        else if (Objects.equals(timeState, "alltime") && Objects.equals(typeState, "campaignslaunched")) {
            linegraphLabel.setText("Campaigns Launched - All Time");
            PreparedStatement ps = connection.prepareStatement("SELECT DATE(createdAt) AS day, COUNT(*) AS count FROM campaign GROUP BY day ORDER BY day");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String day = rs.getDate("day").toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yy"));
                int count = rs.getInt("count");
                series.getData().add(new XYChart.Data<>(day, count));
            }
        }


        lineChart.getData().clear();
        lineChart.getData().add(series);
        lineChart.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent;");
    }

    private void setPieChart(String timeState, String typeState) throws SQLException {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        Connection connection = DriverManager.getConnection(url, username, password);

        if (Objects.equals(timeState, "today") && Objects.equals(typeState, "amountraised")){
            piegraphLabel.setText("Amount Raised - Today");
            PreparedStatement ps = connection.prepareStatement("SELECT campaign.category, SUM(donation.amount) AS totalAmount FROM donation INNER JOIN campaign ON donation.campaignID = campaign.campaignID WHERE DATE(donation.createdAt) = CURDATE() GROUP BY campaign.category");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String category = rs.getString("category");
                double totalAmount = rs.getDouble("totalAmount");
                pieChartData.add(new PieChart.Data(category, totalAmount));
            }
        }
        else if (Objects.equals(timeState, "yesterday") && Objects.equals(typeState, "amountraised")){
            piegraphLabel.setText("Amount Raised - Yesterday");
            PreparedStatement ps = connection.prepareStatement("SELECT campaign.category, SUM(donation.amount) AS totalAmount FROM donation INNER JOIN campaign ON donation.campaignID = campaign.campaignID WHERE donation.createdAt >= DATE(NOW()) - INTERVAL 1 DAY GROUP BY campaign.category");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String category = rs.getString("category");
                double totalAmount = rs.getDouble("totalAmount");
                pieChartData.add(new PieChart.Data(category, totalAmount));
            }
        }
        else if (Objects.equals(timeState, "last7days") && Objects.equals(typeState, "amountraised")){
            piegraphLabel.setText("Amount Raised - Last 7 Days");
            PreparedStatement ps = connection.prepareStatement("SELECT campaign.category, SUM(donation.amount) AS totalAmount FROM donation INNER JOIN campaign ON donation.campaignID = campaign.campaignID WHERE donation.createdAt >= DATE(NOW()) - INTERVAL 7 DAY GROUP BY campaign.category");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String category = rs.getString("category");
                double totalAmount = rs.getDouble("totalAmount");
                pieChartData.add(new PieChart.Data(category, totalAmount));
            }
        }
        else if (Objects.equals(timeState, "alltime") && Objects.equals(typeState, "amountraised")) {
            piegraphLabel.setText("Amount Raised - All Time");
            PreparedStatement ps = connection.prepareStatement("SELECT campaign.category, SUM(donation.amount) AS totalAmount FROM donation INNER JOIN campaign ON donation.campaignID = campaign.campaignID WHERE DATE(donation.createdAt) <= DATE(NOW()) GROUP BY campaign.category");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String category = rs.getString("category");
                double totalAmount = rs.getDouble("totalAmount");
                pieChartData.add(new PieChart.Data(category, totalAmount));
            }
        }

         //Number of Donations
        if (Objects.equals(timeState, "today") && Objects.equals(typeState, "nodonations")){
            piegraphLabel.setText("No of Donations - Today");
            PreparedStatement ps = connection.prepareStatement("SELECT category, COUNT(*) AS count FROM donation, campaign WHERE DATE(donation.createdAt) = CURDATE() AND donation.campaignID = campaign.campaignID GROUP BY category");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String category = rs.getString("category");
                int count = rs.getInt("count");
                pieChartData.add(new PieChart.Data(category, count));
            }

        }
        else if (Objects.equals(timeState, "yesterday") && Objects.equals(typeState, "nodonations")){
            piegraphLabel.setText("Number of Donations - Yesterday");
            PreparedStatement ps = connection.prepareStatement("SELECT category, COUNT(*) AS count FROM donation, campaign WHERE DATE(donation.createdAt) = DATE(NOW()) - INTERVAL 1 DAY AND donation.campaignID = campaign.campaignID GROUP BY category");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String category = rs.getString("category");
                int count = rs.getInt("count");
                pieChartData.add(new PieChart.Data(category, count));
            }
        }
        else if (Objects.equals(timeState, "last7days") && Objects.equals(typeState, "nodonations")){
            piegraphLabel.setText("Number of Donations - Last 7 Days");
            PreparedStatement ps = connection.prepareStatement("SELECT category, COUNT(*) AS count FROM donation, campaign WHERE DATE(donation.createdAt) >= DATE(NOW()) - INTERVAL 7 DAY AND donation.campaignID = campaign.campaignID GROUP BY category");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String category = rs.getString("category");
                int count = rs.getInt("count");
                pieChartData.add(new PieChart.Data(category, count));
            }

        }
        else if (Objects.equals(timeState, "alltime") && Objects.equals(typeState, "nodonations")) {
            piegraphLabel.setText("Number of Donations - All Time");
            PreparedStatement ps = connection.prepareStatement("SELECT category, COUNT(*) AS count FROM donation,campaign WHERE donation.campaignID = campaign.campaignID GROUP BY category");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String category = rs.getString("category");
                int count = rs.getInt("count");
                pieChartData.add(new PieChart.Data(category, count));
            }
        }

        // Campaigns Launched
        if (Objects.equals(timeState, "today") && Objects.equals(typeState, "campaignslaunched")){
            piegraphLabel.setText("Campaigns Launched - Today");
            PreparedStatement ps = connection.prepareStatement("SELECT category, COUNT(*) AS count FROM campaign WHERE DATE(createdAt) = CURDATE() GROUP BY category");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String category = rs.getString("category");
                int count = rs.getInt("count");
                pieChartData.add(new PieChart.Data(category, count));
            }
        }
        else if (Objects.equals(timeState, "yesterday") && Objects.equals(typeState, "campaignslaunched")){
            piegraphLabel.setText("Campaigns Launched - Yesterday");
            PreparedStatement ps = connection.prepareStatement("SELECT category, COUNT(*) AS count FROM campaign WHERE DATE(createdAt) = DATE(NOW()) - INTERVAL 1 DAY GROUP BY category");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String category = rs.getString("category");
                int count = rs.getInt("count");
                pieChartData.add(new PieChart.Data(category, count));
            }
        }
        else if (Objects.equals(timeState, "last7days") && Objects.equals(typeState, "campaignslaunched")){
            piegraphLabel.setText("Campaigns Launched - Last 7 Days");
            PreparedStatement ps = connection.prepareStatement("SELECT category, COUNT(*) AS count FROM campaign WHERE createdAt >= DATE(NOW()) - INTERVAL 7 DAY GROUP BY category");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String category = rs.getString("category");
                int count = rs.getInt("count");
                pieChartData.add(new PieChart.Data(category, count));
            }

        }
        else if (Objects.equals(timeState, "alltime") && Objects.equals(typeState, "campaignslaunched")) {
            piegraphLabel.setText("Campaigns Launched - All Time");
            PreparedStatement ps = connection.prepareStatement("SELECT category, COUNT(*) AS count FROM campaign GROUP BY category");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String category = rs.getString("category");
                int count = rs.getInt("count");
                pieChartData.add(new PieChart.Data(category, count));
            }
        }

        // Set the data for the pie chart
        pieChart.getData().clear();
        pieChart.setData(pieChartData);
    }

//    @FXML
//    public void Logout() throws IOException {
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(getClass().getResource("/login.fxml"));
//        Parent root = loader.load();
//        Scene scene = new Scene(root);
//        Stage stage = (Stage)logout.getScene().getWindow();
//        stage.setScene(scene);
//        stage.show();
//    }





}
