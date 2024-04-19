package controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.ActivitePhysique;
import models.Objectif;
import services.ServiceActivitePhysique;
import services.ServiceObjectif;
import javafx.scene.Node;
import java.util.List;
import java.util.ArrayList;


import java.net.URL;
import java.util.ResourceBundle;


public class AjouterActiviteFXML  implements Initializable {
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
    private VBox objVbox ;
    Integer activitePhysiqueId ;
    AfficherActivitesFXML parentFXMLLoader ;
    ServiceActivitePhysique sapActivite = new ServiceActivitePhysique();
    ServiceObjectif sapObjectif = new ServiceObjectif();
    private List<Objectif> objectifsList ;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try{
           objectifsList = sapObjectif.selectAll() ;
            for (Objectif objectif : objectifsList) {
                CheckBox checkBox = new CheckBox(objectif.getId() + " - " + objectif.getNomObjectif());
                checkBox.setStyle("-fx-font-size: 16px;");
                objVbox.getChildren().add(checkBox);
            }
        }catch(Exception e){
        e.printStackTrace();
        }
        typeFLd.setItems(FXCollections.observableArrayList("Musculation","Cardiovasculaire"));
    }

    @FXML
    private void save(MouseEvent event) {
        // Retrieve values from UI components
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
        activitePhysique.setNomActivite(name);
        activitePhysique.setTypeActivite(selectedType);
        activitePhysique.setDureeActivite(duration); // Handle null value
        activitePhysique.setCaloriesBrules(calories); // Handle null value
        activitePhysique.setNbSeries(serieNum); // Handle null value
        activitePhysique.setNbRepSeries(serieRepNum); // Handle null value
        activitePhysique.setPoidsParSerie(weight); // Handle null value
        // Retrieve the selected objectives from the checkboxes
        List<Objectif> selectedObjectifs = new ArrayList<>();
        for (Node node : objVbox.getChildren()) {
            if (node instanceof CheckBox) {
                CheckBox checkBox = (CheckBox)  node;
                if (checkBox.isSelected()) {
                    String[] parts = checkBox.getText().split(" - ");
                    int objectId = Integer.parseInt(parts[0]);
                    Objectif objectif = new Objectif();
                    objectif.setId(objectId);
                    selectedObjectifs.add(objectif);
                }
            }
        }
        // Set the selected objectives to the activitePhysique
        activitePhysique.setObjectifs(selectedObjectifs);
        // Insert the new physical activity
        try {
            sapActivite.insertOne(activitePhysique);
            System.out.println("ActivitePhysique added successfully!");
        } catch (Exception e) {
            System.out.println(activitePhysique);
            e.getMessage();
        }
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



    public void setParentFXMLLoader(AfficherActivitesFXML parentFXMLLoader) {
        this.parentFXMLLoader = parentFXMLLoader;
    }

    @FXML
    private void reset(MouseEvent event) {
       // TODO
    }

    @FXML
    private void close(MouseEvent event) {
    FontAwesomeIconView closeIcon = (FontAwesomeIconView) event.getSource();
    Stage stage = (Stage) closeIcon.getScene().getWindow();
    parentFXMLLoader.getRefrashable();
    stage.close();
    }


}
