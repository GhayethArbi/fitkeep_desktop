package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import models.User;
import services.session.UserSession;

public class ShowOneUserController extends NavigationController{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text currentUserName;

    @FXML
    private Text currentUserNameUp;

    @FXML
    private Text ftAddress;

    @FXML
    private Text ftBirth;

    @FXML
    private Text ftEmail;

    @FXML
    private Text ftGender;

    @FXML
    private Text ftLast;

    @FXML
    private Text ftName;

    @FXML
    private Text ftPhone;

    @FXML
    private Circle image;

    @FXML
    private Circle imgCircle;

    @FXML
    void goToOverviewUser(){}

    @FXML
    void addLoyalityPoints(){}



    @FXML
    void initialize() {
        assert currentUserName != null : "fx:id=\"currentUserName\" was not injected: check your FXML file 'ShowOneUser.fxml'.";
        assert currentUserNameUp != null : "fx:id=\"currentUserNameUp\" was not injected: check your FXML file 'ShowOneUser.fxml'.";
        assert ftAddress != null : "fx:id=\"ftAddress\" was not injected: check your FXML file 'ShowOneUser.fxml'.";
        assert ftBirth != null : "fx:id=\"ftBirth\" was not injected: check your FXML file 'ShowOneUser.fxml'.";
        assert ftEmail != null : "fx:id=\"ftEmail\" was not injected: check your FXML file 'ShowOneUser.fxml'.";
        assert ftGender != null : "fx:id=\"ftGender\" was not injected: check your FXML file 'ShowOneUser.fxml'.";
        assert ftLast != null : "fx:id=\"ftLast\" was not injected: check your FXML file 'ShowOneUser.fxml'.";
        assert ftName != null : "fx:id=\"ftName\" was not injected: check your FXML file 'ShowOneUser.fxml'.";
        assert ftPhone != null : "fx:id=\"ftPhone\" was not injected: check your FXML file 'ShowOneUser.fxml'.";
        assert image != null : "fx:id=\"image\" was not injected: check your FXML file 'ShowOneUser.fxml'.";
        assert imgCircle != null : "fx:id=\"imgCircle\" was not injected: check your FXML file 'ShowOneUser.fxml'.";
        User user= ListUsersController.userPass;
        currentUserNameUp.setText(user.getName()+" "+user.getLastName());

        ftAddress.setText(user.getAddress());
        ftBirth.setText(user.getBirthDay().toString());
        ftLast.setText(user.getLastName());
        ftEmail.setText(user.getEmail());
        ftName.setText(user.getName());
        ftGender.setText(user.getGender());
        ftPhone.setText(String.valueOf(user.getPhoneNumber()));
        image.setStroke(Color.SEAGREEN);
        Image img =new Image("/IMG-20221027-WA0012.jpg", false);
        image.setFill(new ImagePattern(img));



    }

}
