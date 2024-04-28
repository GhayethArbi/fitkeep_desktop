package Controllers;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.ResourceBundle;

import Entity.Commande;
import Entity.Panier;
import Entity.User;
import Services.CommandeService;
import Services.PanierService;
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
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ListeCommandes {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Commande> commandeTableView;

    @FXML
    private TableColumn<Commande, Integer> panierIdColumn;

    @FXML
    private TableColumn<Commande, Integer> userIdColumn;

    @FXML
    private TableColumn<Commande, String> modePaiementColumn;

    @FXML
    private TableColumn<Commande, String> dateColumn;

    @FXML
    private TableColumn<Commande, String> adresseColumn;

    @FXML
    private TableColumn<Commande, String> statutColumn;

    @FXML
    private TableColumn<Commande, Double> totalPriceColumn;

    private CommandeService commandeService = new CommandeService();
    private PanierService p= new PanierService();


    @FXML
    void initialize() throws SQLException {

        panierIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPanier().getIdPanier()).asObject());
        userIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getUser().getIdUser()).asObject());
        modePaiementColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getModeDePaiement()));
        dateColumn.setCellValueFactory(cellData -> {
            Timestamp timestamp = cellData.getValue().getDate();
            if (timestamp != null) {
                Date date = new Date(timestamp.getTime());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // Define your date format
                return new SimpleStringProperty(sdf.format(date));
            } else {
                return new SimpleStringProperty("");
            }
        });
        adresseColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAdresse()));
        statutColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatut()));

        totalPriceColumn.setCellValueFactory(cellData -> {
            try {
                double totalPrice = p.getTotalPriceByUserId(1); // Use the appropriate user ID
                return new SimpleDoubleProperty(totalPrice).asObject();
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        });
        this.reloadTable();

    }

    private void reloadTable() {
        List<Commande> commandes;
        try {
            new User();
            if(User.isAdmin){
                commandes = commandeService.getAllCommandes();
            }else{
                commandes = commandeService.getAggregatedCommandes(1);
            }

            System.out.println(commandes);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Clear existing items and add the new commandes to the TableView
        commandeTableView.getItems().clear();
        commandeTableView.getItems().addAll(commandes);
    }


    public void viewPannier(ActionEvent actionEvent) {
        //Show Panier.fxml
        try{
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Panier.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Panier");
            stage.setScene(new Scene(root1));
            stage.show();
            currentStage.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void searchCommand(ActionEvent actionEvent) {
    }

    public void viewCommand(ActionEvent actionEvent) {
        //show CommandDetails.fxml and pass the selected command
        Commande selectedCommand = commandeTableView.getSelectionModel().getSelectedItem();
        if (selectedCommand == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select the command that you want to view.");
            alert.showAndWait();
            return;
        }
        try{
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/CommandDetails.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            CommandDetails controller = fxmlLoader.getController();
            controller.setCommand(selectedCommand);
            Stage stage = new Stage();
            stage.setTitle("Détails de la commande");
            stage.setScene(new Scene(root1));
            stage.show();
            currentStage.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void printCommandList(ActionEvent actionEvent) {
    }

    public void delCommand(ActionEvent actionEvent) {
        //Delete the selected command
        Commande selectedCommand = commandeTableView.getSelectionModel().getSelectedItem();
        if (selectedCommand == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select the command that you want to delete.");
            alert.showAndWait();
            return;
        }
        commandeService.deleteCommande(selectedCommand.getIdCommande());
        this.reloadTable();
    }

    public void editCommand(ActionEvent actionEvent) {
        if(commandeTableView.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select the command that you want to edit.");
            alert.showAndWait();
            return;
        }
        try{
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Commande.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            CommandeController controller = fxmlLoader.getController();
            controller.setCommand(commandeTableView.getSelectionModel().getSelectedItem());
            controller.setSelectPanier(true);
            Stage stage = new Stage();
            stage.setTitle("Ajouter une commande");
            stage.setScene(new Scene(root1));
            stage.show();
            currentStage.close();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public void viewProducts(ActionEvent actionEvent) {
        //show add_panier.fxml
        try{
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/add_panier.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Produits");
            stage.setScene(new Scene(root1));
            stage.show();
            currentStage.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addCommand(ActionEvent actionEvent) {
        //Show Commande.fxml and pass a variable to indaicate the panierId to be selcted
        try{
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Commande.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            CommandeController controller = fxmlLoader.getController();
            controller.setSelectPanier(true);
            Stage stage = new Stage();
            stage.setTitle("Ajouter une commande");
            stage.setScene(new Scene(root1));
            stage.show();
            currentStage.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
