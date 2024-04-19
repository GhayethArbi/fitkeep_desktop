package controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.ActivitePhysique;
import services.ServiceActivitePhysique;

import java.net.URL;
import java.util.ResourceBundle;

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
    Integer activitePhysiqueId;
    ServiceActivitePhysique sap = new ServiceActivitePhysique();
    AfficherActivitesFXML parentFXMLLoader ;

    public void update(MouseEvent mouseEvent) {
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
        // Insert the new physical activity
        try {
            sap.updateOne(activitePhysique);
            System.out.println("ActivitePhysique updated successfully!");
        } catch (Exception e) {
            System.out.println(activitePhysique);
            e.getMessage();
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
    void setTextField(Integer id,String nomActivite, String TypeActivite, Integer duration,Integer calories, Integer nbSeries, Integer nbRepSeries, Integer poidsPerSerie) {
        activitePhysiqueId = id;
        nameFld.setText(nomActivite);
        typeFLd.setValue(TypeActivite);
        DurationFLd.setText(duration.toString());
        CaloriesFld.setText(calories.toString());
        SerieNumFld.setText(nbSeries.toString());
        SerieRepNumFLd.setText(nbRepSeries.toString());
        WeightFld.setText(poidsPerSerie.toString());
    }
}
