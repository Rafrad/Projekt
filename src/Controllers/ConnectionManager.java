package Controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

//scalac kontrolserrhye
public class ConnectionManager {
    private Scene scene;
    private static Stage currStage = new Stage();


    public ConnectionManager(Scene scene) {
        this.scene = scene;
    }

    public void showLogInScreen() throws Exception {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("Views/MainMenuScreen.fxml"));
//        scene.setRoot(loader.load());
//        MainMenuController controller =
//                loader.<MainMenuController>getController();
//        controller.
    }

    public void loadGame(AnchorPane layout) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("Views/sample.fxml"));
        layout.getChildren().setAll(pane);
    }
}
