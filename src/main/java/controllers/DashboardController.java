package controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
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

public class DashboardController extends NavigationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Circle imgCircle;
    @FXML
    private Text currentUserName;







    @FXML
    void initialize() throws SQLException {
        super.initialize();
        assert currentUserName != null : "fx:id=\"currentUserName\" was not injected: check your FXML file 'Dashboard.fxml'.";
        assert imgCircle != null : "fx:id=\"imgCircle\" was not injected: check your FXML file 'Dashboard.fxml'.";

    }





}
