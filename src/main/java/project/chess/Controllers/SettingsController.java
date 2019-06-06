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

/**
 * This class defines how settings scene behaves.
 * It starts game and creates new Game class.
 * When game starts, options are given by Option class to Game Controller class.
 */

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

        Parent root = loader(options);

        Scene tableViewScene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        setWindow(tableViewScene, window);
        window.show();
    }

    /**
     * loads Game view and init options
     *
     * @param options needed to start game (time, players, first move of player)
     * @return loader
     * @throws IOException thrown when loader cannot find Game.fxml view
     * @throws PlayerColorException thrown when pieces don't know where they belong to in init method
     * @see GameController#init(project.chess.Models.Options)
     */

    private Parent loader(Options options) throws IOException, PlayerColorException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/fxml/Game.fxml"));
        Parent root = loader.load();
        GameController gameController = loader.getController();
        gameController.init(options);

        return root;
    }

    /**
     * Sets window, and its options (what does it look like)
     * @param tableViewScene new scene given to method
     * @param window window given by event
     */

    private void setWindow(Scene tableViewScene, Stage window) {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        window.setX(primaryScreenBounds.getMinX() + primaryScreenBounds.getWidth() - 1270);
        window.setY(primaryScreenBounds.getMinY() + primaryScreenBounds.getHeight() - 750);
        window.setWidth(1000);
        window.setHeight(700);
        window.setScene(tableViewScene);
    }

    /**
     * Sets options needed for a game like;
     * time (blitz, classic, etc.)
     * VS (player, computer)
     * player 1 starts as white or black
     *
     * @return options ready to use
     */

    private Options setOptions() {
        Options options = new Options();
        options.setGameMode(gameModeChoice.getValue());
        options.setVersusMode(versusModeChoice.getValue());
        options.setFirstPlayerColor(firstPlayerColorChoice.getValue());
        return options;
    }


    /**
     * inits Settings controller
     */

    @FXML
    public void initialize() {
        setChoices();
        setTooltips();
        setDefaultsChoices();

    }

    /**
     * Sets choices for user in GUI for Option class
     */

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

    /**
     * When User doesn't choose anything, will start with these options.
     */

    private void setDefaultsChoices() {
        gameModeChoice.setValue("Classic 15|15");
        versusModeChoice.setValue("Player VS Player");
        firstPlayerColorChoice.setValue("white");
    }

    /**
     * Method sets tooltips for user, if he does not understand what he has to choose.
     */

    private void setTooltips() {
        gameModeChoice.setTooltip(new Tooltip("choose game mode, it tells you how much time you have"));
        firstPlayerColorChoice.setTooltip(new Tooltip("white starts"));
        versusModeChoice.setTooltip(new Tooltip("play vs computer or player"));
    }
}
