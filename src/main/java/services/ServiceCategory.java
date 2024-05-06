package services;

import models.Category;
import utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceCategory implements CRUD<Category>{

    private Connection cnx ;

    public ServiceCategory() {
        cnx = DBConnection.getInstance().getCnx();
    }
    /*
        @Override
        public void insertOne(User user) throws SQLException {
            String req = "INSERT INTO `user`(`name`, `last_name`, `email`) VALUES " +
                    "('"+ user.getName()+"','"+ user.getLastName()+"','+" user.getEmail()+"')";
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("User Added !");
        }
    */
    public void insertOne(Category category) throws SQLException {
        String req = "INSERT INTO `category`(`id`, `name`) VALUES (?, ?)";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, category.getId());
        ps.setString(2, category.getName());
        ps.executeUpdate();
    }


    @Override
    public void updateOne(Category category) throws SQLException {

        String req = "UPDATE `category` SET `name` = ? WHERE `id` = ?";

        PreparedStatement ps = cnx.prepareStatement(req);

        ps.setString(1, category.getName());
        ps.setInt(2, category.getId());


        ps.executeUpdate();

    }

    @Override
    public void deleteOne(Category category) throws SQLException {

        String req = "DELETE FROM `category` WHERE `id`=?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, category.getId()); // Assuming you have an id field in your ActivitePhysique class
        ps.executeUpdate();
        System.out.println("ActivitePhysique Deleted !");
    }

    @Override
    public List<Category> selectAll() throws SQLException {
        List<Category> categoryList = new ArrayList<>();

        String req = "SELECT * FROM `category`";
        Statement st = cnx.createStatement();

        ResultSet rs = st.executeQuery(req);

        while (rs.next()){
            Category u = new Category();
            u.setId(rs.getInt(("id")));
            u.setName(rs.getString((2)));
            categoryList.add(u);
        }
        return categoryList;
    }

    public Category getCategoryByName(String categoryName) throws SQLException {
        String req = "SELECT * FROM `category` WHERE `name`=?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, categoryName);
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

    public List<Category> getAllCategories() throws SQLException {
        List<Category> categories = new ArrayList<>();
        String query = "SELECT * FROM category"; // Table name should be lowercase

        try (PreparedStatement statement = cnx.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int categoryId = resultSet.getInt("id");
                String categoryName = resultSet.getString("name");

                Category category = new Category(categoryId, categoryName);
                categories.add(category);
            }
        }

        return categories;
    }


}
