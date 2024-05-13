package services;

import Database.MyConnections;
import Entity.Commande;
import Entity.Panier;
import models.User;
import repository.UserRepository;
import services.session.UserSession;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommandeService {

    private Connection connection;
    private UserRepository userRepository= new UserRepository();

    public CommandeService() {

        connection = MyConnections.getInstance().getCnx();
    }

    // Create Commande
    public void addCommande(Commande commande) {
        System.out.println(UserSession.CURRENT_USER.getUserLoggedIn().getId());
        String query = "INSERT INTO Commande (panier_id, user_id, mode_de_paiement, date, adresse, statut) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, commande.getPanier().getIdPanier());
            preparedStatement.setInt(2, UserSession.CURRENT_USER.getUserLoggedIn().getId());
            preparedStatement.setString(3, commande.getModeDePaiement());
            preparedStatement.setTimestamp(4, commande.getDate());
            preparedStatement.setString(5, commande.getAdresse());
            preparedStatement.setString(6, commande.getStatut());
            preparedStatement.executeUpdate();
            System.out.println("Commande added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Commande> getAggregatedCommandes(int userId) throws SQLException {
        List<Commande> allCommandes = getAllCommandes();
        List<Commande> aggregatedCommandes = new ArrayList<>();

        // Combine commandes for the specified user
        for (Commande commande : allCommandes) {
            if (commande.getUser().getId() == userId) {
                PanierService p = new PanierService();
                List<Panier> paniers = p.getPanierByUserId(userId);
                double totalPrice = 0.0;
                for (Panier panier : paniers) {
                    totalPrice += panier.getTotalPrice();
                }
                commande.getPanier().setTotalPrice(totalPrice);
                aggregatedCommandes.add(commande);
            }
        }

        return aggregatedCommandes;
    }


    // Read All Commandes
    public List<Commande> getAllCommandes() {
        List<Commande> commandeList = new ArrayList<>();
        String query = "SELECT * FROM Commande";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Commande commande = new Commande();
                commande.setIdCommande(resultSet.getInt("id_commande"));

                // Fetch Panier details
                Panier panier = new PanierService().getPanierById(resultSet.getInt("id_panier"));
                commande.setPanier(panier);

                // Fetch User details
                User user = userRepository.findById(resultSet.getInt("id_user"));
                commande.setUser(user);

                commande.setModeDePaiement(resultSet.getString("mode_de_paiement"));
                commande.setDate(resultSet.getTimestamp("date"));
                commande.setAdresse(resultSet.getString("adresse"));
                commande.setStatut(resultSet.getString("statut"));
                commandeList.add(commande);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commandeList;
    }

    // Update Commande
    public void updateCommande(Commande commande) {
        String query = "UPDATE Commande SET panier_id=?, user_id=?, mode_de_paiement=?, date=?, adresse=?, statut=? WHERE id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, commande.getPanier().getIdPanier());
            preparedStatement.setInt(2, commande.getUser().getId());
            preparedStatement.setString(3, commande.getModeDePaiement());
            preparedStatement.setTimestamp(4, commande.getDate());
            preparedStatement.setString(5, commande.getAdresse());
            preparedStatement.setString(6, commande.getStatut());
            preparedStatement.setInt(7, commande.getIdCommande());
            preparedStatement.executeUpdate();
            System.out.println("Commande updated successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete Commande
    public void deleteCommande(int idCommande) {
        String query = "DELETE FROM Commande WHERE id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idCommande);
            preparedStatement.executeUpdate();
            System.out.println("Commande deleted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Commande getByPanierId(int idPanier) {
        Commande commande = new Commande();
        String query = "SELECT * FROM Commande WHERE panier_id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idPanier);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                commande.setIdCommande(resultSet.getInt("id_commande"));

                // Fetch Panier details
                Panier panier = new PanierService().getPanierById(resultSet.getInt("id_panier"));
                commande.setPanier(panier);

                User user =userRepository.findById(resultSet.getInt("id_user"));
                // Fetch User details

                commande.setUser(user);

                commande.setModeDePaiement(resultSet.getString("mode_de_paiement"));
                commande.setDate(resultSet.getTimestamp("date"));
                commande.setAdresse(resultSet.getString("adresse"));
                commande.setStatut(resultSet.getString("statut"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commande;
    }
}
