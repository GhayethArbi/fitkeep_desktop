package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Entity.Commande;
import Entity.Panier;
import services.CommandeService;
import services.PanierService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Roles;
import repository.UserRepository;
import services.ServiceUser;
import services.session.UserSession;

public class CommandeController {

    @FXML
    public TextField pannierTextFIeld;
    @FXML
    public Label pannnierID;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField adresseTextField;

    @FXML
    private ComboBox<String> modeDePaiementComboBox;

    private ServiceUser userService = new ServiceUser();
    private PanierService panierService = new PanierService();
    private CommandeService commandeService = new CommandeService();

    private int userId= UserSession.CURRENT_USER.getUserLoggedIn().getId();
    private final UserRepository userRepository = new UserRepository();

    @FXML
    void addCommande(ActionEvent event) {
        Commande commande = new Commande();
        try {
            commande.setUser(userRepository.findById(userId));

            commande.setPanier(selectedPanier);
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
            }


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Commandes added successfully.");
            alert.showAndWait();

            // Redirect the user to the list of commandes interface
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListeCommandes.fxml"));
            Parent root;
            try {
                root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) adresseTextField.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private boolean checkCommand(Commande commande) {
        if (commande.getAdresse().isEmpty() || commande.getModeDePaiement()==null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the fields.");
            alert.showAndWait();
            return true;
        }
        if(pannierTextFIeld.getText().isEmpty()){
            if(commande.getPanier() == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please select a panier.");
                alert.showAndWait();
                return true;
            }
        }else{
            try{
                int id = Integer.parseInt(pannierTextFIeld.getText());
                Panier p = panierService.getPanierById(id);
                if(p == null){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Panier not found.");
                    alert.showAndWait();
                    return true;
                }
                commande.setPanier(p);
            }catch (NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Panier ID must be a number.");
                alert.showAndWait();
                return true;
            }
        }
        Panier p = panierService.getPanierById(commande.getPanier().getIdPanier());

        if(!UserSession.CURRENT_USER.getUserLoggedIn().getRoles().equals(Roles.ROLE_ADMIN)) {
            if(p.getUser().getId() != userId){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("This cart doesn't belong to this user.");
                alert.showAndWait();
                return true;
            }
        }
        commande.setPanier(p);
        return false;
    }

    @FXML
    void initialize() {
        modeDePaiementComboBox.getItems().addAll("Cash", "Credit Card", "PayPal"); // Add appropriate payment methods
        userId = 1;
        pannnierID.setOpacity(0);
        pannierTextFIeld.setOpacity(0);
    }

    public void cancelCommand(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListeCommandes.fxml"));
        Parent root;
        try {
            root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) adresseTextField.getScene().getWindow(); // Get the current stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle IOException, if any
        }
    }

    @FXML
    public void setSelectPanier(boolean b) {
        if (b) {
            pannnierID.setOpacity(1);
            pannierTextFIeld.setOpacity(1);
        }
    }

    private Panier selectedPanier;
    @FXML
    public void setPanier(Panier selectedPanier) {
        this.selectedPanier = selectedPanier;
    }

    private boolean isUpdate = false;
    private Commande selectedcommande;
    @FXML
    public void setCommand(Commande selectedCommand) {
        this.selectedcommande = selectedCommand;
        adresseTextField.setText(selectedCommand.getAdresse());
        modeDePaiementComboBox.setValue(selectedCommand.getModeDePaiement());
        pannierTextFIeld.setText(selectedCommand.getPanier().getIdPanier() + "");
        this.isUpdate = true;
    }
}
