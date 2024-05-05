package Controllers;

import Entity.Commande;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class CommandDetails {
    public Label commandID;
    public Label userName;
    public Label payment;
    public Label pannierID;
    public Label date;
    public Label address;
    public Label status;
    public Button payBTN;
    private Commande selectedCommand;
    private final int current_suer = 1;

    public void setCommand(Commande selectedCommand) {
        this.selectedCommand = selectedCommand;
        commandID.setText(String.valueOf(selectedCommand.getIdCommande()));
        userName.setText(selectedCommand.getUser().getEmail());
        payment.setText(selectedCommand.getModeDePaiement());
        pannierID.setText(String.valueOf(selectedCommand.getPanier().getIdPanier()));
        date.setText(selectedCommand.getDate().toString());
        address.setText(selectedCommand.getAdresse());
        status.setText(selectedCommand.getStatut());
        payBTN.setVisible(false);
        if(selectedCommand.getUser().getIdUser() == current_suer){
            if(!selectedCommand.getStatut().equals("Payée")){
                payBTN.setVisible(true);
            }
        }
    }

    public void showListeCommandes(ActionEvent actionEvent) {
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

    public void showEdit(ActionEvent actionEvent) {
        try {
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Commande.fxml"));
            Parent root = loader.load();
            CommandeController commandeController = loader.getController();
            commandeController.setSelectPanier(true);
            commandeController.setCommand(selectedCommand);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            currentStage.close();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void payBtn(ActionEvent actionEvent) {
        try {
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/payment.fxml"));
            Parent root = loader.load();
            PaymentController paymentController = loader.getController();
            double total = 0;
            total = selectedCommand.getPanier().getTotalPrice();
            paymentController.setVariable(total, selectedCommand);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            currentStage.close();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
