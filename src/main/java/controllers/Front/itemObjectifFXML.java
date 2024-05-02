package controllers.Front;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class itemObjectifFXML implements Initializable {

   @FXML
   ImageView imageObjective ;

   @FXML
   Text nameObjective ;

    private AfficherObjectifsFXML parentController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setData(String objectifName, String imageFile) {
        // Set data in the item controller
        nameObjective.setText(objectifName);

        try {
            // Load image from resources folder
            Image image = new Image(getClass().getResourceAsStream("/img/" + imageFile));
            imageObjective.setImage(image);
        } catch (NullPointerException e) {
            System.err.println("Error loading image: Image file not found or resource path incorrect.");
            e.printStackTrace();
        }

        // Store selection callback

    }
    public Image getImage() {
        return imageObjective.getImage();
    }

    public String getName() {
        return nameObjective.getText();
    }
    public void setParentController(AfficherObjectifsFXML parentController) {
        this.parentController = parentController;
    }
    @FXML
    private void handleClick() {
        // Access the fields of the parent controller and update them with the selected objective data
        parentController.imgObj.setImage(imageObjective.getImage());
        parentController.nameObjField.setText(nameObjective.getText());
    }

}
