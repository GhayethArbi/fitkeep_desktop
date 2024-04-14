package services;

import models.User;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceUser implements CRUD<User>{

    private Connection cnx ;

    public ServiceUser() {
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
    public void insertOne(User user) throws SQLException {
        String req = "INSERT INTO `user`(`name`, `last_name`, `email`) VALUES " +
                "(?,?,?)";
        PreparedStatement ps = cnx.prepareStatement(req);

        ps.setString(1, user.getName());
        ps.setString(2, user.getLastName());
        ps.setString(3, user.getEmail());

        ps.executeUpdate(req);
    }

    @Override
    public void updateOne(User user) throws SQLException {
        String req = "UPDATE `user` SET `name`=?, `last_name`=?, `email`=? WHERE `id`=?";
        PreparedStatement ps = cnx.prepareStatement(req);

        ps.setString(1, user.getName());
        ps.setString(2, user.getLastName());
        ps.setString(3, user.getEmail());
        ps.setInt(4, user.getId());

        ps.executeUpdate(req);
    }

    @Override
    public void deleteOne(User user) throws SQLException {
        String req = "Delete `user` WHERE `id`=?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1, user.getId());

        ps.executeUpdate(req);
    }

    @Override
    public List<User> selectAll() throws SQLException {
        List<User> userList = new ArrayList<>();

        String req = "SELECT * FROM `user`";
        Statement st = cnx.createStatement();

        ResultSet rs = st.executeQuery(req);

        while (rs.next()){
            User u = new User();

            u.setId(rs.getInt(("id")));
            u.setName(rs.getString((5)));
            u.setLastName(rs.getString(("last_name")));
            u.setEmail(rs.getString((2)));

            userList.add(u);
        }

        return userList;
    }
}
