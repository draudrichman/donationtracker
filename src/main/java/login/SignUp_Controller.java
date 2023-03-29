package login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SignUp_Controller {

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
    RadioButton fundraiserRb, contributorRb;

    @FXML
    TextField UserName, Name, Email;

    @FXML
    PasswordField Password, Confirm_Password;

    @FXML
    Button Login, SignUp;

    //Variables to contain user information.
    String username, name, email, password, confirmed_password;

    //Variables to visualize the validation of the fields.
    String successMessage = String.format("-fx-text-fill: GREEN;");
    String errorMessage = String.format("-fx-text-fill: RED;");
    String errorStyle = String.format("-fx-border-color: RED; -fx-border-width: 2; -fx-border-radius: 5;");
    String successStyle = String.format("-fx-border-color: #A9A9A9; -fx-border-width: 2; -fx-border-radius: 5;");

    //Methods For the Buttons.

    //Method 1: Makes a new user by signing them up.
    public void signUp(ActionEvent actionEvent) throws IOException {

        //Extracting text from the Text fields.
        username = UserName.getText();
        name = Name.getText();
        email = Email.getText();
        password = Password.getText();
        confirmed_password = Confirm_Password.getText();

        //Creating a new Contributor/Fundraiser.
        Users newUser = null;

        if(fundraiserRb.isSelected()){
            newUser = new FundRaiser(username, name, email, password);
        }
        else if(contributorRb.isSelected()){
            newUser = new Contributor(username, name, email, password);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("User type not selected!");
            alert.setContentText("Please select the type of newUser you want to signup as!");
            alert.show();
        }

        //Variables for the connection to a database.
        Connection connection = null;
        PreparedStatement psInsertValue = null;
        PreparedStatement psCheckUserExist = null;
        ResultSet resultName = null;
        ResultSet resultEmail = null;
        ResultSet resultUserId = null;

        ///String driver = "com.mysql.jdbc.Driver";
        ///String pass = "487@SaaD";
        //String newUser = "root";
        String url = "jdbc:mysql://localhost:3306/donation_tracker";
        String user = "saad";
        String pass = "123@saad";

        if (username.equals("") || name.equals("") || email.equals("") || password.equals("") || confirmed_password.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Empty text fields!");
            alert.setContentText("Please fill up all the information!");
            alert.show();
        }
        else {

            //Storing newUser information into a database.
            try {

                //Class.forName(driver);

                //Establishing the database connection.
                connection = DriverManager.getConnection(url, user, pass);

                //Storing the newUser info of given username in the resultName variable.
                psCheckUserExist = connection.prepareStatement("SELECT * FROM signup WHERE username = ?");
                psCheckUserExist.setString(1, username);
                resultName = psCheckUserExist.executeQuery();

                //Storing the newUser info of given email in the resultEmail variable.
                psCheckUserExist = connection.prepareStatement("SELECT * FROM signup WHERE email = ?");
                psCheckUserExist.setString(1, email);
                resultEmail = psCheckUserExist.executeQuery();

                //Checks if the username is already taken or not. Returns true if username is taken.
                if (resultName.isBeforeFirst()) {

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("You can't use this username!");
                    alert.setContentText("Username is already taken! Choose a new username.");
                    alert.show();
                }
                //Checks if the email is already taken or not. Returns true if email is taken.
                else if (resultEmail.isBeforeFirst()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("You can't use this email!");
                    alert.setContentText("Email is already taken! Choose another email.");
                    alert.show();
                }
                //If the username & email is unique inserts the data into the signup table.
                else {

                    if (!confirmed_password.equals(password)) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText("Wrong Password!");
                        alert.setContentText("Use the same password in the confirm password field!");
                        alert.show();
                    }
                    else {
                        psInsertValue = connection.prepareStatement("INSERT INTO signup (username, name, email, password, reset_code, user_type) VALUES(?, ?, ?, ?, ?, ?)");
                        psInsertValue.setString(1, username);
                        psInsertValue.setString(2, name);
                        psInsertValue.setString(3, email);
                        psInsertValue.setString(4, password);
                        psInsertValue.setString(5, Integer.toString(newUser.getReset_code()));
                        psInsertValue.setString(6, newUser.getUser_type());
                        psInsertValue.executeUpdate();

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setHeaderText("Signup Successful!");
                        alert.setContentText("Your account has been created successfully!");
                        alert.show();

                        //Proceeds to the next scene.
                        root = FXMLLoader.load(getClass().getResource("donation_details.fxml"));
                        stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setTitle("Donation Details");
                        stage.setScene(scene);
                        stage.show();
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {

                //Closing all the connections to the database.
                if (resultName != null) {
                    try {
                        resultName.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (resultEmail != null) {
                    try {
                        resultEmail.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (psCheckUserExist != null) {
                    try {
                        psCheckUserExist.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (psInsertValue != null) {
                    try {
                        psInsertValue.close();
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

    //Method 2: Take backs to the Login Page.
    public void backToLoginPage(ActionEvent actionEvent) throws IOException {

        root = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage = (Stage) ((Node)(actionEvent.getSource())).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Log In");
        stage.setScene(scene);
        stage.show();
    }
}
