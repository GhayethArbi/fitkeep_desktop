package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.Product;

import java.util.function.Consumer;

public class ItemController {

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

        // Set data in the item controller
        NameProductLabel.setText(product.getName());
        priceLabel.setText(String.valueOf(product.getPrice())); // Assuming priceLabel expects a String

        // Load image from file
        Image image = new Image("file:///C:/Users/Dell/PEIPROJECT/public/Uploads/" + product.getIllustration());
        ProductImg.setImage(image);
    }

    @FXML
    void click() {
        // Handle mouse click event here
        if (selectionCallback != null) {
            selectionCallback.accept(product);
        }
    }
}
