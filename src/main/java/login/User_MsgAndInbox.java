package login;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class User_MsgAndInbox extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(Login.class.getResource("user_msg_and_inbox.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 768);
        stage.setTitle("User Message");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
