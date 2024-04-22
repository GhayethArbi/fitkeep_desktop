package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Category;
import services.ServiceCategory;
import services.ServiceProduit;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UpdateCateg {

    @FXML
    private TextField UpdateField;

    private CategoryDetails categoryDetailsController;
    private final ServiceCategory sc = new ServiceCategory();

    // Method to set text in the TextField
    public void setTextField(String text) {
        UpdateField.setText(text);
    }

    // Method to set the reference to the CategoryDetails controller
    public void setCategoryDetailsController(CategoryDetails controller) {
        this.categoryDetailsController = controller;
    }

    // Method to handle update button action
    @FXML
    void UpdateCategory(ActionEvent event) {
        // Get the text from the UpdateField
        String newText = UpdateField.getText();

        // Check if the CategoryDetails controller is set
        if (categoryDetailsController != null) {
            // Call the method in the CategoryDetails controller to update the selected category
            categoryDetailsController.updateSelectedCategory(newText);

            // Refresh the table view to reflect the changes
            categoryDetailsController.loadDate();

            // Close the update window
            ((Stage) UpdateField.getScene().getWindow()).close();
        }
    }

}
