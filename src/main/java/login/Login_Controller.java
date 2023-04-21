package login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Objects;
import java.util.ResourceBundle;

public class Login_Controller implements Initializable {

    //Fx components for the fx:id.
    @FXML
    Stage stage;

    @FXML
    Scene scene;

    @FXML
    Parent root;

    @FXML
    AnchorPane anchorPane;

    @FXML
    TextField UserName;

    @FXML
    PasswordField Password;

    @FXML
    Button LogIn, SignUp, Reset_Password, Exit;

    //Variables to contain user information.
    String username, password;


    //Methods For the Buttons.

    //Method 1: Logs into the user account.
    public void logIn(ActionEvent actionEvent) throws IOException {

        //Extracting text from the text fields.
        username = UserName.getText();
        password = Password.getText();

        if (Objects.equals(username, "admin") && Objects.equals(password, "admin")){
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("admin-dashboard.fxml")));
            stage = (Stage) ((Node)(actionEvent.getSource())).getScene().getWindow();
            scene = new Scene(root);
            stage.setTitle("Admin Dashboard");
            stage.setScene(scene);
            stage.show();
            return;
        }

        //Variables for the connection to a database.
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        //String driver = "com.mysql.jdbc.Driver";
        String url = Constants.DATABASE_URL;
        String user = Constants.DATABASE_USERNAME;
        String pass = Constants.DATABASE_PASSWORD;

        if (username.equals("") || password.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Empty text fields!");
            alert.setContentText("Please fill up all the information!");
            alert.show();
        }
        else {
            try {
                //Class.forName(driver);

                //Establishing the database connection.
                connection = DriverManager.getConnection(url, user, pass);

                //Storing the password in the resultPassword variable.
                preparedStatement = connection.prepareStatement("SELECT password, userID FROM userdetails WHERE username = ?");
                preparedStatement.setString(1, username);
                resultSet = preparedStatement.executeQuery();

                //Checks if the user exist or not. Returns false if the user does not exist.
                if (!resultSet.isBeforeFirst()) {

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("User not Found!");
                    alert.setContentText("Well, this is awkward. Did you forget your password or did your cat walk across your keyboard?");
                    alert.show();
                } else {

                    while (resultSet.next()) {
                        String retrievedPassword = resultSet.getString("password");
                        int retrieveduserID = resultSet.getInt("userID");

                        if (retrievedPassword.equals(password)) {
                            SessionManager.setCurrentUser(retrieveduserID);
                            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("homepage.fxml")));
                            stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
                            scene = new Scene(root);
                            stage.setTitle("Donation Details");
                            stage.setScene(scene);
                            stage.show();
                        } else {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setHeaderText("Wrong Password!");
                            alert.setContentText("Provided password for the username is incorrect!");
                            alert.show();
                        }
                    }
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
        }
    }

    //Method 2: Takes to the SignUp page.
    public void signUp(ActionEvent actionEvent) throws IOException {

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("signup.fxml")));
        stage = (Stage) ((Node)(actionEvent.getSource())).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Sign Up");
        stage.setScene(scene);
        stage.show();
    }

    //Method 3: Takes to the Reset_Password page.
    public void resetPassword(ActionEvent actionEvent) throws IOException {

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("reset_password.fxml")));
        stage = (Stage) ((Node)(actionEvent.getSource())).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Reset Password");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    //Method 4: Exits the program.
//    public void exit(ActionEvent actionEvent) throws IOException {
//
//        stage = (Stage) anchorPane.getScene().getWindow();
//        stage.close();
//    }
}
