package controllers;

import Entity.Panier;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.Product;
import models.User;
import services.PanierService;
import services.session.UserSession;

import javax.swing.text.html.parser.Entity;
import java.util.*;
import java.util.function.Consumer;

public class ItemController {
    private static boolean productAddedToCart = false; // Static variable to track whether any product is added to the cart
    public Button AddToCard;
    private boolean addedToCart = false; // Flag to track whether the product is added to cart

    public Label priceLabel;
    @FXML
    private Label NameProductLabel;
    @FXML
    private ImageView ProductImg;

    private Product product;
    private Consumer<Product> selectionCallback;

    public void setData(Product product, Consumer<Product> selectionCallback) {
        this.product = product;
        this.selectionCallback = selectionCallback;
        ItemController.itemControllers.add(this);
        // Set data in the item controller
        NameProductLabel.setText(product.getName());
        priceLabel.setText(String.valueOf(product.getPrice())); // Assuming priceLabel expects a String

        // Load image from file
        Image image = new Image("file:///D:/pidev/public/Uploads/" + product.getIllustration());

        ProductImg.setImage(image);
    }



    @FXML
    void click() {
        // Handle mouse click event here
        if (selectionCallback != null) {
            selectionCallback.accept(product);
        }
    }

    public void AddToCardAction(ActionEvent actionEvent) {
        // Create a TextInputDialog to prompt the user for the quantity
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add to Cart");
        dialog.setHeaderText("Enter Quantity");
        dialog.setContentText("Quantity:");

        // Show the dialog and wait for the user's response
        dialog.showAndWait().ifPresent(quantity -> {
            // Parse the quantity as an integer
            try {
                int qty = Integer.parseInt(quantity);

                // Create a new Panier object with the provided quantity
                Panier newPanier = new Panier();
                newPanier.setQuantite(qty);
                newPanier.setProduct(this.product);
                User user=new User();
                user.setId(UserSession.CURRENT_USER.getUserLoggedIn().getId());
                newPanier.setUser(user);
                newPanier.setTotalPrice(qty*product.getPrice());
                PanierService panierService=new PanierService();
                panierService.addPanier(newPanier);
                addedToCart = true;

                // Publish an event to notify other components about the change
                notifyProductAddedToCart();

                // Hide the Add to Cart button
                AddToCard.setVisible(false);

            } catch (NumberFormatException e) {
                // If the user entered a non-integer value, show an alert
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please enter a valid integer quantity.");
                alert.showAndWait();
            }
        });
    }
    public static List<ItemController> itemControllers = new ArrayList<>();
    public void notifyProductAddedToCart() {
        // Iterate through all ItemController instances and hide the Add to Cart button
        // You need to have a mechanism to access all instances of ItemController here
        // For demonstration purposes, let's assume you have access to all instances in a list called 'itemControllers'
        for (ItemController controller : itemControllers) {
            if (controller != this) {
                controller.hideAddToCartButton();
            }
        }
    }

    private void hideAddToCartButton() {
        AddToCard.setVisible(false);
    }
}
