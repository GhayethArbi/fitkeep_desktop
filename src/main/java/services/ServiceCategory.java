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
        String req = "UPDATE `category` SET `name`=?WHERE `id`=?";
        PreparedStatement ps = cnx.prepareStatement(req);

        ps.setString(1, category.getName());
        ps.setInt(2, category.getId());

        ps.executeUpdate(req);
    }

    @Override
    public void deleteOne(Category category) throws SQLException {
        String req = "Delete `category` WHERE `id`=?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, category.getId());

        ps.executeUpdate(req);
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
}
