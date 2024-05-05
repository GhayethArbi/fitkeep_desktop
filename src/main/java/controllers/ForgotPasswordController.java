package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import services.OTPService;
import services.ServiceUser;

public class ForgotPasswordController {


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text emailError;

    @FXML
    private TextField ftEmail;


    private final ServiceUser serviceUser = new ServiceUser();

    @FXML
    void findAccount(ActionEvent event) {
        if(serviceUser.emailExists(ftEmail.getText())){
            String otp= OTPService.generateOTP(ftEmail.getText(),8);
            OTPService.sendOTP(ftEmail.getText(),otp);
            try {
                Stage stage = (Stage) ftEmail.getScene().getWindow(); // Get reference to the login window's stage
                stage.setTitle("ForgotPassword");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ResetPassword.fxml"));
                Parent p = loader.load();

                Scene scene = new Scene(p);

                stage.setScene(scene);

                stage.show();
            } catch (Exception e) {

                e.printStackTrace();
                // Handle navigation failure
            }

        }else
            emailError.setText("The email address does not exist.");


    }
    @FXML
    void goToLogin(ActionEvent event) {
        try {
            Stage stage = (Stage) ftEmail.getScene().getWindow(); // Get reference to the login window's stage
            stage.setTitle("Login");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
            Parent p = loader.load();
            Scene scene = new Scene(p);

            stage.setScene(scene);

            stage.show();
        } catch (Exception e) {
            System.out.println("__________________________________forgotpassword__________________________________");
            e.printStackTrace();
            // Handle navigation failure
        }

    }

    @FXML
    void initialize() {

        assert emailError != null : "fx:id=\"emailError\" was not injected: check your FXML file 'ForgotPassword.fxml'.";
        assert ftEmail != null : "fx:id=\"ftEmail\" was not injected: check your FXML file 'ForgotPassword.fxml'.";

    }

}