package Controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;


import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Controller {
    //he
    @FXML
    Pane pane;
    @FXML
    GridPane gridPane;
    @FXML
    AnchorPane anchor;
    @FXML
    Pane kurwa;



    @FXML
    public void initialize() throws FileNotFoundException{
        Image kon = new Image(new FileInputStream("D:\\Java - projects\\Chess\\src\\images\\poprawione.png"));

//        Image kon = new Image(new FileInputStream("url('../images/poprawione.png')"));


        pane.setOnMouseClicked(mouseEvent->{
            pane.setStyle("-fx-background-color: red");
        });

//        System.out.println(anchor.getHeight());
//
        Pane xd = new Pane();
        gridPane.add(xd,1, 1);
        xd.setId("IDD");

//        xd.setStyle("-fx-background-color: green");
        xd.setOnMouseClicked(mouseEvent->{
            System.out.println(xd.getId());
            clicked();
        });

        ImageView v1 = new ImageView(kon);
xd.setStyle("-fx-background-image: url('../images/chessknight.png')");
        pane.getChildren().add(v1);
//        xd.getChildren().add(v1);

        kurwa.setStyle("-fx-background-color: #226322");
        pane.setStyle("-fx-background-color: #dedfa9");
    }

    @FXML
    void clicked() {
        System.out.println("dziala");
    }
}

