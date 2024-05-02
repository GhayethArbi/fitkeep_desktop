package controllers.Front;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import models.ActivitePhysique;

import java.net.URL;
import java.util.ResourceBundle;

public class itemActiviteFXML implements Initializable {

    @FXML
    private ImageView imgActivite;

    @FXML
    private Text nomActivite;

    @FXML
    private Text typeActivite;

    @FXML
    private HBox hboxClicked;

    private ActivitePhysique activite;
    private selectActivitesFXML parentController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void setParentController(selectActivitesFXML parentController) {
        this.parentController = parentController;
    }

    public Image getImage() {
        return imgActivite.getImage();
    }

    public String getName() {
        return nomActivite.getText();
    }
    public String getType() {
        return typeActivite.getText();
    }
    private void handleHBoxClicked() {
      parentController.imgActv.setImage(imgActivite.getImage());
      parentController.nameField.setText(nomActivite.getText());
      parentController.typeField.setValue(typeActivite.getText());
    }

    public void setActivityInfo(String nom, String type, String imagePath) {
        nomActivite.setText(nom);
        typeActivite.setText(type);
        // Load and set the image here using the imagePath
        Image image = new Image("file:///C:/Users/manso/PIP/public/Uploads/" + imagePath);
        imgActivite.setImage(image);
    }


    public HBox getHboxClicked() {
        return hboxClicked;
    }
}
