package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import services.session.UserSession;

public class NavigationController {

    @FXML
    private Text currentUserName;
    @FXML
    private Circle imgCircle;
    @FXML
    void goToDash(ActionEvent event) {
        Stage stage = (Stage) currentUserName.getScene().getWindow(); // Get reference to the login window's stage
        try {
            stage.setTitle("Dashboard");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Dashboard.fxml"));
            Parent p = loader.load();
            Scene scene = new Scene(p);
            stage.setScene(scene);
        } catch (Exception e){
            System.err.println(e);
        }
    }
    @FXML
    public void goToUsers(ActionEvent event) {
        Stage stage = (Stage) this.currentUserName.getScene().getWindow(); // Get reference to the login window's stage
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
    public void Logout(ActionEvent event) {
        Stage stage = (Stage) this.currentUserName.getScene().getWindow(); // Get reference to the login window's stage
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
    public void goToEditProfile(ActionEvent event) {
        Stage stage = (Stage) this.currentUserName.getScene().getWindow(); // Get reference to the login window's stage
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
    void initialize() {
        currentUserName.setText(UserSession.CURRENT_USER.getUserLoggedIn().getName()+" "+UserSession.CURRENT_USER.getUserLoggedIn().getLastName());
        imgCircle.setStroke(Color.SEAGREEN);
        Image in =new Image("/IMG-20221027-WA0012.jpg", false);
        imgCircle.setFill(new ImagePattern(in));
    }
}
