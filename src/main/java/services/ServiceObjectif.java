package services;

import models.ActivitePhysique;
import models.Objectif;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceObjectif implements CRUD<Objectif> {
    private Connection cnx ;

    public ServiceObjectif() {
        cnx = DBConnection.getInstance().getCnx();
    }
    @Override
    public void insertOne(Objectif objectif) throws SQLException {
        // Insert objectif
        String reqObjectif = "INSERT INTO `objectif`(`nom_objectif`, `date_objectif`, `total_calories`, `total_duree`, `note`) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement psObjectif = cnx.prepareStatement(reqObjectif, Statement.RETURN_GENERATED_KEYS);
        psObjectif.setString(1, objectif.getNomObjectif());
        psObjectif.setDate(2, objectif.getDateObjectif());
        psObjectif.setObject(3, objectif.getTotalCalories());
        psObjectif.setObject(4, objectif.getTotalDuree());
        psObjectif.setString(5, objectif.getNote());

        psObjectif.executeUpdate();

        // Retrieve the generated id of the newly inserted objectif
        ResultSet generatedKeys = psObjectif.getGeneratedKeys();
        int objectId = -1;
        if (generatedKeys.next()) {
            objectId = generatedKeys.getInt(1);
          //  System.out.println(objectId);
        } else {
            throw new SQLException("Failed to retrieve the generated id of the objectif.");
        }

        // Insert links between objectif and existing activites
        for (ActivitePhysique activite : objectif.getActivites()) {
            String reqLink = "INSERT INTO `objectif_activite_physique`(`activite_physique_id`, `objectif_id`) VALUES (?, ?)";
            PreparedStatement psLink = cnx.prepareStatement(reqLink);
            psLink.setInt(1, activite.getId()); // Assuming activite has an ID
            psLink.setInt(2, objectId);
            psLink.executeUpdate();
        }
        objectif.setId(objectId);
     //   System.out.println("Objectif and associated Activites Added!");
    }


    @Override
    public void updateOne(Objectif objectif) throws SQLException {
        String updateQuery = "UPDATE `objectif` SET `nom_objectif`=?, `date_objectif`=?, `total_calories`=?, `total_duree`=?, `note`=? WHERE `id`=?";
        String deleteOldActivitesQuery = "DELETE FROM `objectif_activite_physique` WHERE `objectif_id`=?";
        String insertNewActivitesQuery = "INSERT INTO `objectif_activite_physique` (`objectif_id`, `activite_physique_id`) VALUES (?, ?)";

        try (
                PreparedStatement updateStatement = cnx.prepareStatement(updateQuery);
                PreparedStatement deleteStatement = cnx.prepareStatement(deleteOldActivitesQuery);
                PreparedStatement insertStatement = cnx.prepareStatement(insertNewActivitesQuery)) {

            // Set parameters for updating the objectif
            updateStatement.setString(1, objectif.getNomObjectif());
            updateStatement.setDate(2, objectif.getDateObjectif());
            updateStatement.setObject(3, objectif.getTotalCalories());
            updateStatement.setObject(4, objectif.getTotalDuree());
            updateStatement.setString(5, objectif.getNote());
            updateStatement.setInt(6, objectif.getId());

            // Execute the update query
            updateStatement.executeUpdate();

            // Delete old associations
            deleteStatement.setInt(1, objectif.getId());
            deleteStatement.executeUpdate();

            // Insert new associations
            for (ActivitePhysique activite : objectif.getActivites()) {
                insertStatement.setInt(1, objectif.getId());
                insertStatement.setInt(2, activite.getId());
                insertStatement.executeUpdate();
            }
            System.out.println();
          //  System.out.println("Objectif Updated with Associated Activites!");
        } catch (SQLException e) {
            System.err.println("Error updating Objectif: " + e.getMessage());
            throw e;
        }
    }


    public void updateOneDateNote(Objectif objectif) throws SQLException {
        String updateQuery = "UPDATE `objectif` SET `date_objectif`=?, `note`=? WHERE `id`=?";

        try (
                PreparedStatement updateStatement = cnx.prepareStatement(updateQuery)) {

            // Set parameters for updating the objective
            updateStatement.setDate(1, objectif.getDateObjectif());
            updateStatement.setString(2, objectif.getNote());
            updateStatement.setInt(3, objectif.getId());

            // Execute the update query
            updateStatement.executeUpdate();

            System.out.println("Objectif Updated with Date and Note!");
        } catch (SQLException e) {
            System.err.println("Error updating Objectif: " + e.getMessage());
            throw e;
        }
    }

    public void calculateAndUpdateTotalValuesForObjective(int objectiveId) {
        try {
            // Fetch the list of activities associated with the given objective
            List<ActivitePhysique> activitePhysiques = new ArrayList<>();
            String fetchActivitiesQuery = "SELECT * FROM `objectif_activite_physique` WHERE objectif_id = ?";
            try (PreparedStatement ps = cnx.prepareStatement(fetchActivitiesQuery)) {
                ps.setInt(1, objectiveId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        int activitePhysiqueId = rs.getInt("activite_physique_id");
                        ActivitePhysique activitePhysique = fetchActiviteById(activitePhysiqueId);
                        if (activitePhysique != null) {
                            activitePhysiques.add(activitePhysique);
                        }
                    }
                }
            }

            // Initialize variables to store the sum of relevant fields
            int totalDuration = 0;
            int totalCalories = 0;

            // Iterate over the activities to calculate the sum
            for (ActivitePhysique activitePhysique : activitePhysiques) {
                totalDuration += activitePhysique.getDureeActivite();
                totalCalories += activitePhysique.getCaloriesBrules();
            }

            // Update the total values for the objective in the database
            String updateObjectiveQuery = "UPDATE `objectif` SET `total_calories`=?, `total_duree`=? WHERE `id`=?";
            try (PreparedStatement updateStatement = cnx.prepareStatement(updateObjectiveQuery)) {
                updateStatement.setInt(1, totalCalories);
                updateStatement.setInt(2, totalDuration);
                updateStatement.setInt(3, objectiveId);
                updateStatement.executeUpdate();
                System.out.println("Total values updated for Objective with ID: " + objectiveId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTotalCaloriesAndDuration(Objectif objectif) throws SQLException {
        String updateQuery = "UPDATE `objectif` SET `total_calories`=?, `total_duree`=? WHERE `id`=?";

        try (PreparedStatement updateStatement = cnx.prepareStatement(updateQuery)) {


            // Set parameters for updating totalCalories and totalDuration
            updateStatement.setInt(1, objectif.getTotalCalories());
            updateStatement.setInt(2, objectif.getTotalDuree());
            updateStatement.setInt(3, objectif.getId());

            // Execute the update query
            updateStatement.executeUpdate();

            System.out.println("Total Calories and Duration Updated for Objectif with ID: " + objectif.getId());
        } catch (SQLException e) {
            System.err.println("Error updating total calories and duration for Objectif: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void deleteOne(Objectif objectif) throws SQLException {
        String req = "DELETE FROM `objectif` WHERE `id`=?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, objectif.getId());
        ps.executeUpdate();
        System.out.println("Objectif Deleted !");
    }





    @Override
    public List<Objectif> selectAll() throws SQLException {
        List<Objectif> objectifList = new ArrayList<>();

        String req = "SELECT * FROM `objectif`";


        Statement st = cnx.createStatement();

        ResultSet rs = st.executeQuery(req);


        while (rs.next()){
            Objectif p = new Objectif();
            p.setId(rs.getInt(("id")));
            p.setNomObjectif(rs.getString(2));
            p.setDateObjectif(rs.getDate(3));
            p.setTotalCalories(rs.getInt(4));
            p.setTotalDuree(rs.getInt(5));
            p.setNote(rs.getString(6));
            List<ActivitePhysique> activitePhysiques = fetchActivitesForObjectif(p.getId());
            p.setActivites(activitePhysiques);
            objectifList.add(p);
        }
        return objectifList;

    }

    public List<ActivitePhysique> fetchActivitesForObjectif (int objectifId) throws SQLException {
        List<ActivitePhysique> activitePhysiques = new ArrayList<>();
        String req = "SELECT * FROM `objectif_activite_physique` WHERE objectif_id = ?";

        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, objectifId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int activitePhysiqueId = rs.getInt("activite_physique_id");
                    ActivitePhysique activitePhysique = fetchActiviteById(activitePhysiqueId);

                    if (activitePhysique != null) {
                        activitePhysiques.add(activitePhysique);
                    }
                }
            }
        }

        return activitePhysiques;
    }
    public ActivitePhysique fetchActiviteById(int activiteId) throws SQLException {
        ActivitePhysique activitePhysique = null;
        String req = "SELECT * FROM `activite_physique` WHERE id = ?";

        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, activiteId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    activitePhysique = new ActivitePhysique();
                    activitePhysique.setId(rs.getInt("id"));
                    activitePhysique.setNomActivite(rs.getString("nom_activite"));
                    activitePhysique.setTypeActivite(rs.getString("type_activite"));
                    activitePhysique.setDureeActivite(rs.getInt("duree_activite"));
                    activitePhysique.setCaloriesBrules(rs.getInt("calories_brules"));
                    activitePhysique.setDureeActivite(rs.getInt("duree_activite"));
                    activitePhysique.setNbSeries(rs.getInt("nb_series"));
                    activitePhysique.setNbRepSeries(rs.getInt("nb_rep_series"));
                    activitePhysique.setPoidsParSerie(rs.getInt("poids_par_serie"));
                    activitePhysique.setImageActivite(rs.getString("image_activite"));


                }
            }
        }
        return activitePhysique;
    }

    public void deleteObjectifAndActivite(Objectif objectif) throws SQLException {
        try {
            // Step 1: Fetch the activities associated with the objective
            List<ActivitePhysique> activites = fetchActivitesForObjectif(objectif.getId());

            // Step 2: Delete the associated ActivitePhysique objects from the activite_physique table
            String deleteActiviteQuery = "DELETE FROM `activite_physique` WHERE `id`=?";
            try (PreparedStatement psDeleteActivite = cnx.prepareStatement(deleteActiviteQuery)) {
                for (ActivitePhysique activite : activites) {
                    psDeleteActivite.setInt(1, activite.getId());
                    psDeleteActivite.executeUpdate();
                }
            }

            // Step 3: Delete the Objectif from the objectif table
            String deleteObjectifQuery = "DELETE FROM `objectif` WHERE `id`=?";
            try (PreparedStatement psDeleteObjectif = cnx.prepareStatement(deleteObjectifQuery)) {
                psDeleteObjectif.setInt(1, objectif.getId());
                psDeleteObjectif.executeUpdate();
            }

            System.out.println("Objectif and associated Activites Deleted !");
        } catch (SQLException e) {
            System.err.println("Error deleting Objectif and associated Activites: " + e.getMessage());
            throw e; // Rethrow the exception to handle it elsewhere if needed
        }
    }




}
