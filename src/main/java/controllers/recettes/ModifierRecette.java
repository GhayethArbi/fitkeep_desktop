package controllers.recettes;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import models.Recette;
import services.ServiceRecette;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Initializable ?
 * 1) Est une interface
 * 2) But : declarer la methode initialize()
 * cette methode est utilisé pour initialiser les controlleurs des fichiers FXML de javafx
 * */

public class ModifierRecette implements Initializable {

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

    public void initData(Recette recette) {
        this.recette = recette;
        fieldName.setText(recette.getName());
        fieldDescription.setText(recette.getDescription());

        // Vous pouvez définir les cases à cocher en fonction des catégories de la recette
        // Par exemple :
        checkBoxBreakfast.setSelected(recette.getCategory().equals("Breakfast"));
        checkBoxBrunch.setSelected(recette.getCategory().equals("Brunch"));
        checkBoxLunch.setSelected(recette.getCategory().equals("Lunch"));
        checkBoxSnacks.setSelected(recette.getCategory().equals("Snacks"));
        checkBoxDinner.setSelected(recette.getCategory().equals("Dinner"));
    }

    /*@FXML
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
                sr.updateOne(r);
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setContentText("Recette ajoutée avec succès !");
                successAlert.show();
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
    }**/

    @FXML
    void updateRecette(ActionEvent event) {
// Vérifiez d'abord si la recette existe
        if (recette != null) {
            // Mettez à jour les propriétés de la recette avec les valeurs des champs du formulaire
            recette.setName(fieldName.getText());
            recette.setDescription(fieldDescription.getText());

            // Déterminez la catégorie de la recette en fonction des cases à cocher
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
            }
            recette.setCategory(category);

            // Appelez votre service pour mettre à jour la recette dans la base de données
            ServiceRecette sr = new ServiceRecette(); // Assurez-vous d'avoir une instance de votre service de recette
            try {
                sr.updateOne(recette);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            // Assurez-vous d'avoir une instance de votre service de recette ici
            // serviceRecette.updateOne(recette);

            // Si la mise à jour réussit, vous pouvez fermer la fenêtre de modification de la recette

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
