package test;

import controllers.CategoryDetails;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
public class FxMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/ProductDetails.fxml"));
            Scene scene = new Scene(parent);
           // scene.setFill(Color.TRANSPARENT);
            primaryStage.setScene(scene);
           // primaryStage.initStyle(StageStyle.TRANSPARENT);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(CategoryDetails.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * @param args the command line arguments
     */
   /* public static void main(String[] args) {
        launch(args);*/

import models.User;
import repository.UserRepository;

import java.sql.SQLException;


public class FxMain extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
       /*try {
           UserRepository userRepository=new UserRepository();

        User user = new User();
        user.setPassword("12345678");
        user.setId(41);
        userRepository.changePassword(user);
       }catch (SQLException e){
           System.out.println(e.getMessage());
       }*/
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));

        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.setTitle("Login");
        stage.setScene(scene);

        stage.show();



    }

}
