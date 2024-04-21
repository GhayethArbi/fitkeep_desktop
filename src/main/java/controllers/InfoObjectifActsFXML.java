package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.List;
import java.util.ResourceBundle;

public class InfoObjectifActsFXML implements Initializable {
    @FXML
    TableView<ActivitePhysique> AssObjTable ;

    @FXML
    TableColumn<Objectif, String> idCol;

    @FXML
    TableColumn<Objectif, String> nameCol;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
    }

    public void display(Objectif objectif) {
        AssObjTable.getItems().clear();
        //System.out.println(activitePhysique);
        // Get the list of objectives associated with the passed ActivitePhysique
        List<ActivitePhysique> activitePhysiques = objectif.getActivites();

        // Create a new ObservableList to hold the objectives
        ObservableList<ActivitePhysique> observableActivites = FXCollections.observableArrayList(activitePhysiques);
        //System.out.println(objectifs);
        // Set the items of the TableView to the list of objectives
        AssObjTable.setItems(observableActivites);

        // Set the cell value factories for each column
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        nameCol.setCellValueFactory(new PropertyValueFactory<>("nomActivite"));
        System.out.println("------------------------->");
    }

    public void close(MouseEvent mouseEvent) {
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
