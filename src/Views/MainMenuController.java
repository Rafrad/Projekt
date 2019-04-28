package Views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
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

    /*
     * When this method is called, it will change the Scene to
     * a Game
     */

    public void startGame(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/Views/Game.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setWidth(1400);
        window.setHeight(800);
        window.setScene(tableViewScene);
//        window.setResizable(false);

        window.show();

    }


    @FXML
    public void initialize() {

    }
}
