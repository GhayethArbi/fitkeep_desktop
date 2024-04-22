package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Category;
import models.Product;
import services.ServiceCategory;
import services.ServiceProduit;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

public class AddProduct {
    @FXML
    private ChoiceBox<String> Categ_id;

    @FXML
    private TextField DescriField;

    @FXML
    private ImageView IllustrationView;

    @FXML
    private TextField NameField;

    @FXML
    private TextField PriceField;

    @FXML
    private TextField QuantiteField;

    @FXML
    private TextField SlugField;

    @FXML
    private TextField SubtitleField;
    private ProductDetails productDetailsController;

    private final ServiceProduit sp = new ServiceProduit();
    private final ServiceCategory sc = new ServiceCategory();

    public void setProductDetailsController(ProductDetails controller) {
        this.productDetailsController = controller;
    }

    // Method to initialize the controller
    @FXML
    void initialize() {
        try {
            List<Category> categories = sc.getAllCategories();
            if (categories != null) {
                for (Category category : categories) {
                    Categ_id.getItems().add(category.getName());
                }
            } else {
                // Handle case where categories couldn't be fetched
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exception
        }

        // Add listener to the NameField to update SlugField and restrict input to letters
        NameField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z ]*")) {
                NameField.setText(oldValue);
            }
            updateSlug(newValue);
        });

        // Add listener to the SubtitleField to restrict input to letters and numbers
        SubtitleField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z0-9 ]*")) {
                SubtitleField.setText(oldValue);
            }
        });

        // Add listener to the DescriField to restrict input to letters and numbers
        DescriField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z0-9 ]*")) {
                DescriField.setText(oldValue);
            }
        });
    }

    @FXML
    void AddProd(ActionEvent event) {
        String productName = NameField.getText().trim();
        String productSubtitle = SubtitleField.getText().trim();

        // Validation du nom
        if (productName.isEmpty() || productName.length() > 255) {
            // Handle invalid product name
            return;
        }

        // Validation du sous-titre
        if (productSubtitle.isEmpty() || productSubtitle.length() > 255) {
            // Handle invalid subtitle
            return;
        }

        // Validate other fields (PriceField, QuantiteField, DescriField, Categ_id) as needed

        try {
            String fileName = generateUniqueFileName();

            Path destinationPath = Paths.get("C:/Users/Dell/PEIPROJECT/public/Uploads", fileName);

            Image image = IllustrationView.getImage();
            if (image != null) {
                File selectedFile = new File(image.getUrl().replace("file:/", ""));
                byte[] imageData = Files.readAllBytes(selectedFile.toPath());
                Files.write(destinationPath, imageData);
            }

            String illustrationPath = fileName;

            String categoryName = Categ_id.getValue();

            Category category = sc.getCategoryByName(categoryName);

            // Automatically generate slug based on product name
            String productSlug = generateSlug(productName);

            Product newProduct = new Product();
            newProduct.setName(productName);
            newProduct.setPrice(Integer.parseInt(PriceField.getText().trim()));
            newProduct.setQuantite(Integer.parseInt(QuantiteField.getText().trim()));
            newProduct.setSlug(productSlug);
            newProduct.setSubtitle(productSubtitle);
            newProduct.setDescription(DescriField.getText().trim());
            newProduct.setIllustration(illustrationPath);
            newProduct.setCategory(category);

            sp.insertOne(newProduct);
            if (productDetailsController != null) {
                productDetailsController.refreshTableView();
            }

            // Refresh the TableView to display the new data
            loadDate();
            System.out.println("Product added successfully: " + productName);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            // Handle IO or SQL exception
        }
    }


    private String generateUniqueFileName() {
        String extension = "jpg"; // You can modify this to support other image formats
        String uniqueId = md5(uniqid());
        return uniqueId + "." + extension;
    }

    private String md5(String input) {
        // Implement md5 hashing function (you can use libraries or built-in functions for this)
        // This is a simplified example and may not be cryptographically secure
        // You can use libraries like Apache Commons Codec or MessageDigest for secure hashing
        return input; // Placeholder implementation
    }

    private String uniqid() {
        // Implement uniqid generation function (you can use libraries or built-in functions for this)
        // This is a simplified example
        return Long.toHexString(System.currentTimeMillis());
    }

    @FXML
    void chooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image");

        // Show the file chooser dialog
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            // Load the selected image into the ImageView
            Image image = new Image(selectedFile.toURI().toString());
            IllustrationView.setImage(image);
        }
    }

    private void loadDate() {
        // Method to refresh the TableView
    }

    // Method to automatically generate slug based on product name
    private String generateSlug(String productName) {
        return productName.toLowerCase().replaceAll("\\s+", "-");
    }

    // Method to update SlugField based on NameField
    private void updateSlug(String productName) {
        String slug = generateSlug(productName);
        SlugField.setText(slug);
    }
}
