package project.chess.Controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tooltip;
import javafx.stage.Screen;
import javafx.stage.Stage;
import project.chess.Exceptions.PlayerColorException;
import project.chess.Models.Options;


import java.io.IOException;


public class SettingsController {
    @FXML
    ChoiceBox<String> gameModeChoice;
    @FXML
    ChoiceBox<String> versusModeChoice;
    @FXML
    ChoiceBox<String> firstPlayerColorChoice;

    @FXML
    Button startGameButton;
    @FXML
    Button backToMainMenuButton;


    /**
     * When this method is called, it will change the Scene to main menu
     */

    public void changeSceneToMainMenu(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/Views/fxml/MainMenu.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    /**
     * When this method is called, it will change the Scene to a Game
     */

    public void startGame(ActionEvent event) throws IOException, PlayerColorException {
        Options options = setOptions();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/fxml/Game.fxml"));
        Parent root = loader.load();
        GameController gameController = loader.getController();
        gameController.init(options);

        Scene tableViewScene = new Scene(root);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        setWindow(tableViewScene, window);
        window.show();
    }

    private void setWindow(Scene tableViewScene, Stage window) {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        window.setX(primaryScreenBounds.getMinX() + primaryScreenBounds.getWidth() - 1450);
        window.setY(primaryScreenBounds.getMinY() + primaryScreenBounds.getHeight() - 800);
        window.setWidth(1400);
        window.setHeight(800);
        window.setScene(tableViewScene);
    }

    private Options setOptions() {
        Options options = new Options();
        options.setGameMode(gameModeChoice.getValue());
        options.setVersusMode(versusModeChoice.getValue());
        options.setFirstPlayerColor(firstPlayerColorChoice.getValue());
        return options;
    }

    @FXML
    public void initialize() {
        setChoices();
        setTooltips();
        setDefaultsChoices();

    }

    private void setChoices() {
        gameModeChoice.setItems(FXCollections.observableArrayList(
                "Blitz 3|2", "Bullet 1|0", "Rapid 10|0", "Classic 15|15")
        );

        versusModeChoice.setItems(FXCollections.observableArrayList(
                "Player VS Player", "Player VS Computer")
        );

        firstPlayerColorChoice.setItems(FXCollections.observableArrayList(
                "white", "black")
        );
    }

    private void setDefaultsChoices() {
        gameModeChoice.setValue("Classic 15|15");
        versusModeChoice.setValue("Player VS Player");
        firstPlayerColorChoice.setValue("white");
    }

    private void setTooltips() {
        gameModeChoice.setTooltip(new Tooltip("choose game mode"));
        firstPlayerColorChoice.setTooltip(new Tooltip("white starts"));
        versusModeChoice.setTooltip(new Tooltip("play vs computer or player"));
    }
}
