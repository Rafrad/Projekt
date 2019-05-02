package Models;

import Exceptions.PlayerColorException;
import Models.Pieces.*;
import javafx.util.Pair;
import java.util.List;

public class Board {
    //TODO: delete publics
    public Piece[][] board;
    public Piece[][] boardOfPossibleMoves;

    public Board() throws PlayerColorException  {
        board = new Piece[8][8];
        boardOfPossibleMoves = new Piece[8][8];


        /*
         * board init
         */

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
                            board[i][j] = new EmptyTile();
//                            board[i][j] = new WhiteKnight();
                            break;
                        case 2:
                            board[i][j] = new EmptyTile();
//                            board[i][j] = new Bishop("black");
                        case 5:
                            board[i][j] = new EmptyTile();
//                            board[i][j] = new Bishop("white");

                            break;
                        case 3:
                            board[i][j] = new EmptyTile();
//                            board[i][j] = new WhiteQueen();
                            break;
                        case 4:
                            board[i][j] = new EmptyTile();
//                            board[i][j] = new WhiteKing();
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
                            board[i][j] = new EmptyTile();
//                            board[i][j] = new WhiteKnight();
                            break;
                        case 2:
                            board[i][j] = new EmptyTile();
//                            board[i][j] = new Bishop("black");
                        case 5:
                            board[i][j] = new EmptyTile();
//                            board[i][j] = new Bishop("white");

                            break;
                        case 3:
                            board[i][j] = new EmptyTile();
//                            board[i][j] = new WhiteQueen();
                            break;
                        case 4:
                            board[i][j] = new EmptyTile();
//                            board[i][j] = new WhiteKing();
                            break;
                    }
                }
                else if (i == 6) {
                    board[i][j] = new WhitePawn();
                } else if (i == 1) {
                    board[i][j] = new BlackPawn();
                } else {
                    board[i][j] = new EmptyTile();
                }
            }

        }



        /*
         * movable board init
         */

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (i == 0 || i == 7) {
                    switch (j) {
                        case 0:
                            boardOfPossibleMoves[i][j] = new Rook(false);
                            break;
                        case 7:
                            boardOfPossibleMoves[i][j] = new Rook(true);
                            break;
                        case 1:
                        case 6:
                            boardOfPossibleMoves[i][j] = new EmptyTile();
//                            board[i][j] = new WhiteKnight();
                            break;
                        case 2:
                            boardOfPossibleMoves[i][j] = new EmptyTile();
//                            board[i][j] = new Bishop("black");
                        case 5:
                            boardOfPossibleMoves[i][j] = new EmptyTile();
//                            board[i][j] = new Bishop("white");

                            break;
                        case 3:
                            boardOfPossibleMoves[i][j] = new EmptyTile();
//                            board[i][j] = new WhiteQueen();
                            break;
                        case 4:
                            boardOfPossibleMoves[i][j] = new EmptyTile();
//                            board[i][j] = new WhiteKing();
                            break;
                    }
                } else if (i == 6) {
                    boardOfPossibleMoves[i][j] = new WhitePawn();
                } else if (i == 1) {
                    boardOfPossibleMoves[i][j] = new BlackPawn();
                } else {
                    boardOfPossibleMoves[i][j] = new EmptyTile();
                }
            }

        }
    }


    public void PrintBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                boolean player;
                switch (board[i][j].getClass().getSimpleName()) {
                    case "WhitePawn":
                        System.out.print("w ");
                        break;
                    case "BlackPawn":
                        System.out.print("b ");
                        break;
                    case "Rook":
                       player =  ((Rook)board[i][j]).getPlayer();
                       if(player) {
                           System.out.print("R ");
                       } else {
                           System.out.print("r ");
                       }
                       break;

                    default:
                        System.out.print("x ");
                        break;
                }
            }
            System.out.println();
        }
    }


    public Piece getPiece(int row, int column) {
        return board[row][column];
    }

    public void AddPossibleMoves(List<Pair<Integer, Integer>> list) {
        int row = 0;
        int column = 0;
        for(int i = 0; i < list.size(); i++) {
            row = list.get(i).getKey();
            column = list.get(i).getValue();

            boardOfPossibleMoves[row][column] = new Mark_MovableTile();
        }
    }

    public void PrintMovableBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                if(boardOfPossibleMoves[i][j].getClass().getSimpleName().equals("WhitePawn")) {
                    System.out.print("w ");
                } else if(boardOfPossibleMoves[i][j].getClass().getSimpleName().equals("BlackPawn")) {
                    System.out.print("b ");
                } else if(boardOfPossibleMoves[i][j].getClass().getSimpleName().equals("Rook")) {
                    if(((Rook)boardOfPossibleMoves[i][j]).getPlayer()) {
                        System.out.print("R ");
                    } else {
                        System.out.print("r ");
                    }
                }
                else if(boardOfPossibleMoves[i][j].getClass().getSimpleName().equals("Mark_MovableTile")) {
                    System.out.print("m ");
                }
                else {
                    System.out.print("x ");
                }
            }
            System.out.println();
        }
    }

    public void ClearPossibleMoves() {
        for(int row = 0; row <8; row ++) {
            for(int column = 0; column < 8; column++) {
                boardOfPossibleMoves[row][column] = board[row][column];
            }
        }
    }


}
