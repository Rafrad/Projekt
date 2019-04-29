package Views;


import Exceptions.PlayerColorException;
import Models.Game;
import Models.Pieces.BlackPawn;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

import java.awt.event.MouseEvent;
import java.util.List;

public class GameController {
    @FXML
    Label time;
    @FXML
    GridPane gridPane;
    @FXML
    AnchorPane anchor;
    @FXML
    Pane[][] Tile;
    Game game;
    ImageView[] BlackPawns;

    @FXML
    public void initialize() throws PlayerColorException {

        /*
         * Set image for every piece
         * and for dot
         */

        Image BlackPawn = new Image("Images/black_pawn.png");
        ImageView BlackPawnView = new ImageView(BlackPawn);
        BlackPawns = new ImageView[8];

        for (int i = 0; i < BlackPawns.length; i++) {
            BlackPawns[i] = new ImageView(BlackPawn);
        }



        /*
         * dot
         */

        Image dot = new Image("Images/dot.png");
        ImageView dotView = new ImageView(dot);

        Tile = new Pane[8][8];
        game = new Game();
        InitializeTiles();
        PaintBoard();

        SetPiecesOnBoard();


        /*
         * sprawdzamy czy to puste pole, czy figura
         * jezeli puste pole - nic nie robimy, jezeli figura
         * czyscimy zaznaczona figure i mozliwe ruchy
         * 
         */

        for (int k = 0; k < 8; k++) {
            final int xd = k;
            Tile[1][k].setOnMouseClicked(mouseEvent -> {
                PaintBoard();
                ClearPossibleMoves();

                //                Tile[1][0].getChildren().
//                    if (game.boardClass.board[2][0].getClass().getSimpleName().equals("Mark_MovableTile")) {
//                        ImageView lol = new ImageView(BlackPawn);
//                        Tile[2][1].getChildren().remove(0);
//                        Tile[2][1].getChildren().add(lol);
//                        Tile[1][1].getChildren().remove(0);
//                    }


                if (!game.boardClass.board[1][xd].getClass().getSimpleName().equals("EmptyTile")
//                            && game.getCurrentPlayer() == game.boardClass.board[1][1].getPlayer()
                ) {

                    Tile[1][xd].setStyle("-fx-background-color: #fbfb5b");
//                    game.boardClass.board[1][xd] = new BlackPawn();
                    System.out.println(xd);
                    List<Pair<Integer, Integer>> moves = game.moveClass.canMove(1, xd);
                    ImageView[] haha = new ImageView[moves.size()];

                    for (int i = 0; i < moves.size(); i++) {
                        haha[i] = new ImageView(dot);
                        int row = moves.get(i).getKey();
                        int column = moves.get(i).getValue();


                        Tile[row][column].getChildren().add(haha[i]);
                    }

                }
            });
        }

        Tile[2][1].setOnMouseClicked(mouseEvent -> {
            if (game.boardClass.board[2][1].getClass().getSimpleName().equals("Mark_MovableTile")) {
                ImageView lol = new ImageView(BlackPawn);
                Tile[2][1].getChildren().remove(0);
                Tile[2][1].getChildren().add(lol);
                Tile[1][1].getChildren().remove(0);
                //jakis
                //kolejny update
            }
        });

//new EventHandler

        //gowno zegarek
//        long endTime = 10;
////        Label timeLabel;
//        Label timeLabel = new Label();
//        DateFormat timeFormat = new SimpleDateFormat( "HH:mm:ss" );
//        final Timeline timeline = new Timeline(
//                new KeyFrame(
//                        Duration.millis( 500 ),
//                        event -> {
//                            final long diff = endTime - System.currentTimeMillis();
//                            if ( diff < 0 ) {
//                                //  timeLabel.setText( "00:00:00" );
//                                timeLabel.setText( timeFormat.format( 0 ) );
//                            } else {
//                                timeLabel.setText( timeFormat.format( diff ) );
//                            }
//                        }
//                )
//        );
//        timeline.setCycleCount( Animation.INDEFINITE );
//        timeline.play();


        //gowno zegarek
//
//        int lol = 1;
//        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
//            int second = 00;
//            int minute = 10;
////            int second = LocalDateTime.now().getSecond();
////            int minute = LocalDateTime.now().getMinute();
////            int hour = LocalDateTime.now().getHour();
////            time.setText(hour + ":" + (minute) + ":" + second);
//            time.setText(minute + ":" + second);
//        }),
//                new KeyFrame(Duration.seconds(1))
//        );
//        clock.setCycleCount(Animation.INDEFINITE);
//        clock.play();


    }

    public void moves(MouseEvent event) {

        System.out.println("kuppa");
    }


    public void UpdateBoard(int row, int column, int rowDestination, int columnDestination) {
//        boardClass.board[rowDestination][columnDestination] = boardClass.board[row][column];
//        boardClass.board[row][column] = new EmptyTile();
    }

    boolean isEven(int a, int b) {
        return (a + b) % 2 == 0;
    }


    public void InitializeTiles() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Tile[i][j] = new Pane();
                gridPane.add(Tile[i][j], j, i);
            }
        }
    }

    public void PaintBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (isEven(i, j)) {
                    Tile[i][j].setStyle("-fx-background-color: #eeeed2");
                } else {
                    Tile[i][j].setStyle("-fx-background-color: #769656");
                }
            }
        }
    }

    public void ClearPossibleMoves() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (game.boardClass.boardOfPossibleMoves[i][j].getClass().getSimpleName().equals("Mark_MovableTile")) {
                    Tile[i][j].getChildren().remove(0);
                }
            }
        }
        game.ClearBoardWithPossibleMoves();
    }

    public void SetPiecesOnBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                switch (i) {
                    case 1:
                        Tile[i][j].getChildren().add(BlackPawns[j]);
                }
            }
        }
    }

}

