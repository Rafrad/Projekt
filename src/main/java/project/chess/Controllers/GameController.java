package project.chess.Controllers;


import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.junit.FixMethodOrder;
import project.chess.Exceptions.PlayerColorException;
import project.chess.Models.Filter;
import project.chess.Models.Game;

import project.chess.Models.Options;
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
    private Button resignButton;
    @FXML
    private Button drawButton;
    @FXML
    private Button backButton;
    @FXML
    private TextArea moveHistory;

    private Game game;

    private Image blackPawnImage;
    private Image blackRookImage;
    private Image blackKnightImage;
    private Image blackBishopImage;
    private Image blackQueenImage;
    private Image blackKingImage;

    private Image whitePawn;
    private Image whiteRook;
    private Image whiteKnight;
    private Image whiteBishop;
    private Image whiteQueen;
    private Image whiteKing;

    private Image dot;
    private Image resignImage;
    private Image drawImage;

    private StringBuilder history;

    private void showImageResignButton() {
        resignButton.setGraphic(new ImageView(resignImage));
    }

    private void showImageDrawButton() {
        drawButton.setGraphic(new ImageView(drawImage));
    }

    private int n = 0;
    private int numberOfRounds = 1;

    private void drawHistory(String string) {
        if (n == 2) {
            history.append(System.getProperty("line.separator"));
            numberOfRounds++;
            n = 0;
            history.append(numberOfRounds).append(".     ");
        } else if (n == 1) {
            history.append("     ");
        }
        history.append(string);
        moveHistory.setText(history.toString());
        n++;
    }

    void init(Options options) throws PlayerColorException {
        System.out.println(options.getGameMode());
        System.out.println(options.getVersusMode());
        System.out.println(options.getFirstPlayerColor());
        promotionFlagBlockingMove = false;
        hidePromotionButtons();
        backButton.setVisible(false);
        moveHistory.setEditable(false);
        history = new StringBuilder();
        history.append(numberOfRounds).append(".     ");


        /*
         * Set image for every piece
         * dot, draw & resign
         */

        blackPawnImage = new Image("Images/black_pawn.png");
        blackRookImage = new Image("Images/black_rook.png");
        blackKnightImage = new Image("Images/black_knight.png");
        blackBishopImage = new Image("Images/black_bishop.png");
        blackQueenImage = new Image("Images/black_queen.png");
        blackKingImage = new Image("Images/black_king.png");

        whitePawn = new Image("Images/white_pawn.png");
        whiteRook = new Image("Images/white_rook.png");
        whiteKnight = new Image("Images/white_knight.png");
        whiteBishop = new Image("Images/white_bishop.png");
        whiteQueen = new Image("Images/white_queen.png");
        whiteKing = new Image("Images/white_king.png");


        dot = new Image("Images/dot.png");
        resignImage = new Image("Images/resign.png");
        drawImage = new Image("Images/draw.png");
        showImageDrawButton();
        showImageResignButton();


        Tile = new Pane[8][8];
        game = new Game();
        InitializeTiles();
        PaintBoard();
        EmulateBoard();


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

                            assert selectedPiece != null;
                            int selectedPieceRow = selectedPiece.getKey();
                            int selectedPieceColumn = selectedPiece.getValue();

                            try {
                                game.move(selectedPieceRow, selectedPieceColumn, row, column);
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }

                            ClearPossibleMoves();
                            PaintBoard();
                            EmulateBoard();



                            Piece piece = game.boardClass.getPiece(row, column);
                            if (piece instanceof WhitePawn || piece instanceof BlackPawn) {
                                String coordinates = replaceToChessNotation(row, column).toString();
                                drawHistory(coordinates);
                            }
                            

                            System.out.println();

                            if (checkGameOver()) {
                                //TODO
                                //historia ruchow
                                hideRestButtons();
                                backButton.setVisible(true);
                            }

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


                                    //===========================================
                                    Filter filter = new Filter(game, moves, row, column);
                                    moves = filter.filterMoves();

                                    game.boardClass.clearPossibleMoves();

                                    for (Pair<Integer, Integer> move : moves) {
                                        int rowMove = move.getKey();
                                        int columnMove = move.getValue();

                                        game.boardClass.boardOfPossibleMoves[rowMove][columnMove] = new Mark_MovableTile();
                                    }

                                    //===========================================


                                    ImageView[] dotMoves = new ImageView[moves.size()];
                                    for (int i = 0; i < moves.size(); i++) {
                                        dotMoves[i] = new ImageView(dot);
                                        int rowMove = moves.get(i).getKey();
                                        int columnMove = moves.get(i).getValue();

                                        Tile[rowMove][columnMove].getChildren().add(dotMoves[i]);
                                    }
                                }
                            } // current player

                            break;
                    }

                });
            }
        }


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

        //poprawic

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        window.setX(primaryScreenBounds.getMinX() + primaryScreenBounds.getWidth() - 1100);
        window.setY(primaryScreenBounds.getMinY() + primaryScreenBounds.getHeight() - 700);
        window.setWidth(750);
        window.setHeight(540);

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

                while (!Tile[row][column].getChildren().isEmpty()) {
                    Tile[row][column].getChildren().remove(0);
                }

                switch (game.boardClass.board[row][column].getClass().getSimpleName()) {
                    case "BlackPawn":
                        tmp = new ImageView(blackPawnImage);
                        break;
                    case "WhitePawn":
                        tmp = new ImageView(whitePawn);
                        break;
                    case "Rook":
                        if (((Rook) game.boardClass.board[row][column]).getPlayer()) {
                            tmp = new ImageView(whiteRook);
                        } else {
                            tmp = new ImageView(blackRookImage);
                        }
                        break;
                    case "Knight":
                        if (((Knight) game.boardClass.board[row][column]).getPlayer()) {
                            tmp = new ImageView(whiteKnight);
                        } else {
                            tmp = new ImageView(blackKnightImage);
                        }
                        break;
                    case "Bishop":
                        if (((Bishop) game.boardClass.board[row][column]).getPlayer()) {
                            tmp = new ImageView(whiteBishop);
                        } else {
                            tmp = new ImageView(blackBishopImage);
                        }
                        break;
                    case "Queen":
                        if (((Queen) game.boardClass.board[row][column]).getPlayer()) {
                            tmp = new ImageView(whiteQueen);
                        } else {
                            tmp = new ImageView(blackQueenImage);
                        }
                        break;
                    case "BlackKing":
                        tmp = new ImageView(blackKingImage);
                        break;
                    case "WhiteKing":
                        tmp = new ImageView(whiteKing);
                        break;
                }

                if (tmp != null) {
                    Tile[row][column].getChildren().add(tmp);
                }

            }
        }

        checkPromotionBox();
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


    public void promoteToBishop() throws PlayerColorException {
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
        char c = '`';
        c += column;
        row += 1;

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(c);
        stringBuilder.append(row);

        return stringBuilder;
    }

}