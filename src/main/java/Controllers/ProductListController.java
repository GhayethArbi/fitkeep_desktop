package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import Entity.Panier;
import Entity.Product;
import services.PanierService;
import services.ProductService;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.User;
import repository.UserRepository;
import services.session.UserSession;

public class ProductListController {

    @FXML
    public TextField quantityTextField;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Product> productTableView;

    @FXML
    private TableColumn<Product, Integer> idColumn;

    @FXML
    private TableColumn<Product, String> nameColumn;

    @FXML
    private TableColumn<Product, Double> priceColumn;

    @FXML
    private TableColumn<Product, String> descriptionColumn;

    @FXML
    private Button addToCartButton;

    private Panier cart = new Panier();
    private boolean isCartEmpty = true;
    private User userId;
    @FXML
    void initialize() {
        // Set cell value factories for table columns
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIdProduct()).asObject());
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        priceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
        descriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));

        // Initialize the table with product data from the database
        ProductService productService = new ProductService();
        List<Product> productList = productService.getAllProducts();
        productTableView.getItems().addAll(productList);
        UserRepository userRepository = new UserRepository();
        try {
            userId = userRepository.findById(UserSession.CURRENT_USER.getUserLoggedIn().getId());

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @FXML
    void addToCart(ActionEvent event) {


        // Get the selected product from the TableView
        Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();

        // Get the user ID of the currently logged-in user (if applicable)
        UserRepository u = new UserRepository();
       ProductService p = new ProductService();
       try{
        User userId = u.findById(UserSession.CURRENT_USER.getUserLoggedIn().getId()); // Implement this method based on your user authentication logic
        int quantity = 0;

            quantity = Integer.parseInt(quantityTextField.getText());
            if (quantity <= 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please enter a valid quantity.");
                alert.showAndWait();
                return;
            }
        }catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid quantity.");
            alert.showAndWait();
            return;
        }catch (SQLException e){
           e.printStackTrace();
       }


        // Check if a product is selected
        if (selectedProduct != null) {
            // Create a new Panier object
            Panier panier = this.cart;
            panier.setUser(userId);
            Product produit=p.getProductById(selectedProduct.getIdProduct());
            panier.setProduct(produit); // Assuming getId() returns the product ID
            panier.setQuantite(Integer.parseInt(quantityTextField.getText())); // Set the quantity to 1 for now, you can adjust this as needed
            panier.setTotalPrice(selectedProduct.getPrice()*Integer.parseInt(quantityTextField.getText())); // Assuming getPrice() returns the product price

            // Add the product to the panier table
            PanierService panierService = new PanierService();
            if(isCartEmpty){
                panierService.addPanier(panier);
                isCartEmpty = false;
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Product added to cart successfully.");
                alert.showAndWait();
            }else{
                panierService.updatePanier(panier);
            }

        } else {
            // If no product is selected, display an error message to the user
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a product to add to cart.");
            alert.showAndWait();
        }
    }

    public void Panier(ActionEvent event) {
        try {
            // Get the current stage
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Panier.fxml"));
            Parent root = loader.load();

            // Create a new stage
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            // Close the current stage
            currentStage.close();

            // Show the new stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setPanier(Panier selectedPanier) {
        this.cart = selectedPanier;
        this.isCartEmpty = false;
    }

    public void viewPannier(ActionEvent actionEvent) {
        try{
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Panier.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Panier");
            stage.setScene(new Scene(root1));
            stage.show();
            currentStage.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void viewCommand(ActionEvent actionEvent) {
        try {
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListeCommandes.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            currentStage.close();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void viewProducts(ActionEvent actionEvent) {
    }
}

