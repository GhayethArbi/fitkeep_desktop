package controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import models.ActivitePhysique;
import models.Objectif;
import services.ServiceObjectif;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AfficherObjectifsFXML implements Initializable {
    @FXML
    TableView<Objectif> ObjectifsTable;

    @FXML
    TableColumn<Objectif, String> idCol;

    @FXML
    TableColumn<Objectif, String> nomCol;

    @FXML
    TableColumn<Objectif, String> dateCol;

    @FXML
    TableColumn<Objectif, String> durCol;

    @FXML
    TableColumn<Objectif, String> calBrCol;

    @FXML
    TableColumn<Objectif, String> noteCol;
    
    @FXML
    TableColumn<Objectif, String> operCol;


    ObservableList<Objectif> ObjectifList = FXCollections.observableArrayList();
    ServiceObjectif sap = new ServiceObjectif();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

             loadDate() ;
    }

    public void getAddView(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterObjectif.fxml"));
            Parent parent = loader.load();

            // Get the controller of AjouterActiviteFXML
            AjouterObjectifFXML ajouterObjectifController = loader.getController();

            // Pass a reference to AfficherActivitesFXML to AjouterActiviteFXML
            ajouterObjectifController.setParentFXMLLoader(this);

            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AjouterActiviteFXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void refreshTable() {
        try {
            ObjectifList.clear();
            List<Objectif> objectifList = sap.selectAll();
             System.out.println(objectifList);

            ObjectifList.addAll(objectifList);
            System.out.println(ObjectifList);

            ObjectifsTable.setItems(ObjectifList);
             System.out.println(ObjectifsTable);

        } catch (SQLException ex) {
            Logger.getLogger(AfficherActivitesFXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void loadDate() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nomObjectif"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("dateObjectif"));
        durCol.setCellValueFactory(new PropertyValueFactory<>("totalDuree"));
        calBrCol.setCellValueFactory(new PropertyValueFactory<>("totalCalories"));
        noteCol.setCellValueFactory(new PropertyValueFactory<>("note"));
        refreshTable();
        Callback<TableColumn<Objectif, String>, TableCell<Objectif, String>> cellFoctory = (TableColumn<Objectif, String> param) -> {
            // make cell containing buttons
            final TableCell<Objectif, String> cell = new TableCell<Objectif, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {
                        //  FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.Arr);

                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);
                        FontAwesomeIconView infoIcon = new FontAwesomeIconView(FontAwesomeIcon.INFO_CIRCLE);

                        infoIcon.setStyle(
                                " -fx-cursor: hand ;"
                                        + "-glyph-size:28px;"
                                        + "-fx-fill:#633fff;"
                        );
                        deleteIcon.setStyle(
                                " -fx-cursor: hand ;"
                                        + "-glyph-size:28px;"
                                        + "-fx-fill:#ff1744;"
                        );
                        editIcon.setStyle(
                                " -fx-cursor: hand ;"
                                        + "-glyph-size:28px;"
                                        + "-fx-fill:#00E676;"
                        );

                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {

                            try {
                                Objectif objectif =new Objectif() ;
                                objectif = ObjectifsTable.getSelectionModel().getSelectedItem();
                                sap.deleteOne(objectif);
                                refreshTable();

                            } catch (SQLException ex) {
                                Logger.getLogger(AfficherActivitesFXML.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });

                        editIcon.setOnMouseClicked((MouseEvent event) -> {
                            Objectif objectif = ObjectifsTable.getSelectionModel().getSelectedItem();
                            //  System.out.println(activitePhysique) ;
                            if (objectif != null) {
                                try {
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierObjectif.fxml"));
                                    Parent parent = loader.load();

                                    // Get the controller of ModifierActiviteFXML
                                    ModifierObjectifFXML modifierObjectifFXML = loader.getController();

                                    // Pass the selected activity to AjouterActiviteFXML to populate its fields
                                    modifierObjectifFXML.setTextField(
                                            objectif.getId(),
                                            objectif.getNomObjectif(),
                                            objectif.getDateObjectif(),
                                            objectif.getTotalCalories(),
                                            objectif.getTotalDuree(),
                                            objectif.getNote()
                                    );

                                    Scene scene = new Scene(parent);
                                    Stage stage = new Stage();
                                    stage.setScene(scene);
                                    stage.initStyle(StageStyle.UTILITY);
                                    stage.show();


                                } catch (IOException ex) {
                                    Logger.getLogger(AfficherActivitesFXML.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } else {
                                // Handle case when no activity is selected
                                // You can show an alert or message to inform the user to select an activity
                            }
                        });

                        infoIcon.setOnMouseClicked((MouseEvent event) -> {
                            Objectif objectif = new Objectif() ;
                            objectif = ObjectifsTable.getSelectionModel().getSelectedItem();

                            //  System.out.println(activitePhysique.getObjectifs()) ;
                            if (objectif != null) {
                                try {
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/InfoObjectifActs.fxml"));
                                    //   System.out.println(loader);
                                    Parent parent = loader.load();

                                    // Get the controller of ModifierActiviteFXML
                                    InfoObjectifActsFXML infoObjectifActsFXML = loader.getController();
                                    infoObjectifActsFXML.display(objectif);
                                    // Pass the selected activity to AjouterActiviteFXML to populate its fields


                                    Scene scene = new Scene(parent);
                                    Stage stage = new Stage();
                                    stage.setScene(scene);
                                    stage.initStyle(StageStyle.UTILITY);
                                    stage.show();


                                } catch (IOException ex) {
                                    Logger.getLogger(AfficherActivitesFXML.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } else {
                                // Handle case when no activity is selected
                                // You can show an alert or message to inform the user to select an activity
                            }
                        });



                        HBox managebtn = new HBox(editIcon, deleteIcon,infoIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));
                        HBox.setMargin(infoIcon, new Insets(2, 4, 0, 4));

                        setGraphic(managebtn);
                        setText(null);
                    }
                }

            };

            return cell;
        };
        operCol.setCellFactory(cellFoctory);
        ObjectifsTable.setItems(ObjectifList);
    }

    public void print(MouseEvent mouseEvent) {
    }
}
