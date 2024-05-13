package controllers.Front;

import javafx.application.Platform;
import javafx.embed.swing.SwingNode;
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
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import services.ServiceActivitePhysique;
import services.ServiceObjectif;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.awt.Color;
import java.awt.Font;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class selectActivitesForObjectifFXML implements Initializable {

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

    @FXML
    AnchorPane AnchPChart ;
    ServiceActivitePhysique sapActivite = new ServiceActivitePhysique();
    ServiceObjectif sapObjectif = new ServiceObjectif();

    public Integer IdObj ;
    public Integer IdAct;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void displayActivites(Objectif objectif) {
        try {

            IdObj=objectif.getId();

            // Retrieve selected activities
            List<ActivitePhysique> selectedActivities = sapObjectif.fetchActivitesForObjectif(objectif.getId()); // Modify this according to your service
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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Front/itemActiviteCree.fxml"));
                AnchorPane itemActivite = loader.load();

                // Access the controller of itemActivite
                itemActiviteCreeFXML itemActiviteCreeFXML = loader.getController();
                System.out.println(activity);
                // Set values for the itemActiviteFXML
                // Set the parent controller
                itemActiviteCreeFXML.setParentController(this);
                itemActiviteCreeFXML.setActivityInfo(activity); // Assuming getImageActivite() returns the image path
                // Set up event handler for HboxClicked
                itemActiviteCreeFXML.getHboxClicked().setOnMouseClicked(event -> handleHBoxClicked(itemActiviteCreeFXML));

                // Add itemActiviteFXML to the grid
                Mygrid.add(itemActivite, col, row);

// Set the parent controller
                itemActiviteCreeFXML.setParentController(this);
                // Increment row index
                row++;

                // If the end of a row is reached, reset row index
                if (row == selectedActivities.size() / 2 + 1) {
                    row = 0;
                    col++;
                }
            }
            displayChart(objectif) ;
        } catch (Exception e) {
            System.out.println("jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj");
            e.printStackTrace();
        }

    }

    public void displayChart(Objectif objectif) {
        try {
            // Fetch the activities associated with the objective
            List<ActivitePhysique> activites = sapObjectif.fetchActivitesForObjectif(objectif.getId());
            System.out.println(activites);
            // Count the number of activities of each type
            long cardioCount = activites.stream()
                    .filter(activite -> activite.getTypeActivite().equalsIgnoreCase("Cardiovasculaire"))
                    .count();

            long musculaCount = activites.stream()
                    .filter(activite -> activite.getTypeActivite().equalsIgnoreCase("Musculation"))
                    .count();

            System.out.println(musculaCount);
            // Create a dataset for the pie chart
            DefaultPieDataset dataset = new DefaultPieDataset();
            dataset.setValue("Cardiovascule", cardioCount);
            dataset.setValue("Musculation", musculaCount);

            // Create the pie chart
            JFreeChart chart = ChartFactory.createPieChart(
                    "Répartition des activités pour l'objectif",
                    dataset,
                    true,
                    true,
                    false);

            // Customizations for the chart...
            chart.setBackgroundPaint(new Color(240, 240, 240)); // Background color
            chart.getTitle().setFont(new Font("Arial", Font.BOLD, 18)); // Title font
            chart.getTitle().setPaint(Color.DARK_GRAY); // Title color
            chart.getLegend().setItemFont(new Font("Arial", Font.PLAIN, 12)); // Legend font
            chart.getLegend().setBorder(0, 0, 0, 0); // Remove legend border

            // Create a chart panel and add it to the Swing node
            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new java.awt.Dimension(600, 400));
            chartPanel.setBackground(Color.WHITE); // Chart panel background color

            // Clear existing content and add the new chart to the AnchorPane
            AnchPChart.getChildren().clear();
            Platform.runLater(() -> {
                SwingNode swingNode = new SwingNode();
                swingNode.setContent(chartPanel);
                AnchPChart.getChildren().add(swingNode);

                // Set the chart panel size in AnchorPane
                AnchorPane.setTopAnchor(swingNode, 0.0);
                AnchorPane.setLeftAnchor(swingNode, 0.0);
                AnchorPane.setRightAnchor(swingNode, 0.0);
                AnchorPane.setBottomAnchor(swingNode, 0.0);
            });

        } catch (SQLException e) {
            System.err.println("Error fetching activities for objective: " + e.getMessage());
        }
    }

    private void handleHBoxClicked(itemActiviteCreeFXML itemActiviteCreeFXML){
        imgActv.setImage(itemActiviteCreeFXML.getImage());
        nameField.setText(itemActiviteCreeFXML.getName());
        typeField.setValue(itemActiviteCreeFXML.getType());
        calField.setText(itemActiviteCreeFXML.getCalories());
        DuratField.setText(itemActiviteCreeFXML.getDurat());
        nbSerField.setText(itemActiviteCreeFXML.getNbSer());
        NbRepField.setText(itemActiviteCreeFXML.getNbRep());
        weigField.setText(itemActiviteCreeFXML.getWeight());

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



    public void addActivity(MouseEvent mouseEvent) throws IOException, SQLException {
        String name = nameField.getText();
        String selectedType = typeField.getSelectionModel().getSelectedItem();
        String durationText = DuratField.getText().trim();
        String caloriesText = calField.getText().trim();
        String serieNumText = nbSerField.getText().trim();
        String serieRepNumText = NbRepField.getText().trim();
        String weightText = weigField.getText().trim();
        String fileName = generateUniqueFileName();
        Path destinationPath = Paths.get("C:/Users/manso/PIP/public/Uploads", fileName);
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

        // Create an instance of ActivitePhysique with the retrieved data
        ActivitePhysique activitePhysique = new ActivitePhysique();
        activitePhysique=sapObjectif.fetchActiviteById(IdAct) ;
        Objectif objectif = sapActivite.fetchObjectifById(IdObj);
        //System.out.println(IdAct);

        activitePhysique.setNomActivite(name);
        activitePhysique.setTypeActivite(selectedType);
        activitePhysique.setDureeActivite(duration); // Handle null value
        activitePhysique.setCaloriesBrules(calories); // Handle null value
        activitePhysique.setNbSeries(serieNum); // Handle null value
        activitePhysique.setNbRepSeries(serieRepNum); // Handle null value
        activitePhysique.setPoidsParSerie(weight); // Handle null value
        activitePhysique.setImageActivite(ImageActivitePath);

       // System.out.println(IdObj);

            sapActivite.updateOnlyFields(activitePhysique);
           sapObjectif.calculateAndUpdateTotalValuesForObjective(IdObj) ;
            displayActivites(objectif) ;
            //   System.out.println("ActivitePhysique added successfully!");

    }

    public void clearFields(MouseEvent mouseEvent) {
    }

    public void deleteActivite(ActivitePhysique activitePhysique) {
        try {
            Objectif objectif = sapActivite.fetchObjectifById(IdObj);
            sapActivite.deleteOne(activitePhysique);
            sapObjectif.calculateAndUpdateTotalValuesForObjective(IdObj) ;
            // Optionally, you can update the view after deletion
            displayActivites(objectif);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }
    }
    public void getBack(MouseEvent mouseEvent) throws IOException {
        try {
            // Load the AfficherObjectifs.fxml file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Front/AfficherObjectifs.fxml"));
            Parent root = fxmlLoader.load();

            // Get the controller associated with the loaded FXML file
            AfficherObjectifsFXML controller = fxmlLoader.getController();

            // Call a method on the controller to trigger the refresh
            controller.clearList();
            // Get the current stage
            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

            // Create a new scene with the root and set it to the stage
            stage.setScene(new Scene(root));

            // Close the current stage
            stage.close();
        } catch (IOException e) {
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
}
