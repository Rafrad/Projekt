import Exceptions.PlayerColorException;
import Models.Game;
import Models.Pieces.BlackPawn;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/*
 * Currently I don't know how to make separate
 * folder for Controllers
 *
 * At the moment Views & Controllers are in the same folder
 */

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        //TODO: scenes


//        primaryStage.setTitle("Zu Wilkomen");
//        AnchorPane rootLayout;
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(Main.class.getClassLoader().getResource("Views/MainMenuScreen.fxml"));
//        rootLayout = loader.load();
//        Scene s = new Scene(rootLayout);
//
//        ConnectionManager connectionManager = new ConnectionManager(s);
//        connectionManager.showLogInScreen();
//
//        primaryStage.setScene(s);
//        primaryStage.show();

        Parent root = FXMLLoader.load(getClass().getResource("Views/Game.fxml"));
        primaryStage.setTitle("Chess");
        primaryStage.setScene(new Scene(root, 1000, 800));
        primaryStage.show();
        primaryStage.setResizable(false);

    }


    public static void main(String[] args){
        launch(args);
    }
}