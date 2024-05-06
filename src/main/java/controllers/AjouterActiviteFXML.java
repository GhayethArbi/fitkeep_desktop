package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import models.ActivitePhysique;
import models.Objectif;
import services.ServiceActivitePhysique;
import services.ServiceObjectif;
import javafx.scene.Node;
import java.util.ArrayList;
import java.net.URL;
import java.util.ResourceBundle;


public class AjouterActiviteFXML  implements Initializable {
    @FXML
    private TextField nameFld;

    @FXML
    private Text nameErrorLabel ;

    @FXML
    private ComboBox<String> typeFLd;

    @FXML
    private Text TypeErrorLabel ;

    @FXML
    private TextField DurationFLd;

    @FXML
    private Text durErrorLabel ;

    @FXML
    private TextField CaloriesFld;

    @FXML
    private Text calErrorLabel ;

    @FXML
    private TextField SerieNumFld;

    @FXML
    private Text serErrorLabel ;

    @FXML
    private TextField SerieRepNumFLd;

    @FXML
    private Text repSerErrorLabel ;

    @FXML
    private TextField WeightFld;

    @FXML
    private Text weigErrorLabel ;

    @FXML
    private VBox objVbox ;

    @FXML
    private ImageView activiteImg;

    @FXML
    private Text imgErrorLabel ;
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
        // Reset error labels
        nameErrorLabel.setVisible(false);
        TypeErrorLabel.setVisible(false);
        durErrorLabel.setVisible(false);
        calErrorLabel.setVisible(false);
        serErrorLabel.setVisible(false);
        repSerErrorLabel.setVisible(false);
        weigErrorLabel.setVisible(false);
        imgErrorLabel.setVisible(false);

        // Retrieve values from UI components
        String name = nameFld.getText();
        String selectedType = typeFLd.getSelectionModel().getSelectedItem();
        String durationText = DurationFLd.getText().trim();
        String caloriesText = CaloriesFld.getText().trim();
        String serieNumText = SerieNumFld.getText().trim();
        String serieRepNumText = SerieRepNumFLd.getText().trim();
        String weightText = WeightFld.getText().trim();

        // Perform validation
        boolean isValid = true;

        if (name.isEmpty()) {
            nameErrorLabel.setText("Name cannot be empty");
            nameErrorLabel.setVisible(true);
            isValid = false;
        } else if (!name.matches("[a-zA-Z]+")) {
            nameErrorLabel.setText("Name must contain only letters");
            nameErrorLabel.setVisible(true);
            isValid = false;
        }

        if (selectedType == null) {
            TypeErrorLabel.setText("Please select a type");
            TypeErrorLabel.setVisible(true);
            isValid = false;
        }

        if (!durationText.isEmpty() && !isInteger(durationText)) {
            durErrorLabel.setText("Invalid duration");
            durErrorLabel.setVisible(true);
            isValid = false;
        }

        if (!caloriesText.isEmpty() && !isInteger(caloriesText)) {
            calErrorLabel.setText("Invalid calories");
            calErrorLabel.setVisible(true);
            isValid = false;
        }

        if (!serieNumText.isEmpty() && !isInteger(serieNumText)) {
            serErrorLabel.setText("Invalid Serie number");
            serErrorLabel.setVisible(true);
            isValid = false;
        }

        if (!serieRepNumText.isEmpty() && !isInteger(serieRepNumText)) {
            repSerErrorLabel.setText("Invalid RepSerie number");
            repSerErrorLabel.setVisible(true);
            isValid = false;
        }

        if (!weightText.isEmpty() && !isInteger(weightText)) {
            weigErrorLabel.setText("Invalid Weight");
            weigErrorLabel.setVisible(true);
            isValid = false;
        }

        try {
            String fileName = generateUniqueFileName();
            Path destinationPath = Paths.get("C:/Users/manso/PIP/public/Uploads", fileName);
            Image image = activiteImg.getImage();

            if (image != null) {
                System.out.println("------------------------------------------------");

                File selectedFile = new File(image.getUrl().replace("file:/", ""));
                byte[] imageData = Files.readAllBytes(selectedFile.toPath());
                Files.write(destinationPath, imageData);
            }

            String ImageActivitePath = fileName;
            System.out.println(ImageActivitePath);
            if (image==null) {
                imgErrorLabel.setText("The image cannot be empty");
                imgErrorLabel.setVisible(true);
                isValid = false;
            }

            // Parse integer values if provided
            Integer duration = parseInteger(durationText.trim());
            Integer calories = parseInteger(caloriesText.trim());
            Integer serieNum = parseInteger(serieNumText.trim());
            Integer serieRepNum = parseInteger(serieRepNumText.trim());
            Integer weight = parseInteger(weightText.trim());

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

            // Retrieve the selected objectives from the checkboxes
            List<Objectif> selectedObjectifs = new ArrayList<>();
            for (Node node : objVbox.getChildren()) {
                if (node instanceof CheckBox) {
                    CheckBox checkBox = (CheckBox) node;
                    if (checkBox.isSelected()) {
                        String[] parts = checkBox.getText().split(" - ");
                        int objectId = Integer.parseInt(parts[0]);
                        Objectif objectif = new Objectif();
                        objectif.setId(Integer.valueOf(objectId));
                        selectedObjectifs.add(objectif);
                    }
                }
            }

            // Set the selected objectives to the activitePhysique
            activitePhysique.setObjectifs(selectedObjectifs);
            if (isValid){
            // Insert the new physical activity
            try {
                sapActivite.insertOne(activitePhysique);
                System.out.println("ActivitePhysique added successfully!");
            } catch (Exception e) {
                System.out.println(activitePhysique);
                e.getMessage();
            }}else{
                System.out.println("errrrrrrooorrrrr");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Helper method to check if a string can be parsed to an integer
    private boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
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

    @FXML
    void chooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image");

        // Show the file chooser dialog
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            // Load the selected image into the ImageView
            Image image = new Image(selectedFile.toURI().toString());
            activiteImg.setImage(image);
        }
    }


        private Integer parseInteger(String value) {
        if (value.trim().isEmpty()) {
            return null; // Return null if the value is empty
        }
        try {
            return (Integer) Integer.parseInt(value.trim()); // Parse integer value
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
