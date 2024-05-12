package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Date;

public class PlanNutritionnel {
    private int id;

    private int recettes_id; // Modifié le nom de l'attribut pour correspondre à la colonne dans la base de données

    private String name;
    private Date date;

    public PlanNutritionnel() {
    }




    public PlanNutritionnel(int id, int recettes_id, String name, Date date) {
        this.id = id;
        this.recettes_id = recettes_id;
        this.name = name;
        this.date = date;
    }

    public PlanNutritionnel(int recettes_id, String name, Date date) {
        this.recettes_id = recettes_id;

        this.name = name;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getRecettes_id() { // Modifié le nom de la méthode getter pour correspondre à l'attribut
        return recettes_id;
    }

    public void setRecettes_id(int recettes_id) { // Modifié le nom de la méthode setter pour correspondre à l'attribut
        this.recettes_id = recettes_id;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Date getDate() { // Modifié le type de retour de la méthode getDate pour correspondre à l'attribut
        return date;
    }

    public void setDate(Date date) { // Modifié le type de paramètre de la méthode setDate pour correspondre à l'attribut

        this.date = date;
    }

    @Override
    public String toString() {
        return "PlanNutritionnel{" +
                "id=" + id +
                ", recettes_id=" + recettes_id +

                ", name='" + name + '\'' +
                ", date=" + date +
                '}';
    }


    public Recette getRecettes() {
        Recette recette = null;
        String req = "SELECT * FROM recette WHERE id = ?";
        Connection cnx = null;
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, this.recettes_id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    recette = new Recette();
                    recette.setId(rs.getInt("id"));
                    recette.setName(rs.getString("name"));
                    // Ajoutez d'autres attributs de la recette si nécessaire
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Gérez l'exception de manière appropriée
        }
        return recette;
    }
}
