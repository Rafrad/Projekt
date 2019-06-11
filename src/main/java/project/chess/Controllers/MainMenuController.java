package project.chess.Controllers;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * Class controls how main menu behaves
 */

public class MainMenuController {
    @FXML
    ImageView playButton;
    @FXML
    ImageView exitButton;


    /**
     * This method changes scene to settings
     *
     * @param event event
     * @throws IOException thrown when loader cannot find settings.fxml view
     */

    public void changeSceneToSettings(Event event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/Views/fxml/Settings.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    /**
     * exits game and terminating every process (includes clock thread)
     */

    public void exitGame() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
        System.exit(0);
    }
}
