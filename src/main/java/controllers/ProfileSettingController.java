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
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.User;
import services.ServiceUser;
import services.session.AuthDTO;
import services.session.UserSession;

public class ProfileSettingController extends NavigationController{
    ServiceUser serviceUser;


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text addressError;

    @FXML
    private Text birthError;

    @FXML
    private Text currentUserName;

    @FXML
    private Text currentUserNameUp;

    @FXML
    private Text emailError;

    @FXML
    private Text fnError;

    @FXML
    private TextField ftAddress;

    @FXML
    private DatePicker ftBirth;

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
    private Text phoneError;
    @FXML
    private Circle image;

    void clearError(){
        phoneError.setText("");

        lnError.setText("");
        genderError.setText("");
        fnError.setText("");
        emailError.setText("");

        birthError.setText("");

        addressError.setText("");
        ftName.getStyleClass().remove("error");
        ftPhone.getStyleClass().remove("error");
        ftEmail.getStyleClass().remove("error");
        ftAddress.getStyleClass().remove("error");

        ftBirth.getStyleClass().remove("error");

        ftLastName.getStyleClass().remove("error");
    }

    void chargeData(){
        serviceUser =new ServiceUser();
        AuthDTO curr=UserSession.CURRENT_USER.getUserLoggedIn();
        ftEmail.setText(curr.getEmail());
        ftBirth.setValue(curr.getBirthDay().toLocalDate());
        ftAddress.setText(curr.getAddress());
        ftPhone.setText(curr.getPhoneNumber()+"");
        ftName.setText(curr.getName());
        ftLastName.setText(curr.getLastName());
        if(curr.getGender().equalsIgnoreCase("male")){
            ftMale.setSelected(true);
        }else{
            ftFemale.setSelected(true);
        }

    }

    @FXML
    void saveChanges(ActionEvent event)
         {
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
            }

           // Gender validation
            if (!ftMale.isSelected() && !ftFemale.isSelected()) {

                isValid = false;
                genderError.setText("Select your gender");
            }

            // Birthdate validation
            if (ftBirth.getValue() == null) {
                ftBirth.getStyleClass().add("error");
                isValid = false;
                birthError.setText("Select your birth date (MM/DD/YYYY)");
            } else if (ftBirth.getValue().isAfter(LocalDate.now().minusYears(15))) {
                ftBirth.getStyleClass().add("error");
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

            if(isValid){
                User user = new User();
                user.setName(ftName.getText());
                user.setLastName(ftLastName.getText());
                user.setPhoneNumber(Integer.parseInt(ftPhone.getText()));
                user.setEmail(ftEmail.getText());
                user.setAddress(ftAddress.getText());
                user.setBirthDay(Date.valueOf(ftBirth.getValue()));
                user.setId(UserSession.CURRENT_USER.getUserLoggedIn().getId());


                if (ftMale.isSelected()) {
                    user.setGender("Male");
                    // Process male selection
                } else {
                    user.setGender("Female");
                }
                user.setPassword(UserSession.CURRENT_USER.getUserLoggedIn().getPassword());
                user.setRoles(UserSession.CURRENT_USER.getUserLoggedIn().getRoles().toString());
                try{
                    serviceUser.updateOne(user);

                    UserSession.CURRENT_USER.getUserLoggedIn().setName(ftName.getText());
                    UserSession.CURRENT_USER.getUserLoggedIn().setLastName(ftLastName.getText());
                    UserSession.CURRENT_USER.getUserLoggedIn().setPhoneNumber(Integer.parseInt(ftPhone.getText()));
                    UserSession.CURRENT_USER.getUserLoggedIn().setEmail(ftEmail.getText());
                    UserSession.CURRENT_USER.getUserLoggedIn().setAddress(ftAddress.getText());
                    UserSession.CURRENT_USER.getUserLoggedIn().setBirthDay(Date.valueOf(ftBirth.getValue()));


                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("User updated");
                    alert.setContentText("You are updates your account.");
                    alert.show();
                    Stage stage = (Stage) this.ftEmail.getScene().getWindow(); // Get reference to the login window's stage
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Dashboard.fxml"));
                        Parent root = loader.load();
                        Scene scene = new Scene(root, 822, 495);
                        stage.setTitle("Dashboard");
                        stage.setScene(scene);
                        stage.show();
                    } catch (Exception e){
                        System.err.println(e);
                    }
                }

                catch (SQLException e){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("SQLException");
                    alert.setContentText(e.getMessage());
                    alert.show();
                }
                catch(Exception e){
                    System.out.println(e.getMessage());
                }
            }
        }

    @FXML
    void initialize() {
        super.initialize();
        assert addressError != null : "fx:id=\"addressError\" was not injected: check your FXML file 'ProfileSetting.fxml'.";
        assert birthError != null : "fx:id=\"birthError\" was not injected: check your FXML file 'ProfileSetting.fxml'.";
        assert currentUserName != null : "fx:id=\"currentUserName\" was not injected: check your FXML file 'ProfileSetting.fxml'.";
        assert currentUserNameUp != null : "fx:id=\"currentUserNameUp\" was not injected: check your FXML file 'ProfileSetting.fxml'.";
        assert emailError != null : "fx:id=\"emailError\" was not injected: check your FXML file 'ProfileSetting.fxml'.";
        assert fnError != null : "fx:id=\"fnError\" was not injected: check your FXML file 'ProfileSetting.fxml'.";
        assert ftAddress != null : "fx:id=\"ftAddress\" was not injected: check your FXML file 'ProfileSetting.fxml'.";
        assert ftBirth != null : "fx:id=\"ftBirth\" was not injected: check your FXML file 'ProfileSetting.fxml'.";
        assert ftEmail != null : "fx:id=\"ftEmail\" was not injected: check your FXML file 'ProfileSetting.fxml'.";
        assert ftLastName != null : "fx:id=\"ftLastName\" was not injected: check your FXML file 'ProfileSetting.fxml'.";
        assert ftName != null : "fx:id=\"ftName\" was not injected: check your FXML file 'ProfileSetting.fxml'.";
        assert ftPhone != null : "fx:id=\"ftPhone\" was not injected: check your FXML file 'ProfileSetting.fxml'.";
        assert genderError != null : "fx:id=\"genderError\" was not injected: check your FXML file 'ProfileSetting.fxml'.";
        assert genderToggleGroup != null : "fx:id=\"genderToggleGroup\" was not injected: check your FXML file 'ProfileSetting.fxml'.";
        assert lnError != null : "fx:id=\"lnError\" was not injected: check your FXML file 'ProfileSetting.fxml'.";
        assert phoneError != null : "fx:id=\"phoneError\" was not injected: check your FXML file 'ProfileSetting.fxml'.";
        currentUserNameUp.setText(UserSession.CURRENT_USER.getUserLoggedIn().getName()+" "+UserSession.CURRENT_USER.getUserLoggedIn().getLastName());

        currentUserName.setText(UserSession.CURRENT_USER.getUserLoggedIn().getName()+" "+UserSession.CURRENT_USER.getUserLoggedIn().getLastName());
        image.setStroke(Color.SEAGREEN);
        Image img =new Image("/IMG-20221027-WA0012.jpg", false);
        image.setFill(new ImagePattern(img));
        chargeData();
    }

}
