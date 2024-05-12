package services;

import Database.MyConnections;
import Entity.Panier;
import models.Product;
import javafx.scene.control.Label;
import models.User;
import repository.UserRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PanierService {

    private Connection connection;
    private UserRepository userRepository= new UserRepository();

    public PanierService() {

        connection = MyConnections.getInstance().getCnx();
    }

    // Create Panier
    public void addPanier(Panier panier) {
        String query = "INSERT INTO Panier (id_user, id_product, quantite, total_price) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, panier.getUser().getId());
            preparedStatement.setInt(2, panier.getProduct().getId());
            preparedStatement.setInt(3, panier.getQuantite());
            preparedStatement.setDouble(4, panier.getTotalPrice());
            preparedStatement.executeUpdate();
            System.out.println("Panier added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Read All Paniers
    public List<Panier> getAllPaniers() {
        List<Panier> panierList = new ArrayList<>();
        String query = "SELECT * FROM Panier";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Panier panier = new Panier();
                panier.setIdPanier(resultSet.getInt("id_panier"));

                // Fetch User details
                User user = new UserRepository().findById(resultSet.getInt("id_user"));
                panier.setUser(user);

                // Fetch Product details
                Product product = new ServiceProduit().getProductById(resultSet.getInt("id_product"));
                panier.setProduct(product);

                panier.setQuantite(resultSet.getInt("quantite"));
                panier.setTotalPrice(resultSet.getDouble("total_price"));
                panierList.add(panier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return panierList;
    }

    // Update Panier
    public void updatePanier(Panier panier) {
        String query = "UPDATE Panier SET id_user=?, id_product=?, quantite=?, total_price=? WHERE id_panier=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, panier.getUser().getId());
            preparedStatement.setInt(2, panier.getProduct().getId());
            preparedStatement.setInt(3, panier.getQuantite());
            preparedStatement.setDouble(4, panier.getTotalPrice());
            preparedStatement.setInt(5, panier.getIdPanier());
            preparedStatement.executeUpdate();
            System.out.println("Panier updated successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete Panier
    public void deletePanier(int idPanier) {
        String query = "DELETE FROM Panier WHERE id_panier=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idPanier);
            preparedStatement.executeUpdate();
            System.out.println("Panier deleted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Get Panier by ID
    public Panier getPanierById(int idPanier) {
        Panier panier = null;
        String query = "SELECT * FROM Panier WHERE id_panier=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idPanier);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                panier = new Panier();
                panier.setIdPanier(resultSet.getInt("id_panier"));

                // Fetch User details
                User user = userRepository.findById(resultSet.getInt("id_user"));
                panier.setUser(user);

                // Fetch Product details
                Product product = new ServiceProduit().getProductById(resultSet.getInt("id_product"));
                panier.setProduct(product);

                panier.setQuantite(resultSet.getInt("quantite"));
                panier.setTotalPrice(resultSet.getDouble("total_price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return panier;
    }
    // Method to retrieve panier entries by user ID
    public List<Panier> getPanierByUserId(int userId) throws SQLException {
        List<Panier> paniers = new ArrayList<>();

        // SQL query to retrieve panier entries for the specified user ID
        String query = "SELECT * FROM panier WHERE id_user = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            // Iterate over the result set and create Panier objects
            while (resultSet.next()) {
                int idPanier = resultSet.getInt("id_panier");
                int idProduct = resultSet.getInt("id_product");
                int quantite = resultSet.getInt("quantite");
                double totalPrice = resultSet.getDouble("total_price");

                // Create a Panier object and add it to the list

                ServiceProduit p = new ServiceProduit();
                User x=userRepository.findById(userId);
                Product y = p.getProductById(idProduct);
                Panier panier = new Panier(idPanier, x, y, quantite, totalPrice);
                paniers.add(panier);
            }
        }

        return paniers;
    }
    public double getTotalPriceByUserId(int userId) throws SQLException {
        double totalPrice = 0.0;
        List<Panier> paniers = getPanierByUserId(userId);
        for (Panier panier : paniers) {
            totalPrice += panier.getTotalPrice();
        }
        return totalPrice;
    }

    public Panier getPanierByUserIdandProductID(int idUser, int idProduct) {
        Panier panier = null;

        // SQL query to retrieve panier entry for the specified user ID and product ID
        String query = "SELECT * FROM Panier WHERE id_user = ? AND id_product = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idUser);
            preparedStatement.setInt(2, idProduct);
            ResultSet resultSet = preparedStatement.executeQuery();

            // If a result is found, populate the panier object
            if (resultSet.next()) {
                panier = new Panier();
                panier.setIdPanier(resultSet.getInt("id_panier"));

                // Fetch User details
                User user = userRepository.findById(resultSet.getInt("id_user"));
                panier.setUser(user);

                // Fetch Product details
                Product product = new ServiceProduit().getProductById(resultSet.getInt("id_product"));
                panier.setProduct(product);

                panier.setQuantite(resultSet.getInt("quantite"));
                panier.setTotalPrice(resultSet.getDouble("total_price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return panier;
    }

}
