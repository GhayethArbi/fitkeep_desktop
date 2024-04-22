package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import models.Product;
import services.ServiceProduit;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class MarketController {

    @FXML
    private VBox chosenFruitCard;

    @FXML
    private ImageView ProdImg;

    @FXML
    private Label ProductNameLabel;

    @FXML
    private Label ProductPriceLabel;

    @FXML
    private ScrollPane scroll;

    @FXML
    private Button search;

    @FXML
    private TextField searchBarField;

    private final ServiceProduit sp = new ServiceProduit();
    private List<Product> products;

    @FXML
    void search(ActionEvent event) {
        String searchKeyword = searchBarField.getText().trim().toLowerCase();
        if (!searchKeyword.isEmpty()) {
            List<Product> filteredProducts = products.stream()
                    .filter(product -> product.getName().toLowerCase().contains(searchKeyword))
                    .collect(Collectors.toList());

            if (!filteredProducts.isEmpty()) {
                displayProducts(filteredProducts);
            } else {
                clearDisplay();
            }
        } else {
            displayAllProducts(); // Call method to display all products
        }
    }

    @FXML
    void clearDisplay() {
        scroll.setContent(new Region()); // Clear the content of the scroll pane
    }

    private void displayAllProducts() {
        displayProducts(products);
    }

    @FXML
    public void initialize() {
        try {
            // Fetch products from the database
            products = sp.selectAll();

            if (!products.isEmpty()) {
                // Display all products
                displayAllProducts();
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
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                ItemController itemController = fxmlLoader.getController();
                itemController.setData(product, this::handleProductSelection);
                grid.add(anchorPane, i % 3, i / 3); // Add item to the grid
            }

            scroll.setContent(grid); // Set the grid as content of the scroll pane
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleProductSelection(Product product) {
        // Update chosenFruitCard with selected product details
        ProductNameLabel.setText(product.getName());
        ProductPriceLabel.setText(String.valueOf(product.getPrice()));
        ProdImg.setImage(new Image("file:///C:/Users/Dell/PEIPROJECT/public/Uploads/" + product.getIllustration()));

        // Generate a random color code
        String colorCode = generateRandomColorCode();

        // Set the background color of the grid
        chosenFruitCard.setStyle("-fx-background-color: #" + colorCode + ";\n" +
                "    -fx-background-radius: 30;");
    }

    // Method to generate a random color code
    private String generateRandomColorCode() {
        Random random = new Random();
        // Generate random values for red, green, and blue components
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);
        // Format the color code as a hexadecimal string
        return String.format("%02x%02x%02x", red, green, blue);
    }
}
