package controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.User;
import repository.UserRepository;
import services.OTPService;
import services.UserDao;

public class ChangeForgotPasswordController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text newError;

    @FXML
    private Text confirmError;

    @FXML
    private PasswordField fpConfirm;

    @FXML
    private PasswordField fpNew;

    private final UserRepository userRepository = new UserRepository();

    @FXML
    void changePassword(ActionEvent event) {


        String email =OTPService.EMAIL;
        if (fpNew.getText().isEmpty()) {

            newError.setText("New Password is required");
            fpNew.getStyleClass().add("error");

        } else if (fpNew.getText().length()<8) {

            fpNew.getStyleClass().add("error");
            newError.setText("New Password must contain at least 8 characters.");
        }

        // Confirm password validation
        else if (!fpConfirm.getText().equals(fpNew.getText())) {
            fpConfirm.getStyleClass().add("error");

            confirmError.setText("Passwords do not match");
        }else {
            try {
                User user = new User();
                User user1 = userRepository.findByEmail(email);
                user.setId(user1.getId());
                user.setPassword(fpConfirm.getText());
                userRepository.changePassword(user);
                goToLogin();

            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    @FXML
    void goToLogin() {
        try {
            Stage stage = (Stage) fpNew.getScene().getWindow(); // Get reference to the login window's stage
            stage.setTitle("Login");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
            Parent p = loader.load();
            Scene scene = new Scene(p);

            stage.setScene(scene);

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle navigation failure
        }

    }

    @FXML
    void initialize() {
        assert newError != null : "fx:id=\"newError\" was not injected: check your FXML file 'ChangeForgotPassword.fxml'.";
        assert confirmError != null : "fx:id=\"confirmError\" was not injected: check your FXML file 'ChangeForgotPassword.fxml'.";
        assert fpConfirm != null : "fx:id=\"fpConfirm\" was not injected: check your FXML file 'ChangeForgotPassword.fxml'.";
        assert fpNew != null : "fx:id=\"fpNew\" was not injected: check your FXML file 'ChangeForgotPassword.fxml'.";

    }

}
