package Views;


import Exceptions.PlayerColorException;
import Models.Game;

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
         * czyscimy zaznaczona figure i wyswietlamy mozliwe ruchy
         *
         * sprawdzamy czy Mark_MovableTile
         *
         */




        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                final int row = r;
                final int column = c;

                Tile[row][column].setOnMouseClicked(mouseEvent-> {
                    switch (game.boardClass.board[row][column].getClass().getSimpleName()) {
                        case "EmptyTile":
                            System.out.println("emptyTile");
                            break;
                        case "Mark_MovableTile":
                            Pair<Integer, Integer> selectedPiece = getSelectedPiece();

                            int selectedPieceRow = selectedPiece.getKey();
                            int selectedPieceColumn = selectedPiece.getValue();

                            game.move(selectedPieceRow, selectedPieceColumn, row, column);
                            ClearPossibleMoves();
                            PaintBoard();


                            Tile[row][column].getChildren().remove(0);
                            Tile[selectedPieceRow][selectedPieceColumn].getChildren().remove(0);
                            ImageView tmp = new ImageView(BlackPawn);
                            Tile[row][column].getChildren().add(tmp);


                            break;
                        default:
                            PaintBoard();
                            ClearPossibleMoves();

                            System.out.println("figura");


                            Tile[row][column].setStyle("-fx-background-color: #fbfb5b");
//                    game.boardClass.board[1][xd] = new BlackPawn();
                            List<Pair<Integer, Integer>> moves = game.moveClass.CalculateMoves(row, column);
                            ImageView[] xd = new ImageView[moves.size()];

                            for (int i = 0; i < moves.size(); i++) {
                                xd[i] = new ImageView(dot);
                                int rowMove = moves.get(i).getKey();
                                int columnMove = moves.get(i).getValue();


                                Tile[rowMove][columnMove].getChildren().add(xd[i]);
                            }
                            break;
                    }

                });
            }
        }







//        Tile[2][1].setOnMouseClicked(mouseEvent -> {
//            if (game.boardClass.board[2][1].getClass().getSimpleName().equals("Mark_MovableTile")) {
//                ImageView lol = new ImageView(BlackPawn);
//                Tile[2][1].getChildren().remove(0);
//                Tile[2][1].getChildren().add(lol);
//                Tile[1][1].getChildren().remove(0);
//                //jakis
//                //kolejny update
//            }
//        });

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

    public Pair<Integer, Integer> getSelectedPiece() {
        for(int row = 0; row < 8; row++) {
            for(int column = 0; column < 8; column++) {
                if(Tile[row][column].styleProperty().getValue().subSequence(22,29).equals("#fbfb5b")) {
                    Pair<Integer, Integer> pair = new Pair<Integer, Integer>(row, column);
                    return pair;
                }
            }
        }
        return null;
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

