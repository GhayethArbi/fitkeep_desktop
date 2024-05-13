package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import models.Product;
import org.apache.pdfbox.pdmodel.PDDocument;
import services.ServiceProduit;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductDetails extends NavigationController implements Initializable {
    @FXML
    private TableView<Product> ProductTab;
    @FXML
    private TableColumn<Product, Integer> IdCol;
    @FXML
    private TableColumn<Product, String> NameCol;
    @FXML
    private TableColumn<Product, Integer> Id_categ_col;
    @FXML
    private TableColumn<Product, String> Slug_Col;
    @FXML
    private TableColumn<Product, String> IllustrationCol;
    @FXML
    private TableColumn<Product, String> SubtitleCol;
    @FXML
    private TableColumn<Product, String> DescriCol;
    @FXML
    private TableColumn<Product, Integer> PriceCol;
    @FXML
    private TableColumn<Product, Integer> QuantityCol;
    @FXML
    private TableColumn<Product, String> operCol;

    private final ServiceProduit sp = new ServiceProduit();
    private final ObservableList<Product> ProductList = FXCollections.observableArrayList();

    public void refreshTableView() {
        loadData();
    }
    @FXML
    private void PrintPdf() {
        String filePath = "C:/Users/ghaye/OneDrive/Bureau/output.pdf";
        PDFExporter.exportTableViewToPDF(ProductTab, "C:/Users/Dell/Desktop/logo.jpg", filePath, new PDDocument());
    }



    @FXML
    private void AddView() {
        try {
            // Load the AddProduct.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddProduct.fxml"));
            Parent root = loader.load();

            // Get the controller instance of AddProduct
            AddProduct addProductController = loader.getController();

            // Pass a reference to this ProductDetails controller
            addProductController.setProductDetailsController(this);

            // Create a new stage
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            // Show the stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            super.initialize();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // Initialize TableView columns
        IdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        NameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        Id_categ_col.setCellValueFactory(new PropertyValueFactory<>("category_id"));
        Slug_Col.setCellValueFactory(new PropertyValueFactory<>("slug"));
        SubtitleCol.setCellValueFactory(new PropertyValueFactory<>("subtitle"));
        DescriCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        PriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        QuantityCol.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        IllustrationCol.setCellValueFactory(new PropertyValueFactory<>("illustration"));

        // Load data into TableView
        loadData();
    }

    public void loadData() {
        try {
            // Fetch data from the service
            ProductList.setAll(sp.selectAll());
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exception
        }

        // Set the items to the TableView
        ProductTab.setItems(ProductList);

        // Set up illustration column to display images
        IllustrationCol.setCellFactory(column -> new TableCell<Product, String>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(String filename, boolean empty) {
                super.updateItem(filename, empty);
                if (filename == null || empty) {
                    setGraphic(null);
                } else {
                    // Load image from file
                    File file = new File("D:/pidev/public/Uploads/" + filename);
                    System.out.println("D:/pidev/public/Uploads/" + filename);

                    if (file.exists()) {
                        System.out.println("exist");

                        Image image = new Image(file.toURI().toString());
                        imageView.setImage(image);
                        imageView.setFitWidth(50); // Adjust as needed
                        imageView.setFitHeight(50); // Adjust as needed
                        setGraphic(imageView);

                    } else {
                        setGraphic(null); // Clear the graphic if the file doesn't exist
                    }
                }
            }
        });

        // Set up operation column with icons
        operCol.setCellFactory(column -> new TableCell<Product, String>() {
            private final HBox hbox = new HBox();
            private final FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);
            private final FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
            private final FontAwesomeIconView infoIcon = new FontAwesomeIconView(FontAwesomeIcon.INFO_CIRCLE);

            {
                // Set icon colors
                editIcon.setStyle("-fx-cursor: hand; -glyph-size: 28px; -fx-fill: green;");
                deleteIcon.setStyle("-fx-cursor: hand; -glyph-size: 28px; -fx-fill: red;");
                infoIcon.setStyle("-fx-cursor: hand; -glyph-size: 28px; -fx-fill: blue;");

                editIcon.setOnMouseClicked((MouseEvent event) -> {
                    Product product = getTableView().getItems().get(getIndex());
                    // Handle edit action here
                    // Example: Open a new window for editing
                    openEditWindow(product);
                });

                deleteIcon.setOnMouseClicked((MouseEvent event) -> {
                    Product product = getTableView().getItems().get(getIndex());
                    // Handle delete action here
                    // Example: Delete the product from the database
                    deleteProduct(product);
                });
                infoIcon.setOnMouseClicked((MouseEvent event) -> {
                    Product product = getTableView().getItems().get(getIndex());
                    // Redirect to the page displaying the QR code
                    redirectToQRCodePage(product);
                });

                // Add icons to HBox
                hbox.getChildren().addAll(editIcon, deleteIcon, infoIcon);
                hbox.setStyle("-fx-alignment:center");
                hbox.setSpacing(10); // Adjust spacing as needed
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(hbox);
                }
            }
        });
    }

    @FXML
    private void openEditWindow(Product product) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditProduct.fxml"));
            Parent root = loader.load();
            EditProduct editProductController = loader.getController();
            editProductController.setProduct(product);
            // Pass a reference to the ProductDetails controller
            editProductController.setProductDetailsController(this);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ProductDetails.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void deleteProduct(Product product) {
        try {
            sp.deleteOne(product);
            ProductList.remove(product);
        } catch (SQLException ex) {
            Logger.getLogger(ProductDetails.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void redirectToQRCodePage(Product product) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/QrCodeGenerator.fxml"));
            Parent root = loader.load();

            // Get the controller instance
            QRCodeGenerator qrCodeGeneratorController = loader.getController();

            // Generate QR code containing product details
            qrCodeGeneratorController.generateQRCode(product);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ProductDetails.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
