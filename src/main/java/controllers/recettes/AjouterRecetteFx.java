package controllers.recettes;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.Recette;
import org.controlsfx.control.Notifications;
import services.ServiceRecette;
import javafx.scene.image.Image;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.input.KeyEvent;

import javax.swing.text.html.ImageView;

/** Initializable ?
 * 1) Est une interface
 * 2) But : declarer la methode initialize()
 * cette methode est utilisé pour initialiser les controlleurs des fichiers FXML de javafx
 * */

public class AjouterRecetteFx implements Initializable {

    @FXML
    private TextField fieldName;

    @FXML
    private TextArea fieldDescription;

    @FXML
    private CheckBox checkBoxBreakfast;

    @FXML
    private CheckBox checkBoxBrunch;

    @FXML
    private CheckBox checkBoxLunch;

    @FXML
    private CheckBox checkBoxSnacks;

    @FXML
    private CheckBox checkBoxDinner;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnCancel;

    @FXML
    private Label counter;

    @FXML
    private Label control;
    private Recette recette;


    @FXML
    void ajouterRecette(ActionEvent event) {
        String name = fieldName.getText();
        String description = fieldDescription.getText();
        String category = "";

        if (checkBoxBreakfast.isSelected()) {
            category = "Breakfast";
        } else if (checkBoxBrunch.isSelected()) {
            category = "Brunch";
        } else if (checkBoxLunch.isSelected()) {
            category = "Lunch";
        } else if (checkBoxSnacks.isSelected()) {
            category = "Snacks";
        } else if (checkBoxDinner.isSelected()) {
            category = "Dinner";
        }else { Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Erreur de base de données");
            errorAlert.setContentText("Veuillez selectionner une categorie");
            errorAlert.show();}
        //fin
        try {
            Recette r = new Recette(name, category, new Date(), description);
            ServiceRecette sr = new ServiceRecette();
            if (!name.isEmpty() && !category.isEmpty() && !description.isEmpty()) {
                sr.insertOne(r);
                //---------notif start
                Notifications notification = Notifications.create()
                        .title("Recipe")
                        .text("Your Recipe was  Added  successfully ")
                        .hideAfter(Duration.seconds(5))
                        .position(Pos.BOTTOM_RIGHT)
                        .graphic(null) // No graphic
                        .hideCloseButton(); // Hide close button
                // Apply the CSS styling directly
                          notification.show();
                //---------notif end
            } else {Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Veuillez remplir le formulaire");
                errorAlert.show();}


            //alert

            //fin
        } catch (SQLException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Erreur de base de données");
            errorAlert.setContentText("Erreur lors de l'ajout de la recette à la base de données : " + e.getMessage());
            errorAlert.show();
        }

    }


    @FXML
    void annuler(ActionEvent event) throws IOException {
        Stage stage = new Stage(); // Create a new stage
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/recette/AfficherRecette.fxml")); // Note the leading '/'
        Parent root = loader.load();//necessite IOExeption
        AfficherRecetteFX controller = loader.getController();
        Scene scene = new Scene(root);
        stage.setScene(scene); // Set the scene to the stage
        stage.show(); // Show the stage
        stage.showAndWait();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Text Area multiligne
        fieldDescription.setWrapText(true);
    }

    @FXML
    void descriptionpressed(KeyEvent event) {
        /**
         * La classe ObservableList fait partie du package javafx.collections
         * Role : - Stocker les élements de données dynamique
         *        - Etre notifié lorsque les données changent
         * -----------------------------------------------------------------
         * CharSequence: est une interface de lecture seulement des caractères .
         **/
        ObservableList<CharSequence> list = fieldDescription.getParagraphs();
        int par = list.size();
        // Diviser la paragraphe en des mots chaque chaine de caractere
        // separé par un espace ou plusieurs espaces est compté un mot
        String[] words = fieldDescription.getText().split("\\s+");
        counter.setText("paragraph: "+ par +"   | words : "+ words.length + "  | Characters :"+ fieldDescription.getLength());
        fieldDescription.setStyle("-fx-border-color: green");
        if(fieldDescription.getLength() > 20 ){
            counter.setStyle("-fx-text-fill: red;");
            fieldDescription.setStyle("-fx-border-color: red");
            counter.setText("Text est très long ");
        }
    }

    @FXML
    void namepressed(KeyEvent event) {
        String name = fieldName.getText();
        String regex = "^[a-zA-Z][a-zA-Z0-9 ]{0,15}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(name);
        if(matcher.matches()){
            control.setText("nom est valide!");
            control.setStyle("-fx-text-fill: green ;");
            fieldName.setStyle("-fx-border-color: green");
        }else if (name.isEmpty()){
            control.setText("Veuillez ecrire le nom de la recette");
            control.setStyle("-fx-text-fill: red;");
            fieldName.setStyle("-fx-border-color: red");

        }else{
            control.setText("Le nom doit commencer par une lettre et de longueur 15 caractères max \n " +
                    "Les caractères speciaux ne sont pas autorisées! ");
            fieldName.setStyle("-fx-border-color: red");
            control.setStyle("-fx-text-fill: red;");

        }
    }

    public void setRecette(Recette selectedRecette) {
        this.recette = selectedRecette;
        fieldName.setText(selectedRecette.getName());
        updateCheckBoxes(selectedRecette.getCategory());
        fieldDescription.setText(selectedRecette.getDescription());
    }

    private void updateCheckBoxes(String category) {
        // Assurez-vous de désélectionner toutes les CheckBox d'abord
        checkBoxBreakfast.setSelected(false);
        checkBoxBrunch.setSelected(false);
        checkBoxLunch.setSelected(false);
        checkBoxSnacks.setSelected(false);
        checkBoxDinner.setSelected(false);

        // Sélectionnez la CheckBox appropriée en fonction de la catégorie de la recette
        switch (category) {
            case "Breakfast":
                checkBoxBreakfast.setSelected(true);
                break;
            case "Brunch":
                checkBoxBrunch.setSelected(true);
                break;
            case "Lunch":
                checkBoxLunch.setSelected(true);
                break;
            case "Snacks":
                checkBoxSnacks.setSelected(true);
                break;
            case "Dinner":
                checkBoxDinner.setSelected(true);
                break;
        }
    }

}
