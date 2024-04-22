package services;

import models.Roles;
import models.User;
import models.User1;
import org.mindrot.jbcrypt.BCrypt;
import services.session.UserSession;
import utils.DBConnection;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceUser implements CRUD<User>{

    private Connection cnx ;
    PreparedStatement ps;

    public ServiceUser() {
        cnx = DBConnection.getInstance().getCnx();
    }

    /*@Override
    public void insertOne(User user) throws SQLException {
        String req = "INSERT INTO `user`(`name`, `last_name`, `email`) VALUES " +
                "('"+ user.getName()+"','"+ user.getLastName()+"','+" user.getEmail()+"')";
        Statement st = cnx.createStatement();
        st.executeUpdate(req);
        System.out.println("User Added !");
    }*/

// the part of register
    private boolean emailExists(String email) {
        String query = "SELECT COUNT(*) FROM user WHERE email = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0; // If count > 0, email exists
                }
            }
        } catch (SQLException e) {
            System.err.println("Error checking email existence: " + e.getMessage());
        }
        return false; // Default to false in case of exceptions or no result
    }

    public void insertOne(User user) throws SQLException {
        // Check if the email already exists
        if (emailExists(user.getEmail())) {
            System.err.println("Error adding user: Email already exists");
            return; // Exit method, don't proceed with insertion
        }

        // Encrypt the password using BCrypt
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);

        String req = "INSERT INTO `user`(`email`, `roles`, `password`, `name`, `last_name`,`gender`,`birth_day`, `phone_number`,`address`,`is_banned`,`registration_date`,loyality_points) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        try{
            PreparedStatement ps = cnx.prepareStatement(req);


            ps.setString(1, user.getEmail());
            ps.setString(2, "[\"" + Roles.ROLE_USER + "\"]");
            ps.setString(3,user.getPassword());
            ps.setString(4, user.getName());
            ps.setString(5, user.getLastName());
            ps.setString(6,user.getGender());
            ps.setDate(7, user.getBirthDay());
            ps.setInt(8,user.getPhoneNumber());
            ps.setString(9,user.getAddress());
            ps.setBoolean(10,false);
            ps.setDate(11, new Date(System.currentTimeMillis()));
            ps.setInt(12,0);

            ps.executeUpdate();
        } catch (SQLException e){
                // Handle SQLException appropriately, e.g., log or propagate
                System.err.println("Error adding user: " + e.getMessage());

        }
    }
    /*public void insertOne1(User1 user) throws SQLException {
        String req = "INSERT INTO `user`(`id`, `email`, `roles`, `password`, `name`, `last_name`,`gender`,`birth_day`, `phone_number`, `loyality_points`, `profile_image`, `address`,`is_banned`,`registration_date`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1,user.getId());
        ps.setString(2, user.getEmail());
        ps.setString(3, "[\"" + Roles.ROLE_USER + "\"]");
        ps.setString(4,user.getPassword());
        ps.setString(5, user.getName());
        ps.setString(6, user.getLastName());
        ps.setString(7,user.getGender());
        ps.setDate(8, user.getBirthDay());

        ps.setInt(9,user.getPhoneNumber());
        ps.setInt(10,0);
        ps.setString(11,null);
        ps.setString(12,user.getAddress());
        ps.setBoolean(13,false);
        ps.setDate(14, new Date(System.currentTimeMillis()));






        ps.executeUpdate();
    }*/

 //   part of login


    @Override
    public void updateOne(User user) throws SQLException {

        String req = "UPDATE `user` SET `name`=?, `last_name`=?, `email`=?, `gender`=?, `birth_day`=?, `phone_number`=?, `address`=?  WHERE `id`=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            System.out.println("------------------------------------------------>1");
            ps.setString(1, user.getName());
            System.out.println("------------------------------------------------>2");

            ps.setString(2, user.getLastName());
            System.out.println("------------------------------------------------>3");
            ps.setString(3, user.getEmail());
            System.out.println("------------------------------------------------>4");
            ps.setString(4, user.getGender());
            System.out.println("------------------------------------------------>5");
            ps.setDate(5, user.getBirthDay());
            System.out.println("------------------------------------------------>6");
            ps.setInt(6, user.getPhoneNumber());
            System.out.println("------------------------------------------------>7");
            ps.setString(7, user.getAddress());
            System.out.println("------------------------------------------------>8");
            ps.setInt(8, user.getId());
            System.out.println("-----------------"+user.getId());

            ps.executeUpdate();
            System.out.println("------------------------------------------------>10");

        } catch (SQLException e){
            // Handle SQLException appropriately, e.g., log or propagate
            System.err.println("Error adding user: " + e.getMessage());

        }
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
           // String role = rs.getString("roles").trim().toUpperCase(); // Trim and convert to uppercase
            User u = new User();
            u.setId(rs.getInt(("id")));
            u.setName(rs.getString((5)));
            u.setLastName(rs.getString(("last_name")));
            u.setEmail(rs.getString((2)));
            u.setPassword(rs.getString("password"));
            u.setGender(rs.getString("gender"));



            u.setRoles(rs.getString("roles").trim().toUpperCase());// Set the role for the user
            u.setBirthDay(rs.getDate("birth_day"));
            u.setPhoneNumber(rs.getInt("phone_number"));
            u.setLoyalityPoints(rs.getInt("loyality_points"));
            u.setBanned(rs.getBoolean("is_banned"));


            userList.add(u);
        }

        return userList;
    }



    public List<User1> selectAll1() throws SQLException {
        List<User1> userList = new ArrayList<>();

        String req = "SELECT * FROM `user`";
        Statement st = cnx.createStatement();

        ResultSet rs = st.executeQuery(req);

        while (rs.next()){
            String roleString = rs.getString("roles").trim().toUpperCase(); // Trim and convert to uppercase
            roleString = roleString.replaceAll("[\\[\\]\"]", ""); // Remove square brackets and double quotes
            Roles role = Roles.valueOf(roleString); // Convert the string value to Roles enum
            User1 u = new User1
                    .UserBuilder()
                    .setId(rs.getInt(("id")))
                    .setName(rs.getString((5)))
                    .setLastName(rs.getString(("last_name")))
                    .setEmail(rs.getString((2)))
                    .setRoles(role)// Set the role for the user
                    .build();
            userList.add(u);
        }

        return userList;
    }
}
