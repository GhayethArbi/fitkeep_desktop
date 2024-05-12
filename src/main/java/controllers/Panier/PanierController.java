package controllers.Panier;

import Entity.Panier;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import models.Product;
import services.PanierService;
import services.session.AuthDTO;
import services.session.UserSession;

import java.util.function.Consumer;

public class PanierController {
    public Label priceLabel;
    public Label qty;
    public AnchorPane paniercard;
    @FXML
    private Label NameProductLabel;
    @FXML
    private ImageView ProductImg;

    private Product product;
    private Consumer<Product> selectionCallback;
    private PanierService panierService = new PanierService();

    public void setData(Product product) {
        this.product = product;
        this.selectionCallback = selectionCallback;
        Panier panier = panierService.getPanierByUserIdandProductID(UserSession.CURRENT_USER.getUserLoggedIn().getId(),this.product.getId());

        this.qty.setText(String.valueOf(panier.getQuantite()));
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

    public void DeleteItemFromPanier(ActionEvent actionEvent) {
        Panier panier = panierService.getPanierByUserIdandProductID(UserSession.CURRENT_USER.getUserLoggedIn().getId(),this.product.getId());

        panierService.deletePanier(panier.getIdPanier());
        this.paniercard.setVisible(false);
        GridPane parent = (GridPane) paniercard.getParent();

        // Remove the paniercard from the parent container
        parent.getChildren().remove(paniercard);
    }
}
