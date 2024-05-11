package controllers.recettes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import models.PlanNutritionnel;
import models.Recette;
import services.ServiceRecette;
import javafx.stage.Stage;
import services.ServicesPlanNutritionnel;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AfficherRecetteFX implements Initializable {
    ServiceRecette sr = new ServiceRecette();
    ServicesPlanNutritionnel pl = new ServicesPlanNutritionnel();
    Recette selectedRecette= null;
    Recette selectedPlan= null;


    @FXML
    private TableColumn<Recette, Integer> id_col;

    @FXML
    private TableColumn<Recette, String> name_col, description_col, category_col;

    @FXML
    private TableColumn<Recette, Date> recette_date_col;

    @FXML
    private TableView<Recette> recetteTable;

    @FXML
    private Button submit_btn;

    @FXML
    private Button onClickModifier_btn;

    //ObservableList permet d'assurer l'enregistrement du donnée en une façon dynamique !
    private ObservableList<Recette> recetteObservableList = FXCollections.observableArrayList();
    private ObservableList<PlanNutritionnel> planObservableList = FXCollections.observableArrayList();

//Plan nutritionnel
    @FXML
    private Label error_name;

    @FXML
    private Label error_recette_id;
    @FXML
    private TextField input_recette_id;

    @FXML
    private TextField input_type;
    @FXML
    private Button submit_btn1;

    @FXML
    private TableView<PlanNutritionnel> planTable;

    @FXML
    private TableColumn<PlanNutritionnel, Date> plan_date_col;

    @FXML
    private TableColumn<PlanNutritionnel, String> PlanName_col;

    @FXML
    private TableColumn<PlanNutritionnel, Integer> recette_id_col, id_col1 ;
///end plan

    @FXML
    private Button refresh_btn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
           loadRecette();
            loadPlanNutritionnel();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void loadRecette() throws SQLException {
        List<Recette> recettes = sr.selectAll();

        // Ajoutez les recettes à l'ObservableList
        recetteObservableList.setAll(recettes);

        // Associez l'ObservableList à votre TableView
        recetteTable.setItems(recetteObservableList);

        id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        category_col.setCellValueFactory(new PropertyValueFactory<>("category"));
        description_col.setCellValueFactory(new PropertyValueFactory<>("Description"));
        recette_date_col.setCellValueFactory(new PropertyValueFactory<>("date"));


        /**
         * PropertyValueFactory : Generalement utilisée avec un tableView ou TableColumn
         * Elle façilite l'affichage des données
         * */

    }

    @FXML
    void goToAjouterRecipe() throws IOException {
        /** - stage est une fenètre principale elle peut avoir une ou plusieurs scenes
         *  - Scene agit comme un conteneur dans stage
         *  N.B: une app javafx peut avoir plusieurs scenes mais un seul stage.
         * */
        Stage stage = new Stage(); // Create a new stage
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/recette/AjouterRecetteFXML.fxml")); // Load the FXML file
        Parent root = loader.load();
        AjouterRecetteFx controller = loader.getController();
        Scene scene = new Scene(root, 518, 319);
        stage.setScene(scene); // Set the scene to the stage
        stage.show(); // Show the stage
    }

    @FXML void onDelete() {
        selectedRecette = recetteTable.getSelectionModel().getSelectedItem();
        if (selectedRecette != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Voulez vous supprimer le recette ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    sr.deleteOne(selectedRecette);
                    loadRecette();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Veuillez selectionner une recette");
            errorAlert.setContentText("Veuillez selectionner une recette");
            errorAlert.show();
        }

    }

    @FXML
    void onClickModifier(javafx.event.ActionEvent event) {
        selectedRecette = recetteTable.getSelectionModel().getSelectedItem();
        if (selectedRecette != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/recette/ModifierRecetteFXML.fxml"));
                Parent root = loader.load();
                ModifierRecette controller = loader.getController();
                controller.initData(selectedRecette); // Passer la recette sélectionnée au contrôleur ModifierRecetteFXML
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Gérer le cas où aucune recette n'est sélectionnée
        }
    }

    //plan nutritionnel
    @FXML
    void onSubmit(ActionEvent event) {
        String plan_name = input_type.getText();
        int recette_id = Integer.parseInt(input_recette_id.getText());
        try {
            PlanNutritionnel r = new PlanNutritionnel(recette_id, plan_name, new Date());
            ServicesPlanNutritionnel sr = new ServicesPlanNutritionnel();
            if (!plan_name.isEmpty() ) {
                sr.insertOne(r);
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setContentText("Recette ajoutée avec succès !");
                successAlert.show();
            } else {Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Veuillez remplir le formulaire");
                errorAlert.show();}
            //alert

            //fin
        } catch (SQLException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Erreur de base de données");
            errorAlert.setContentText("Erreur lors de l'ajout de la recette à la base de données : " + e.getMessage());
            errorAlert.show();
        }

    }
    @FXML
    void loadPlanNutritionnel() throws SQLException {
        List<PlanNutritionnel> planNutritionnels = pl.selectAll();

        // Ajoutez les recettes à l'ObservableList
        planObservableList.setAll(planNutritionnels);

        // Associez l'ObservableList à votre TableView
        planTable.setItems(planObservableList);
        id_col1.setCellValueFactory(new PropertyValueFactory<>("id"));
        recette_id_col.setCellValueFactory(new PropertyValueFactory<>("recettes_id"));
        PlanName_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        plan_date_col.setCellValueFactory(new PropertyValueFactory<>("date"));

    }
    @FXML
    void onRefresh(ActionEvent event) {
        try {
            loadPlanNutritionnel();
            loadRecette();
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception en conséquence, par exemple, afficher une boîte de dialogue d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Erreur lors du chargement des données. Veuillez réessayer.");
            alert.showAndWait();
        }
    }

    @FXML
    void plan_onDelete(ActionEvent event) {
        PlanNutritionnel selectedPlan = planTable.getSelectionModel().getSelectedItem();
        if (selectedPlan != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Voulez-vous supprimer le plan sélectionné ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    pl.deleteOne(selectedPlan); // Assuming pl is your service for PlanNutritionnel
                    loadPlanNutritionnel(); // Reload the plan table after deletion
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Veuillez sélectionner un plan");
            errorAlert.setContentText("Veuillez sélectionner un plan");
            errorAlert.show();
        }
    }

}





