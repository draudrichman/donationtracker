package login;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.HashMap;
import java.util.Scanner;

public class WriteThreadServer implements Runnable{

    private Thread thr;
    String name;
    public HashMap<String, NetworkUtil> clientMap;

    public WriteThreadServer(HashMap<String, NetworkUtil> map, String name) {
        this.clientMap = map;
        this.name = name;
        this.thr = new Thread(this);
        thr.start();
    }

    public void run() {
        try {
            Scanner input = new Scanner(System.in);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("admin_msg_and_inbox.fxml)"));
            Admin_Server_Controller acc = fxmlLoader.getController();

            while (true) {

                String message = acc.messageField.getText();

                HBox hBox = new HBox();
                hBox.setAlignment(Pos.CENTER_RIGHT);
                hBox.setPadding(new Insets(5, 5, 5, 10));

                Text text = new Text(message);
                TextFlow textFlow = new TextFlow(text);

                textFlow.setStyle("-fx-color: rgb(239, 242, 255)" + "-fx-background-color: rgb(15, 125, 242)" + "-fx-background-radius: 20px");
                textFlow.setPadding(new Insets(5, 10 , 5, 10));
                text.setFill(Color.color(0.934, 0.945, 0.996));

                hBox.getChildren().add(textFlow);
                acc.vBox_message.getChildren().add(hBox);

                //System.out.println("Enter the name of the client to send, a message to send: ");
                //String[] s = input.nextLine().split(",");
                String[] s = message.split(",");

                NetworkUtil networkUtil = clientMap.get(s[0]);

                if (networkUtil != null) {
                    networkUtil.write(name + ":" + s[1]);
                }

                acc.messageField.clear();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
