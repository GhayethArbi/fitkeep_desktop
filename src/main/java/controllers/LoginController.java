package controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.prefs.Preferences;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    private CheckBox rememberMeCheckbox;
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
    private static final String REMEMBER_ME_EMAIL_KEY = "rememberMeEmail";
    private static final String REMEMBER_ME_PASSWORD_KEY = "rememberMePassword";
    private final  Preferences prefs = Preferences.userNodeForPackage(LoginController.class);
    void clearError(){
        invalidLogin.setText("");
        currentUser.setText("");


    }
    void clearLoginInfo(){
        prefs.remove(REMEMBER_ME_EMAIL_KEY);
        prefs.remove(REMEMBER_ME_PASSWORD_KEY);
    }
    private void saveLoginInfo( String email, String password) {

        prefs.put(REMEMBER_ME_EMAIL_KEY, email);
        prefs.put(REMEMBER_ME_PASSWORD_KEY, password);
    }
    private String getSavedEmail() {

        return prefs.get(REMEMBER_ME_EMAIL_KEY, null);
    }

    private String getSavedPassword() {

        return prefs.get(REMEMBER_ME_PASSWORD_KEY, null);
    }
    @FXML
    void handleLogin(ActionEvent event) {
        clearError();
        String email = tfEmail.getText();
        String password = pfPassword.getText();


        if (UserSession.CURRENT_USER==null) {
            boolean rememberMe = rememberMeCheckbox.isSelected();
            if (rememberMe) {
                saveLoginInfo(email, password);
            } else {
                clearLoginInfo();
            }
            try {
                UserDao userDao = new UserDao();
                // Assuming login method returns a boolean indicating success or failure
                String loggedIn = userDao.login(tfEmail.getText(), pfPassword.getText());
                if(loggedIn.equalsIgnoreCase("Your account is banned!")){
                    System.out.println("if(loggedIn.equalsIgnoreCase(Your account is banned!)){");
                    invalidLogin.setText(loggedIn);

                }
                else {
                    // Login successful, proceed to next screen or action
                    // For now, just display a success messagere
                /*Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Login Successful");
                alert.setContentText("Welcome!" + loggedIn);
                alert.show();*/

                    Stage stage = (Stage) tfEmail.getScene().getWindow(); // Get reference to the login window's stage
                    stage.setTitle("Dashboard");


                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Dashboard.fxml"));
                    Parent p = loader.load();
                    Scene scene = new Scene(p);

                    stage.setScene(scene);


                }

            } catch (SQLException e) {
                // Error handling for database operation
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Database Error");
                alert.setContentText("An error occurred while accessing the database.");
                alert.show();
                System.out.println("An error occurred while accessing the database."+e);
            }
            catch (Exception exception){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Navigation Error");
                alert.setContentText("An error occurred while accessing the nextpage. "+ exception.getMessage());
                alert.show();
                System.out.println("An error occurred while accessing the database."+2);
                exception.printStackTrace();
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
    // Method to initialize multiple users' credentials

    @FXML
    void initialize() {
        assert ft != null : "fx:id=\"ft\" was not injected: check your FXML file 'Login.fxml'.";
        assert pfPassword != null : "fx:id=\"pfPassword\" was not injected: check your FXML file 'Login.fxml'.";
        assert tfEmail != null : "fx:id=\"tfEmail\" was not injected: check your FXML file 'Login.fxml'.";
        assert rememberMeCheckbox != null : "fx:id=\"rememberMeCheckbox\" was not injected: check your FXML file 'Login.fxml'.";
        tfEmail.setText(getSavedEmail());
        pfPassword.setText(getSavedPassword());
        rememberMeCheckbox.setSelected(true);
    }


}
