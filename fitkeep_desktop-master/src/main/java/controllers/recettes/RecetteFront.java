package controllers.recettes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import models.Recette;
import services.ServiceRecette;

public class RecetteFront {

    @FXML
    private GridPane recipe_grid;

    private final ServiceRecette sr = new ServiceRecette();
    public void initialize() {
        try {
            loadGridRecette();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    @FXML
    void addRecipeBtn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/recette/AjouterRecetteFXML.fxml"));
            Parent root = loader.load();
            AjouterRecetteFx controller = loader.getController();
            Stage stage = new Stage();
            Scene scene = new Scene(root, 518, 319);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Error loading Add Recipe window: " + e.getMessage());
        }
    }

    void loadGridRecette() throws SQLException {
        recipe_grid.getChildren().clear();
        List<Recette> recettes = sr.selectAll();
        int row = 1;
        int col = 0;
        for (Recette r : recettes) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/recette/recetteCard.fxml"));
                try {
                    VBox card = fxmlLoader.load();
                    RecetteCardFront recetteController = fxmlLoader.getController();
                    recetteController.setRecettes(r);
                    recipe_grid.add(card, col++, row);
                    if (col > 3) {
                        col = 0;
                        row++;
                    }
                    GridPane.setMargin(card, new Insets(10));
                } catch (IOException e) {
                    System.out.println("Error loading recette card: " + e.getMessage());
                }
        }
    }

}
