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

import java.sql.SQLException;

public class NavigationController {

    @FXML
    private Text currentUserName;
    @FXML
    private Circle imgCircle;

    @FXML
    void goToAddToCart(ActionEvent event) {
        Stage stage = (Stage) currentUserName.getScene().getWindow(); // Get reference to the login window's stage
        try {
            stage.setTitle("Add to Cart");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/add_panier.fxml"));
            Parent p = loader.load();
            Scene scene = new Scene(p);
            stage.setScene(scene);
        } catch (Exception e){
            System.err.println(e);
        }

    }
    @FXML
    void goToOverviewUser(ActionEvent event){
        Stage stage = (Stage) currentUserName.getScene().getWindow(); // Get reference to the login window's stage
        try {
            stage.setTitle("Show User Details");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowOneUser.fxml"));
            Parent p = loader.load();
            Scene scene = new Scene(p);
            stage.setScene(scene);
        } catch (Exception e){
            System.err.println(e);
        }
    }

    @FXML
    void addLoyalityPoints(ActionEvent event){
        Stage stage = (Stage) currentUserName.getScene().getWindow(); // Get reference to the login window's stage
        try {
            stage.setTitle("Show User Details");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddLoyalityPoints.fxml"));
            Parent p = loader.load();
            Scene scene = new Scene(p);
            stage.setScene(scene);
        } catch (Exception e){
            System.err.println(e);
        }
    }
    @FXML
    void goToRecette(ActionEvent event){
        try {
            Stage stage = (Stage) currentUserName.getScene().getWindow(); // Get reference to the login window's stage
            stage.setTitle("Recettes");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/recette/AfficherRecette.fxml"));
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
    void goToLogin(ActionEvent event) {
        try {
            Stage stage = (Stage) currentUserName.getScene().getWindow(); // Get reference to the login window's stage
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
    void goToCategoryDetails(ActionEvent event) {
        Stage stage = (Stage) currentUserName.getScene().getWindow(); // Get reference to the login window's stage
        try {
            stage.setTitle("Category Details");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Category_details.fxml"));
            Parent p = loader.load();
            Scene scene = new Scene(p);
            stage.setScene(scene);
        } catch (Exception e){
            System.err.println(e);
        }

    }
    @FXML
    void goToProduct(ActionEvent event) {
        Stage stage = (Stage) currentUserName.getScene().getWindow(); // Get reference to the login window's stage
        try {
            stage.setTitle("Product Details");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ProductDetails.fxml"));
            Parent p = loader.load();
            Scene scene = new Scene(p);
            stage.setScene(scene);
        } catch (Exception e){
            System.err.println(e);
        }
    }
    @FXML
    void goToDash() {
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
    public void goToUsers() {
        Stage stage = (Stage) this.currentUserName.getScene().getWindow(); // Get reference to the login window's stage
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListUsers.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setTitle("User List");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e){
            System.err.println(e);
        }
    }
    @FXML
    public void goToChangePassword() {
        Stage stage = (Stage) this.currentUserName.getScene().getWindow(); // Get reference to the login window's stage
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ChangePassword.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setTitle("Change Password");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e){
            System.err.println(e);
        }
    }
    @FXML
    public void goToOverview() {
        Stage stage = (Stage) this.currentUserName.getScene().getWindow(); // Get reference to the login window's stage
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowProfile.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setTitle("Profile Setting");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e){
            System.err.println(e);
        }
    }
    @FXML
    public void Logout() {
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
    public void goToObjectives(){
        Stage stage = (Stage) this.currentUserName.getScene().getWindow(); // Get reference to the login window's stage
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherObjectifs.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setTitle("Objectives Details");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e){
            System.err.println(e);
        }
    }
    @FXML
    public void goToActivites (){
        Stage stage = (Stage) this.currentUserName.getScene().getWindow(); // Get reference to the login window's stage
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherActivites.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setTitle("Activities Details");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e){
            System.err.println(e);
        }
    }
    @FXML
    public void goToEditProfile() {
        Stage stage = (Stage) this.currentUserName.getScene().getWindow(); // Get reference to the login window's stage
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ProfileSetting.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setTitle("Profile edit");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e){
            System.err.println(e);
        }
    }
    @FXML
    void initialize() throws SQLException {
        currentUserName.setText(UserSession.CURRENT_USER.getUserLoggedIn().getName()+" "+UserSession.CURRENT_USER.getUserLoggedIn().getLastName());
        imgCircle.setStroke(Color.SEAGREEN);
        Image in =new Image("/IMG-20221027-WA0012.jpg", false);
        imgCircle.setFill(new ImagePattern(in));

    }

}
