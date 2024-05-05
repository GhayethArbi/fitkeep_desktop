package controllers;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import models.Product;
import net.glxn.qrgen.javase.QRCode;
import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
public class QRCodeGenerator {
    @FXML
    private ImageView QrCode;

    public void generateQRCode(Product product) {
        // Extract product details
        int productId = product.getId();
        String productName = product.getName();
        int categoryId = product.getCategory_id();
        double price = product.getPrice();

        // Concatenate product details into a single string
        String productDetails = String.format("ID: %d\nName: %s\nCategory ID: %d\nPrice: $%.2f", productId, productName, categoryId, price);

        // Generate QR code containing product details
        ByteArrayOutputStream out = QRCode.from(productDetails).withSize(200, 200).stream();
        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        try {
            // Convert the QR code stream to an Image
            Image image = new Image(in);
            // Set the image to the ImageView
            QrCode.setImage(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
