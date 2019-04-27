package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;


public class MainMenuController {
    @FXML
    Button start;

    @FXML
    private AnchorPane rootLayout;


    @FXML
    public void loadSecond() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getClassLoader().getResource("Views/Game.fxml"));
        rootLayout.getChildren().setAll(pane);
    }

    @FXML
    public void initialize() {

    }
}
