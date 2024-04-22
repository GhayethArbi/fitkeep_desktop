package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import models.Category;
import models.Product;
import services.ServiceCategory;
import services.ServiceProduit;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

public class EditProduct {
    private Product product;

    @FXML
    private ChoiceBox<Category> Categ_id;

    @FXML
    private TextField DescriUpdate;
    @FXML
    private ImageView IllustrationView;

    @FXML
    private TextField PriceUpdate;

    @FXML
    private TextField QuantiteUpdate;

    @FXML
    private TextField SubtitleUpdate;

    @FXML
    private TextField UpdateName;

    @FXML
    private TextField UpdateSlug;
    private final ServiceCategory sc = new ServiceCategory();
    private final ServiceProduit sp = new ServiceProduit();

    private ProductDetails productDetailsController;
    public void setProductDetailsController(ProductDetails controller) {
        this.productDetailsController = controller;
    }

    // Method to set the Product object and populate the fields
    public void setProduct(Product product) {
        this.product = product;
        if (product != null) {
            // Populate fields with product data
            UpdateName.setText(product.getName());
            UpdateSlug.setText(product.getSlug());
            SubtitleUpdate.setText(product.getSubtitle());
            DescriUpdate.setText(product.getDescription());
            PriceUpdate.setText(String.valueOf(product.getPrice()));
            QuantiteUpdate.setText(String.valueOf(product.getQuantite()));

            // Load and display the image using the MD5 link
            try {
                String imagePath = product.getIllustration();
                if (imagePath != null && !imagePath.isEmpty()) {
                    // Construct the file path based on the directory where images are stored
                    String directoryPath = "C:/Users/Dell/PEIPROJECT/public/Uploads/"; // Change this to the actual directory path
                    File file = new File(directoryPath + imagePath);
                    if (file.exists()) {
                        Image image = new Image(file.toURI().toString());
                        IllustrationView.setImage(image);
                    } else {
                        System.err.println("Image file not found: " + imagePath);
                    }
                }
            } catch (Exception e) {
                System.err.println("Error loading image: " + e.getMessage());
                e.printStackTrace();
            }

            // Set the value of the ChoiceBox to the product's category
            Category productCategory = product.getCategory();
            if (productCategory != null) {
                Categ_id.setValue(productCategory);
            } else {
                System.err.println("Product category is null.");
            }
        }
    }

    @FXML
    void ChangePhoto() {
        // Open a file chooser dialog to select a new image
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            try {
                // Generate a unique file name using MD5
                String fileName = generateUniqueFileName(selectedFile);

                // Construct the destination file path with the MD5 filename
                String directoryPath = "C:/Users/Dell/PEIPROJECT/public/Uploads/";
                File destinationFile = new File(directoryPath + fileName);

                // Copy the selected file to the new location with the MD5 hash as the file name
                Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                // Load and display the selected image
                Image newImage = new Image(destinationFile.toURI().toString());
                IllustrationView.setImage(newImage);

                // Update the product's illustration path with the MD5 link
                product.setIllustration(fileName);
            } catch (Exception e) {
                System.err.println("Error loading new image: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private String generateUniqueFileName(File file) throws IOException, NoSuchAlgorithmException {
        // Read the content of the file to generate an MD5 hash
        byte[] fileBytes = Files.readAllBytes(file.toPath());
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] md5Bytes = md.digest(fileBytes);

        // Convert the MD5 bytes to a hexadecimal string
        StringBuilder sb = new StringBuilder();
        for (byte md5Byte : md5Bytes) {
            sb.append(Integer.toString((md5Byte & 0xff) + 0x100, 16).substring(1));
        }

        // Get the file extension
        String extension = getFileExtension(file.getName());

        // Construct the new file name with MD5 hash and file extension
        return sb.toString() + "." + extension;
    }

    private String getFileExtension(String fileName) {
        int lastIndex = fileName.lastIndexOf(".");
        if (lastIndex == -1) {
            return ""; // No extension found
        }
        return fileName.substring(lastIndex + 1);
    }

    @FXML
    void UpdateButton() throws SQLException {
        if (product != null) {
            // Update the product data with the values from the text fields and choice box
            product.setName(UpdateName.getText());
            product.setSlug(UpdateSlug.getText());
            product.setSubtitle(SubtitleUpdate.getText());
            product.setDescription(DescriUpdate.getText());

            // Convert the price text to an integer
            int price = Integer.parseInt(PriceUpdate.getText());
            product.setPrice(price);

            // Convert the quantity text to an integer
            int quantite = Integer.parseInt(QuantiteUpdate.getText());
            product.setQuantite(quantite);

            // Set the category based on the selected value from the ChoiceBox
            Category selectedCategory = Categ_id.getValue();
            if (selectedCategory != null) {
                product.setCategory(selectedCategory);
            } else {
                System.err.println("Selected category not found.");
            }

            // Update the product in the database
            try {
                sp.updateOne(product);
                System.out.println("Product updated successfully.");
            } catch (SQLException e) {
                System.err.println("Error updating product: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.err.println("No product selected for update.");
        }
        // Call the refresh method of ProductDetails controller
        if (productDetailsController != null) {
            productDetailsController.refreshTableView();
        }
    }

    @FXML
    public void initialize() throws SQLException {
        // Populate the ChoiceBox with categories
        List<Category> categories = sc.getAllCategories();
        Categ_id.getItems().addAll(categories);
    }
}
