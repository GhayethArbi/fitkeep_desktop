package controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.ActivitePhysique;
import models.Objectif ;
import services.ServiceActivitePhysique;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.List;
import java.util.ArrayList;
import javafx.scene.control.CheckBox;
import javafx.scene.Node;
import services.ServiceObjectif;

public class ModifierActiviteFXML implements Initializable {
    @FXML
    private TextField nameFld;

    @FXML
    private ComboBox<String> typeFLd;

    @FXML
    private TextField DurationFLd;

    @FXML
    private TextField CaloriesFld;

    @FXML
    private TextField SerieNumFld;

    @FXML
    private TextField SerieRepNumFLd;

    @FXML
    private TextField WeightFld;

    @FXML
    private VBox objVbox;

    Integer activitePhysiqueId;
    ServiceActivitePhysique sapActivite = new ServiceActivitePhysique();
    ServiceObjectif sapObjectif = new ServiceObjectif();
    AfficherActivitesFXML parentFXMLLoader ;

    public void update(MouseEvent mouseEvent) {
        try {
            String name = nameFld.getText();
            String selectedType = typeFLd.getSelectionModel().getSelectedItem();

            // Check if any of the required fields are empty
            if (name.isEmpty() || selectedType == null) {
                System.err.println("Please fill in all required fields.");
                return; // Exit the method
            }

            // Parse integer values if provided
            Integer duration = parseInteger(DurationFLd.getText().trim());
            Integer calories = parseInteger(CaloriesFld.getText().trim());
            Integer serieNum = parseInteger(SerieNumFld.getText().trim());
            Integer serieRepNum = parseInteger(SerieRepNumFLd.getText().trim());
            Integer weight = parseInteger(WeightFld.getText().trim());


            // Retrieve selected objectives from checkboxes
            List<Objectif> selectedObjectifs = new ArrayList<>();
            for (Node node : objVbox.getChildren()) {
                if (node instanceof CheckBox) {
                    CheckBox checkBox = (CheckBox) node;
                    if (checkBox.isSelected()) {
                        String text = checkBox.getText();
                        // Split text to get objective id
                        String[] parts = text.split(" - ");
                        Integer objectiveId = Integer.parseInt(parts[0]);
                        Objectif objectif = new Objectif();
                        objectif.setId(objectiveId);
                        selectedObjectifs.add(objectif);
                    }
                }
            }
            System.out.println(selectedObjectifs);


            // Create an instance of ActivitePhysique with the retrieved data
            ActivitePhysique activitePhysique = new ActivitePhysique();
            activitePhysique.setId(activitePhysiqueId);
            activitePhysique.setNomActivite(name);
            activitePhysique.setTypeActivite(selectedType);
            activitePhysique.setDureeActivite(duration); // Handle null value
            activitePhysique.setCaloriesBrules(calories); // Handle null value
            activitePhysique.setNbSeries(serieNum); // Handle null value
            activitePhysique.setNbRepSeries(serieRepNum); // Handle null value
            activitePhysique.setPoidsParSerie(weight); // Handle null value
            activitePhysique.setObjectifs(selectedObjectifs); // Set selected objectives

            // Update the physical activity
            sapActivite.updateOne(activitePhysique);
           // System.out.println("ActivitePhysique updated successfully!");
        } catch (Exception e) {
            //System.out.println(activitePhysique);
            e.printStackTrace();
        }
    }

    public void setParentFXMLLoader(AfficherActivitesFXML parentFXMLLoader) {
        this.parentFXMLLoader = parentFXMLLoader;
    }
    @FXML
    private void close(MouseEvent event) {
        FontAwesomeIconView closeIcon = (FontAwesomeIconView) event.getSource();
        Stage stage = (Stage) closeIcon.getScene().getWindow();
        parentFXMLLoader.getRefrashable();
        stage.close();
    }
    private Integer parseInteger(String value) {
        if (value.trim().isEmpty()) {
            return null; // Return null if the value is empty
        }
        try {
            return Integer.parseInt(value.trim()); // Parse integer value
        } catch (NumberFormatException e) {
            System.err.println("Please enter valid integer values in numeric fields.");
            return null; // Return null if parsing fails
        }
    }
    public void reset(MouseEvent mouseEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO
        typeFLd.setItems(FXCollections.observableArrayList("Musculation","Cardiovasculaire"));
    }
    void setTextField(Integer id, String nomActivite, String TypeActivite, Integer duration, Integer calories, Integer nbSeries, Integer nbRepSeries, Integer poidsPerSerie) {
        activitePhysiqueId = id;
        nameFld.setText(nomActivite);
        typeFLd.setValue(TypeActivite);
        DurationFLd.setText(duration.toString());
        CaloriesFld.setText(calories.toString());
        SerieNumFld.setText(nbSeries.toString());
        SerieRepNumFLd.setText(nbRepSeries.toString());
        WeightFld.setText(poidsPerSerie.toString());

        try {
            // Retrieve all objectives
            List<Objectif> allObjectifs = sapObjectif.selectAll();

            // Retrieve associated objectives for the activity
            List<Objectif> associatedObjectifs = sapActivite.fetchObjectifsForActivite(id);

            // Clear existing checkboxes
            objVbox.getChildren().clear();

            // Populate checkboxes with all objectives
            for (Objectif objectif : allObjectifs) {
                CheckBox checkBox = new CheckBox(objectif.getId() + " - " + objectif.getNomObjectif());
                checkBox.setStyle("-fx-font-size: 16px;");
                // Check if the objective is associated with the activity
                boolean isSelected = associatedObjectifs.stream()
                        .anyMatch(assocObj -> assocObj.getId().equals(objectif.getId()));
                checkBox.setSelected(isSelected);
                objVbox.getChildren().add(checkBox);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error message)
        }
    }

}
