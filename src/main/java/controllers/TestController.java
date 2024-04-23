package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class TestController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Circle circle;

    @FXML
    void initialize() {
        assert circle != null : "fx:id=\"circle\" was not injected: check your FXML file 'test.fxml'.";
        circle.setStroke(Color.SEAGREEN);
        Image in =new Image("/IMG-20221027-WA0012.jpg", false);
        circle.setFill(new ImagePattern(in));

    }

}
