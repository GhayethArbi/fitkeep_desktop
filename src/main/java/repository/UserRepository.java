package repository;

import models.User;
import org.mindrot.jbcrypt.BCrypt;
import utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.sql.ResultSet;

public class UserRepository {
    private Connection cnx;
    private PreparedStatement ps;

    public UserRepository() {
        cnx = DBConnection.getInstance().getCnx();
    }

    // Example method to retrieve all users from the database
    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM user";
        try {
            ps = cnx.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt(("id")));
                user.setName(rs.getString((5)));
                user.setAddress(rs.getString("address"));
                user.setLastName(rs.getString(("last_name")));
                user.setEmail(rs.getString((2)));
                user.setPassword(rs.getString("password"));
                user.setGender(rs.getString("gender"));
                user.setRoles(rs.getString("roles").trim().toUpperCase());// Set the role for the user
                user.setBirthDay(rs.getDate("birth_day"));
                user.setPhoneNumber(rs.getInt("phone_number"));
                user.setLoyalityPoints(rs.getInt("loyality_points"));
                user.setBanned(rs.getBoolean("is_banned"));
                // Set other properties
                users.add(user);
                System.out.println(users);
            }
        } finally {
            if (ps != null) {
                ps.close();
            }
        }
        return users;
    }

    // Example method to delete a user from the database
    public void deleteUser(int userId) throws SQLException {
        String query = "DELETE FROM user WHERE id = ?";
        try {
            ps = cnx.prepareStatement(query);
            ps.setInt(1, userId);
            ps.executeUpdate();
        } finally {
            if (ps != null) {
                ps.close();
            }
        }
    }
    public User findByEmail(String email) throws SQLException {
        String query = "SELECT * FROM user WHERE email = ?";
        try {
            ps = cnx.prepareStatement(query);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt(1));
                user.setEmail(email);
                user.setPassword(rs.getString("password"));
                user.setRoles(rs.getString("roles").trim().toUpperCase());
                user.setBanned(rs.getBoolean("is_banned"));
                user.setBirthDay(rs.getDate("birth_day"));
                user.setAddress(rs.getString("address"));
                user.setGender(rs.getString("gender"));
                user.setLastName(rs.getString("last_name"));
                user.setName(rs.getString("name"));
                user.setLoyalityPoints(rs.getInt("loyality_points"));
                user.setRegistration_date(rs.getDate("registration_date"));
                user.setResetToken(rs.getString("reset_token"));
                user.setAuthCode(rs.getString("auth_code"));
                user.setProfileImage(rs.getString("profile_image"));
                // Set other properties
                return user;
            } else {
                return null; // No user found with the given email
            }
        } finally {
            if (ps != null) {
                ps.close();
            }
        }
    }
    public User findById(int id)throws SQLException{
        User user =new User();
        String query = "SELECT * FROM user WHERE id = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                user.setId(id);
                user.setName(rs.getString(("name")));
                user.setLastName(rs.getString(("last_name")));
                user.setEmail(rs.getString((2)));
                user.setAddress(rs.getString("address"));
                user.setPassword(rs.getString("password"));
                user.setGender(rs.getString("gender"));
                user.setRoles(rs.getString("roles").trim().toUpperCase());// Set the role for the user
                user.setBirthDay(rs.getDate("birth_day"));
                user.setPhoneNumber(rs.getInt("phone_number"));
                user.setLoyalityPoints(rs.getInt("loyality_points"));
                user.setBanned(rs.getBoolean("is_banned"));
            }
            rs.close();
        }
        finally {
            if (ps != null) {
                ps.close();
            }
        }
        return user;
    }
    public void changePassword(User user)throws SQLException{
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);
        String req = "UPDATE `user` SET `password`=? WHERE `id`=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);

            ps.setString(1, user.getPassword());
            ps.setInt(2, user.getId());

            ps.executeUpdate();

        } catch (SQLException e){
            // Handle SQLException appropriately, e.g., log or propagate
            System.err.println("Error adding user: " + e.getMessage());

        }
    }


    // Other methods as needed
}
