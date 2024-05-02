package controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import models.ActivitePhysique;
import models.Objectif;
import services.ServiceActivitePhysique;
import services.ServiceObjectif;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class AjouterObjectifFXML implements Initializable {

    @FXML
    private ComboBox<String> nameFld;

    @FXML
    private DatePicker dateObCol;

    @FXML
    private TextField totCalFld;

    @FXML
    private TextField totDurFld;

    @FXML
    private TextArea noteFld;

    @FXML
    private VBox actVbox;

    AfficherObjectifsFXML parentFXMLLoader;
    ServiceActivitePhysique sapActivite = new ServiceActivitePhysique();
    ServiceObjectif sapObjectif = new ServiceObjectif();
    private List<ActivitePhysique> activitePhysiqueList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO
        try {
            activitePhysiqueList = sapActivite.selectAll();
            for (ActivitePhysique activitePhysique : activitePhysiqueList) {
                CheckBox checkBox = new CheckBox(activitePhysique.getId() + " - " + activitePhysique.getNomActivite());
                checkBox.setStyle("-fx-font-size: 16px;");
                actVbox.getChildren().add(checkBox);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        nameFld.setItems(FXCollections.observableArrayList("perte de poids", "renforcement musculaire", "amelioration endurance", "reduction du stress", "augmentation de la masse musculaire", "maintien de la forme physique"));
    }

    public void close(MouseEvent mouseEvent) {
    }

    public void save(MouseEvent mouseEvent) {
        try {
            String nameSelected = nameFld.getSelectionModel().getSelectedItem();
            Date date = java.sql.Date.valueOf(dateObCol.getValue());
            String totalCaloriesText = totCalFld.getText().trim();
            String totalDurationText = totDurFld.getText().trim();
            int totalCalories = 0;
            int totalDuration = 0;
            String note = noteFld.getText();
            // Check if totalCaloriesText and totalDurationText are empty
            if (!totalCaloriesText.isEmpty() && !totalDurationText.isEmpty()) {
                // Parse the text to integers
                totalCalories = Integer.parseInt(totalCaloriesText);
                totalDuration = Integer.parseInt(totalDurationText);
            }
            if (nameSelected == null) {
                System.err.println("Please fill in all required fields.");
                return; // Exit the method
            }

            Objectif objectif = new Objectif();
            objectif.setNomObjectif(nameSelected);
            objectif.setDateObjectif(date);
            objectif.setTotalCalories(totalCalories);
            objectif.setTotalDuree(totalDuration);
            objectif.setNote(note);

            // Retrieve the selected objectives from the checkboxes
            List<ActivitePhysique> selectedActivites = new ArrayList<>();
            for (Node node : actVbox.getChildren()) {
                if (node instanceof CheckBox) {
                    CheckBox checkBox = (CheckBox) node;
                    if (checkBox.isSelected()) {
                        String[] parts = checkBox.getText().split(" - ");
                        int activiteId = Integer.parseInt(parts[0]);
                        ActivitePhysique activitePhysique = new ActivitePhysique();
                        activitePhysique.setId(activiteId);
                        selectedActivites.add(activitePhysique);
                    }
                }
            }

            // Set the selected objectives to the activitePhysique
            objectif.setActivites(selectedActivites);

            // Insert the new physical activity
            try {
                sapObjectif.insertOne(objectif);
                System.out.println("objectif added successfully!");
            } catch (Exception e) {
                System.out.println(objectif);
                e.getMessage();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void reset(MouseEvent mouseEvent) {
    }

    public void setParentFXMLLoader(AfficherObjectifsFXML parentFXMLLoader) {
       parentFXMLLoader.refreshTable();
        this.parentFXMLLoader = parentFXMLLoader;
    }
}
