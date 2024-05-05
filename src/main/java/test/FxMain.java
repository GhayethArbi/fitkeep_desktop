package test;

<<<<<<< HEAD
import controllers.CategoryDetails;
=======
>>>>>>> ca935f6e13b21fdf28b7c8dbf7f9751984a06275
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
<<<<<<< HEAD
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


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
    public static void main(String[] args) {
        launch(args);
=======

public class FxMain extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/AjouterUserFXML.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);

        stage.setTitle("Ajouter un user ");
        stage.setScene(scene);

        stage.show();

>>>>>>> ca935f6e13b21fdf28b7c8dbf7f9751984a06275
    }
}
