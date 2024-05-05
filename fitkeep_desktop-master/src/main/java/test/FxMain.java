package test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FxMain extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterPersonneFXML.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);

        stage.setTitle("Ajouter un user ");
        stage.setScene(scene);

        stage.show();
    }

}
