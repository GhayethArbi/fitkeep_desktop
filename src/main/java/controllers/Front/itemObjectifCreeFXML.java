package controllers.Front;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Objectif;
import services.ServiceActivitePhysique;
import services.ServiceObjectif;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class itemObjectifCreeFXML implements Initializable {

    @FXML
    Text nameText ;

    @FXML
    Text dateText ;

    @FXML
    Text duratText ;

    @FXML
    Text calText ;

    @FXML
    Text noteText ;
    ServiceObjectif sapObjectif = new ServiceObjectif();
    ServiceActivitePhysique sapActivitePhysique = new ServiceActivitePhysique();
    private AfficherObjectifsFXML parentController;
    private Integer objectId;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    

    public void setData(Objectif objectif) {
        this.objectId = objectif.getId(); // Set the id
        nameText.setText(objectif.getNomObjectif());
        dateText.setText(objectif.getDateObjectif().toString());
        calText.setText(objectif.getTotalCalories().toString());
        duratText.setText(objectif.getTotalDuree().toString());
        noteText.setText(objectif.getNote());
    }
    public void setParentController(AfficherObjectifsFXML parentController) {
        this.parentController = parentController;
    }

    public int getObjectId() {
        return objectId;
    }
    public String getName() {
        return nameText.getText();
    }

    public LocalDate getDate(){
       return LocalDate.parse(dateText.getText());
    }

    public String getNote() {
        return noteText.getText();
    }


    public void deleteObjectif(MouseEvent mouseEvent) throws SQLException {
        // Fetch the objective details
        Objectif objectif = new Objectif();
        objectif = sapActivitePhysique.fetchObjectifById(objectId);

        // Call the delete method of the parent controller (AfficherObjectifsFXML)
        if (parentController != null) {
            parentController.deleteObjective(objectif);
        } else {
            System.err.println("Parent controller is not set.");
        }
    }

    public void viewActivites(MouseEvent mouseEvent) {
        try {
            // Fetch the objective details
            Objectif objectif = sapActivitePhysique.fetchObjectifById(objectId);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Front/selectActivitesForObjectif.fxml"));
            Parent root = fxmlLoader.load();

            // Get the controller associated with the new FXML file
            selectActivitesForObjectifFXML selectActivitesForObjectifFXML = fxmlLoader.getController();

            // Pass the selected objective to the controller
            selectActivitesForObjectifFXML.displayActivites(objectif);

            // Get the current stage
            Stage stage = new Stage(); // Create a new stage for the new scene

            // Create a new scene with the root and set it to the stage
            stage.setScene(new Scene(root));
            stage.show();



        } catch (SQLException e) {
            System.err.println("Error fetching objective details: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
