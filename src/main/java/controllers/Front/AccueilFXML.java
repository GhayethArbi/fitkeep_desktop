package controllers.Front;

import controllers.NavigationController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.session.UserSession;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AccueilFXML extends NavigationController implements Initializable {
    @FXML
    private Stage stage;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void MoveToActivity(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/AfficherObjectifs.fxml"));
            Parent root = loader.load();

            stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void mouveToNutrition(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/recette/recetteFront.fxml"));
            Parent root = loader.load();

            stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void logOut(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 822, 495);
            Stage loginStage = new Stage(); // Créez une nouvelle instance de Stage
            loginStage.setTitle("Login");
            loginStage.setScene(scene);
            loginStage.show();
            UserSession.CURRENT_USER.logout();
            // Fermez la fenêtre actuelle
            ((Stage) ((Node) mouseEvent.getSource()).getScene().getWindow()).close();
        } catch (IOException e){
            System.err.println(e);
        }
    }

    public void MoveToPrSettings(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/modifyUser.fxml"));
            Parent root = loader.load();

            stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void moveToShop(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/game.fxml"));
            Parent root = loader.load();

            stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
