package controllers.Panier;

import Entity.Commande;
import Entity.Panier;
import controllers.PaymentController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Roles;
import repository.UserRepository;
import services.CommandeService;
import services.PanierService;
import services.ServiceUser;
import services.session.UserSession;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

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
    public static Panier panier;
    private int userId= UserSession.CURRENT_USER.getUserLoggedIn().getId();
    private final UserRepository userRepository = new UserRepository();
    private double total;

    @FXML
    void addCommande(ActionEvent event) {
        Commande commande = new Commande();
        try {
            commande.setUser(userRepository.findById(userId));

            commande.setPanier(CommandeController.panier);
            this.total= CommandeController.panier.getTotalPrice();
            commande.setModeDePaiement(modeDePaiementComboBox.getValue());
            commande.setAdresse(adresseTextField.getText());
            commande.setStatut("En cours de traitement");
                System.out.println(commande.getPanier());
                commandeService.addCommande(commande);



            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Commandes added successfully.");
            alert.showAndWait();

            // Redirect the user to the list of commandes interface
           this.payBtn(commande);
        }catch (SQLException e){
            e.printStackTrace();
        }
        CommandeController.panier=null;
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

        commande.setPanier(p);
        return false;
    }

    @FXML
    void initialize() {
        modeDePaiementComboBox.getItems().addAll("Cash", "Credit Card", "PayPal"); // Add appropriate payment methods
        userId = 1;
        pannnierID.setOpacity(0);
        pannierTextFIeld.setOpacity(0);
        pannierTextFIeld.setDisable(true);
    }

    public void cancelCommand(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Front/Accueil.fxml"));
        Parent root = fxmlLoader.load();

        // Get the current stage
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        // Create a new scene with the root and set it to the stage
        stage.setScene(new Scene(root));
        stage.show();
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
    public void payBtn(Commande commande) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Panier/Payment.fxml"));
            Stage stage = (Stage) adresseTextField.getScene().getWindow();

            Parent root = loader.load();
            controllers.Panier.PaymentController paymentController = loader.getController();
            
            paymentController.setVariable(this.total, commande);
            stage.setScene(new Scene(root));
            stage.show();

          
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
