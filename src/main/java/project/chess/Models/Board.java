package project.chess.Models;

import project.chess.Exceptions.PlayerColorException;
import project.chess.Models.Pieces.*;
import javafx.util.Pair;

import java.util.List;

public class Board {
    public Piece[][] board;
    public Piece[][] boardOfPossibleMoves;
    Piece[][] whitePlayerAttackBoard;
    Piece[][] blackPlayerAttackBoard;

    public Board() throws PlayerColorException {
        board = new Piece[8][8];
        boardOfPossibleMoves = new Piece[8][8];
        whitePlayerAttackBoard = new Piece[8][8];
        blackPlayerAttackBoard = new Piece[8][8];

        initBoard();
        initEveryBoard();
    }

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
                    board[i][j] = new EmptyTile();
                } else if (i == 1) {
                    board[i][j] = new BlackPawn();
                } else {
                    board[i][j] = new EmptyTile();
                }
            }

        }
    }

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

    void addPossibleMoves(List<Pair<Integer, Integer>> list, String boardChosen) {
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

        int row;
        int column;
        for (Pair<Integer, Integer> pair : list) {
            row = pair.getKey();
            column = pair.getValue();

            boardToFill[row][column] = new Mark_MovableTile();
        }
    }


    private void printChosenBoard(Piece[][] boardToPrint) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                boolean player;
                switch (boardToPrint[i][j].getClass().getSimpleName()) {
                    case "WhitePawn":
                        System.out.print("w ");
                        break;
                    case "BlackPawn":
                        System.out.print("b ");
                        break;
                    case "Rook":
                        player = boardToPrint[i][j].getPlayer();
                        if (player) {
                            System.out.print("R ");
                        } else {
                            System.out.print("r ");
                        }
                        break;
                    case "Knight":
                        player = boardToPrint[i][j].getPlayer();
                        if (player) {
                            System.out.print("K ");
                        } else {
                            System.out.print("k ");
                        }
                        break;
                    case "Bishop":
                        player = boardToPrint[i][j].getPlayer();
                        if (player) {
                            System.out.print("I ");
                        } else {
                            System.out.print("i ");
                        }
                        break;
                    case "Queen":
                        player = boardToPrint[i][j].getPlayer();
                        if (player) {
                            System.out.print("Q ");
                        } else {
                            System.out.print("q ");
                        }
                        break;
                    case "WhiteKing":
                        System.out.print("Y ");
                        break;
                    case "BlackKing":
                        System.out.print("y ");
                        break;
                    case "Mark_MovableTile":
                        System.out.print("m ");
                        break;
                    default:
                        System.out.print("x ");
                        break;
                }
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