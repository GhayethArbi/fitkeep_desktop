package services;

import models.Roles;
import models.User;
import org.mindrot.jbcrypt.BCrypt;
import repository.UserRepository;
import services.session.AuthDTO;
import services.session.UserSession;
import utils.DBConnection;

import java.sql.*;

public class UserDao {
    private Connection cnx ;
    // prepare sql query
    PreparedStatement ps;
    UserSession userSession;
    private final ServiceUser serviceUser = new ServiceUser();
    private final UserRepository userRepository =new UserRepository();

    public UserDao() {
        cnx = DBConnection.getInstance().getCnx();
    }


    public String getHashedPasswordByUsername(String email) throws SQLException {
        String hashedPassword = null;
        String modifiedString = null;
        // Prepare SQL statement
        String sql = "SELECT password FROM user WHERE email = ?";
        try (PreparedStatement statement = cnx.prepareStatement(sql)) {
            statement.setString(1, email);

            // Execute query
            try (ResultSet resultSet = statement.executeQuery()) {
                // If user found, retrieve hashed password
                if (resultSet.next()) {
                    hashedPassword = resultSet.getString("password");
                    modifiedString = hashedPassword.substring(0, 2) + "a" + hashedPassword.substring(3);
                }
            }
        }
        catch (SQLException e) {
            System.out.println("_____________>erroe");
            // Handle any SQL exceptions
            e.printStackTrace();
            throw e;
        }

        return modifiedString;
    }


    public String login(String email, String password) throws SQLException {
        UserSession  userSession = UserSession.CURRENT_USER;
        if (UserSession.CURRENT_USER!=null){

            System.out.println(UserSession.CURRENT_USER.getUserLoggedIn().getEmail()+" is already connected !");

            return UserSession.CURRENT_USER.getUserLoggedIn().getEmail()+" is already connected !";
        }

        // Retrieve hashed password from the database based on the provided email
        String hashedPasswordFromDatabase = getHashedPasswordByUsername(email);

        // If no hashed password is found, deny access
        if (hashedPasswordFromDatabase == null) {

            return "User not found";
        }

        // Verify the provided password against the hashed password
        if (BCrypt.checkpw(password, hashedPasswordFromDatabase)) {
            // Passwords match, authenticate the user
            PreparedStatement ps = cnx.prepareStatement("SELECT * FROM user WHERE email = ?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            rs.next();
            AuthDTO authDTO = new AuthDTO();
            authDTO.setId(rs.getInt(1));
            authDTO.setEmail(email);
            authDTO.setPassword(password);
            authDTO.setRoles(rs.getString("roles").trim().toUpperCase());
            authDTO.setBanned(rs.getBoolean("is_banned"));
            authDTO.setBirthDay(rs.getDate("birth_day"));
            authDTO.setAddress(rs.getString("address"));
            authDTO.setGender(rs.getString("gender"));
            authDTO.setLastName(rs.getString("last_name"));
            authDTO.setName(rs.getString("name"));
            authDTO.setLoyalityPoints(rs.getInt("loyality_points"));
            authDTO.setRegistration_date(rs.getDate("registration_date"));
            authDTO.setResetToken(rs.getString("reset_token"));
            authDTO.setAuthCode(rs.getString("auth_code"));
            authDTO.setProfileImage(rs.getString("profile_image"));
            System.out.println(authDTO);
            if (authDTO.isBanned()){
                return "Your account is banned!";
            }


            UserSession.getSession(authDTO);
            userSession = new UserSession(authDTO);
            //UserSession.getSession(authDTO);
            userSession.getSession(authDTO);

            System.out.println("Current user "+UserSession.CURRENT_USER.getUserLoggedIn());


            return "Login successful";
        } else {
            // Passwords don't match, deny access
            return "Incorrect password";
        }
    }

    public String banUser(int id)throws SQLException{
        User user = userRepository.findById(id);
        System.out.println(user+"  /n zefkjndkvnkjvnkdfjcvc;v;");
        if(user.isBanned()){
            user.setBanned(false);
            System.out.println(  user.isBanned()+"1");
        }else{
            user.setBanned(true);
            System.out.println(  user.isBanned()+"2");
        }
        serviceUser.updateOne(user);
        System.out.println(  user.isBanned()+"3");

        return "Updating Successful.";
    }

}
    /*public String verifierPassword(String email, String inputPassword )throws SQLException {

        String hashedPasswordFromDatabase =getHashedPasswordByUsername(email);
        System.out.println("--------------->kjhkjkj");
        if (BCrypt.checkpw(inputPassword, hashedPasswordFromDatabase)) {
            // Passwords match, authentication successful
            // Allow user to log in
            return "password success";
        } else {
            // Passwords don't match
            // Deny access or show error message
            return "password error";
        }
    }*/

    /*private String getPasswordByEmail(String email) {
        String query = "SELECT password FROM user WHERE email = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("password");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving password: " + e.getMessage());
        }
        return null; // Return null if email not found or error occurs
    }*/
