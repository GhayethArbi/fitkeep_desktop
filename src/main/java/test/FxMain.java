package test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));

        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.setTitle("Login");
        stage.setScene(scene);

        stage.show();
    }

}
