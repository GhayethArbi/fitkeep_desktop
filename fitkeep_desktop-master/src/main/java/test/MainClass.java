import models.Recette;
import services.ServiceRecette;
import utils.DBConnection;

import java.sql.SQLException;
import java.util.Date;

public class MainClass {

    public static void main(String[] args) {

        DBConnection cn1 = DBConnection.getInstance();

        ServiceRecette sr = new ServiceRecette();
        Recette recette = new Recette();
        recette.setId(1);
        recette.setName("Your Recipe Name");
        recette.setCategory("Your Recipe Category");
        recette.setDate(new Date()); // Set the current date
        recette.setDescription("Your Recipe Description");
        Recette recette1 = new Recette(2,"recipe_3","Lunch",new Date(),"recipe_3");
        try {
            // Insert the Recette object into the database
            //sr.insertOne(recette1);
            sr.selectAll();
            sr.updateOne(recette1);
        } catch (SQLException e) {
            System.err.println("Erreur: " + e.getMessage());
        }
    }
}
