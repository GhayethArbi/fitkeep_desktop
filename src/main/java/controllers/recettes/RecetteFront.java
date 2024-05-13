package controllers.recettes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Recette;
import services.ServiceRecette;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import controllers.recettes.RecetteCardFront;
public class RecetteFront {

    @FXML
    private HBox hbox;

    @FXML
    private Button modeBTN;

    @FXML
    private GridPane recipe_grid;

    @FXML
    private ImageView tnImage;

    @FXML
    private Button refreshBtn;

    private final ServiceRecette sr = new ServiceRecette();
    public void initialize() {
        try {
            loadGridRecette();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void goBack(ActionEvent event)throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Front/Accueil.fxml"));
        Parent root = fxmlLoader.load();

        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Create a new scene with the root and set it to the stage
        stage.setScene(new Scene(root));
        stage.show();
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
    private boolean isLightMode =true;
    private void setDarkMode() {
        hbox.getStylesheets().remove("css/lightMode.css");
        hbox.getStylesheets().add("css/darkMode.css");
       // Image image = new Image("images/brightness.png");
        //tnImage.setImage(image);
    }

    private void setLightMode() {
        hbox.getStylesheets().remove("css/darkMode.css");
        hbox.getStylesheets().add("css/lightMode.css");
       //Image image = new Image("images/brightness.png");
       //tnImage.setImage(image);
    }


    @FXML
    void modeclicked(MouseEvent event) {
        isLightMode =!isLightMode;
        if (isLightMode) {
            setLightMode();
        }else
        {
            setDarkMode();
        }
    }

    @FXML
    void mouseClicked(MouseEvent event) {
    }

    @FXML
    void refresh(MouseEvent event) {
        try {
        loadGridRecette();
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception en conséquence, par exemple, afficher une boîte de dialogue d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Erreur lors du chargement des données. Veuillez réessayer.");
            alert.showAndWait();
        }
    }
}
