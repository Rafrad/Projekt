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

import java.io.IOException;

import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.List;

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
    private ImageView resignButton;
    @FXML
    private ImageView drawButton;
    @FXML
    private Button backButton;
    @FXML
    private TextArea moveHistory;

    @FXML
    public Text whiteClockGUI;
    @FXML
    public Text blackClockGUI;
    @FXML
    private Text firstPlayer;
    @FXML
    private Text secondPlayer;


    public Game game;//zmienione z private przez Jakuba Filipa, potrzebne do napisania testu


    @FXML
    private Button yesButton;
    @FXML
    private Button noButton;
    @FXML
    private Text drawMessage;



    private Computer computer;


    private Image blackRookImage;
    private Image blackKnightImage;
    private Image blackBishopImage;
    private Image blackQueenImage;
    private Image blackPawnImage;
    private Image blackKingImage;

    private Image whiteRookImage;
    private Image whiteKnightImage;
    private Image whiteBishopImage;
    private Image whiteQueenImage;
    private Image whitePawnImage;
    private Image whiteKingImage;

    private Image dot;

    private StringBuilder historyString;

    private int playerTimeFromOptions;
    public int timePerRound;

    private boolean CP;


    /**
     * This method inits GameController class.
     *
     * @param options they are set in SettingsController calss
     * @throws PlayerColorException  thrown when piece does not know where belongs to (white or black)
     */

    void init(Options options) throws PlayerColorException, MalformedURLException {
        drawMessage.setVisible(false);
        yesButton.setVisible(false);
        noButton.setVisible(false);
        addListenerForMatchHistory();

        System.out.println(options.getGameMode());
        System.out.println(options.getVersusMode());
        System.out.println(options.getFirstPlayerColor());

        CP = options.getVersusMode().equals("Player VS Computer");
        setPlayerText(options);

        setTime(options);

        int minutes = playerTimeFromOptions / 60;
        int seconds = playerTimeFromOptions - ((playerTimeFromOptions / 60) * 60);

        whiteClockGUI.setText((minutes + " : " + seconds + "0"));
        blackClockGUI.setText((minutes + " : " + seconds + "0"));


        promotionFlagBlockingMove = false;
        hidePromotionButtons();
        backButton.setVisible(false);
        moveHistory.setEditable(false);
        historyString = new StringBuilder();
        historyString.append(numberOfRounds).append(".     ");


        setImageForPieces();
        setImageForSupportComponents();

        Tile = new Pane[8][8];

        setCustomClock();
        InitializeTiles();
        PaintBoard();
        EmulateBoard();

        computer = new Computer(game, !options.getFirstPlayerColor().equals("white"), this);

        if(options.getFirstPlayerColor().equals("black") && CP) {
            computer.move();
            EmulateBoard();
        }

        setOnMouseClicked();
    }

    private void setPlayerText(Options options) {
        if (options.getFirstPlayerColor().equals("white")) {
            firstPlayer.setText("Player 1");
            if (options.getVersusMode().equals("Player VS Player")) {
                secondPlayer.setText("Player 2");
            } else {
                secondPlayer.setText("Computer");
            }
        } else {
            secondPlayer.setText("Player 1");
            if (options.getVersusMode().equals("Player VS Player")) {
                firstPlayer.setText("Player 2");
            } else {
                firstPlayer.setText("Computer");
            }
        }
    }


    /**
     * This method sets clocks how they behave.
     * What clock does when time ends and what does every second.
     * It also has helpful updateTime() method and works the same like onTimeStep() method
     * but onTimeStep() cannot be used outside.
     * updateTime() method should be deleted in future
     *
     * @throws PlayerColorException thrown when piece does not know where belongs to (white or black)
     */

    private void setCustomClock() throws PlayerColorException {
        game = new Game(new CustomClockAbstract() {
            @Override
            public void setTime() {
                time = playerTimeFromOptions;
            }

            @Override
            protected void onTimeStep() {
                int minutes = time / 60;
                int tmp = time / 60;
                int seconds = time - (tmp * 60);
                if (seconds < 10) {
                    whiteClockGUI.setText((minutes + " : 0" + seconds));
                } else {
                    whiteClockGUI.setText((minutes + " : " + seconds));
                }
            }

            @Override
            protected void onTimeEnd() {
                whiteClockGUI.setText("0 : 00");
                endGame();
                drawHistory("BLACK WON. TIMEOUT!!", true);
            }

            @Override
            public void updateTime() {
                int tmp = time;
                tmp /= 60;
                int minutes = time / 60;
                int seconds = time - (tmp * 60);
                if (seconds < 10) {
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
                int minutes = time / 60;
                int seconds = time - ((time / 60) * 60);
                if (seconds < 10) {
                    blackClockGUI.setText((minutes + " : 0" + seconds));
                } else {
                    blackClockGUI.setText((minutes + " : " + seconds));
                }
            }

            @Override
            protected void onTimeEnd() {
                blackClockGUI.setText("0 : 00");
                endGame();
                drawHistory("WHITE WON. TIMEOUT!!", true);
            }

            @Override
            public void updateTime() {
                int minutes = time / 60;
                int seconds = time - ((time / 60) * 60);
                if (seconds < 10) {
                    blackClockGUI.setText((minutes + " : 0" + seconds));
                } else {
                    blackClockGUI.setText((minutes + " : " + seconds));
                }

            }
        });
    }

    /**
     * Ends game (only in GUI) and stops clock.
     * Sets isOver variable in game to true.
     *
     * @see Game#isOver
     */

    private void endGame() {
        if (game.getCurrentPlayer()) {
            game.whiteClock.stop();
        } else {
            game.blackClock.stop();
        }

        game.isOver = true;

        hideRestButtons();
        backButton.setVisible(true);
    }

    /**
     * Sets time given by options.
     *
     * @param options should be set in SettingsController
     */

    private void setTime(Options options) {
        switch (options.getGameMode().charAt(2)) {
            case 'i':
                playerTimeFromOptions = 3 * 60;
                timePerRound = 2 + 1;
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
                playerTimeFromOptions = 15 * 60;
                timePerRound = 15 + 1;
                break;
        }
    }


    //these variables are used ONLY in drawHistory() method,
    private int whiteOrBlackRound = 0;
    private int numberOfRounds = 1;


    /**
     * This method draws history to the right of the screen.
     *
     * @param string text to add
     * @param end    checks if it is game over
     */

    public void drawHistory(String string, boolean end) {
        if (end) {
            historyString.append('\n');
            historyString.append('\n');

            historyString.append(string);
            moveHistory.setText(historyString.toString());
        } else {
            if (whiteOrBlackRound == 2) {
                historyString.append(System.getProperty("line.separator"));
                numberOfRounds++;
                whiteOrBlackRound = 0;
                historyString.append(numberOfRounds).append(".     ");
            } else if (whiteOrBlackRound == 1) {
                historyString.append("     ");
            }

            historyString.append(string);
            moveHistory.setText(historyString.toString());
            whiteOrBlackRound++;

//        listener is probably bugged and set text doesnt trigger it
            moveHistory.appendText("");
        }

    }


    /**
     * This method checks if it need to scroll down automatically in match history text area.
     */

    private void addListenerForMatchHistory() {
        moveHistory.textProperty().addListener((ChangeListener<Object>) (observable, oldValue, newValue) ->
                moveHistory.setScrollTop(Double.MAX_VALUE));
    }

    /**
     * One of the main methods in controller.
     * Defines what happens when we click on the empty tile, movable tile or piece.
     * <p>
     * If we click on the empty tile, nothing happens. Only "empty tile" string is written in console.
     */

    private void setOnMouseClicked() {
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

                            Pair<Integer, Integer> selectedPiece = getSelectedPiece();
                            if (selectedPiece == null) break;
                            if (game.getCurrentPlayer()) {
                                game.whiteClock.setTimePerRound(timePerRound);
                                game.whiteClock.updateTime();
                            } else {
                                game.blackClock.setTimePerRound(timePerRound);
                                game.blackClock.updateTime();
                            }

                            int selectedPieceRow = selectedPiece.getKey();
                            int selectedPieceColumn = selectedPiece.getValue();


                            boolean attack = false;
                            if (Tile[row][column].getChildren().size() > 1) {
                                attack = true;
                            }

                            game.move(selectedPieceRow, selectedPieceColumn, row, column);

                            ClearPossibleMoves();
                            PaintBoard();
                            EmulateBoard();

                            isGameOver();


                            if (game.boardClass.getPiece(row, column) instanceof WhiteKing
                                    || game.boardClass.getPiece(row, column) instanceof BlackKing) {
                                if (column - selectedPieceColumn == 2) {
                                    drawHistory("O - O", false);
                                } else if (column - selectedPieceColumn == -2) {
                                    drawHistory("O - O - O", false);
                                } else {
                                    updateMatchHistory(row, column, "");
                                }
                            } else if (checkGameOver()) {
                                endGame();
                                updateMatchHistory(row, column, "++");
                            } else {
                                if (attack) {
                                    updateMatchHistory(row, column, "");
                                } else {
                                    updateMatchHistory(row, column, "");
                                }
                            }


                            if(CP) {
                                try {
                                    computer.move();
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }

                            }

                            EmulateBoard();

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
                                    List<Pair<Integer, Integer>> moves = game.moveClass.CalculateMoves(row, column, "movable board", dummy);


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

    /**
     * This method filters moves which trigger check
     *
     * @param row    piece row
     * @param column piece column
     * @param moves  piece movement, calculated with calculateMoves method in Move class
     * @return final movement
     * @see Move#CalculateMoves(int, int, java.lang.String, java.util.List)
     */

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

    public void updateMatchHistory(int row, int column, String somethingMore) {
        Piece piece = game.boardClass.getPiece(row, column);
        char unicode = piece.getUnicode();

        String coordinates = somethingMore +
                unicode +
                replaceToChessNotation(row, column);

        drawHistory(coordinates, false);
    }

    private void setImageForSupportComponents() {
        dot = new Image("Images/dot.png");
    }

    private void setImageForPieces() {
        blackRookImage = new Image("Images/black_rook.png");
        blackKnightImage = new Image("Images/black_knight.png");
        blackBishopImage = new Image("Images/black_bishop.png");
        blackQueenImage = new Image("Images/black_queen.png");
        blackKingImage = new Image("Images/black_king.png");
        blackPawnImage = new Image("Images/black_pawn.png");

        whiteRookImage = new Image("Images/white_rook.png");
        whiteKnightImage = new Image("Images/white_knight.png");
        whiteBishopImage = new Image("Images/white_bishop.png");
        whiteQueenImage = new Image("Images/white_queen.png");
        whiteKingImage = new Image("Images/white_king.png");
        whitePawnImage = new Image("Images/white_pawn.png");
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
        String player;
        if (game.getCurrentPlayer()) {
            player = "black";
        } else {
            player = "white";
        }

        drawHistory("         GAME OVER" + '\n'
                + player + " wins by RESIGN!", true);
        endGame();
    }


    /**
     * draws game
     */

    @FXML
    private void draw() {
        noButton.setVisible(true);
        yesButton.setVisible(true);
        drawMessage.setVisible(true);
    }

    @FXML
    private void clickYes() {
        noButton.setVisible(false);
        yesButton.setVisible(false);
        drawMessage.setVisible(false);

        drawHistory("         GAME OVER" + '\n'
                + "             DRAW!", true);
        endGame();
    }

    @FXML
    private void clickNo() {
        noButton.setVisible(false);
        drawMessage.setVisible(false);
        yesButton.setVisible(false);
    }


    private boolean checkGameOver() {
        return game.isOver;
    }

    /**
     * This method changes game scene to settings scene, by clicking back button.
     * It includes changing width, height and set scene to center of the screen.
     *
     * @param event action
     * @throws IOException thrown when Settings.fxml view cannot be find by loader
     */

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


    /**
     * Emulates board with board in Board class.
     *
     * @see project.chess.Models.Board
     */

    private void EmulateBoard() {
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                ImageView tmp = null;
                removeImagesFromTile(Tile[row][column]);
                switch (game.boardClass.board[row][column].getClass().getSimpleName()) {
                    case "BlackPawn":
                        tmp = new ImageView(blackPawnImage);
                        break;
                    case "WhitePawn":
                        tmp = new ImageView(whitePawnImage);
                        break;
                    case "Rook":
                        if (game.boardClass.board[row][column].getPlayer()) {
                            tmp = new ImageView(whiteRookImage);
                        } else {
                            tmp = new ImageView(blackRookImage);
                        }
                        break;
                    case "Knight":
                        if (game.boardClass.board[row][column].getPlayer()) {
                            tmp = new ImageView(whiteKnightImage);
                        } else {
                            tmp = new ImageView(blackKnightImage);
                        }
                        break;
                    case "Bishop":
                        if (game.boardClass.board[row][column].getPlayer()) {
                            tmp = new ImageView(whiteBishopImage);
                        } else {
                            tmp = new ImageView(blackBishopImage);
                        }
                        break;
                    case "Queen":
                        if (game.boardClass.board[row][column].getPlayer()) {
                            tmp = new ImageView(whiteQueenImage);
                        } else {
                            tmp = new ImageView(blackQueenImage);
                        }
                        break;
                    case "BlackKing":
                        tmp = new ImageView(blackKingImage);
                        break;
                    case "WhiteKing":
                        tmp = new ImageView(whiteKingImage);
                        break;
                }


                if (tmp != null) {
                    Tile[row][column].getChildren().add(tmp);
                }

            }
        }

        checkPromotionBox();
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
            promoteToQueenButton.setGraphic(new ImageView(whiteQueenImage));
            promoteToKnightButton.setGraphic(new ImageView(whiteKnightImage));
            promoteToBishopButton.setGraphic(new ImageView(whiteBishopImage));
            promoteToRookButton.setGraphic(new ImageView(whiteRookImage));
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


    private void isGameOver() {

        new Thread(() -> {
            int numberOfWhiteMoves = 0;
            int numberOfBlackMoves = 0;

            for (int row = 0; row < 8; row++) {
                for (int column = 0; column < 8; column++) {
                    if (game.boardClass.board[row][column].getPlayer()) {
                        numberOfWhiteMoves = game.getNumberOfMoves(numberOfWhiteMoves, row, column);
                    } else if (!game.boardClass.board[row][column].getPlayer()) {
                        numberOfBlackMoves = game.getNumberOfMoves(numberOfBlackMoves, row, column);
                    }

                }
            }



            if (numberOfBlackMoves == 0 || numberOfWhiteMoves == 0) {
                //stalemate
                //...
                //game over
                endGame();
                game.whiteClock.stop();
                game.blackClock.stop();
                String player = "white";
                if (game.getCurrentPlayer()) {
                    player = "black";
                }

                System.out.println("white attack: ");
                game.boardClass.printChosenBoard(game.boardClass.whitePlayerAttackBoard);
                System.out.println();
                System.out.println("black attack: ");
                game.boardClass.printChosenBoard(game.boardClass.blackPlayerAttackBoard);
                System.out.println();

                int whiteRow = 0;
                int whiteColumn = 0;
                int blackRow = 0;
                int blackColumn = 0;

                for(int row = 0; row < 8; row++) {
                    for(int column = 0; column < 8; column++) {
                        if(game.boardClass.board[row][column] instanceof WhiteKing) {
                            whiteRow = row;
                            whiteColumn = column;
                        } else if(game.boardClass.board[row][column] instanceof BlackKing) {
                            blackRow = row;
                            blackColumn = column;
                        }
                    }
                }

                if(!(game.boardClass.whitePlayerAttackBoard[blackRow][blackColumn] instanceof Mark_MovableTile)) {
                    drawHistory("         STALEMATE", true);
                } else if(!(game.boardClass.blackPlayerAttackBoard[whiteRow][whiteColumn] instanceof  Mark_MovableTile)) {
                    drawHistory("         STALEMATE", true);
                } else {
                    drawHistory("         GAME OVER" + '\n'
                            + player + " wins. CHECKMATE!", true);
                }

            } else {
                game.isOver = false;
            }
        }).start();

    }


}