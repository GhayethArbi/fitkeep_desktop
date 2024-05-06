package services;

import Database.MyConnections;
import Entity.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductService {

    private Connection connection;

    public ProductService() {
        connection = MyConnections.getInstance().getCnx();
    }

    // Create Product
    public void addProduct(Product product) {
        String query = "INSERT INTO Product (name, price, description) VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setString(3, product.getDescription());
            preparedStatement.executeUpdate();
            System.out.println("Product added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Read All Products
    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        String query = "SELECT * FROM Product";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setIdProduct(resultSet.getInt("id_product"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getDouble("price"));
                product.setDescription(resultSet.getString("description"));
                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    // Update Product
    public void updateProduct(Product product) {
        String query = "UPDATE Product SET name=?, price=?, description=? WHERE id_product=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setString(3, product.getDescription());
            preparedStatement.setInt(4, product.getIdProduct());
            preparedStatement.executeUpdate();
            System.out.println("Product updated successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete Product
    public void deleteProduct(int idProduct) {
        String query = "DELETE FROM Product WHERE id_product=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idProduct);
            preparedStatement.executeUpdate();
            System.out.println("Product deleted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Get Product by ID
    public Product getProductById(int idProduct) {
        Product product = null;
        String query = "SELECT * FROM Product WHERE id_product=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idProduct);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                product = new Product();
                product.setIdProduct(resultSet.getInt("id_product"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getDouble("price"));
                product.setDescription(resultSet.getString("description"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

}
