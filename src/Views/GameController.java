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

import java.util.List;

public class GameController {
    @FXML Label time;
    @FXML GridPane gridPane;
    @FXML AnchorPane anchor;
    @FXML Pane[][] Tile;


    @FXML
    public void initialize() throws PlayerColorException {

        Image BlackPawn = new Image("Images/black_pawn.png");
        ImageView BlackPawnView = new ImageView(BlackPawn);


        //********************************************************

        Image dot = new Image("Images/dot.png");
//        ImageView dotView = new ImageView(dot);

        Tile = new Pane[8][8];
        Game game = new Game();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Tile[i][j] = new Pane();
                gridPane.add(Tile[i][j], j, i);

                if (isEven(i, j)) {
                    Tile[i][j].setStyle("-fx-background-color: #eeeed2");
                } else {
                    Tile[i][j].setStyle("-fx-background-color: #769656");
                }


            }
        }



        Tile[1][1].getChildren().add(BlackPawnView);
        Tile[1][1].setId("black");
        boolean flag = false;
        switch (Tile[1][1].getId()) {
            case "black":
                flag = false;
                break;
            case "white":
                flag = true;
                break;
        }

        Tile[1][1].setOnMouseClicked(mouseEvent-> {
            Tile[1][1].setStyle("-fx-background-color: #fbfb5b");
            game.boardClass.board[1][1] = new BlackPawn();
            List<Pair<Integer, Integer>> moves = game.moveClass.canMove(1,1);
            ImageView[] haha = new ImageView[moves.size()];

            for(int i = 0; i < moves.size(); i++) {
                haha[i] = new ImageView(dot);
                int row = moves.get(i).getKey();
                int column = moves.get(i).getValue();


                Tile[row][column].getChildren().add(haha[i]);
            }

        });

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




    boolean isEven(int a, int b) {
        return (a + b) % 2 == 0;
    }

}

