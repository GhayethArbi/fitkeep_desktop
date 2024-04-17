package services;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import models.ActivitePhysique;

public class ServiceActivitePhysique implements CRUD<ActivitePhysique>{
    private Connection cnx ;

    public ServiceActivitePhysique() {
        cnx = DBConnection.getInstance().getCnx();
    }

    @Override
    public void insertOne(ActivitePhysique ActivitePhysique) throws SQLException {
        String req = "INSERT INTO `activite_physique`(`nom_Activite`, `type_Activite`, `duree_Activite`, `calories_Brules`, `nb_Series`, `nb_Rep_Series`, `poids_Par_Serie`) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, ActivitePhysique.getNomActivite());
        ps.setString(2, ActivitePhysique.getTypeActivite());
        ps.setObject(3, ActivitePhysique.getDureeActivite());
        ps.setObject(4, ActivitePhysique.getCaloriesBrules());
        ps.setObject(5, ActivitePhysique.getNbSeries());
        ps.setObject(6, ActivitePhysique.getNbRepSeries());
        ps.setObject(7, ActivitePhysique.getPoidsParSerie());
        ps.executeUpdate();
        System.out.println("ActivitePhysique Added !");
    }

   /*
   public void insertOne1(ActivitePhysique ActivitePhysique) throws SQLException {
        String req = "INSERT INTO `person`(`nomActivite`, `prenom`, `age`) VALUES " +
                "(?,?,?)";
        PreparedStatement ps = cnx.prepareStatement(req);

        ps.setString(1, ActivitePhysique.getNom());
        ps.setString(2, ActivitePhysique.getPrenom());
        ps.setInt(3, ActivitePhysique.getAge());

        ps.executeUpdate(req);
    }

    */

    @Override
    public void updateOne(ActivitePhysique ActivitePhysique) throws SQLException {
        String req = "UPDATE `activite_physique` SET `nomActivite`=?, `typeActivite`=?, `dureeActivite`=?, `caloriesBrules`=?, `nbSeries`=?, `nbRepSeries`=?, `poidsParSerie`=? WHERE `id`=?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, ActivitePhysique.getNomActivite());
        ps.setString(2, ActivitePhysique.getTypeActivite());
        ps.setInt(3, ActivitePhysique.getDureeActivite());
        ps.setInt(4, ActivitePhysique.getCaloriesBrules());
        ps.setInt(5, ActivitePhysique.getNbSeries());
        ps.setInt(6, ActivitePhysique.getNbRepSeries());
        ps.setInt(7, ActivitePhysique.getPoidsParSerie());
        ps.setInt(8, ActivitePhysique.getId()); // Assuming you have an id field in your ActivitePhysique class
        ps.executeUpdate();
        System.out.println("ActivitePhysique Updated !");
    }

    @Override
    public void deleteOne(ActivitePhysique ActivitePhysique) throws SQLException {
        String req = "DELETE FROM `activite_physique` WHERE `id`=?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, ActivitePhysique.getId()); // Assuming you have an id field in your ActivitePhysique class
        ps.executeUpdate();
        System.out.println("ActivitePhysique Deleted !");
    }


    @Override
    public List<ActivitePhysique> selectAll() throws SQLException {
        List<ActivitePhysique> physicalActivityList = new ArrayList<>();

        String req = "SELECT * FROM `activite_physique`";
        Statement st = cnx.createStatement();

        ResultSet rs = st.executeQuery(req);

        while (rs.next()){
            ActivitePhysique p = new ActivitePhysique();

            p.setId(rs.getInt(("id")));
            p.setNomActivite(rs.getString(2));
            p.setTypeActivite(rs.getString(3));
            p.setCaloriesBrules(rs.getInt(4));
            p.setDureeActivite(rs.getInt(5));
            p.setNbSeries(rs.getInt(6));
            p.setNbRepSeries(rs.getInt(7));
            p.setPoidsParSerie(rs.getInt(8));
            physicalActivityList.add(p);
        }
        return physicalActivityList;
    }
}
