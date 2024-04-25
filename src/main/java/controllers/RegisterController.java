package controllers;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.User;
import services.ServiceUser;
import services.UserDao;
import services.session.AuthDTO;
import services.session.UserSession;

public class RegisterController {
    private ServiceUser serviceUser=new ServiceUser();
    private final UserDao userDao = new UserDao();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text addressError;

    @FXML
    private Text agreeError;

    @FXML
    private Text birthError;

    @FXML
    private Text cPassError;

    @FXML
    private Text emailError;

    @FXML
    private Text fnError;

    @FXML
    private PasswordField fpCofirm;

    @FXML
    private PasswordField fpPass;

    @FXML
    private TextField ftAddress;

    @FXML
    private CheckBox ftAgree;

    @FXML
    private DatePicker ftBirthDay;

    @FXML
    private TextField ftEmail;

    @FXML
    private RadioButton ftFemale;

    @FXML
    private TextField ftLastName;

    @FXML
    private RadioButton ftMale;

    @FXML
    private TextField ftName;

    @FXML
    private TextField ftPhone;

    @FXML
    private Text genderError;

    @FXML
    private ToggleGroup genderToggleGroup;


    @FXML
    private Text lnError;

    @FXML
    private Text passError;

    @FXML
    private Text phoneError;

    void clearError(){
        phoneError.setText("");
        passError.setText("");
        lnError.setText("");
        genderError.setText("");
        fnError.setText("");
        emailError.setText("");
        cPassError.setText("");
        birthError.setText("");
        agreeError.setText("");
        addressError.setText("");
        ftName.getStyleClass().remove("error");
        ftPhone.getStyleClass().remove("error");
        ftEmail.getStyleClass().remove("error");
        ftAddress.getStyleClass().remove("error");
        ftAgree.getStyleClass().remove("error");
        ftBirthDay.getStyleClass().remove("error");
        fpPass.getStyleClass().remove("error");
        fpCofirm.getStyleClass().remove("error");
        ftLastName.getStyleClass().remove("error");
    }
    @FXML
    void createAccount(ActionEvent event) {

        clearError();
        boolean isValid = true;
        int phoneNumber;
        try {
            phoneNumber=Integer.parseInt(ftPhone.getText());
            if (ftPhone.getText().isEmpty()) {
                isValid = false;
                phoneError.setText("Phone number is required.");

                ftPhone.getStyleClass().add("error");

            } else if (ftPhone.getText().length()!=8) {
                phoneError.setText("Phone number must contain at 8 characters.");
                ftPhone.getStyleClass().add("error");
                isValid = false;
            }
        } catch (NumberFormatException e)
        {
            phoneError.setText("Invalid phone number");
            isValid=false;
            ftPhone.getStyleClass().add("error");


        }
        // Phone number validation


        // First name validation
        if (ftName.getText().isEmpty()) {
            isValid = false;
            ftName.getStyleClass().add("error");
            fnError.setText("First name is required");
        } else if (ftName.getText().length()<4) {
            fnError.setText("First name must contain at least 4 characters.");
            isValid = false;
            ftName.getStyleClass().add("error");
        }

        // Last name validation
        if (ftLastName.getText().isEmpty()) {
            isValid = false;
            lnError.setText("Last name is required");
            ftLastName.getStyleClass().add("error");
        } else if (ftLastName.getText().length()<4) {
            lnError.setText("Last name must contain at least 4 characters.");
            ftLastName.getStyleClass().add("error");
            isValid = false;
        }

        // Email validation
        if (ftEmail.getText().isEmpty()) {
            isValid = false;
            ftEmail.getStyleClass().add("error");
            emailError.setText("Email address is required.");
        }else if (!ftEmail.getText().matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")){
            emailError.setText("invalid email address.");
            ftEmail.getStyleClass().add("error");
            isValid = false;
        }else if(serviceUser.emailExists(ftEmail.getText())){
            emailError.setText("Email already exists.");
            ftEmail.getStyleClass().add("error");
            isValid = false;
        }

        // Password validation
        if (fpPass.getText().isEmpty()) {
            isValid = false;
            passError.setText("Password is required");
            fpPass.getStyleClass().add("error");

        } else if (fpPass.getText().length()<8) {
            isValid = false;
            fpPass.getStyleClass().add("error");
            passError.setText("Password must contain at least 8 characters.");
        }

        // Confirm password validation
        if (!fpCofirm.getText().equals(fpPass.getText())) {
            fpCofirm.getStyleClass().add("error");
            isValid = false;
            cPassError.setText("Passwords do not match");
        }

        // Gender validation
        if (!ftMale.isSelected() && !ftFemale.isSelected()) {

            isValid = false;
            genderError.setText("Select your gender");
        }

        // Birthdate validation
        if (ftBirthDay.getValue() == null) {
            ftBirthDay.getStyleClass().add("error");
            isValid = false;
            birthError.setText("Select your birth date (MM/DD/YYYY)");
        } else if (ftBirthDay.getValue().isAfter(LocalDate.now().minusYears(15))) {
            ftBirthDay.getStyleClass().add("error");
            isValid = false;
            birthError.setText("You must be at least 15 years old");
        }

        // Address validation
        if (ftAddress.getText().isEmpty()) {
            ftAddress.getStyleClass().add("error");
            isValid = false;
            addressError.setText("Address is required");
        } else if (ftAddress.getText().length()<5) {
            ftAddress.getStyleClass().add("error");
            isValid = false;
            addressError.setText("Address must contain at least 5 characters.");
        }
        if (!ftAgree.isSelected()){
            isValid = false;
            agreeError.setText("You must agree to the terms and conditions");

        }
        if(isValid){
            User user = new User();
            user.setName(ftName.getText());
            user.setLastName(ftLastName.getText());
            user.setPhoneNumber(Integer.parseInt(ftPhone.getText()));
            user.setEmail(ftEmail.getText());
            user.setAddress(ftAddress.getText());
            user.setBirthDay(Date.valueOf(ftBirthDay.getValue()));
            user.setPassword(fpPass.getText());
            if (ftMale.isSelected()) {
                user.setGender("Male");
                // Process male selection
            } else {
                user.setGender("Female");
            }
            try{
                serviceUser.insertOne(user);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Sign Up");
                alert.setContentText("You have an account now.");
                alert.show();
                userDao.login(ftEmail.getText(),fpPass.getText());
                Stage stage = (Stage) this.ftPhone.getScene().getWindow(); // Get reference to the login window's stage
                stage.setTitle("Dashboard");


                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Dashboard.fxml"));
                Parent p = loader.load();
                Scene scene = new Scene(p);

                stage.setScene(scene);
            }
            catch (SQLException e){
                System.out.println(e.getMessage());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("SQLException");
                alert.setContentText(e.getMessage());
                alert.show();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }

        }
    }

    @FXML
    void goToLogin(ActionEvent event) {
        try {
            Stage stage = (Stage) ftEmail.getScene().getWindow(); // Get reference to the login window's stage
            stage.setTitle("Login");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
            Parent p = loader.load();
            Scene scene = new Scene(p);

            stage.setScene(scene);

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle navigation failure
        }

    }

    @FXML
    void initialize() {
        assert addressError != null : "fx:id=\"addressError\" was not injected: check your FXML file 'Register.fxml'.";
        assert agreeError != null : "fx:id=\"agreeError\" was not injected: check your FXML file 'Register.fxml'.";
        assert birthError != null : "fx:id=\"birthError\" was not injected: check your FXML file 'Register.fxml'.";
        assert cPassError != null : "fx:id=\"cPassError\" was not injected: check your FXML file 'Register.fxml'.";
        assert emailError != null : "fx:id=\"emailError\" was not injected: check your FXML file 'Register.fxml'.";
        assert fnError != null : "fx:id=\"fnError\" was not injected: check your FXML file 'Register.fxml'.";
        assert fpCofirm != null : "fx:id=\"fpCofirm\" was not injected: check your FXML file 'Register.fxml'.";
        assert fpPass != null : "fx:id=\"fpPass\" was not injected: check your FXML file 'Register.fxml'.";
        assert ftAddress != null : "fx:id=\"ftAddress\" was not injected: check your FXML file 'Register.fxml'.";
        assert ftAgree != null : "fx:id=\"ftAgree\" was not injected: check your FXML file 'Register.fxml'.";
        assert ftBirthDay != null : "fx:id=\"ftBirthDay\" was not injected: check your FXML file 'Register.fxml'.";
        assert ftEmail != null : "fx:id=\"ftEmail\" was not injected: check your FXML file 'Register.fxml'.";
        assert ftFemale != null : "fx:id=\"ftFemale\" was not injected: check your FXML file 'Register.fxml'.";
        assert ftLastName != null : "fx:id=\"ftLastName\" was not injected: check your FXML file 'Register.fxml'.";
        assert ftMale != null : "fx:id=\"ftMale\" was not injected: check your FXML file 'Register.fxml'.";
        assert ftName != null : "fx:id=\"ftName\" was not injected: check your FXML file 'Register.fxml'.";
        assert ftPhone != null : "fx:id=\"ftPhone\" was not injected: check your FXML file 'Register.fxml'.";
        assert genderError != null : "fx:id=\"genderError\" was not injected: check your FXML file 'Register.fxml'.";
        assert genderToggleGroup != null : "fx:id=\"genderToggleGroup\" was not injected: check your FXML file 'Register.fxml'.";
        assert lnError != null : "fx:id=\"lnError\" was not injected: check your FXML file 'Register.fxml'.";
        assert passError != null : "fx:id=\"passError\" was not injected: check your FXML file 'Register.fxml'.";
        assert phoneError != null : "fx:id=\"phoneError\" was not injected: check your FXML file 'Register.fxml'.";

    }

}