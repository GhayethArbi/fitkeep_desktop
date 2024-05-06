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
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class ModifierObjectifFXML implements Initializable {
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

    ServiceActivitePhysique sapActivite = new ServiceActivitePhysique();
    ServiceObjectif sapObjectif = new ServiceObjectif();
    Integer objectifId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameFld.setItems(FXCollections.observableArrayList("perte de poids", "renforcement musculaire", "amelioration endurance", "reduction du stress", "augmentation de la masse musculaire", "maintien de la forme physique"));

    }


    public void close(MouseEvent mouseEvent) {
    }

    public void reset(MouseEvent mouseEvent) {
    }

    public void save(MouseEvent mouseEvent) {
        try {
            String nameSelected = nameFld.getSelectionModel().getSelectedItem();
            Date date =java.sql.Date.valueOf(dateObCol.getValue());
            String totalCaloriesText = totCalFld.getText().trim();
            String totalDurationText = totDurFld.getText().trim();
            Integer totalCalories = (Integer) Integer.parseInt(totalCaloriesText);
            Integer totalDuration = (Integer) Integer.parseInt(totalDurationText);

            // Retrieve the selected activities from the checkboxes
            List<ActivitePhysique> selectedActivites = new ArrayList<>();
            for (Node node : actVbox.getChildren()) {
                if (node instanceof CheckBox) {
                    CheckBox checkBox = (CheckBox) node;
                    if (checkBox.isSelected()) {
                        String[] parts = checkBox.getText().split(" - ");
                        int activiteId = Integer.parseInt(parts[0]);
                        ActivitePhysique activitePhysique = new ActivitePhysique();
                        activitePhysique.setId(Integer.valueOf(activiteId));
                        selectedActivites.add(activitePhysique);
                    }
                }
            }
            System.out.println(selectedActivites);
            // Create an instance of Objectif with the retrieved data
            Objectif objectif = new Objectif();
            objectif.setId(objectifId);
            objectif.setNomObjectif(nameSelected);
            objectif.setDateObjectif(date);
            objectif.setTotalCalories(totalCalories);
            objectif.setTotalDuree(totalDuration);
            objectif.setActivites(selectedActivites);

            sapObjectif.updateOne(objectif);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setTextField(Integer id, String nomObjectif, Date dateObjectif, Integer totalCalories, Integer totalDuree, String note) {
        objectifId = id;
        nameFld.setValue(nomObjectif);
        dateObCol.setValue(dateObjectif.toLocalDate());
        totDurFld.setText(totalDuree.toString());
        totCalFld.setText(totalCalories.toString());
        noteFld.setText(note);

        try {
            // Retrieve all objectives
            List<ActivitePhysique> allActivites = sapActivite.selectAll();

            // Retrieve associated objectives for the activity
            List<ActivitePhysique> associatedActivites = sapObjectif.fetchActivitesForObjectif(id);

            // Clear existing checkboxes
            actVbox.getChildren().clear();

            // Populate checkboxes with all objectives
            for (ActivitePhysique activitePhysique : allActivites) {
                CheckBox checkBox = new CheckBox(activitePhysique.getId() + " - " + activitePhysique.getNomActivite());
                checkBox.setStyle("-fx-font-size: 16px;");
                // Check if the objective is associated with the activity
                boolean isSelected = associatedActivites.stream()
                        .anyMatch(assocObj -> assocObj.getId().equals(activitePhysique.getId()));
                checkBox.setSelected(isSelected);
                actVbox.getChildren().add(checkBox);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error message)
        }

    }
}
