package controllers.Front;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import models.ActivitePhysique;
import models.Objectif;
import services.ServiceActivitePhysique;
import services.ServiceObjectif;

import java.sql.SQLException;

public class itemActiviteCreeFXML {

    @FXML
    private ImageView imgActivite;

    @FXML
    private Text nomActivite;

    @FXML
    private Text typeActivite;

    @FXML
    private Text duratActivite;

    @FXML
    private Text calActvite;

    @FXML
    private Text nbSerActivite;

    @FXML
    private Text nbRepSerActivite;

    @FXML
    private Text weightActivite;

    @FXML
    private HBox hboxClicked;


    private selectActivitesForObjectifFXML parentController;
    private Integer IdAct ;
    private ServiceObjectif sapObjectif = new ServiceObjectif() ;


    public void setParentController(selectActivitesForObjectifFXML parentController) {
        this.parentController = parentController;
    }


    public void setActivityInfo(ActivitePhysique activitePhysique) {
      this.IdAct=activitePhysique.getId();
      Image image = new Image("file:///C:/Users/manso/PIP/public/Uploads/" + activitePhysique.getImageActivite());
      imgActivite.setImage(image);
      nomActivite.setText(activitePhysique.getNomActivite());
      typeActivite.setText(activitePhysique.getTypeActivite());
      duratActivite.setText(activitePhysique.getDureeActivite().toString());
      calActvite.setText(activitePhysique.getCaloriesBrules().toString());
      nbSerActivite.setText(activitePhysique.getNbSeries().toString());
      nbRepSerActivite.setText(activitePhysique.getNbRepSeries().toString());
      weightActivite.setText(activitePhysique.getPoidsParSerie().toString());
    }

    public void updateActivite(MouseEvent mouseEvent) {
            // Update the fields in the parent controller with the details of the selected activity
            parentController.imgActv.setImage(imgActivite.getImage());
            parentController.nameField.setText(nomActivite.getText());
            parentController.typeField.setValue(typeActivite.getText());
            parentController.calField.setText(calActvite.getText());
            parentController.DuratField.setText(nbSerActivite.getText());
            parentController.nbSerField.setText(nbRepSerActivite.getText());
            parentController.NbRepField.setText(nbRepSerActivite.getText());
            parentController.weigField.setText(weightActivite.getText());
            parentController.IdAct=IdAct;
        System.out.println(IdAct);
            // Set other fields accordingly (calField, DuratField, etc.)

            // Optionally, you can also close the current stage or dialog after updating the fields
            // For example, if your selectActivitesForObjectifFXML controller is displayed in a dialog,
            // you can close the dialog after updating the fields.
            // ((Stage) ((Node) mouseEvent.getSource()).getScene().getWindow()).close();

    }

    private void handleHBoxClicked() {
        parentController.imgActv.setImage(imgActivite.getImage());
        parentController.nameField.setText(nomActivite.getText());
        parentController.typeField.setValue(typeActivite.getText());
        parentController.calField.setText(calActvite.getText());
        parentController.DuratField.setText(nbSerActivite.getText());
        parentController.nbSerField.setText(nbRepSerActivite.getText());
        parentController.NbRepField.setText(nbRepSerActivite.getText());
        parentController.weigField.setText(weightActivite.getText());
    }

    public Image getImage() {
        return imgActivite.getImage();
    }

    public String getType() {
        return typeActivite.getText() ;
    }

    public void deleteActivite(MouseEvent mouseEvent) throws SQLException {
        // Fetch the objective details
        ActivitePhysique activitePhysique = new ActivitePhysique();
        activitePhysique = sapObjectif.fetchActiviteById(IdAct);

        // Call the delete method of the parent controller (AfficherObjectifsFXML)
        if (parentController != null) {
            parentController.deleteActivite(activitePhysique);
        } else {
            System.err.println("Parent controller is not set.");
        }
    }

    public Node getHboxClicked() {
    return hboxClicked;
    }

    public String getName() {
        return nomActivite.getText();
    }
    public String getCalories(){
        return calActvite.getText();
    }
    public String getDurat(){
        return duratActivite.getText();
    }
    public String getNbSer(){
        return nbSerActivite.getText();
    }

    public String getNbRep(){
        return nbRepSerActivite.getText();
    }
    public String getWeight(){
        return weightActivite.getText();
    }


}
