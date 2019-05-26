package project.chess.Controllers;


import javafx.scene.control.Button;
import project.chess.Exceptions.PlayerColorException;
import project.chess.Models.Board;
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


    void init(Options options) throws PlayerColorException {
        System.out.println(options.getGameMode());
        System.out.println(options.getVersusMode());
        System.out.println(options.getFirstPlayerColor());
        promotionFlagBlockingMove = false;
        hidePromotionButtons();

        /*
         * Set image for every piece
         * and for dot
         */

        blackPawn = new Image("Images/black_pawn.png");
        blackRook = new Image("Images/black_rook.png");
        blackKnight = new Image("Images/black_knight.png");
        blackBishop = new Image("Images/black_bishop.png");
        blackQueen = new Image("Images/black_queen.png");
        blackKing = new Image("Images/black_king.png");

        whitePawn = new Image("Images/white_pawn.png");
        whiteRook = new Image("Images/white_rook.png");
        whiteKnight = new Image("Images/white_knight.png");
        whiteBishop = new Image("Images/white_bishop.png");
        whiteQueen = new Image("Images/white_queen.png");
        whiteKing = new Image("Images/white_king.png");


        dot = new Image("Images/dot.png");


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
//                            if (game.getCurrentPlayer() == game.boardClass.board[row][column].getPlayer()) {
                            if (!promotionFlagBlockingMove) {
                                PaintBoard();
                                ClearPossibleMoves();
                                try {
                                    EmulateBoard();
                                } catch (PlayerColorException e) {
                                    e.printStackTrace();
                                }

                                Tile[row][column].setStyle("-fx-background-color: #fbfb5b");
                                List<Pair<Integer, Integer>> dummy = new LinkedList<>();
                                List<Pair<Integer, Integer>> moves = game.moveClass.CalculateMoves(row, column, "", dummy);

//                                for (Pair<Integer, Integer> pair : dummy) {
//                                    int tmpRow = pair.getKey();
//                                    int tmpColumn = pair.getKey();
//
//                                    moves.add(new Pair<>(tmpRow, tmpColumn));
//                                }


                                //TUTAJ FILTR
                                //===========================================
                                Filter filter = new Filter(game, moves, row, column, false);
//                                moves.clear();
                                moves = filter.filterMoves();

                                game.boardClass.clearPossibleMoves();

                                for(int q = 0; q < moves.size(); q++) {
                                    int rowMove = moves.get(q).getKey();
                                    int columnMove = moves.get(q).getValue();

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
//                            }

                            break;
                    }

                });
            }
        }


    }

    private boolean promotionFlagBlockingMove;

    @FXML
    public void initialize(){
    }


    public Pair<Integer, Integer> getSelectedPiece() {
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                if (Tile[row][column].styleProperty().getValueSafe().subSequence(22, 29).equals("#fbfb5b")) {
                    return new Pair<>(row, column);
                }
            }
        }
        return null;
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


    private void EmulateBoard() throws PlayerColorException {
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

        //promotion box

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