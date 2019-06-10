package project.chess.Models;

import project.chess.Exceptions.PlayerColorException;
import project.chess.Models.Pieces.*;
import javafx.util.Pair;

import java.util.List;

/**
 * Board class defines;
 * main board - it is visible for user,
 * board of possible moves - tells where single piece can move to,
 * white player attack board - filled with attack of white player pieces
 * black player attack board - filler with attack of black player pieces
 */

public class Board {
    public Piece[][] board;
    public Piece[][] boardOfPossibleMoves;
    public Piece[][] whitePlayerAttackBoard;
    public Piece[][] blackPlayerAttackBoard;

    public Board() throws PlayerColorException {
        board = new Piece[8][8];
        boardOfPossibleMoves = new Piece[8][8];
        whitePlayerAttackBoard = new Piece[8][8];
        blackPlayerAttackBoard = new Piece[8][8];

        initBoard();
        initEveryBoard();
    }


    /**
     * Init main board.
     *
     * @throws PlayerColorException thrown when piece has no player (white or black)
     */

    private void initBoard() throws PlayerColorException {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (i == 7) {
                    switch (j) {
                        case 0:
                        case 7:
                            board[i][j] = new Rook(true);
                            break;
                        case 1:
                        case 6:
                            board[i][j] = new Knight(true);
                            break;
                        case 2:
                        case 5:
                            board[i][j] = new Bishop(true);
                            break;
                        case 3:
                            board[i][j] = new Queen(true);
                            break;
                        case 4:
                            board[i][j] = new WhiteKing();
                            break;
                    }
                } else if (i == 0) {
                    switch (j) {
                        case 0:
                        case 7:
                            board[i][j] = new Rook(false);
                            break;
                        case 1:
                        case 6:
                            board[i][j] = new Knight(false);
                            break;
                        case 2:
                        case 5:
                            board[i][j] = new Bishop(false);
                            break;
                        case 3:
                            board[i][j] = new Queen(false);
                            break;
                        case 4:
                            board[i][j] = new BlackKing();
                            break;
                    }
                } else if (i == 6) {
                    board[i][j] = new WhitePawn();
                } else if (i == 1) {
                    board[i][j] = new BlackPawn();
                } else {
                    board[i][j] = new EmptyTile();
                }
            }

        }
    }

    /**
     * Inits boardOfPossibleMoves, whitePlayerAttackBoard, blackPlayerAttackBoard.
     */

    private void initEveryBoard() {
        for (int row = 0; row < 8; row++) {
            System.arraycopy(board[row], 0, boardOfPossibleMoves[row], 0, 8);
        }


        for (int row = 0; row < 8; row++) {
            System.arraycopy(board[row], 0, whitePlayerAttackBoard[row], 0, 8);
        }

        for (int row = 0; row < 8; row++) {
            System.arraycopy(board[row], 0, blackPlayerAttackBoard[row], 0, 8);
        }
    }


    public Piece getPiece(int row, int column) {
        return board[row][column];
    }

    /**
     * Fills given board with mark movable tiles.
     *
     * @param list        movement list
     * @param boardChosen board given
     * @param stack       helps with filtering moves
     */

    void addPossibleMoves(List<Pair<Integer, Integer>> list, String boardChosen, List<Pair<Integer, Integer>> stack) {
        Piece[][] boardToFill = getBoard(boardChosen);

        int row;
        int column;
        for (Pair<Integer, Integer> pair : list) {
            row = pair.getKey();
            column = pair.getValue();


            if (boardToFill[row][column] instanceof EmptyTile) {
                boardToFill[row][column] = new Mark_MovableTile();
            } else if (boardChosen.equals("w") || boardChosen.equals("b")) {
                stack.add(new Pair<>(row, column));
            } else {
                boardToFill[row][column] = new Mark_MovableTile();
            }

        }
    }

    private Piece[][] getBoard(String boardChosen) {
        Piece[][] boardToFill;
        switch (boardChosen) {
            case "w":
                boardToFill = whitePlayerAttackBoard;
                break;
            case "b":
                boardToFill = blackPlayerAttackBoard;
                break;
            default:
                boardToFill = boardOfPossibleMoves;
                break;
        }
        return boardToFill;
    }


    /**
     * Made for development and tests.
     *
     * @param boardToPrint written in console
     */

    public void printChosenBoard(Piece[][] boardToPrint) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                boardToPrint[i][j].print();
            }
            System.out.println();
        }
    }


    public void clearPossibleMoves() {
        for (int row = 0; row < 8; row++) {
            System.arraycopy(board[row], 0, boardOfPossibleMoves[row], 0, 8);
        }
    }

    void clearWhitePlayerAttackBoard() {
        for (int row = 0; row < 8; row++) {
            System.arraycopy(board[row], 0, whitePlayerAttackBoard[row], 0, 8);
        }
    }

   void clearBlackPlayerAttackBoard() {
        for (int row = 0; row < 8; row++) {
            System.arraycopy(board[row], 0, blackPlayerAttackBoard[row], 0, 8);
        }
    }

}