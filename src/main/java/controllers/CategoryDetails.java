package controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Category;
import services.ServiceCategory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.util.Callback;
import javafx.scene.input.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CategoryDetails implements Initializable {

    @FXML
    private TableView<Category> CategoryTab;

    @FXML
    private TextField NameField;

    @FXML
    private TableColumn<Category, Integer> idCol;

    @FXML
    private TableColumn<Category, String> NameCol;

    @FXML
    private TableColumn<Category, String> operCol;

    private final ServiceCategory sc = new ServiceCategory();
    private ObservableList<Category> CategoryList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize TableView columns
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        NameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        // Load data into TableView
        loadDate();
    }

    @FXML
    public void AddCateg() {
        String categoryName = NameField.getText().trim();

        // Check if the name is not empty
        if (!categoryName.isEmpty()) {
            // Create a new Category object
            Category newCategory = new Category(categoryName);

            try {
                // Call the service method to insert the new category into the database
                sc.insertOne(newCategory);

                // Refresh the TableView to display the new data
                loadDate();
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle SQL exception
            }
        } else {
            // Display an error message or prompt the user to enter a name for the category
        }
    }

    @FXML
    void ClearField() {
        NameField.clear();
    }

    @FXML
    void UpdateCateg() {
        // Get the selected category from the TableView
        Category selectedCategory = CategoryTab.getSelectionModel().getSelectedItem();

        // Check if a category is selected
        if (selectedCategory != null) {
            // Extract the name of the selected category and set it in the NameField
            NameField.setText(selectedCategory.getName());

            // Listen for changes in the NameField
            NameField.textProperty().addListener((observable, oldValue, newValue) -> {
                // Update the name of the selected category as the user types
                selectedCategory.setName(newValue);
            });
        } else {
            // Display a message to prompt the user to select a category before updating
            // This can be a dialog or a label indicating that no category is selected
        }
    }

    public void loadDate() {
        try {
            // Fetch data from the service
            CategoryList.setAll(sc.selectAll());
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exception
        }

        // Set the items to the TableView
        CategoryTab.setItems(CategoryList);

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        NameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        Callback<TableColumn<Category, String>, TableCell<Category, String>> cellFactory = (TableColumn<Category, String> param) -> {
            // make cell containing buttons
            final TableCell<Category, String> cell = new TableCell<Category, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);
                        editIcon.setStyle(
                                " -fx-cursor: hand ;"
                                        + "-glyph-size:28px;"
                                        + "-fx-fill:#00E676;"
                        );
                        deleteIcon.setStyle(
                                "-fx-cursor: hand ;"
                                        + "-glyph-size:28px;"
                                        + "-fx-fill:#ff1744;"
                        );
                        editIcon.setOnMouseClicked((MouseEvent event) -> {
                            Category category = getTableView().getItems().get(getIndex());
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateCateg.fxml"));

                            try {
                                Parent root = loader.load();
                                UpdateCateg addCategController = loader.getController();
                                addCategController.setTextField(category.getName());

                                // Set the reference to the CategoryDetails controller
                                addCategController.setCategoryDetailsController(CategoryDetails.this);

                                Stage stage = new Stage();
                                stage.setScene(new Scene(root));
                                stage.initStyle(StageStyle.UTILITY);
                                stage.show();
                            } catch (IOException ex) {
                                Logger.getLogger(CategoryDetails.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });

                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {
                            try {
                                Category category = getTableView().getItems().get(getIndex());
                                sc.deleteOne(category);

                                // After deletion, remove the category from the TableView
                                CategoryList.remove(category);
                            } catch (SQLException ex) {
                                Logger.getLogger(CategoryDetails.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });

                        HBox hbox = new HBox(editIcon, deleteIcon);
                        hbox.setStyle("-fx-alignment:center");
                        setGraphic(hbox);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        operCol.setCellFactory(cellFactory);
    }

    public void updateSelectedCategory(String newName) {
        // Get the selected category from the TableView
        Category selectedCategory = CategoryTab.getSelectionModel().getSelectedItem();

        // Check if a category is selected
        if (selectedCategory != null) {
            System.out.println("gjhjyggutf");
            // Update the name of the selected category
            selectedCategory.setName(newName);

            try {
                // Call the service method to update the category in the database
                sc.updateOne(selectedCategory);
            } catch (SQLException ex) {
                Logger.getLogger(CategoryDetails.class.getName()).log(Level.SEVERE, null, ex);
                // Handle the exception
            }

            // Refresh the TableView to reflect the changes
            loadDate();
        }
    }

}
