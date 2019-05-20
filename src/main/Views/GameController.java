package main.Views;


import javafx.scene.control.Button;
import main.Exceptions.PlayerColorException;
import main.Models.Game;

import main.Models.Options;
import main.Models.Pieces.*;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

import java.io.IOException;
import java.util.List;

public class GameController {
    @FXML
    private Label time;
    @FXML
    private GridPane gridPane;
    @FXML
    private AnchorPane anchor;
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

    private Game game;

    private Image blackPawn;
    private Image blackRook;
    private Image blackKnight;
    private Image blackBishop;
    private Image blackQueen;
    private Image blackKing;

    private Image whitePawn;
    private Image whiteRook;
    private Image whiteKnight;
    private Image whiteBishop;
    private Image whiteQueen;
    private Image whiteKing;


    private Image dot;

    private List<String> lista;
    Options opcje;

    public void smiechawa(Options o) throws PlayerColorException {
        opcje = o;
        System.out.println(o.getGameMode());
        System.out.println(o.getVersusMode());
        System.out.println(o.getFirstPlayerColor());
        promotionFlagBlockingMove = false;
        hidePromotionButtons();

        /*
         * Set image for every piece
         * and for dot
         */

        blackPawn = new Image("main/Images/black_pawn.png");
        blackRook = new Image("main/Images/black_rook.png");
        blackKnight = new Image("main/Images/black_knight.png");
        blackBishop = new Image("main/Images/black_bishop.png");
        blackQueen = new Image("main/Images/black_queen.png");
        blackKing = new Image("main/Images/black_king.png");

        whitePawn = new Image("main/Images/white_pawn.png");
        whiteRook = new Image("main/Images/white_rook.png");
        whiteKnight = new Image("main/Images/white_knight.png");
        whiteBishop = new Image("main/Images/white_bishop.png");
        whiteQueen = new Image("main/Images/white_queen.png");
        whiteKing = new Image("main/Images/white_king.png");


        dot = new Image("main/Images/dot.png");


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

                            int selectedPieceRow = selectedPiece.getKey();
                            int selectedPieceColumn = selectedPiece.getValue();

                            game.move(selectedPieceRow, selectedPieceColumn, row, column);
                            ClearPossibleMoves();
                            PaintBoard();
                            try {
                                EmulateBoard();
                            } catch (PlayerColorException e) {
                                e.printStackTrace();
                            }
                            break;
                        default:
                            game.fillAttackBoard(game.boardClass.boardOfPossibleMoves[row][column].getPlayer());
//                            if (game.getCurrentPlayer() == game.boardClass.board[row][column].getPlayer()) {
                            if(!promotionFlagBlockingMove) {
                                PaintBoard();
                                ClearPossibleMoves();
                                try {
                                    EmulateBoard();
                                } catch (PlayerColorException e) {
                                    e.printStackTrace();
                                }

                                Tile[row][column].setStyle("-fx-background-color: #fbfb5b");
                                List<Pair<Integer, Integer>> moves = game.moveClass.CalculateMoves(row, column, false);

                                ImageView[] dotMoves = new ImageView[moves.size()];
                                System.out.println(moves.size());
                                for (int i = 0; i < moves.size(); i++) {
                                    dotMoves[i] = new ImageView(dot);
                                    int rowMove = moves.get(i).getKey();
                                    int columnMove = moves.get(i).getValue();

                                    Tile[rowMove][columnMove].getChildren().add(dotMoves[i]);
                                }
                            }
//                            }

                            break;
                    }

                });
            }
        }


    }

    private boolean promotionFlagBlockingMove;

    @FXML
    public void initialize() throws PlayerColorException, IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/Views/Settings.fxml"));
//        SettingsController kupa = loader.getController();
//        System.out.println(kupa.testint);
//        List<String> haha = kupa.odpowiedziDoSprawdzianiu();
    }


    public Pair<Integer, Integer> getSelectedPiece() {
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                if (Tile[row][column].styleProperty().getValueSafe().subSequence(22, 29).equals("#fbfb5b")) {
                    Pair<Integer, Integer> pair = new Pair<Integer, Integer>(row, column);
                    return pair;
                }
            }
        }
        return null;
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
                    for (int s = 0; s < Tile[i][j].getChildren().size(); s++) {
                        Tile[i][j].getChildren().remove(s);
                    }

                }
            }
        }
        game.boardClass.clearPossibleMoves();
    }


    public void EmulateBoard() throws PlayerColorException {
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                ImageView tmp = null;

                while (!Tile[row][column].getChildren().isEmpty()) {
                    Tile[row][column].getChildren().remove(0);
                }

                switch (game.boardClass.board[row][column].getClass().getSimpleName()) {
                    case "BlackPawn":
                        tmp = new ImageView(blackPawn);
                        break;
                    case "WhitePawn":
                        tmp = new ImageView(whitePawn);
                        break;
                    case "Rook":
                        if (((Rook) game.boardClass.board[row][column]).getPlayer()) {
                            tmp = new ImageView(whiteRook);
                        } else {
                            tmp = new ImageView(blackRook);
                        }
                        break;
                    case "Knight":
                        if (((Knight) game.boardClass.board[row][column]).getPlayer()) {
                            tmp = new ImageView(whiteKnight);
                        } else {
                            tmp = new ImageView(blackKnight);
                        }
                        break;
                    case "Bishop":
                        if (((Bishop) game.boardClass.board[row][column]).getPlayer()) {
                            tmp = new ImageView(whiteBishop);
                        } else {
                            tmp = new ImageView(blackBishop);
                        }
                        break;
                    case "Queen":
                        if (((Queen) game.boardClass.board[row][column]).getPlayer()) {
                            tmp = new ImageView(whiteQueen);
                        } else {
                            tmp = new ImageView(blackQueen);
                        }
                        break;
                    case "BlackKing":
                        tmp = new ImageView(blackKing);
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

        /**
         * promotion box
         */

        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                switch (game.boardClass.getPiece(row, column).getClass().getSimpleName()) {
                    case "WhitePawn":
                        if (((WhitePawn) game.boardClass.getPiece(row, column)).getPromotion()) {
                            showPromotionButtons(true);
                        }
                        break;
                    case "BlackPawn":
                        if(((BlackPawn)game.boardClass.getPiece(row, column)).getPromotion()) {
                            showPromotionButtons(false);
                        }
                        break;
                }
            }
        }
    }


    public void promoteToQueen() throws PlayerColorException {
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



    public void promoteToRook() throws PlayerColorException {
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


    public void hidePromotionButtons() {
        promoteToBishopButton.setVisible(false);
        promoteToRookButton.setVisible(false);
        promoteToKnightButton.setVisible(false);
        promoteToQueenButton.setVisible(false);

        promotionFlagBlockingMove = false;
    }

    public void showPromotionButtons(boolean player) {
        promotionFlagBlockingMove = true;
        if (player) {
            promoteToQueenButton.setGraphic(new ImageView(whiteQueen));
            promoteToKnightButton.setGraphic(new ImageView(whiteKnight));
            promoteToBishopButton.setGraphic(new ImageView(whiteBishop));
            promoteToRookButton.setGraphic(new ImageView(whiteRook));
        } else {
            promoteToQueenButton.setGraphic(new ImageView(blackQueen));
            promoteToKnightButton.setGraphic(new ImageView(blackKnight));
            promoteToBishopButton.setGraphic(new ImageView(blackBishop));
            promoteToRookButton.setGraphic(new ImageView(blackRook));
        }


        promoteToBishopButton.setVisible(true);
        promoteToRookButton.setVisible(true);
        promoteToKnightButton.setVisible(true);
        promoteToQueenButton.setVisible(true);
    }

}