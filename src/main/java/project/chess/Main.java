package project.chess;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/*
 * Currently I don't know how to make separate
 * folder for Controllers
 *
 * At the moment main.java.project.chess.Views & Controllers are in the same folder
 */

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/fxml/MainMenu.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Chess");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("/Images/icon.png"));
    }


    public static void main(String[] args){
        launch(args);
    }
}