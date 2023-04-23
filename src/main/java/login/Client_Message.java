package login;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Client_Message extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("user_msg_and_inbox.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 768);
        stage.setTitle("Client Message");
        stage.setScene(scene);
        stage.show();
    }
}
