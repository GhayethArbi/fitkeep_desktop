package controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
<<<<<<< HEAD
import models.Category;
import models.User;
import services.ServiceCategory;
=======
import models.User;
import services.ServiceUser;
>>>>>>> ca935f6e13b21fdf28b7c8dbf7f9751984a06275

public class AjouterUserFXML {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfLastName;

    @FXML
    void ajouterUser(ActionEvent event) {
        try {
<<<<<<< HEAD
            Category c = new Category();
            ServiceCategory cat = new ServiceCategory();
            cat.insertOne(c);
=======
            User u = new User(tfName.getText(), tfLastName.getText(), tfEmail.getText());
            ServiceUser su = new ServiceUser();
            su.insertOne(u);
>>>>>>> ca935f6e13b21fdf28b7c8dbf7f9751984a06275
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("Vous avez une erreur dans la saisie de vos données!");
            alert.show();
        }catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("Vous avez une erreur dans la saisie de vos données!");
            alert.show();
        }
    }

    @FXML
    void initialize() {
        assert tfEmail != null : "fx:id=\"tfEmail\" was not injected: check your FXML file 'AjouterUserFXML.fxml'.";
        assert tfName != null : "fx:id=\"tfName\" was not injected: check your FXML file 'AjouterUserFXML.fxml'.";
        assert tfLastName != null : "fx:id=\"tfLastName\" was not injected: check your FXML file 'AjouterUserFXML.fxml'.";

    }

}
