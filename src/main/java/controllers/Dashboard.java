package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Dashboard {

    @FXML
    private Button CategoryDashboard;

    @FXML
    private Button ProductDash;

    @FXML
    private Button close;

    @FXML
    private Button home_btn;

    @FXML
    private AnchorPane home_form;

    @FXML
    private Button logout;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button minimize;

    @FXML
    private void CategoryDash(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Category_details.fxml"));
            AnchorPane root = loader.load();
            CategoryDetails categoryDetailsController = loader.getController();
            categoryDetailsController.loadDate(); // Corrected method name
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void DashProd(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ProductDetails.fxml"));
            AnchorPane root = loader.load();
            ProductDetails productDetailsController = loader.getController();
            productDetailsController.loadData(); // Corrected method name
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void close(ActionEvent event) {

    }

    @FXML
    void logout(ActionEvent event) {

    }

    @FXML
    void minimize(ActionEvent event) {

    }

    @FXML
    void switchForm(ActionEvent event) {

    }
}
