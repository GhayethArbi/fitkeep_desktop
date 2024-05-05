package controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import models.User;
import repository.UserRepository;
import services.session.UserSession;

public class ChangePasswordController extends NavigationController {
    private final UserRepository userRepository = new UserRepository();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text currentUserNameUp;

    @FXML
    private Text confirmError;

    @FXML
    private Text currError;
    @FXML
    private Circle image;

    @FXML
    private PasswordField fpConfirm;

    @FXML
    private PasswordField fpCurrent;

    @FXML
    private PasswordField fpNew;

    @FXML
    private Text newError;

    void clearError(){
        confirmError.setText("");
        currError.setText("");
        newError.setText("");
        fpConfirm.getStyleClass().remove("error");
        fpCurrent.getStyleClass().remove("error");
        fpNew.getStyleClass().remove("error");

    }
    @FXML
    void saveChanges(ActionEvent event) {
        clearError();
        boolean isValid = true;
        if (fpCurrent.getText().isEmpty()) {
            isValid = false;
            currError.setText("Current Password is required");
            fpCurrent.getStyleClass().add("error");
        } else if (fpCurrent.getText().length()<8) {
            currError.setText("Current Password must contain at least 8 characters.");
            fpCurrent.getStyleClass().add("error");
            isValid = false;
        }
// Password validation
        if (fpNew.getText().isEmpty()) {
            isValid = false;
            newError.setText("New Password is required");
            fpNew.getStyleClass().add("error");

        } else if (fpNew.getText().length()<8) {
            isValid = false;
            fpNew.getStyleClass().add("error");
            newError.setText("New Password must contain at least 8 characters.");
        }

        // Confirm password validation
        else if (!fpConfirm.getText().equals(fpNew.getText())) {
            fpConfirm.getStyleClass().add("error");
            isValid = false;
            confirmError.setText("Passwords do not match");
        }
        if(isValid) {
            if (fpCurrent.getText().equals(UserSession.CURRENT_USER.getUserLoggedIn().getPassword())){
                User user = new User();
                user.setPassword(fpNew.getText());
                user.setId(UserSession.CURRENT_USER.getUserLoggedIn().getId());
                try {
                    userRepository.changePassword(user);
                    UserSession.CURRENT_USER.getUserLoggedIn().setPassword(fpNew.getText());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Password changed");
                    alert.setContentText("You are updates your password.");
                    alert.show();

                    goToOverview();

                }catch (SQLException e){
                    e.printStackTrace();
                }
            }else {
                currError.setText("Current Password is incorrect!");
                fpCurrent.getStyleClass().add("error");
            }
        }

    }

    @FXML
    void initialize() {
        super.initialize();
        assert confirmError != null : "fx:id=\"confirmError\" was not injected: check your FXML file 'ChangePassword.fxml'.";
        assert currError != null : "fx:id=\"currError\" was not injected: check your FXML file 'ChangePassword.fxml'.";
        assert fpConfirm != null : "fx:id=\"fpConfirm\" was not injected: check your FXML file 'ChangePassword.fxml'.";
        assert fpCurrent != null : "fx:id=\"fpCurrent\" was not injected: check your FXML file 'ChangePassword.fxml'.";
        assert fpNew != null : "fx:id=\"fpNew\" was not injected: check your FXML file 'ChangePassword.fxml'.";
        assert newError != null : "fx:id=\"newError\" was not injected: check your FXML file 'ChangePassword.fxml'.";
        currentUserNameUp.setText(UserSession.CURRENT_USER.getUserLoggedIn().getName()+" "+UserSession.CURRENT_USER.getUserLoggedIn().getLastName());
        image.setStroke(Color.SEAGREEN);
        Image img =new Image("/IMG-20221027-WA0012.jpg", false);
        image.setFill(new ImagePattern(img));
    }

}
