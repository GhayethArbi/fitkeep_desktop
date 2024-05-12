package controllers.recettes;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import models.Recette;
import services.ServiceRecette;
import services.ServicesPlanNutritionnel;
import test.FxMain;

import java.sql.SQLException;
import javafx.scene.layout.VBox;
public class RecetteCardFront {

    @FXML
    private VBox card;

    @FXML
    private Text category;

    @FXML
    private Text date;

    @FXML
    private ImageView image;

    @FXML
    private Text title;

     Recette selectedRecette ;
    private final ServicesPlanNutritionnel planNutritionnelService = new ServicesPlanNutritionnel();
    @FXML
    void click(MouseEvent event) {

    }

    public void setRecettes(Recette recette) {
        selectedRecette = recette;
        System.out.println(selectedRecette);
        title.setText(recette.getName());
        //Check if the date is not null before calling toString()
        if (recette.getDate() != null) {
            date.setText(recette.getDate().toString());
        } else {
            date.setText("No Date Available"); // or any default value you prefer
        }
        category.setText(recette.getCategory());
        try {
            Image img = new Image(getClass().getResource("images/cooking.png").toExternalForm());
            image.setImage(img);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            // Handle the exception gracefully
            try {
                Image img = new Image(getClass().getResource("images/cooking.png").toExternalForm()); // added missing .png extension
                image.setImage(img);
            } catch (Exception ex) {
                System.out.println("Error loading default image: " + ex.getMessage());
            }
        }
    }


}
