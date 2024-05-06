package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import Entity.Commande;
import Entity.Panier;
import services.CommandeService;
import services.PanierService;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.stage.Stage;
import models.Roles;
import services.ServiceUser;
import services.session.UserSession;

public class PanierController {

    private final int current_user = 1;
    public TextField searchText;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Panier> panierTableView;

    @FXML
    private TableColumn<Panier, Integer> productIdColumn;

    @FXML
    private TableColumn<Panier, String> productNameColumn;

    @FXML
    private TableColumn<Panier, Integer> quantityColumn;

    @FXML
    private TableColumn<Panier, Double> totalPriceColumn;

    private ServiceUser serviceUser = new ServiceUser(); // Initialize your UserService
    private PanierService panierService = new PanierService(); // Initialize your PanierService
    private CommandeService commandeService = new CommandeService(); // Initialize your CommandeService


    @FXML
    void initialize() {
        productIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getIdPanier()).asObject());
        productNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProduct().getName()));
        quantityColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantite()).asObject());
        totalPriceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getTotalPrice()).asObject());

        this.reloadTable();
    }

    @FXML
    public void printPanier(ActionEvent actionEvent) {
    }

    @FXML
    public void viewCommand(ActionEvent actionEvent) {
        if (panierTableView.getSelectionModel().getSelectedItem() != null) {
            Panier selectedPanier = panierTableView.getSelectionModel().getSelectedItem();
            Commande commande = new CommandeService().getByPanierId(selectedPanier.getIdPanier());
            if(commande.getIdCommande() == 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information'");
                alert.setHeaderText(null);
                alert.setContentText("You don't have a commande for this cart you will be prompted to create one.");
                alert.showAndWait();
                Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Commande.fxml"));
                //set the selected panier in the commande controller
                Parent root = null;
                try {
                    root = loader.load();
                    CommandeController commandeController = loader.getController();
                    commandeController.setPanier(selectedPanier);
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    currentStage.close();
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information'");
                alert.setHeaderText(null);
                alert.setContentText("You already have a commande for this cart.");
                alert.showAndWait();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select the cart that you want to create your the commande from.");
            alert.showAndWait();
        }

    }

    public void viewProduit(ActionEvent actionEvent) {
        try {
            // Get the current stage
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/add_panier.fxml"));
            Parent root = loader.load();

            // Create a new stage
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            // Close the current stage
            currentStage.close();

            // Show the new stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void searchPanier(InputMethodEvent actionEvent) throws SQLException {
        // Get the search keyword
        String keyword = searchText.getText();
        if(keyword.isEmpty()) {
            this.reloadTable();
        }else{
            List<Panier> paniers = null;

            if(UserSession.CURRENT_USER.getUserLoggedIn().getRoles().equals(Roles.ROLE_ADMIN)) {
                paniers = panierService.getAllPaniers();
            } else {
                paniers = panierService.getPanierByUserId(this.current_user);
            }
            //loop through the paniers and remove the ones that don't match the keyword in all of it's attribues
            for (int i = 0; i < paniers.size(); i++) {
                if(!paniers.get(i).getProduct().getName().contains(keyword) && !String.valueOf(paniers.get(i).getQuantite()).contains(keyword) && !String.valueOf(paniers.get(i).getTotalPrice()).contains(keyword)){
                    paniers.remove(i);
                    i--;
                }
            }
            //clear the table
            panierTableView.getItems().clear();

            //add the filtered paniers to the table
            panierTableView.getItems().addAll(paniers);

        }

    }

    public void deletePanier(ActionEvent actionEvent) {
        // Get the selected panier
        Panier selectedPanier = panierTableView.getSelectionModel().getSelectedItem();
        if (selectedPanier == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select the panier that you want to delete.");
            alert.showAndWait();
            return;
        }
        //check if there is a command related to this panier
        Commande commande = commandeService.getByPanierId(selectedPanier.getIdPanier());
        if (commande.getIdCommande() != 0) {
            //ask for confirmation and warn the user that the related command will be deleted
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("This panier is related to a command. If you delete it, the related command will be deleted too. Are you sure you want to delete this panier?");
            alert.showAndWait();
            if (alert.getResult().getText().equals("Cancel")) {
                return;
            }
            commandeService.deleteCommande(commande.getIdCommande());
        }
        panierService.deletePanier(selectedPanier.getIdPanier());
        this.reloadTable();
    }

    public void editPanier(ActionEvent actionEvent) {
        // Get the selected panier
        Panier selectedPanier = panierTableView.getSelectionModel().getSelectedItem();
        if (selectedPanier == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select the panier that you want to edit.");
            alert.showAndWait();
            return;
        }

        try {
            // Get the current stage
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // Load the new FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/add_panier.fxml"));
            Parent root = loader.load();

            // Get the controller of the new FXML file
            ProductListController controller = loader.getController();
            controller.setPanier(selectedPanier);

            // Create a new stage
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            // Close the current stage
            currentStage.close();

            // Show the new stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void reloadTable() {
        panierTableView.getItems().clear();
        List<Panier> userPanier = null;
        try {

            if(UserSession.CURRENT_USER.getUserLoggedIn().getRoles().equals(Roles.ROLE_ADMIN)) {
                userPanier = panierService.getAllPaniers();
            } else {
                userPanier = panierService.getPanierByUserId(this.current_user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        panierTableView.getItems().addAll(userPanier);
    }

    @FXML
    public void viewCommandFXML(ActionEvent actionEvent) {
        try {
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListeCommandes.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            currentStage.close();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
