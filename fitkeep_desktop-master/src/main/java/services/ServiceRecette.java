package services;

import models.Recette;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceRecette implements CRUD<Recette> {
    private Connection cnx;

    public ServiceRecette() {
        cnx = DBConnection.getInstance().getCnx();
    }

    @Override
    public void insertOne(Recette recette) throws SQLException {
        String req = "INSERT INTO `recette`(`id`, `name`, `category`, `date`, `description`) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement ps = cnx.prepareStatement(req);

        ps.setInt(1, recette.getId());
        ps.setString(2, recette.getName());
        ps.setString(3, recette.getCategory());
        ps.setDate(4, new java.sql.Date(recette.getDate().getTime())); // Convert util Date to SQL Date
        ps.setString(5, recette.getDescription());

        ps.executeUpdate();
    }


    @Override
    public void updateOne(Recette recette) throws SQLException {
        String req = "UPDATE `recette` SET `name`=?, `category`=?, `date`=?, `description`=? WHERE `id`=?";

        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, recette.getName());
        ps.setString(2, recette.getCategory());
        ps.setDate(3, new java.sql.Date(recette.getDate().getTime())); // Convert util Date to SQL Date
        ps.setString(4, recette.getDescription());
        ps.setInt(5, recette.getId());

        ps.executeUpdate();
    }

    @Override
    public void deleteOne(Recette recette) throws SQLException {
        String req = "DELETE FROM `recette` WHERE `id`=?";

        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, recette.getId());
        ps.executeUpdate();
    }

    @Override
    public List<Recette> selectAll() throws SQLException {
        List<Recette> Recettes = new ArrayList<>();
        String reqc = "SELECT * FROM `recette` ";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(reqc);
        while (rs.next()) {
            Recette Recette = new Recette();
            Recette.setId(rs.getInt("id"));
            Recette.setName(rs.getString("Name"));
            Recette.setCategory(rs.getString("category"));
            Recette.setDescription(rs.getString("description"));
            Recettes.add(Recette);
        }
        return Recettes;

    }
}