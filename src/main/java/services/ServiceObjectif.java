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
        String req = "INSERT INTO `objectif`(`nom_objectif`, `date_objectif`, `total_calories`, `total_duree`, `note`) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, objectif.getNomObjectif());
        ps.setDate(2, objectif.getDateObjectif());
        ps.setObject(3, objectif.getTotalCalories());
        ps.setObject(4, objectif.getTotalDuree());
        ps.setObject(5, objectif.getNote());

        ps.executeUpdate();
        System.out.println("Objectif Added !");
    }

    @Override
    public void updateOne(Objectif objectif) throws SQLException {

    }

    @Override
    public void deleteOne(Objectif objectif) throws SQLException {

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
            List<ActivitePhysique> activitePhysiques = fetchActiviteForObjectifs(p.getId());
            p.setActivites(activitePhysiques);
            objectifList.add(p);
        }
        return objectifList;

    }

    public List<ActivitePhysique> fetchActiviteForObjectifs (int objectifId) throws SQLException {
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


                }
            }
        }
        return activitePhysique;
    }

}
