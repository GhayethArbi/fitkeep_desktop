package controllers.Front;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class modifyUser {




        @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;

        @FXML
        private Text addressError;

        @FXML
        private Text birthError;

        @FXML
        private Text emailError;

        @FXML
        private Text fnError;

        @FXML
        private TextField ftAddress;

        @FXML
        private DatePicker ftBirth;

        @FXML
        private TextField ftEmail;

        @FXML
        private RadioButton ftFemale;

        @FXML
        private TextField ftLastName;

        @FXML
        private RadioButton ftMale;

        @FXML
        private TextField ftName;

        @FXML
        private TextField ftPhone;

        @FXML
        private Text genderError;

        @FXML
        private ToggleGroup genderToggleGroup;

        @FXML
        private Text lnError;

        @FXML
        private Text phoneError;

        @FXML
        void getBack(MouseEvent mouseEvent) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Front/Accueil.fxml"));
            Parent root = fxmlLoader.load();

            // Get the current stage
            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

            // Create a new scene with the root and set it to the stage
            stage.setScene(new Scene(root));
            stage.show();
        }

        @FXML
        void goToEditProfile(ActionEvent event) {

        }

        @FXML
        void saveChangesUser(ActionEvent event) {

        }

        @FXML
        void initialize() {
            assert addressError != null : "fx:id=\"addressError\" was not injected: check your FXML file 'modifyUser.fxml'.";
            assert birthError != null : "fx:id=\"birthError\" was not injected: check your FXML file 'modifyUser.fxml'.";
            assert emailError != null : "fx:id=\"emailError\" was not injected: check your FXML file 'modifyUser.fxml'.";
            assert fnError != null : "fx:id=\"fnError\" was not injected: check your FXML file 'modifyUser.fxml'.";
            assert ftAddress != null : "fx:id=\"ftAddress\" was not injected: check your FXML file 'modifyUser.fxml'.";
            assert ftBirth != null : "fx:id=\"ftBirth\" was not injected: check your FXML file 'modifyUser.fxml'.";
            assert ftEmail != null : "fx:id=\"ftEmail\" was not injected: check your FXML file 'modifyUser.fxml'.";
            assert ftFemale != null : "fx:id=\"ftFemale\" was not injected: check your FXML file 'modifyUser.fxml'.";
            assert ftLastName != null : "fx:id=\"ftLastName\" was not injected: check your FXML file 'modifyUser.fxml'.";
            assert ftMale != null : "fx:id=\"ftMale\" was not injected: check your FXML file 'modifyUser.fxml'.";
            assert ftName != null : "fx:id=\"ftName\" was not injected: check your FXML file 'modifyUser.fxml'.";
            assert ftPhone != null : "fx:id=\"ftPhone\" was not injected: check your FXML file 'modifyUser.fxml'.";
            assert genderError != null : "fx:id=\"genderError\" was not injected: check your FXML file 'modifyUser.fxml'.";
            assert genderToggleGroup != null : "fx:id=\"genderToggleGroup\" was not injected: check your FXML file 'modifyUser.fxml'.";
            assert lnError != null : "fx:id=\"lnError\" was not injected: check your FXML file 'modifyUser.fxml'.";
            assert phoneError != null : "fx:id=\"phoneError\" was not injected: check your FXML file 'modifyUser.fxml'.";

        }

    }
