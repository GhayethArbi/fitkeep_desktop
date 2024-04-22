package controllers;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.User;
import services.ServiceUser;
import services.session.UserSession;

public class ListUsersController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text currentUserName;

    @FXML
    private TreeTableColumn<User, ?> ftAction;

    @FXML
    private TreeTableColumn<User, Date> ftBirth;

    @FXML
    private TreeTableColumn<User, String> ftEmail;

    @FXML
    private TreeTableColumn<User, String> ftGender;

    @FXML
    private TreeTableColumn<User, String> ftLn;

    @FXML
    private TreeTableColumn<User, String> ftName;

    @FXML
    private TreeTableColumn<User, Integer> ftPhone;

    @FXML
    private TreeTableColumn<User, Integer> ftPoint;

    @FXML
    private TreeTableColumn<User, Boolean> ftStatus;

    @FXML
    private TreeTableView<User> tableView;

    private ServiceUser serviceUser = new ServiceUser();

    @FXML
    void Logout(ActionEvent event) {
        Stage stage = (Stage) this.currentUserName.getScene().getWindow(); // Get reference to the login window's stage
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 822, 495);
            stage.setTitle("Login");
            stage.setScene(scene);
            stage.show();
            UserSession.CURRENT_USER.logout();
        } catch (Exception e){
            System.err.println(e);
        }
    }

    @FXML
    void goToDash(ActionEvent event) {
        Stage stage = (Stage) currentUserName.getScene().getWindow(); // Get reference to the login window's stage
        try {
        stage.setTitle("Dashboard");


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Dashboard.fxml"));
        Parent p = loader.load();
        Scene scene = new Scene(p);

        stage.setScene(scene);
        } catch (Exception e){
            System.err.println(e);
        }

    }

    @FXML
    void goToEditProfile(ActionEvent event) {
        Stage stage = (Stage) this.currentUserName.getScene().getWindow(); // Get reference to the login window's stage
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ProfileSetting.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 1200, 600);
            stage.setTitle("Edit Profile");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e){
            System.err.println(e);
        }
    }

    @FXML
    void initialize() {
        assert currentUserName != null : "fx:id=\"currentUserName\" was not injected: check your FXML file 'ListUsers.fxml'.";
        assert ftAction != null : "fx:id=\"ftAction\" was not injected: check your FXML file 'ListUsers.fxml'.";
        assert ftBirth != null : "fx:id=\"ftBirth\" was not injected: check your FXML file 'ListUsers.fxml'.";
        assert ftEmail != null : "fx:id=\"ftEmail\" was not injected: check your FXML file 'ListUsers.fxml'.";
        assert ftGender != null : "fx:id=\"ftGender\" was not injected: check your FXML file 'ListUsers.fxml'.";
        assert ftLn != null : "fx:id=\"ftLn\" was not injected: check your FXML file 'ListUsers.fxml'.";
        assert ftName != null : "fx:id=\"ftName\" was not injected: check your FXML file 'ListUsers.fxml'.";
        assert ftPhone != null : "fx:id=\"ftPhone\" was not injected: check your FXML file 'ListUsers.fxml'.";
        assert ftPoint != null : "fx:id=\"ftPoint\" was not injected: check your FXML file 'ListUsers.fxml'.";
        assert ftStatus != null : "fx:id=\"ftStatus\" was not injected: check your FXML file 'ListUsers.fxml'.";
        assert tableView != null : "fx:id=\"tableView\" was not injected: check your FXML file 'ListUsers.fxml'.";
        try {
            List<User> userList = serviceUser.selectAll();
            TreeItem<User> root = new TreeItem<>();
            for (User user : userList) {
                TreeItem<User> userItem = new TreeItem<>(user);
                root.getChildren().add(userItem);
            }
            tableView.setRoot(root);
            tableView.setShowRoot(false); // Hides the root node
            // Set cell value factories for each column

            ftName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getName()));
            ftLn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getLastName()));
            ftEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getEmail()));
            ftGender.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getGender()));
           // ftBirth.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getBirthDay()));
           // ftPhone.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getPhoneNumber()));
            //ftPoint.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getLoyalityPoints()));
            //ftStatus.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().isBanned()));
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


}
