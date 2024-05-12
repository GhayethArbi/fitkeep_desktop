package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Product;

public class GameController {

    @FXML
    private Text Question1;

    @FXML
    private Text Question11;

    @FXML
    private Text Question111;

    @FXML
    private Text Question112;

    @FXML
    private Text Question113;

    @FXML
    private Text Question12;

    @FXML
    private RadioButton option_one;

    @FXML
    private RadioButton option_one1;

    @FXML
    private RadioButton option_one11;

    @FXML
    private RadioButton option_one12;

    @FXML
    private RadioButton option_one13;

    @FXML
    private RadioButton option_one2;

    @FXML
    private RadioButton option_three;

    @FXML
    private RadioButton option_three1;

    @FXML
    private RadioButton option_three11;

    @FXML
    private RadioButton option_three12;

    @FXML
    private RadioButton option_three13;

    @FXML
    private RadioButton option_three2;

    @FXML
    private RadioButton option_two;

    @FXML
    private RadioButton option_two1;

    @FXML
    private RadioButton option_two11;

    @FXML
    private RadioButton option_two12;

    @FXML
    private RadioButton option_two13;

    @FXML
    private RadioButton option_two2;

    @FXML
    private VBox question11;

    @FXML
    private VBox question2;

    @FXML
    private VBox question3;

    @FXML
    private VBox question4;

    @FXML
    private VBox question5;

    @FXML
    private VBox question6;

    @FXML
    private Text scoreText;

    private ToggleGroup toggleGroup;// ToggleGroup to group radio buttons
    private ToggleGroup toggleGroup1;
    private ToggleGroup toggleGroup2;
    private ToggleGroup toggleGroup3;
    private ToggleGroup toggleGroup4;
    private ToggleGroup toggleGroup5;

    private int score = 0; // Initialize score to 0

    // Discount percentage when score reaches 10
    private static final double DISCOUNT_PERCENTAGE = 0.1; // 10% discount

    @FXML
    public void initialize() {
        // Create ToggleGroup instances
        toggleGroup = new ToggleGroup();
        toggleGroup1 = new ToggleGroup();
        toggleGroup2 = new ToggleGroup();
        toggleGroup3 = new ToggleGroup();
        toggleGroup4 = new ToggleGroup();
        toggleGroup5 = new ToggleGroup();

        // Set ToggleGroup for radio buttons
        option_one.setToggleGroup(toggleGroup);
        option_two.setToggleGroup(toggleGroup);
        option_three.setToggleGroup(toggleGroup);

        option_one1.setToggleGroup(toggleGroup1);
        option_two1.setToggleGroup(toggleGroup1);
        option_three1.setToggleGroup(toggleGroup1);

        option_one2.setToggleGroup(toggleGroup2);
        option_two2.setToggleGroup(toggleGroup2);
        option_three2.setToggleGroup(toggleGroup2);

        option_one11.setToggleGroup(toggleGroup3);
        option_two11.setToggleGroup(toggleGroup3);
        option_three11.setToggleGroup(toggleGroup3);

        option_one12.setToggleGroup(toggleGroup4);
        option_two12.setToggleGroup(toggleGroup4);
        option_three12.setToggleGroup(toggleGroup4);

        option_one13.setToggleGroup(toggleGroup5);
        option_two13.setToggleGroup(toggleGroup5);
        option_three13.setToggleGroup(toggleGroup5);
    }

    @FXML
    public void submitAnswer() {
        RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
        RadioButton selectedRadioButton1 = (RadioButton) toggleGroup1.getSelectedToggle();
        RadioButton selectedRadioButton2 = (RadioButton) toggleGroup2.getSelectedToggle();
        RadioButton selectedRadioButton3 = (RadioButton) toggleGroup3.getSelectedToggle();
        RadioButton selectedRadioButton4 = (RadioButton) toggleGroup4.getSelectedToggle();
        RadioButton selectedRadioButton5 = (RadioButton) toggleGroup5.getSelectedToggle();

        if (selectedRadioButton != null) {
            if (selectedRadioButton == option_two) {
                score += 2;
                if (selectedRadioButton1 != null && selectedRadioButton1 == option_one1) {
                    score += 2;
                }
            }
        }

        if (selectedRadioButton2 != null) {
            if (selectedRadioButton2 == option_two2) {
                score += 2;
                if (selectedRadioButton3 != null && selectedRadioButton3 == option_three11) {
                    score += 2;
                }
            }
        }

        if (selectedRadioButton4 != null) {
            if (selectedRadioButton4 == option_one12) {
                score += 2;
                if (selectedRadioButton5 != null && selectedRadioButton5 == option_two13) {
                    score += 2;
                }
            }
        }

        updateScoreDisplay();

        if (score >= 10) {
            applyDiscount();
        } else {
            // If the score is less than 10, continue as usual
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sorry!");

            alert.setContentText("YOU LOOSE !!!!!!!!");

            // Show the alert and wait for it to be closed
            alert.showAndWait();
            closeWindow();
        }

    }

    // Method to update the score display
    private void updateScoreDisplay() {
        scoreText.setText("Score: " + score);
    }

    // Method to apply discount to the product prices
    private void applyDiscount() {
        // Create an alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Congratulations!");
        alert.setHeaderText("You've won a 10% discount!");
        alert.setContentText("All products will now be discounted by 10%.");

        // Show the alert and wait for it to be closed
        alert.showAndWait();

        // Close the window
        closeWindow();
    }

    // Method to close the window
    private void closeWindow() {
        // Get the current stage (window) from any of the UI elements
        Stage stage = (Stage) option_one.getScene().getWindow();
        // Close the window
        stage.close();
        redirectToProductDetails();
    }
    private void redirectToProductDetails() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ProductDetails.fxml"));
            Parent root = loader.load();


            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ProductDetails.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
