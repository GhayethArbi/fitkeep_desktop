package services;

import models.PlanNutritionnel;
import models.Recette;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicesPlanNutritionnel implements CRUD<PlanNutritionnel> {
    private final Connection cnx;

    public ServicesPlanNutritionnel() {
        cnx = DBConnection.getInstance().getCnx();
    }

    @Override
    public void insertOne(PlanNutritionnel planNutritionnel) throws SQLException {
        String req = "INSERT INTO `plan_nutritionnel`(`id`, `recettes_id`, `name`, `date`) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, planNutritionnel.getId());
            ps.setInt(2, planNutritionnel.getRecettes_id());
            ps.setString(3, planNutritionnel.getName());
            ps.setDate(4, new java.sql.Date(planNutritionnel.getDate().getTime()));
            ps.executeUpdate();
        }
    }

    @Override
    public void updateOne(PlanNutritionnel planNutritionnel) throws SQLException {
        String req = "UPDATE `plan_nutritionnel` SET `recettes_id` = ?, `name` = ?, `date` = ? WHERE `id` = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, planNutritionnel.getRecettes_id());
            ps.setString(2, planNutritionnel.getName());
            ps.setDate(3, new java.sql.Date(planNutritionnel.getDate().getTime()));
            ps.setInt(4, planNutritionnel.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void deleteOne(PlanNutritionnel planNutritionnel) throws SQLException {
        String req = "DELETE FROM `plan_nutritionnel` WHERE `id`=?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, planNutritionnel.getId());
            ps.executeUpdate();
            System.out.println("PlanNutritionnel Deleted !");
        }
    }

    @Override
    public List<PlanNutritionnel> selectAll() throws SQLException {
        List<PlanNutritionnel> planNutritionnelList = new ArrayList<>();

        String req = "SELECT * FROM `plan_nutritionnel`";
        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                PlanNutritionnel p = new PlanNutritionnel();
                p.setId(rs.getInt("id"));
                p.setRecettes_id(rs.getInt("recettes_id"));
                p.setName(rs.getString("name"));
                p.setDate(rs.getDate("date"));
                planNutritionnelList.add(p);
            }
        }
        return planNutritionnelList;
    }
private Recette fetchRecetteById(int recettes_id) throws SQLException {
        String req = "SELECT * FROM `recette` WHERE `id`=?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, recettes_id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Recette recette = new Recette();
                    recette.setId(rs.getInt("id"));
                    recette.setName(rs.getString("name"));
                    return recette;
                }
            }
        }
        return null;
    }

    public List<PlanNutritionnel> getAllPlanNutritionnels() throws SQLException {
        return selectAll();
    }
}