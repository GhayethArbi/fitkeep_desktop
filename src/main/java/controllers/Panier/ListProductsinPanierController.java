package controllers.Panier;

import Entity.Commande;
import Entity.Panier;
import controllers.CommandDetails;
import controllers.ItemController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import models.Product;
import repository.UserRepository;
import services.PanierService;
import services.ServiceProduit;
import services.ServiceUser;
import services.session.UserSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ListProductsinPanierController {


        @FXML
        private GridPane leftGridPane;

        private final ServiceProduit sp = new ServiceProduit();
        private List<Product> products;

        @FXML
        public void initialize() {
            // Fetch products from the database
            products = sp.selectProductByUserIdinPanier(UserSession.CURRENT_USER.getUserLoggedIn().getId());

            if (!products.isEmpty()) {
                // Display all products
                displayProducts(products);
            }
        }

        private void displayProducts(List<Product> productsToDisplay) {
            try {
                GridPane grid = new GridPane(); // Create a new grid
                grid.setHgap(10); // Set horizontal gap between items
                grid.setVgap(10); // Set vertical gap between items

                for (int i = 0; i < productsToDisplay.size(); i++) {
                    Product product = productsToDisplay.get(i);
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Panier/ProductsPanierCard.fxml"));
                    AnchorPane item = fxmlLoader.load(); // Use AnchorPane instead of VBox
                    PanierController panierController = fxmlLoader.getController();
                    panierController.setData(product);
                    grid.add(item, i % 3, i / 3); // Add item to the grid
                }

                // Set the grid as content of the left GridPane
                leftGridPane.getChildren().setAll(grid);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void handleProductSelection(Product product) {
            // Handle product selection here if needed
        }

        public void getBack(MouseEvent mouseEvent) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Front/Accueil.fxml"));
            Parent root = fxmlLoader.load();

            // Get the current stage
            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

            // Create a new scene with the root and set it to the stage
            stage.setScene(new Scene(root));
            stage.show();
        }

    public void MakeCommande(ActionEvent actionEvent) throws SQLException {
        System.out.println("making commande");
            Commande commande = new Commande();
        PanierService panierService=new PanierService();
        List<Panier> listPaniers=panierService.getPanierByUserId(UserSession.CURRENT_USER.getUserLoggedIn().getId());
        if(listPaniers.isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fail");
            alert.setHeaderText(null);
            alert.setContentText("Panier est vide.");
            alert.showAndWait();
            return;
        }
        Panier panier = listPaniers.get(0);
        try {
                UserRepository SU=new UserRepository();
                commande.setUser(SU.findById(UserSession.CURRENT_USER.getUserLoggedIn().getId()));
                commande.setPanier(panier);

                    //Show Commande.fxml and pass a variable to indaicate the panierId to be selcted
                    try{
                        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Panier/CommandeClient.fxml"));
                        Parent root1 = (Parent) fxmlLoader.load();
                        CommandeController controller = fxmlLoader.getController();
                        CommandeController.panier=panier;
                        controller.setSelectPanier(true);
                        controller.setPanier(panier);
                        panierService.deletePanier(panier.getIdPanier());
                        Stage stage = new Stage();
                        stage.setTitle("Ajouter une commande");
                        stage.setScene(new Scene(root1));
                        stage.show();
                        currentStage.close();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
  /*   commande.setPanier(selectedPanier);
                commande.setModeDePaiement(modeDePaiementComboBox.getValue());
                commande.setAdresse(adresseTextField.getText());
                commande.setStatut("En cours de traitement");
                if (this.checkCommand(commande)) {
                    return;
                }
                if (this.isUpdate) {
                    commande.setIdCommande(this.selectedcommande.getIdCommande());
                    commandeService.updateCommande(commande);
                } else {
                    commandeService.addCommande(commande);
                }*/



                // Redirect the user to the list of commandes interface

            }catch (SQLException e){
                e.printStackTrace();
            }

    }
}

