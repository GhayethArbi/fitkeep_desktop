package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import services.session.UserSession;

public class DashboardController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text userName;

    @FXML
    void Logout(ActionEvent event) {
        Stage stage = (Stage) this.userName.getScene().getWindow(); // Get reference to the login window's stage
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 822, 495);
            stage.setTitle("Login");
            stage.setScene(scene);
            stage.show();
            UserSession.CURRENT_USER.logout();
            } catch (Exception e){
            System.err.println(e);
        }
    }
    @FXML
    void goToEditProfile(ActionEvent event) {
        Stage stage = (Stage) this.userName.getScene().getWindow(); // Get reference to the login window's stage
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ProfileSetting.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 1200, 600);
            stage.setTitle("Edit Profile");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e){
            System.err.println(e);
        }
    }
    @FXML
    void goToListUsers(ActionEvent event) {
        Stage stage = (Stage) this.userName.getScene().getWindow(); // Get reference to the login window's stage
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListUsers.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 1200, 600);
            stage.setTitle("User List");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e){
            System.err.println(e);
        }
    }
    @FXML
    void initialize() {
        assert userName != null : "fx:id=\"userName\" was not injected: check your FXML file 'Dashboard.fxml'.";
        userName.setText(UserSession.CURRENT_USER.getUserLoggedIn().getEmail());
    }

}
