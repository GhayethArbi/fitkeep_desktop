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
import java.io.IOException;
import models.Objectif ;
import services.ServiceActivitePhysique;
import javafx.stage.FileChooser;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.List;
import java.util.ArrayList;
import javafx.scene.control.CheckBox;
import javafx.scene.Node;
import services.ServiceObjectif;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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

    @FXML
    private ImageView activiteImg ;

    private File selectedFile;
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

            // Update the image if a new image is selected
            if (selectedFile != null) {
                try {
                    String fileName = generateUniqueFileName();
                    Path destinationPath = Paths.get("C:/Users/manso/PIP/public/Uploads", fileName);

                    // Read the bytes from the selected file
                    byte[] imageData = Files.readAllBytes(selectedFile.toPath());

                    // Write the bytes to the destination path
                    Files.write(destinationPath, imageData);

                    // Set the image path in the activitePhysique object
                    String imageActivitePath = fileName;
                    activitePhysique.setImageActivite(imageActivitePath);
                } catch (IOException e) {
                    // Handle file I/O exception
                    e.printStackTrace();
                }
            }

            // Update the physical activity
            sapActivite.updateOne(activitePhysique);
            // System.out.println("ActivitePhysique updated successfully!");
        } catch (Exception e) {
            //System.out.println(activitePhysique);
            e.printStackTrace();
        }
    }



    private String generateUniqueFileName() {
        String extension = "jpg"; // You can modify this to support other image formats
        String uniqueId = md5(uniqid());
        return uniqueId + "." + extension;
    }

    private String md5(String input) {
        // Implement md5 hashing function (you can use libraries or built-in functions for this)
        // This is a simplified example and may not be cryptographically secure
        // You can use libraries like Apache Commons Codec or MessageDigest for secure hashing
        return input; // Placeholder implementation
    }

    private String uniqid() {
        // Implement uniqid generation function (you can use libraries or built-in functions for this)
        // This is a simplified example
        return Long.toHexString(System.currentTimeMillis());
    }
    public void chooseImage(MouseEvent mouseEvent) {
        // Create a new FileChooser
        FileChooser fileChooser = new FileChooser();

        // Set extension filters
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        // Show open file dialog
         selectedFile = fileChooser.showOpenDialog(new Stage());

        // Check if a file was selected
        if (selectedFile != null) {
            // Convert the selected file to a file URI
            String imageFilePath = selectedFile.toURI().toString();

            // Load and display the selected image
            try {
                Image image = new Image(imageFilePath);
                activiteImg.setImage(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
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

    void setTextField(Integer id, String nomActivite, String TypeActivite, Integer duration, Integer calories, Integer nbSeries, Integer nbRepSeries, Integer poidsPerSerie, String imageName) {
        activitePhysiqueId = id;
        nameFld.setText(nomActivite);
        typeFLd.setValue(TypeActivite);
        DurationFLd.setText(duration.toString());
        CaloriesFld.setText(calories.toString());
        SerieNumFld.setText(nbSeries.toString());
        SerieRepNumFLd.setText(nbRepSeries.toString());
        WeightFld.setText(poidsPerSerie.toString());

        // Construct the full file path
        String imageFilePath = "C:/Users/manso/PIP/public/Uploads/" + imageName;

        // Load and display the image
        if (imageName != null && !imageName.isEmpty()) {
            try {
                Image image = new Image("file:///" + imageFilePath);
                activiteImg.setImage(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

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
