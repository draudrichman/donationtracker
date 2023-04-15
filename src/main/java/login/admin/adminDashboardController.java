package login.admin;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import login.Constants;

import java.net.URL;
import java.sql.*;
import java.util.Arrays;
import java.util.ResourceBundle;

public class adminDashboardController implements Initializable {

    @FXML
    private LineChart<String, Integer> lineChart;

    @FXML
    private ImageView refresh;


    @FXML
    private PieChart pieChart;

    String url = Constants.DATABASE_URL;
    String username = Constants.DATABASE_USERNAME;
    String password = Constants.DATABASE_PASSWORD;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Refresh();


    }

    public void Refresh(){
//        refresh.setOnMouseClicked(event -> {
            try {
                inlineChart();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
//        });
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
            series.getData().add(new XYChart.Data<>(String.valueOf(hour), count));
        }


        lineChart.getData().clear();
        lineChart.getData().add(series);
    }

}
