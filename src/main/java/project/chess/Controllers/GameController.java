package project.chess.Controllers;


import javafx.beans.value.ChangeListener;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import project.chess.Exceptions.PlayerColorException;
import project.chess.Models.*;

import project.chess.Models.Pieces.*;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

import javax.swing.plaf.UIResource;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Time;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameController {
    @FXML
    private GridPane gridPane;
    @FXML
    private Pane[][] Tile;
    @FXML
    private Button promoteToRookButton;
    @FXML
    private Button promoteToKnightButton;
    @FXML
    private Button promoteToBishopButton;
    @FXML
    private Button promoteToQueenButton;
    @FXML
    private Button resignButton;
    @FXML
    private Button drawButton;
    @FXML
    private Button backButton;
    @FXML
    private TextArea moveHistory;

    @FXML
    public Text whiteClockGUI;
    public Text blackClockGUI;

    private Game game;

    private Image blackRookImage;
    private Image blackKnightImage;
    private Image blackBishopImage;
    private Image blackQueenImage;

    private Image whiteRook;
    private Image whiteKnight;
    private Image whiteBishop;
    private Image whiteQueen;

    private Image dot;
    private Image resignImage;
    private Image drawImage;

    private StringBuilder history;

    private int playerTimeFromOptions;
    private int timePerRound;

    private void showImageResignButton() {
        resignButton.setGraphic(new ImageView(resignImage));
    }

    private void showImageDrawButton() {
        drawButton.setGraphic(new ImageView(drawImage));
    }

    private int n = 0;
    private int numberOfRounds = 1;


    private void drawHistory(String string, boolean attack, boolean end) {

        if(end) {
            history.append('\n');
            history.append('\n');
            //inna czcionka??

            history.append(string);
            moveHistory.setText(history.toString());
        } else {
            if (n == 2) {
                history.append(System.getProperty("line.separator"));
                numberOfRounds++;
                n = 0;
                history.append(numberOfRounds).append(".     ");
            } else if (n == 1) {
                history.append("     ");
            }

            if(attack) {
                history.append("x");
            }

            history.append(string);
            moveHistory.setText(history.toString());
            n++;

//        listener is probably bugged and set text doesnt trigger it
            moveHistory.appendText("");
        }

    }

    void init(Options options) throws PlayerColorException, MalformedURLException {
        addListenerForMatchHistory();


        System.out.println(options.getGameMode());
        System.out.println(options.getVersusMode());
        System.out.println(options.getFirstPlayerColor());

//        System.out.println(options.getGameMode().charAt(3));

        switch (options.getGameMode().charAt(2)) {
            case 'i':
                playerTimeFromOptions = 3 * 60;
                timePerRound = 2;
                break;
            case 'l':
                playerTimeFromOptions = 60;
                timePerRound = 0;
                break;
            case 'p':
                playerTimeFromOptions = 10 * 60;
                timePerRound = 0;
                break;
            case 'a':
//                playerTimeFromOptions = 15 * 60;
                playerTimeFromOptions = 11;
                timePerRound = 15;
                break;
        }

        int minutes = playerTimeFromOptions/60;
        int seconds = playerTimeFromOptions - ((playerTimeFromOptions / 60)  * 60);
//        int seconds = 10;
        whiteClockGUI.setText((minutes + " : " + seconds + "0"));
        blackClockGUI.setText((minutes + " : " + seconds + "0"));




        promotionFlagBlockingMove = false;
        hidePromotionButtons();
        backButton.setVisible(false);
        moveHistory.setEditable(false);
        history = new StringBuilder();
        history.append(numberOfRounds).append(".     ");


        setImageForPieces();
        setImageForSupportComponents();
        showImageDrawButton();
        showImageResignButton();





        Tile = new Pane[8][8];
        game = new Game(new CustomClockAbstract() {
            @Override
            public void setTime() {
                time = playerTimeFromOptions;
            }

            @Override
            protected void onTimeStep() {
                int minutes = time/60;
                int seconds = time - ((time / 60)  * 60);
                if(seconds < 10) {
                    whiteClockGUI.setText((minutes + " : 0" + seconds));
                } else {
                    whiteClockGUI.setText((minutes + " : " + seconds));
                }
            }

            @Override
            protected void onTimeEnd() {
                whiteClockGUI.setText("0 : 00");
                game.isOver = true;
                drawHistory("BLACK WON NO TIME ", false, true);
            }

            @Override
            public void updateTime() {
                int minutes = time/60;
                int seconds = time - ((time / 60)  * 60);
                if(seconds < 10) {
                    whiteClockGUI.setText((minutes + " : 0" + seconds));
                } else {
                    whiteClockGUI.setText((minutes + " : " + seconds));
                }

            }
        }, new CustomClockAbstract() {
            @Override
            public void setTime() {
                time = playerTimeFromOptions;
            }

            @Override
            protected void onTimeStep() {
//                System.out.println("black player time: " + time);
                int minutes = time/60;
                int seconds = time - ((time / 60)  * 60);
                if(seconds < 10) {
                    blackClockGUI.setText((minutes + " : 0" + seconds));
                } else {
                    blackClockGUI.setText((minutes + " : " + seconds));
                }
            }

            @Override
            protected void onTimeEnd() {

            }

            @Override
            public void updateTime() {
                int minutes = time/60;
                int seconds = time - ((time / 60)  * 60);
                if(seconds < 10) {
                    blackClockGUI.setText((minutes + " : 0" + seconds));
                } else {
                    blackClockGUI.setText((minutes + " : " + seconds));
                }

            }
        });
        InitializeTiles();
        PaintBoard();
        EmulateBoard();


        setOnMouseClicked();
    }


    private void addListenerForMatchHistory() {
        moveHistory.textProperty().addListener((ChangeListener<Object>) (observable, oldValue, newValue) ->
                moveHistory.setScrollTop(Double.MAX_VALUE));
    }

    private void setOnMouseClicked() {
//        Computer computer = new Computer(game, false);
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                final int row = r;
                final int column = c;

                Tile[row][column].setOnMouseClicked(mouseEvent -> {
                    switch (game.boardClass.boardOfPossibleMoves[row][column].getClass().getSimpleName()) {
                        case "EmptyTile":
                            System.out.println("empty tile");
                            break;
                        case "Mark_MovableTile":

                            if(game.getCurrentPlayer()) {
                                game.whiteClock.setTimePerRound(timePerRound);
                                game.whiteClock.updateTime();
                            } else {
                                game.blackClock.setTimePerRound(timePerRound);
                                game.blackClock.updateTime();
                            }



                            Pair<Integer, Integer> selectedPiece = getSelectedPiece();

                            assert selectedPiece != null;
                            int selectedPieceRow = selectedPiece.getKey();
                            int selectedPieceColumn = selectedPiece.getValue();

                            boolean attack = false;
                            if (Tile[row][column].getChildren().size() > 1) {
                                attack = true;
                            }

                            try {
                                game.move(selectedPieceRow, selectedPieceColumn, row, column);
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }

                            ClearPossibleMoves();
                            PaintBoard();
                            EmulateBoard();


                            if (game.boardClass.getPiece(row, column) instanceof WhiteKing
                                    || game.boardClass.getPiece(row, column) instanceof BlackKing) {
                                if (column - selectedPieceColumn == 2) {
                                    drawHistory("O - O", false, false);
                                } else if (column - selectedPieceColumn == -2) {
                                    drawHistory("O - O - O", false, false);
                                } else {
                                    updateMatchHistory(row, column, attack);
                                }
                            } else {
                                updateMatchHistory(row, column, attack);
                            }


                            if (checkGameOver()) {
                                //TODO
                                //historia ruchow
                                hideRestButtons();
                                backButton.setVisible(true);
                            }


//                            try {
//                                computer.makeAMove();
//                            } catch (MalformedURLException e) {
//                                e.printStackTrace();
//                            }
//                            EmulateBoard();


                            break;
                        default:
                            if (game.getCurrentPlayer() == game.boardClass.board[row][column].getPlayer()
                                    && !game.isOver) {
                                if (!promotionFlagBlockingMove) {
                                    PaintBoard();
                                    ClearPossibleMoves();

                                    EmulateBoard();


                                    Tile[row][column].setStyle("-fx-background-color: #fbfb5b");
                                    List<Pair<Integer, Integer>> dummy = new LinkedList<>();
                                    List<Pair<Integer, Integer>> moves = game.moveClass.CalculateMoves(row, column, "", dummy);


                                    moves = filterMoves(row, column, moves);

                                    ImageView[] dotMoves = new ImageView[moves.size()];
                                    for (int i = 0; i < moves.size(); i++) {
                                        dotMoves[i] = new ImageView(dot);
                                        int rowMove = moves.get(i).getKey();
                                        int columnMove = moves.get(i).getValue();

                                        Tile[rowMove][columnMove].getChildren().add(dotMoves[i]);
                                    }
                                }
                            }

                            break;
                    }

                });
            }
        }
    }

    private List<Pair<Integer, Integer>> filterMoves(int row, int column, List<Pair<Integer, Integer>> moves) {
        return mainFiler(row, column, moves, game);
    }

    public static List<Pair<Integer, Integer>> mainFiler(int row, int column, List<Pair<Integer, Integer>> moves, Game game) {
        Filter filter = new Filter(game, moves, row, column);
        moves = filter.filterMoves();

        game.boardClass.clearPossibleMoves();

        for (Pair<Integer, Integer> move : moves) {
            int rowMove = move.getKey();
            int columnMove = move.getValue();

            game.boardClass.boardOfPossibleMoves[rowMove][columnMove] = new Mark_MovableTile();
        }
        return moves;
    }

    private void updateMatchHistory(int row, int column, boolean attack) {
        Piece piece = game.boardClass.getPiece(row, column);
        char unicode = piece.getUnicode();

        String coordinates = String.valueOf(unicode) +
                replaceToChessNotation(row, column);

        drawHistory(coordinates, attack, false);
    }

    private void setImageForSupportComponents() {
        dot = new Image("Images/dot.png");
        resignImage = new Image("Images/resign.png");
        drawImage = new Image("Images/draw.png");
    }

    private void setImageForPieces() {
        blackRookImage = new Image("Images/black_rook.png");
        blackKnightImage = new Image("Images/black_knight.png");
        blackBishopImage = new Image("Images/black_bishop.png");
        blackQueenImage = new Image("Images/black_queen.png");

        whiteRook = new Image("Images/white_rook.png");
        whiteKnight = new Image("Images/white_knight.png");
        whiteBishop = new Image("Images/white_bishop.png");
        whiteQueen = new Image("Images/white_queen.png");
    }

    private boolean promotionFlagBlockingMove;

    @FXML
    public void initialize() {
    }


    private Pair<Integer, Integer> getSelectedPiece() {
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                if (Tile[row][column].styleProperty().getValueSafe().subSequence(22, 29).equals("#fbfb5b")) {
                    return new Pair<>(row, column);
                }
            }
        }
        return null;
    }


    /**
     * resign game
     */

    @FXML
    private void resign() {
        game.isOver = true;
        EmulateBoard();
        PaintBoard();
        hideRestButtons();
        backButton.setVisible(true);
        //wypisanie w historii ruchow
    }


    private boolean checkGameOver() {
        return game.isOver;
    }

    public void changeSceneToSettings(Event event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/Views/fxml/Settings.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();


        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        window.setX(primaryScreenBounds.getMinX() + primaryScreenBounds.getWidth() - 1050);
        window.setY(primaryScreenBounds.getMinY() + primaryScreenBounds.getHeight() - 650);
        window.setWidth(614.4);
        window.setHeight(437.6);

        window.setScene(tableViewScene);

        window.show();
    }


    private boolean isEven(int a, int b) {
        return (a + b) % 2 == 0;
    }


    private void InitializeTiles() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Tile[i][j] = new Pane();
                gridPane.add(Tile[i][j], j, i);
            }
        }
    }


    private void PaintBoard() {
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

    private void ClearPossibleMoves() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (game.boardClass.boardOfPossibleMoves[i][j].getClass().getSimpleName().equals("Mark_MovableTile")) {
                    for (int s = 0; s < Tile[i][j].getChildren().size(); s++) {
                        Tile[i][j].getChildren().remove(s);
                    }

                }
            }
        }
        game.boardClass.clearPossibleMoves();
    }


    private void EmulateBoard() {
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                ImageView tmp = null;

                removeImagesFromTile(Tile[row][column]);
                tmp = emulateTileImage(row, column, tmp);

                if (tmp != null) {
                    Tile[row][column].getChildren().add(tmp);
                }

            }
        }

        checkPromotionBox();
    }

    private ImageView emulateTileImage(int row, int column, ImageView tmp) {
        if (!(game.boardClass.getPiece(row, column) instanceof EmptyTile)) {
            tmp = game.boardClass.board[row][column].getImageView();
        }
        return tmp;
    }

    private void removeImagesFromTile(Pane pane) {
        while (!pane.getChildren().isEmpty()) {
            pane.getChildren().remove(0);
        }
    }

    private void checkPromotionBox() {
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                switch (game.boardClass.getPiece(row, column).getClass().getSimpleName()) {
                    case "WhitePawn":
                        if (((WhitePawn) game.boardClass.getPiece(row, column)).getPromotion()) {
                            showPromotionButtons(true);
                        }
                        break;
                    case "BlackPawn":
                        if (((BlackPawn) game.boardClass.getPiece(row, column)).getPromotion()) {
                            showPromotionButtons(false);
                        }
                        break;
                }
            }
        }
    }




    public void promoteToQueen() {
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                switch (game.boardClass.getPiece(row, column).getClass().getSimpleName()) {
                    case "WhitePawn":
                        if (((WhitePawn) game.boardClass.getPiece(row, column)).getPromotion()) {
                            boolean player = game.boardClass.getPiece(row, column).getPlayer();
                            game.boardClass.board[row][column] = new Queen(player);
                        }
                        break;
                    case "BlackPawn":
                        if (((BlackPawn) game.boardClass.getPiece(row, column)).getPromotion()) {
                            boolean player = game.boardClass.getPiece(row, column).getPlayer();
                            game.boardClass.board[row][column] = new Queen(player);
                        }
                        break;
                }
            }
        }
        hidePromotionButtons();
        EmulateBoard();
    }


    public void promoteToRook() {
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                switch (game.boardClass.getPiece(row, column).getClass().getSimpleName()) {
                    case "WhitePawn":
                        if (((WhitePawn) game.boardClass.getPiece(row, column)).getPromotion()) {
                            boolean player = game.boardClass.getPiece(row, column).getPlayer();
                            game.boardClass.board[row][column] = new Rook(player);
                        }
                        break;
                    case "BlackPawn":
                        if (((BlackPawn) game.boardClass.getPiece(row, column)).getPromotion()) {
                            boolean player = game.boardClass.getPiece(row, column).getPlayer();
                            game.boardClass.board[row][column] = new Rook(player);
                        }
                        break;
                }
            }
        }
        hidePromotionButtons();
        EmulateBoard();
    }


    public void promoteToBishop() {
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                switch (game.boardClass.getPiece(row, column).getClass().getSimpleName()) {
                    case "WhitePawn":
                        if (((WhitePawn) game.boardClass.getPiece(row, column)).getPromotion()) {
                            boolean player = game.boardClass.getPiece(row, column).getPlayer();
                            game.boardClass.board[row][column] = new Bishop(player);
                        }
                        break;
                    case "BlackPawn":
                        if (((BlackPawn) game.boardClass.getPiece(row, column)).getPromotion()) {
                            boolean player = game.boardClass.getPiece(row, column).getPlayer();
                            game.boardClass.board[row][column] = new Bishop(player);
                        }
                        break;
                }
            }
        }
        hidePromotionButtons();
        EmulateBoard();
    }


    public void promoteToKnight() throws PlayerColorException {
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                switch (game.boardClass.getPiece(row, column).getClass().getSimpleName()) {
                    case "WhitePawn":
                        if (((WhitePawn) game.boardClass.getPiece(row, column)).getPromotion()) {
                            boolean player = game.boardClass.getPiece(row, column).getPlayer();
                            game.boardClass.board[row][column] = new Knight(player);
                        }
                        break;
                    case "BlackPawn":
                        if (((BlackPawn) game.boardClass.getPiece(row, column)).getPromotion()) {
                            boolean player = game.boardClass.getPiece(row, column).getPlayer();
                            game.boardClass.board[row][column] = new Knight(player);
                        }
                        break;
                }
            }
        }
        hidePromotionButtons();
        EmulateBoard();
    }


    private void hidePromotionButtons() {
        promoteToBishopButton.setVisible(false);
        promoteToRookButton.setVisible(false);
        promoteToKnightButton.setVisible(false);
        promoteToQueenButton.setVisible(false);

        promotionFlagBlockingMove = false;
    }

    private void showPromotionButtons(boolean player) {
        promotionFlagBlockingMove = true;
        if (player) {
            promoteToQueenButton.setGraphic(new ImageView(whiteQueen));
            promoteToKnightButton.setGraphic(new ImageView(whiteKnight));
            promoteToBishopButton.setGraphic(new ImageView(whiteBishop));
            promoteToRookButton.setGraphic(new ImageView(whiteRook));
        } else {
            promoteToQueenButton.setGraphic(new ImageView(blackQueenImage));
            promoteToKnightButton.setGraphic(new ImageView(blackKnightImage));
            promoteToBishopButton.setGraphic(new ImageView(blackBishopImage));
            promoteToRookButton.setGraphic(new ImageView(blackRookImage));
        }


        promoteToBishopButton.setVisible(true);
        promoteToRookButton.setVisible(true);
        promoteToKnightButton.setVisible(true);
        promoteToQueenButton.setVisible(true);
    }

    private void hideRestButtons() {
        drawButton.setVisible(false);
        resignButton.setVisible(false);
    }

    private StringBuilder replaceToChessNotation(int row, int column) {
        char c = 'a';
        c += column;
        row += 1;

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(c);
        stringBuilder.append(row);

        return stringBuilder;
    }

}