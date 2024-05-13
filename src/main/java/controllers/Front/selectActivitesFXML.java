package controllers.Front;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import models.ActivitePhysique;
import models.Objectif;
import services.ServiceActivitePhysique;
import services.ServiceObjectif;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;

public class selectActivitesFXML implements Initializable {


    @FXML
    GridPane Mygrid ;

    @FXML
    TextField nameField ;

    @FXML
    ComboBox<String> typeField ;

    @FXML
    TextField DuratField ;

    @FXML
    HBox durHbox ;

    @FXML
    HBox calHbox ;

    @FXML
    HBox nbSHbox ;

    @FXML
    HBox nbRSHbox ;

    @FXML
    HBox weightHbox ;

    @FXML
    TextField calField ;

    @FXML
    TextField nbSerField ;

    @FXML
    TextField NbRepField ;

    @FXML
    TextField weigField ;

    @FXML
    ImageView imgActv ;

    ServiceActivitePhysique sapActivite = new ServiceActivitePhysique();
    ServiceObjectif sapObjectif = new ServiceObjectif();

    public Integer IdObj ;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
     displayActivites();
    }

    public void displayActivites() {
        try {
            // Retrieve selected activities
            List<ActivitePhysique> selectedActivities = sapActivite.selectAllWithNullObjectifs(); // Modify this according to your service
            // Clear existing content in the grid
            Mygrid.getChildren().clear();

            // Set the horizontal gap between columns
            Mygrid.setHgap(10); // Adjust the value as needed
            // Set the vertical gap between rows
            Mygrid.setVgap(10); // Adjust the value as needed

            // Set the padding for the grid (same value for all sides)
            Mygrid.setPadding(new Insets(2)); // Adjust the value as needed

            // Set the column count of the grid
            Mygrid.getColumnConstraints().clear();
            ColumnConstraints col1 = new ColumnConstraints();
            ColumnConstraints col2 = new ColumnConstraints();
            col2.setHgrow(Priority.ALWAYS);
            Mygrid.getColumnConstraints().addAll(col1, col2);

            // Load and add itemActiviteFXML for each selected activity
            int row = 0;
            int col = 0;
            for (ActivitePhysique activity : selectedActivities) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/itemActivite.fxml"));
                AnchorPane itemActivite = loader.load();

                // Access the controller of itemActivite
                itemActiviteFXML itemActiviteController = loader.getController();

                // Set values for the itemActiviteFXML
                itemActiviteController.setActivityInfo(activity.getNomActivite(), activity.getTypeActivite(), activity.getImageActivite()); // Assuming getImageActivite() returns the image path

                // Add itemActiviteFXML to the grid
                Mygrid.add(itemActivite, col, row);

                // Set up event handler for HboxClicked
                itemActiviteController.getHboxClicked().setOnMouseClicked(event -> handleHBoxClicked(itemActiviteController));

                // Increment row index
                row++;

                // If the end of a row is reached, reset row index
                if (row == selectedActivities.size() / 2 + 1) {
                    row = 0;
                    col++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void handleHBoxClicked(itemActiviteFXML itemActiviteFXML) {
     imgActv.setImage(itemActiviteFXML.getImage());
     nameField.setText(itemActiviteFXML.getName());
     typeField.setValue(itemActiviteFXML.getType());

     if (typeField.getValue().equals("Cardiovasculaire")){

         calHbox.setVisible(true);
         durHbox.setVisible(true);
         calHbox.setManaged(true);
         durHbox.setManaged(true);
         nbSHbox.setVisible(false);
         nbRSHbox.setVisible(false);
         weightHbox.setVisible(false);
         nbSHbox.setManaged(false);
         nbRSHbox.setManaged(false);
         weightHbox.setManaged(false);

         DuratField.setVisible(true);
         calField.setVisible(true);
         nbSerField.setVisible(false);
         NbRepField.setVisible(false);
         weigField.setVisible(false);
     }else{

         calHbox.setVisible(false);
         durHbox.setVisible(false);
         calHbox.setManaged(false);
         durHbox.setManaged(false);
         nbSHbox.setVisible(true);
         nbRSHbox.setVisible(true);
         weightHbox.setVisible(true);
         nbSHbox.setManaged(true);
         nbRSHbox.setManaged(true);
         weightHbox.setManaged(true);


         DuratField.setVisible(false);
         calField.setVisible(false);

         nbSerField.setVisible(true);
         NbRepField.setVisible(true);
         weigField.setVisible(true);
     }
    }



    public void clearFields(MouseEvent mouseEvent) {
        DuratField.setText("");
        calField.setText("");
        nbSerField.setText("");
        NbRepField.setText("");
        weigField.setText("");
    }

    public void addActivity(MouseEvent mouseEvent) throws IOException {
        String name = nameField.getText();
        String selectedType = typeField.getSelectionModel().getSelectedItem();
        String durationText = DuratField.getText().trim();
        String caloriesText = calField.getText().trim();
        String serieNumText = nbSerField.getText().trim();
        String serieRepNumText = NbRepField.getText().trim();
        String weightText = weigField.getText().trim();
        String fileName = generateUniqueFileName();
        Path destinationPath = Paths.get("D:/pidev/public/Uploads", fileName);
        Image image = imgActv.getImage();

        if (image != null) {
            //System.out.println("------------------------------------------------");

            File selectedFile = new File(image.getUrl().replace("file:/", ""));
            byte[] imageData = Files.readAllBytes(selectedFile.toPath());
            Files.write(destinationPath, imageData);
        }

        String ImageActivitePath = fileName;
        Integer duration = parseInteger(durationText.trim());
        Integer calories = parseInteger(caloriesText.trim());
        Integer serieNum = parseInteger(serieNumText.trim());
        Integer serieRepNum = parseInteger(serieRepNumText.trim());
        Integer weight = parseInteger(weightText.trim());
        ////////////////////////////////
        if(selectedType==null){
            selectedType="Cardiovasculaire";
        }
        ///////////////////////////////
        // Create an instance of ActivitePhysique with the retrieved data
        ActivitePhysique activitePhysique = new ActivitePhysique();
        activitePhysique.setNomActivite(name);
        activitePhysique.setTypeActivite(selectedType);
        activitePhysique.setDureeActivite(duration); // Handle null value
        activitePhysique.setCaloriesBrules(calories); // Handle null value
        activitePhysique.setNbSeries(serieNum); // Handle null value
        activitePhysique.setNbRepSeries(serieRepNum); // Handle null value
        activitePhysique.setPoidsParSerie(weight); // Handle null value
        activitePhysique.setImageActivite(ImageActivitePath);

        System.out.println(IdObj);
        try {
            Objectif objectif = sapActivite.fetchObjectifById(IdObj);
            System.out.println(activitePhysique.getObjectifs());
            activitePhysique.getObjectifs().add(objectif) ;
            System.out.println(activitePhysique.getObjectifs());

            if(selectedType.equals("musculation")) {
                objectif.setTotalDuree(objectif.getTotalDuree() + duration);
                objectif.setTotalCalories(objectif.getTotalCalories() + calories);
                sapObjectif.updateTotalCaloriesAndDuration(objectif);
            }
            sapActivite.insertOne(activitePhysique);
            System.out.println("ActivitePhysique added successfully!");
        } catch (Exception e) {
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaa");
            System.out.println(activitePhysique);
            e.printStackTrace();
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

    public void getBack(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Front/AfficherObjectifs.fxml"));
        Parent root = fxmlLoader.load();

        // Get the current stage
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

        // Create a new scene with the root and set it to the stage
        stage.setScene(new Scene(root));
        stage.show();
    }
}
