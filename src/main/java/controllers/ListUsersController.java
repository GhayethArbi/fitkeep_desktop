package controllers;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.User;
import services.ServiceUser;
import services.UserDao;
import services.session.UserSession;

public class ListUsersController extends NavigationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text currentUserName;

    @FXML
    private TreeTableColumn<User, HBox> ftAction;

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

    private final ServiceUser serviceUser = new ServiceUser();
    private final UserDao userDao = new UserDao();
    @FXML
    private ObservableList<User> userList = FXCollections.observableArrayList();

    void initializeList(){
        this.userList.clear();
        try {

            List<User> userList = serviceUser.selectAll();
            this.userList.addAll(userList);
            System.out.println(userList);
            TreeItem<User> root = new TreeItem<>();
            for (User user : this.userList) {
                TreeItem<User> userItem = new TreeItem<>(user);
                root.getChildren().add(userItem);
            }
            tableView.setRoot(root);
            tableView.setShowRoot(false); // Hides the root node
            // Set cell value factories for each column
            Button deleteBtn = new Button("Delete");
            Button showBtn = new Button("Show");
            Button banBtn = new Button("Ban");
            HBox btns = new HBox();
            btns.getChildren().addAll(deleteBtn,showBtn,banBtn);

            ftName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getName()));
            ftLn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getLastName()));
            ftEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getEmail()));
            ftGender.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().getGender()));
            ftBirth.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getValue().getBirthDay()));
            ftPhone.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getValue().getPhoneNumber()));
            ftPoint.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getValue().getLoyalityPoints()));
            ftStatus.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getValue().isBanned()));
            ftAction.setCellValueFactory(cellData-> new SimpleObjectProperty<>(btns));
            ftAction.setCellFactory(column -> new TreeTableCell<User, HBox>() {
                final Button deleteBtn = new Button("Delete");
                final Button showBtn = new Button("Show");
                final Button banBtn = new Button("Ban");


                {
                    deleteBtn.getStyleClass().add("delete-button");

                    showBtn.getStyleClass().add("show-button");


                    banBtn.getStyleClass().add("ban-button");



                    deleteBtn.setOnAction(event -> {
                        User user = getTreeTableRow().getItem();
                        try {
                            serviceUser.deleteOne(user.getId()); // Assuming serviceUser has a delete method
                            getTreeTableView().getRoot().getChildren().remove(getTreeTableRow().getIndex());
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    });

                    showBtn.setOnAction(event -> {

                        // Implement show action
                    });

                    banBtn.setOnAction(event -> {
                        User user = getTreeTableRow().getItem();
                        try {
                            userDao.banUser(user.getId()); // Assuming serviceUser has a ban method

                            initializeList();
                        } catch (SQLException e) {

                            System.out.println(e.getMessage()+"lknkjbhbkjb");
                            e.printStackTrace();
                        }
                        // Implement ban action
                    });
                    ;

                }


                @Override
                protected void updateItem(HBox item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        HBox buttons = new HBox(deleteBtn, showBtn, banBtn);
                        setGraphic(buttons);
                    }
                }
            });
           // userList = serviceUser.selectAll();
            //this.userList.clear();
            //this.userList.addAll(userList);

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

    }






    @FXML
    void initialize() {

        super.initialize();

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
        currentUserName.setText(UserSession.CURRENT_USER.getUserLoggedIn().getName()+" "+UserSession.CURRENT_USER.getUserLoggedIn().getLastName());
        initializeList();


    }



}
