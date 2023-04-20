package login;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import login.Login;

import java.io.IOException;

public class adminDashboard_Campaigns extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("admin-dashboard-campaigns.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1296, 864);
        stage.setTitle("Admin Dashboard");
        stage.setScene(scene);
        stage.show();
    }
}