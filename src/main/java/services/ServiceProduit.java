package services;


import models.Category;
import models.Product;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ServiceProduit implements CRUD<Product> {
    private final Connection cnx;

    public ServiceProduit() {
        cnx = DBConnection.getInstance().getCnx();
    }

    public void insertOne(Product product) throws SQLException {
        String req = "INSERT INTO `product`(`id`, `name`, `slug`, `illustration`, `subtitle`, `description`, `quantite`, `price`, `category_id`) VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {  ps.setString(1, product.getName());
            ps.setString(2, product.getSlug());
            ps.setString(3, product.getIllustration());
            ps.setString(4, product.getSubtitle());
            ps.setString(5, product.getDescription());
            ps.setInt(6, product.getQuantite());
            ps.setInt(7, product.getPrice());
            ps.setInt(8, product.getCategory().getId()); // Set the category_id
            ps.executeUpdate();
        }
    }

    @Override
    public void updateOne(Product product) throws SQLException {
        String req = "UPDATE `product` SET `name` = ?, `slug` = ?, `subtitle` = ?, `description` = ?, `price` = ?, `quantite` = ?, `category_id` = ?, `illustration` = ? WHERE `id` = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, product.getName());
            ps.setString(2, product.getSlug());
            ps.setString(3, product.getSubtitle());
            ps.setString(4, product.getDescription());
            ps.setInt(5, product.getPrice());
            ps.setInt(6, product.getQuantite());
            ps.setInt(7, product.getCategory().getId()); // Assuming 'getId()' returns the ID of the category
            ps.setString(8, product.getIllustration());
            ps.setInt(9, product.getId());
            ps.executeUpdate();
        }
    }



    @Override
    public void deleteOne(Product product) throws SQLException {
        String req = "DELETE FROM `product` WHERE `id`=?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, product.getId());
            ps.executeUpdate();
            System.out.println("Product Deleted !");
        }
    }

    @Override
    public List<Product> selectAll() throws SQLException {
        List<Product> productList = new ArrayList<>();

        String req = "SELECT * FROM `product`";
        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("id"));
                p.setCategory(fetchCategoryById(rs.getInt("category_id")));
                p.setSlug(rs.getString("slug"));
                p.setName(rs.getString("name"));
                p.setIllustration(rs.getString("illustration"));
                p.setSubtitle(rs.getString("subtitle"));
                p.setDescription(rs.getString("description"));
                p.setQuantite(rs.getInt("quantite"));
                p.setPrice(rs.getInt("price"));
                productList.add(p);
            }
        }
        return productList;
    }

    private Category fetchCategoryById(int categoryId) throws SQLException {
        String req = "SELECT * FROM `category` WHERE `id`=?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, categoryId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Category category = new Category();
                    category.setId(rs.getInt("id"));
                    category.setName(rs.getString("name"));
                    return category;
                }
            }
        }
        return null;
    }

    public List<Product> getAllProducts() throws SQLException {
        return selectAll();
    }

    public List<Product> selectProductByUserIdinPanier(int userId) {
        List<Product> productList = new ArrayList<>();

        // SQL query to retrieve products in panier for the specified user ID
        String query = "SELECT p.* FROM product p INNER JOIN panier pa ON p.id = pa.id_product WHERE pa.id_user = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product();
                    product.setId(rs.getInt("id"));
                    product.setCategory(fetchCategoryById(rs.getInt("category_id")));
                    product.setSlug(rs.getString("slug"));
                    product.setName(rs.getString("name"));
                    product.setIllustration(rs.getString("illustration"));
                    product.setSubtitle(rs.getString("subtitle"));
                    product.setDescription(rs.getString("description"));
                    product.setQuantite(rs.getInt("quantite"));
                    product.setPrice(rs.getInt("price"));
                    productList.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productList;
    }

    public Product getProductById(int idProduct) {
        String query = "SELECT * FROM Product WHERE id=?";
        Product product = new Product();

        try {
            PreparedStatement preparedStatement = cnx.prepareStatement(query);
            preparedStatement.setInt(1, idProduct);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                product.setId(resultSet.getInt("id"));
                product.setCategory(Objects.requireNonNull(fetchCategoryById(resultSet.getInt("category_id"))));
                product.setSlug(resultSet.getString("slug"));
                product.setName(resultSet.getString("name"));
                product.setIllustration(resultSet.getString("illustration"));
                product.setSubtitle(resultSet.getString("subtitle"));
                product.setDescription(resultSet.getString("description"));
                product.setQuantite(resultSet.getInt("quantite"));
                product.setPrice(resultSet.getInt("price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

}
