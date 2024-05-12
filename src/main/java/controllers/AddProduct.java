package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
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
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class AddProduct{


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
    @FXML
    private Text SlugError;
    @FXML
    private Text SubError;
    @FXML
    private Text nameError;
    @FXML
    private Text DescriError;
    private static final String ACCOUNT_SID = "ACacecc750024966258be4ef1c74c3cfe7";
    private static final String AUTH_TOKEN = "3b445010e6bf2d74796ceb0ac34d9184";
    private static final String FROM_PHONE_NUMBER = "+12242796337";
    private ProductDetails productDetailsController;
    private final ServiceProduit sp = new ServiceProduit();
    private final ServiceCategory sc = new ServiceCategory();

    public void setProductDetailsController(ProductDetails controller) {
        this.productDetailsController = controller;
    }

    static {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
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

        if (productName.isEmpty() || productName.length() > 8) {
            // Handle invalid product name
            nameError.setFill(Color.RED); // Set error text color to red
            nameError.setText(" length < 8 characters)");
            nameError.setVisible(true); // Make the error text visible
            return;
        } else {
            nameError.setVisible(false); // Hide the error text if validation succeeds
        }

        // Validation du sous-titre
        if (productSubtitle.isEmpty() || productSubtitle.length() > 8) {
            // Handle invalid subtitle
            SubError.setFill(Color.RED); // Set error text color to red
            SubError.setText("Invalid subtitle (must be non-empty and <= 255 characters)");
            SubError.setVisible(true); // Make the error text visible
            return;
        } else {
            SubError.setVisible(false); // Hide the error text if validation succeeds
        }

        try {
            String fileName = generateUniqueFileName();

            Path destinationPath = Paths.get("C:/Users/ghaye/OneDrive/Bureau/pidev/public/Uploads", fileName);

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

            // Send Twilio message
            String successMessage = "Product added successfully: " + productName;
            sendMessage("+21656688168", successMessage);
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

    // Method to send Twilio message
    private void sendMessage(String toPhoneNumber, String messageBody) {
        Message message = Message.creator(
                        new PhoneNumber(toPhoneNumber),
                        new PhoneNumber(FROM_PHONE_NUMBER),
                        messageBody)
                .create();

        System.out.println("Twilio Message SID: " + message.getSid());
    }
}
