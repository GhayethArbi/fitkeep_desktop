package test;

import models.PlanNutritionnel;
import models.Recette;
import services.ServiceRecette;
import services.ServicesPlanNutritionnel;
import utils.DBConnection;
import java.sql.SQLException;
import java.util.Date;

public class MainClass {

    public static void main(String[] args) {
        // Initialisation de la connexion à la base de données
        DBConnection dbConnection = DBConnection.getInstance();

        // Instanciation du service de plan nutritionnel
        ServicesPlanNutritionnel servicePlanNutritionnel = new ServicesPlanNutritionnel();

        // Création d'un nouvel objet PlanNutritionnel
        PlanNutritionnel planNutritionnelq1 = new PlanNutritionnel(1, 62, "Plan C", new Date());
        PlanNutritionnel planNutritionnel = new PlanNutritionnel(2, 62, "Plan B", new Date());
        try {

            // Insertion du plan nutritionnel
            servicePlanNutritionnel.selectAll();
                        System.out.println("PlanNutritionnel inserted: " + planNutritionnel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
