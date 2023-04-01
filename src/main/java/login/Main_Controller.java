package login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main_Controller {

    @FXML

    public Stage stage;
    public Scene scene;
    public Parent root;
    public AnchorPane anchorPane;

    //Log In Button From Login Page.
    public void logIn(ActionEvent actionEvent) throws IOException {

        root = FXMLLoader.load(getClass().getResource("campaignDetails.fxml"));
        stage = (Stage) ((Node)(actionEvent.getSource())).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Donation Details");
        stage.setScene(scene);
        stage.show();
    }

    //Sign Up Button From Login Page.
    public void goToSignUp(ActionEvent actionEvent) throws IOException {

        root = FXMLLoader.load(getClass().getResource("signup.fxml"));
        stage = (Stage) ((Node)(actionEvent.getSource())).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Sign Up");
        stage.setScene(scene);
        stage.show();
    }

    //Reset Password Button From Login Page.
    public void goToResetPassword(ActionEvent actionEvent) throws IOException {

        root = FXMLLoader.load(getClass().getResource("reset_password.fxml"));
        stage = (Stage) ((Node)(actionEvent.getSource())).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Reset Password");
        stage.setScene(scene);
        stage.show();
    }

    //Exit Button From Login Page.
    public void exitFromLogin(ActionEvent actionEvent) {

        stage = (Stage) anchorPane.getScene().getWindow();
        stage.close();
    }

    //Sign Up Button From SignUp Page.
    public void signUp(ActionEvent actionEvent) throws IOException {

        root = FXMLLoader.load(getClass().getResource("campaignDetails.fxml"));
        stage = (Stage) ((Node)(actionEvent.getSource())).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Donation Details");
        stage.setScene(scene);
        stage.show();
    }

    //Log In Button From SignUP Page.
    public void backToLoginPage(ActionEvent actionEvent) throws IOException {

        root = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage = (Stage) ((Node)(actionEvent.getSource())).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Log In");
        stage.setScene(scene);
        stage.show();
    }

    //Donate Now Button From Donation_Details Page.
    public void goToDonationPayment(ActionEvent actionEvent) throws IOException {

        root = FXMLLoader.load(getClass().getResource("donation_payment.fxml"));
        stage = (Stage) ((Node)(actionEvent.getSource())).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Donation Payment");
        stage.setScene(scene);
        stage.show();
    }

    //Confirm Payment Button From Donation_Details Page.
    public void goToDonationConfirmation(ActionEvent actionEvent) throws IOException {

        root = FXMLLoader.load(getClass().getResource("donation_confirmation.fxml"));
        stage = (Stage) ((Node)(actionEvent.getSource())).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Donation Confirmation");
        stage.setScene(scene);
        stage.show();
    }

    //Return to Campaign Button From Donation_Payment Page.
    public void goBackToDonationDetails(ActionEvent actionEvent) throws IOException {

        root = FXMLLoader.load(getClass().getResource("campaignDetails.fxml"));
        stage = (Stage) ((Node)(actionEvent.getSource())).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Donation Details");
        stage.setScene(scene);
        stage.show();
    }

}