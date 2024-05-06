package controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import models.ActivitePhysique;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
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
import services.ServiceActivitePhysique;
import javafx.scene.image.Image;

public class AfficherActivitesFXML implements Initializable {
    @FXML
    TableView<ActivitePhysique> PhysicalActivitiesTable;

    @FXML
    TableColumn<ActivitePhysique, String> idCol;

    @FXML
    TableColumn<ActivitePhysique, String> nomCol;

    @FXML
    TableColumn<ActivitePhysique, String> typeCol;

    @FXML
    TableColumn<ActivitePhysique, String> durCol;

    @FXML
    TableColumn<ActivitePhysique, String> calBrCol;

    @FXML
    TableColumn<ActivitePhysique, String> nbSerCol;

    @FXML
    TableColumn<ActivitePhysique, String> nbRepSerCol;

    @FXML
    TableColumn<ActivitePhysique, String> poSerCol;

    @FXML
    TableColumn<ActivitePhysique, String> operCol;

    @FXML
    TableColumn<ActivitePhysique,String> imgCol ;
    int activitePhysiqueId ;
    //private final ImageView imageView = new ImageView();

    ObservableList<ActivitePhysique> ActiviteList = FXCollections.observableArrayList();
    ServiceActivitePhysique sap = new ServiceActivitePhysique();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

         loadDate();
    }



    @FXML
    private void refreshTable() {
        try {
            ActiviteList.clear();
            List<ActivitePhysique> physicalActivityList = sap.selectAll();
         //   System.out.println(physicalActivityList);

            ActiviteList.addAll(physicalActivityList);
            PhysicalActivitiesTable.setItems(ActiviteList);
           // System.out.println(PhysicalActivitiesTable);

        } catch (SQLException ex) {
           Logger.getLogger(AfficherActivitesFXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void getAddView(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterActivite.fxml"));
            Parent parent = loader.load();

            // Get the controller of AjouterActiviteFXML
            AjouterActiviteFXML ajouterActiviteController = loader.getController();

            // Pass a reference to AfficherActivitesFXML to AjouterActiviteFXML
            ajouterActiviteController.setParentFXMLLoader(this);

            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AjouterActiviteFXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getRefrashable(){
        refreshTable();
    }
    @FXML
    private void print(MouseEvent event) {
    }
     private void loadDate() {

         refreshTable();
         idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
         nomCol.setCellValueFactory(new PropertyValueFactory<>("nomActivite"));
         typeCol.setCellValueFactory(new PropertyValueFactory<>("typeActivite"));
         durCol.setCellValueFactory(new PropertyValueFactory<>("dureeActivite"));
         calBrCol.setCellValueFactory(new PropertyValueFactory<>("caloriesBrules"));
         nbSerCol.setCellValueFactory(new PropertyValueFactory<>("nbSeries"));
         nbRepSerCol.setCellValueFactory(new PropertyValueFactory<>("nbRepSeries"));
         poSerCol.setCellValueFactory(new PropertyValueFactory<>("poidsParSerie"));
         imgCol.setCellValueFactory(new PropertyValueFactory<>("imageActivite"));

         imgCol.setCellFactory(column -> {
             return new TableCell<ActivitePhysique, String>() {
                 private final ImageView imageView = new ImageView();

                 {
                     // Set preserveRatio to true to maintain aspect ratio
                     imageView.setPreserveRatio(true);
                     // Set fixed width and height for the image view
                     imageView.setFitWidth(100); // Adjust as needed
                     imageView.setFitHeight(100); // Adjust as needed
                 }

                 @Override
                 protected void updateItem(String imagePath, boolean empty) {
                     super.updateItem(imagePath, empty);
                     if (imagePath == null || empty) {
                         setGraphic(null);
                     } else {
                         // Load image from file path
                         Image image = new Image("file:///C:/Users/manso/PIP/public/Uploads/" + imagePath);
                         imageView.setImage(image);
                         setGraphic(imageView);
                     }
                 }
             };
         });


         /*   .setCellValueFactory(new PropertyValueFactory<>("imageActivite"));
         imgCol.setCellFactory(column -> {
             return new TableCell() {
                 private final ImageView imageView = new ImageView();

                 @Override
                 protected void updateItem(String imagePath, boolean empty) {
                     super.updateItem(imagePath, empty);
                     if (imagePath == null || empty) {
                         setGraphic(null);
                     } else {
                         // Load image from file path
                         Image image = new Image("file:///C:/Users/manso/PIP/public/Uploads/" + imagePath);
                         imageView.setImage(image);
                         imageView.setFitWidth(50); // Adjust as needed
                         imageView.setFitHeight(50); // Adjust as needed
                         setGraphic(imageView);
                     }
                 }
             };
         });*/


    Callback<TableColumn<ActivitePhysique, String>, TableCell<ActivitePhysique, String>> cellFoctory = (TableColumn<ActivitePhysique, String> param) -> {
             // make cell containing buttons
             final TableCell<ActivitePhysique, String> cell = new TableCell<ActivitePhysique, String>() {
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
                                 ActivitePhysique activitePhysique =new ActivitePhysique() ;
                                 activitePhysique = PhysicalActivitiesTable.getSelectionModel().getSelectedItem();
                                 sap.deleteOne(activitePhysique);
                                 refreshTable();

                             } catch (SQLException ex) {
                                 Logger.getLogger(AfficherActivitesFXML.class.getName()).log(Level.SEVERE, null, ex);
                             }
                         });

                         editIcon.setOnMouseClicked((MouseEvent event) -> {
                             ActivitePhysique activitePhysique = PhysicalActivitiesTable.getSelectionModel().getSelectedItem();
                           //  System.out.println(activitePhysique) ;
                             if (activitePhysique != null) {
                                 try {
                                     FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierActivite.fxml"));
                                     Parent parent = loader.load();

                                     // Get the controller of ModifierActiviteFXML
                                     ModifierActiviteFXML modifierActiviteFXML = loader.getController();

                                     // Pass the selected activity to AjouterActiviteFXML to populate its fields
                                     modifierActiviteFXML.setTextField(
                                             activitePhysique.getId(),
                                             activitePhysique.getNomActivite(),
                                             activitePhysique.getTypeActivite(),
                                             activitePhysique.getDureeActivite(),
                                             activitePhysique.getCaloriesBrules(),
                                             activitePhysique.getNbSeries(),
                                             activitePhysique.getNbRepSeries(),
                                             activitePhysique.getPoidsParSerie(),
                                             activitePhysique.getImageActivite()
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
                             ActivitePhysique activitePhysique = new ActivitePhysique() ;
                             activitePhysique = PhysicalActivitiesTable.getSelectionModel().getSelectedItem();

                           //  System.out.println(activitePhysique.getObjectifs()) ;
                             if (activitePhysique != null) {
                                 try {
                                     FXMLLoader loader = new FXMLLoader(getClass().getResource("/InfoActiviteObjs.fxml"));
                                  //   System.out.println(loader);
                                     Parent parent = loader.load();

                                     // Get the controller of ModifierActiviteFXML
                                     InfoActiviteObjsFXML infoActiviteObjsFXML = loader.getController();
                                     infoActiviteObjsFXML.display(activitePhysique);
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
         PhysicalActivitiesTable.setItems(ActiviteList);
     }
    }