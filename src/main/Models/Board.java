package main.Models;

import main.Exceptions.PlayerColorException;
import main.Models.Pieces.*;
import javafx.util.Pair;

import java.util.List;

public class Board {
    public Piece[][] board;
    public Piece[][] boardOfPossibleMoves;
    public Piece[][] attackBoard;

    public Board() throws PlayerColorException {
        board = new Piece[8][8];
        boardOfPossibleMoves = new Piece[8][8];
        attackBoard = new Piece[8][8];

        
        //boardClass init
        
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

        
        //movable boardClass init
        
        for (int row = 0; row < 8; row++) {
            System.arraycopy(board[row], 0, boardOfPossibleMoves[row], 0, 8);
        }

        //attack boardClass init

        for(int row = 0; row < 8; row++) {
            System.arraycopy(board[row], 0, attackBoard[row], 0, 0);
        }

    }

    public Piece getPiece(int row, int column) {
        return board[row][column];
    }


    /*
     * TRUE - ATTACK BOARD
     * FALSE - MOVABLE BOARD
     */

    public void addPossibleMoves(List<Pair<Integer, Integer>> list, boolean boardChosen) {
        Piece[][] boardToFill;
        if(boardChosen) {
            boardToFill = attackBoard;
        } else {
            boardToFill = boardOfPossibleMoves;
        }

        int row = 0;
        int column = 0;
        for (int i = 0; i < list.size(); i++) {
            row = list.get(i).getKey();
            column = list.get(i).getValue();

            boardToFill[row][column] = new Mark_MovableTile();
        }
    }


    /*
     * TRUE - BOARD
     * FALSE - MOVABLE BOARD
     */

    public void printBoard(boolean boardChosen) {
        Piece[][] boardToPrint;
        if(boardChosen) {
            boardToPrint = board;
        } else {
            boardToPrint = boardOfPossibleMoves;
        }

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
                        player = ((Rook) boardToPrint[i][j]).getPlayer();
                        if (player) {
                            System.out.print("R ");
                        } else {
                            System.out.print("r ");
                        }
                        break;
                    case "Knight":
                        player = ((Knight) boardToPrint[i][j]).getPlayer();
                        if (player) {
                            System.out.print("K ");
                        } else {
                            System.out.print("k ");
                        }
                        break;
                    case "Bishop":
                        player = ((Bishop) boardToPrint[i][j]).getPlayer();
                        if (player) {
                            System.out.print("B ");
                        } else {
                            System.out.print("b ");
                        }
                        break;
                    case "Queen":
                        player = ((Queen) boardToPrint[i][j]).getPlayer();
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

    public void clearAttackBoard() {
        for (int row = 0; row < 8; row++) {
            System.arraycopy(board[row], 0, attackBoard[row], 0, 8);
        }
    }

}
