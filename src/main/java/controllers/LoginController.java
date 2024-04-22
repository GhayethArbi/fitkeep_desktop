package controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import services.UserDao;
import services.session.UserSession;

public class LoginController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private Label invalidLogin;

    @FXML
    private Label ft;
    @FXML
    private Label currentUser;

    @FXML
    private PasswordField pfPassword;

    @FXML
    private Text emailError;
    @FXML
    private Text passError;
    @FXML
    private TextField tfEmail;
    void clearError(){
        invalidLogin.setText("");
        currentUser.setText("");
    }
    @FXML
    void handleLogin(ActionEvent event) {
        clearError();
        if (UserSession.CURRENT_USER==null) {

            try {
                UserDao userDao = new UserDao();
                // Assuming login method returns a boolean indicating success or failure
                String loggedIn = userDao.login(tfEmail.getText(), pfPassword.getText());
                // Login successful, proceed to next screen or action
                // For now, just display a success message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Login Successful");
                alert.setContentText("Welcome!" + loggedIn);
                alert.show();

                Stage stage = (Stage) tfEmail.getScene().getWindow(); // Get reference to the login window's stage
                stage.setTitle("Dashboard");


                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Dashboard.fxml"));
                Parent p = loader.load();
                Scene scene = new Scene(p);

                stage.setScene(scene);




            } catch (SQLException e) {
                // Error handling for database operation
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Database Error");
                alert.setContentText("An error occurred while accessing the database.");
                alert.show();
            }
            catch (Exception exception){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Navigation Error");
                alert.setContentText("An error occurred while accessing the nextpage. "+ exception.getMessage());
                alert.show();
            }
        }else {
            currentUser.setText(UserSession.CURRENT_USER.getUserLoggedIn().getEmail()+" is already Logged in!");
        }
    }
    @FXML
    void navigateToRegister(ActionEvent event) {
        try {
            Stage stage = (Stage) tfEmail.getScene().getWindow(); // Get reference to the login window's stage
            stage.setTitle("Register");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Register.fxml"));
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
        assert ft != null : "fx:id=\"ft\" was not injected: check your FXML file 'Login.fxml'.";
        assert pfPassword != null : "fx:id=\"pfPassword\" was not injected: check your FXML file 'Login.fxml'.";
        assert tfEmail != null : "fx:id=\"tfEmail\" was not injected: check your FXML file 'Login.fxml'.";

    }


}
