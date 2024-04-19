package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.ActivitePhysique;
import models.Objectif;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;

public class InfoActiviteObjsFXML implements Initializable {

    @FXML
     TableView<Objectif> objAssActTable ;

    @FXML
    TableColumn<ActivitePhysique, String> idCol;

    @FXML
    TableColumn<ActivitePhysique, String> nameCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
     //TODO
    }

    public void display (ActivitePhysique activitePhysique){
        objAssActTable.getItems().clear();

        // Get the list of objectives associated with the passed ActivitePhysique
        List<Objectif> objectifs = activitePhysique.getObjectifs();
        System.out.println(objectifs);
        // Create a new ObservableList to hold the objectives
        ObservableList<Objectif> observableObjectifs = FXCollections.observableArrayList(objectifs);

        // Set the items of the TableView to the list of objectives
        objAssActTable.setItems(observableObjectifs);

        // Set the cell value factories for each column
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("nom_objectif"));

      }
    @FXML
    private void close(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
