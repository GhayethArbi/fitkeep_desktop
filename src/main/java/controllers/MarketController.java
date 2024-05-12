package controllers;

import Entity.Panier;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.Product;
import services.PanierService;
import services.ServiceProduit;
import services.session.UserSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MarketController {

    @FXML
    private GridPane leftGridPane;
    private PanierService panierService=new PanierService();
    private final ServiceProduit sp = new ServiceProduit();
    private List<Product> products;

    @FXML
    public void initialize() {
        try {
            // Fetch products from the database
            products = sp.selectAll();

            if (!products.isEmpty()) {
                // Display all products
                displayProducts(products);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exception
        }
    }

    private void displayProducts(List<Product> productsToDisplay) {
        try {
            GridPane grid = new GridPane(); // Create a new grid
            grid.setHgap(10); // Set horizontal gap between items
            grid.setVgap(10); // Set vertical gap between items

            for (int i = 0; i < productsToDisplay.size(); i++) {
                Product product = productsToDisplay.get(i);
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Item.fxml"));
                AnchorPane item = fxmlLoader.load(); // Use AnchorPane instead of VBox
                ItemController itemController = fxmlLoader.getController();
                itemController.setData(product, this::handleProductSelection);
                grid.add(item, i % 3, i / 3); // Add item to the grid
            }

            // Set the grid as content of the left GridPane
            leftGridPane.getChildren().setAll(grid);
            List<Panier> paniers = panierService.getPanierByUserId(UserSession.CURRENT_USER.getUserLoggedIn().getId());
            System.out.println(paniers);
            if (!(paniers.isEmpty())) {
                for (ItemController controller : ItemController.itemControllers) {
                    controller.notifyProductAddedToCart();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleProductSelection(Product product) {
        // Handle product selection here if needed
    }

    public void getBack(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Front/Accueil.fxml"));
        Parent root = fxmlLoader.load();

        // Get the current stage
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

        // Create a new scene with the root and set it to the stage
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void goToAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Panier/ListProductsinpanier.fxml"));
        Parent root = fxmlLoader.load();

        // Get the current stage
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        // Create a new scene with the root and set it to the stage
        stage.setScene(new Scene(root));
        stage.show();

    }
}
