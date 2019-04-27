package Controllers;

import Exceptions.PlayerColorException;
import Models.Game;
import Models.Pieces.BlackPawn;
import Models.Pieces.Piece;
import Models.Pieces.WhitePawn;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Pair;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class GameController {
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
    Pane[][] Tile;

    public Node getNodeByRowColumnIndex(final int row, final int column) {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();

        for (Node node : childrens) {
            if (gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }

        return result;
    }


    @FXML
    public void initialize() throws FileNotFoundException, PlayerColorException {
        Image kon = new Image(new FileInputStream("D:\\Projekt\\Chess\\src\\images\\pionekczarny.png"));


//        Image kon = new Image(new FileInputStream("url('../images/poprawione.png')"));


        pane.setOnMouseClicked(mouseEvent -> {
            pane.setStyle("-fx-background-color: red");
        });

//        System.out.println(anchor.getHeight());
//
        Pane xd = new Pane();
        gridPane.add(xd, 1, 1);
        xd.setId("IDD");

//        xd.setStyle("-fx-background-color: green");
        xd.setOnMouseClicked(mouseEvent -> {
            System.out.println(xd.getId());
            clicked();
        });

        ImageView v1 = new ImageView(kon);
        xd.setStyle("-fx-background-image: url('../images/chessknight.png')");
        pane.getChildren().add(v1);
//        xd.getChildren().add(v1);

        kurwa.setStyle("-fx-background-color: #226322");
        pane.setStyle("-fx-background-color: #dedfa9");


        Tile = new Pane[8][8];
        Game game = new Game();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Tile[i][j] = new Pane();

                GridPane.setRowIndex(Tile[i][j], i);
                GridPane.setColumnIndex(Tile[i][j], j);
                gridPane.getChildren().add(Tile[i][j]);

                if (isEven(i, j)) {
                    Tile[i][j].setStyle("-fx-background-color: #eeeed2");
                } else {
                    Tile[i][j].setStyle("-fx-background-color: #769656");
                }


            }
        }

        Image kropka = new Image(new FileInputStream("D:\\Projekt\\Chess\\src\\images\\kropka.png"));

        ImageView kropkaImage = new ImageView(kropka);

        Tile[1][1].getChildren().add(v1);
        Tile[1][1].setOnMouseClicked(mouseEvent-> {
            Tile[1][1].setStyle("-fx-background-color: #fbfb5b");
            game.boardClass.board[1][1] = new BlackPawn();
            List<Pair<Integer, Integer>> moves = game.moveClass.canMove(1,1);
            ImageView[] haha = new ImageView[moves.size()];

            for(int i = 0; i < moves.size(); i++) {
                haha[i] = new ImageView(kropka);
                int row = moves.get(i).getKey();
                int column = moves.get(i).getValue();


                Tile[row][column].getChildren().add(haha[i]);
            }
//            for(int i = 0; i< 8; i++) {
//                for(int j = 0; j < 8; j++) {
//
//                }
//            }
        });
    }

    boolean isEven(int a, int b) {
        return (a + b) % 2 == 0;
    }

    @FXML
    void clicked() {
        System.out.println("dziala");
    }
}

