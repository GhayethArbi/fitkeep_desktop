package controllers.Front;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.ActivitePhysique;
import models.Objectif;
import services.ServiceObjectif;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherObjectifsFXML implements Initializable {

    @FXML
    ListView listObjectives ;

    @FXML
    ImageView imgObj ;

    @FXML
    TextField nameObjField ;

    @FXML
    DatePicker dateObjField ;

    @FXML
    TextArea NoteObjField ;

    ServiceObjectif sapObjectif = new ServiceObjectif();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
   try {
       displayObjectifs();
       // Wrap ListView with a ScrollPane and disable scroll bars

   }catch (Exception e){
       e.printStackTrace();
   }
    }

    private void displayObjectifs() {
        try {
            // Static data for six products
            String[] objectifNames = {"Renforcement musculaire", "amelioration endurance", "reduction de stress", "perte de poids", "Augmentation de la masse musculaire", "maintien de la forme physique"};
            String[] imageFiles = {"gallery1.png", "gallery2.png", "gallery3.png", "gallery4.png", "gallery5.png", "gallery6.png"};

            // Create a horizontal box to hold each objective
            HBox objectivesBox = new HBox();

            for (int i = 0; i < objectifNames.length; i++) {
                String productName = objectifNames[i];
                String imageFile = imageFiles[i];

                // Load the FXML file for the item
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Front/ItemObjectif.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                itemObjectifFXML itemObjectifFXML = fxmlLoader.getController();
                itemObjectifFXML.setParentController(this);
                // Set data for the item
                itemObjectifFXML.setData(productName, imageFile);

                // Add event handler for each item
                HBox hbox = (HBox) anchorPane.getChildren().get(0); // Assuming the first child is the HBox
                hbox.setOnMouseClicked(event -> handleItemClick(itemObjectifFXML));

                // Add the anchor pane to the horizontal box
                objectivesBox.getChildren().add(anchorPane);
            }
            // Set the fixed cell size to prevent scrolling
            listObjectives.setFixedCellSize(HBox.USE_COMPUTED_SIZE);

            // Set the horizontal box as the content of the ListView
            listObjectives.getItems().add(objectivesBox);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void clickLeft(MouseEvent mouseEvent) {
    }

    public void clickRight(MouseEvent mouseEvent) {
    }

    private void handleItemClick(itemObjectifFXML itemObjectifFXML) {
        // Update the fields with the data of the selected objective
        imgObj.setImage(itemObjectifFXML.getImage());
        nameObjField.setText(itemObjectifFXML.getName());
    }

    public void createObj(MouseEvent mouseEvent) {
        try {
            String name = nameObjField.getText() ;
            Date date = java.sql.Date.valueOf(dateObjField.getValue());
            int totalCalories = 0;
            int totalDuration = 0;
            String note = NoteObjField.getText();
            // Check if totalCaloriesText and totalDurationText are empty
          
            if (name == null) {
                System.err.println("Please fill in all required fields.");
                return; // Exit the method
            }

            Objectif objectif = new Objectif();
            objectif.setNomObjectif(name);
            objectif.setDateObjectif(date);
            objectif.setTotalCalories(totalCalories);
            objectif.setTotalDuree(totalDuration);
            objectif.setNote(note);

            // Insert the new physical activity
            try {
                sapObjectif.insertOne(objectif);
                System.out.println(objectif.getId());
              //  System.out.println("objectif added successfully!");
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Front/selectActivites.fxml"));
                Parent root = fxmlLoader.load();

                // Get the controller associated with the new FXML file
                selectActivitesFXML selectActivitesFXML = fxmlLoader.getController();
                // Pass the ID of the new objective to the controller
                selectActivitesFXML.IdObj = objectif.getId(); // Assuming getId() returns the ID of the newly created objective
                // Pass the ID of the new objective to the controller
                selectActivitesFXML.displayActivites(); // Assuming getId() returns the ID of the newly created objective

                // Get the current stage
                Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

                // Create a new scene with the root and set it to the stage
                stage.setScene(new Scene(root));
                stage.show();
            } catch (Exception e) {
                System.out.println(objectif);
                e.getMessage();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getBack(MouseEvent mouseEvent) {
    }

    public void clearFields(MouseEvent mouseEvent) {
    }
}
