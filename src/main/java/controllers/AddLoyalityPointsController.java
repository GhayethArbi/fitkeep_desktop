package controllers;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.User;
import repository.UserRepository;
import services.ServiceUser;
import services.session.UserSession;

public class AddLoyalityPointsController extends NavigationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text currentUserName;

    @FXML
    private Text currentUserNameUp;


    @FXML
    private Circle image;

    @FXML
    private Circle imgCircle;

    @FXML
    private Text pointsError;

    @FXML
    private TextField ftpoints;

    private final UserRepository userRepository = new UserRepository();

    @FXML
    void saveChanges(ActionEvent event) {
        pointsError.setText("");
        User user = ListUsersController.userPass;
        String pointsText = ftpoints.getText().trim(); // Get the text from the TextField and remove leading/trailing whitespace

        if (pointsText.isEmpty()) {
            pointsError.setText("Loyality points cannot be empty");
        } else {

            int points = Integer.parseInt(pointsText);
            if (points < 0) {
                pointsError.setText("Points cannot be negative");
            } else {
                user.setLoyalityPoints(points);
                ServiceUser serviceUser = new ServiceUser();
                try {
                    serviceUser.updateOne(user);
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("User updated");
                    alert.setContentText("Loyality Points added with succefull.");
                    alert.show();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }






    @FXML
    void initialize() throws SQLException {
        super.initialize();
        assert currentUserName != null : "fx:id=\"currentUserName\" was not injected: check your FXML file 'AddLoyalityPoints.fxml'.";
        assert currentUserNameUp != null : "fx:id=\"currentUserNameUp\" was not injected: check your FXML file 'AddLoyalityPoints.fxml'.";
        assert ftpoints != null : "fx:id=\"ftpoints\" was not injected: check your FXML file 'AddLoyalityPoints.fxml'.";
        assert image != null : "fx:id=\"image\" was not injected: check your FXML file 'AddLoyalityPoints.fxml'.";
        assert imgCircle != null : "fx:id=\"imgCircle\" was not injected: check your FXML file 'AddLoyalityPoints.fxml'.";
        assert pointsError != null : "fx:id=\"pointsError\" was not injected: check your FXML file 'AddLoyalityPoints.fxml'.";
        User user= ListUsersController.userPass;
        currentUserNameUp.setText(user.getName()+" "+user.getLastName());

        image.setStroke(Color.SEAGREEN);
        Image img =new Image("/IMG-20221027-WA0012.jpg", false);
        image.setFill(new ImagePattern(img));
    }

}
