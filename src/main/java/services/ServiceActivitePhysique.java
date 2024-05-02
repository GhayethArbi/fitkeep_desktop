package services;
import models.Objectif;
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
    public void insertOne(ActivitePhysique activitePhysique) throws SQLException {
        String reqActivite = "INSERT INTO `activite_physique`(`nom_Activite`, `type_Activite`, `duree_Activite`, `calories_Brules`, `nb_Series`, `nb_Rep_Series`, `poids_Par_Serie`,`image_Activite`) VALUES (?, ?, ?, ?, ?, ?, ?,?)";
        PreparedStatement psActivite = cnx.prepareStatement(reqActivite, Statement.RETURN_GENERATED_KEYS);
        psActivite.setString(1, activitePhysique.getNomActivite());
        psActivite.setString(2, activitePhysique.getTypeActivite());
        psActivite.setObject(3, activitePhysique.getDureeActivite());
        psActivite.setObject(4, activitePhysique.getCaloriesBrules());
        psActivite.setObject(5, activitePhysique.getNbSeries());
        psActivite.setObject(6, activitePhysique.getNbRepSeries());
        psActivite.setObject(7, activitePhysique.getPoidsParSerie());
        psActivite.setString(8, activitePhysique.getImageActivite());

        psActivite.executeUpdate();
        System.out.println(psActivite);
        // Retrieve the generated id of the newly inserted activite_physique
        ResultSet generatedKeys = psActivite.getGeneratedKeys();
        int activiteId = -1;
        if (generatedKeys.next()) {
            activiteId = generatedKeys.getInt(1);
        } else {
            throw new SQLException("Failed to retrieve the generated id of the activite_physique.");
        }

        // Insert associated objectifs
        for (Objectif objectif : activitePhysique.getObjectifs()) {
            String reqObjectif = "INSERT INTO `objectif_activite_physique`(`activite_physique_id`, `objectif_id`) VALUES (?, ?)";
            PreparedStatement psObjectif = cnx.prepareStatement(reqObjectif);
            psObjectif.setInt(1, activiteId);
            psObjectif.setInt(2, objectif.getId());
            psObjectif.executeUpdate();
        }

        System.out.println("ActivitePhysique and associated Objectifs Added!");
    }


    public void insertActivityWithidObjectif (ActivitePhysique activitePhysique, int idObj) throws SQLException {
        String reqActivite = "INSERT INTO `activite_physique`(`nom_Activite`, `type_Activite`, `duree_Activite`, `calories_Brules`, `nb_Series`, `nb_Rep_Series`, `poids_Par_Serie`,`image_Activite`) VALUES (?, ?, ?, ?, ?, ?, ?,?)";
        PreparedStatement psActivite = cnx.prepareStatement(reqActivite, Statement.RETURN_GENERATED_KEYS);
        psActivite.setString(1, activitePhysique.getNomActivite());
        psActivite.setString(2, activitePhysique.getTypeActivite());
        psActivite.setObject(3, activitePhysique.getDureeActivite());
        psActivite.setObject(4, activitePhysique.getCaloriesBrules());
        psActivite.setObject(5, activitePhysique.getNbSeries());
        psActivite.setObject(6, activitePhysique.getNbRepSeries());
        psActivite.setObject(7, activitePhysique.getPoidsParSerie());
        psActivite.setString(8, activitePhysique.getImageActivite());

        psActivite.executeUpdate();

        // Retrieve the generated id of the newly inserted activite_physique
        ResultSet generatedKeys = psActivite.getGeneratedKeys();
        int activiteId = -1;
        if (generatedKeys.next()) {
            activiteId = generatedKeys.getInt(1);
        } else {
            throw new SQLException("Failed to retrieve the generated id of the activite_physique.");
        }

        // Insert associated objectif
        String reqObjectif = "INSERT INTO `objectif_activite_physique`(`activite_physique_id`, `objectif_id`) VALUES (?, ?)";
        PreparedStatement psObjectif = cnx.prepareStatement(reqObjectif);
        psObjectif.setInt(1, activiteId);
        psObjectif.setInt(2, idObj);
        psObjectif.executeUpdate();

        System.out.println("ActivitePhysique and associated Objectif Added!");
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
    public void updateOne(ActivitePhysique activitePhysique) throws SQLException {
        String updateQuery = "UPDATE `activite_physique` SET `nom_Activite`=?, `type_Activite`=?, `duree_Activite`=?, `calories_Brules`=?, `nb_Series`=?, `nb_Rep_Series`=?, `poids_Par_Serie`=? , `image_Activite`=? WHERE `id`=?";
        String deleteOldObjectifsQuery = "DELETE FROM `objectif_activite_physique` WHERE `activite_physique_id`=?";
        String insertNewObjectifsQuery = "INSERT INTO `objectif_activite_physique` (`activite_physique_id`, `objectif_id`) VALUES (?, ?)";

        try (
             PreparedStatement updateStatement = cnx.prepareStatement(updateQuery);
             PreparedStatement deleteStatement = cnx.prepareStatement(deleteOldObjectifsQuery);
             PreparedStatement insertStatement = cnx.prepareStatement(insertNewObjectifsQuery)) {

            // Set parameters for updating the physical activity
            updateStatement.setString(1, activitePhysique.getNomActivite());
            updateStatement.setString(2, activitePhysique.getTypeActivite());
            updateStatement.setObject(3, activitePhysique.getDureeActivite());
            updateStatement.setObject(4, activitePhysique.getCaloriesBrules());
            updateStatement.setObject(5, activitePhysique.getNbSeries());
            updateStatement.setObject(6, activitePhysique.getNbRepSeries());
            updateStatement.setObject(7, activitePhysique.getPoidsParSerie());
            updateStatement.setString(8, activitePhysique.getImageActivite());
            updateStatement.setInt(9, activitePhysique.getId());

            // Execute the update query
            updateStatement.executeUpdate();

            // Delete old associations
            deleteStatement.setInt(1, activitePhysique.getId());
            deleteStatement.executeUpdate();

            // Insert new associations
            for (Objectif objectif : activitePhysique.getObjectifs()) {
                insertStatement.setInt(1, activitePhysique.getId());
                insertStatement.setInt(2, objectif.getId());
                insertStatement.executeUpdate();
            }

            System.out.println("ActivitePhysique Updated with Associated Objectifs!");
        } catch (SQLException e) {
            System.err.println("Error updating ActivitePhysique: " + e.getMessage());
            throw e;
        }
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
            p.setImageActivite(rs.getString(9));
            // Fetch objectives for this activity
            List<Objectif> objectifs = fetchObjectifsForActivite(p.getId());
            p.setObjectifs(objectifs);
            physicalActivityList.add(p);
        }
        System.out.println(physicalActivityList);
        return physicalActivityList;
    }

    public List<Objectif> fetchObjectifsForActivite (Integer activiteId) throws SQLException {
        List<Objectif> objectifs = new ArrayList<>();
        String req = "SELECT * FROM `objectif_activite_physique` WHERE activite_physique_id = ?";

        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, activiteId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int objectId = rs.getInt("objectif_id");
                    Objectif objectif = fetchObjectifById(objectId);

                    if (objectif != null) {
                        objectifs.add(objectif);
                    }
                }
            }
        }

        return objectifs;
    }


    public Objectif fetchObjectifById(int objectId) throws SQLException {
        Objectif objectif = null;
        String req = "SELECT * FROM `objectif` WHERE id = ?";

        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, objectId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    objectif = new Objectif();
                    objectif.setId(rs.getInt("id"));
                    objectif.setNomObjectif(rs.getString("nom_objectif"));
                    objectif.setNote(rs.getString("note"));
                    objectif.setDateObjectif(rs.getDate("date_objectif"));
                    objectif.setTotalCalories(rs.getInt("total_calories"));
                    objectif.setTotalDuree(rs.getInt("total_duree"));
                }
            }
        }

        return objectif;
    }


    public List<ActivitePhysique> selectAllWithNullObjectifs() throws SQLException {
        List<ActivitePhysique> physicalActivityList = new ArrayList<>();

        String req = "SELECT * FROM activite_physique";

        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(req)) {

            while (rs.next()) {
                ActivitePhysique p = new ActivitePhysique();
                p.setId(rs.getInt("id"));
                p.setNomActivite(rs.getString("nom_activite"));
                p.setTypeActivite(rs.getString("type_activite"));
                p.setImageActivite(rs.getString("image_activite"));

                // Fetch objectives for this activity
                List<Objectif> objectifs = fetchObjectifsForActivite(p.getId());

                // Check if objectives list is null or empty
                if (objectifs == null || objectifs.isEmpty()) {
                    physicalActivityList.add(p);
                }
            }
            System.out.println(physicalActivityList);
        }

        return physicalActivityList;
    }
}
