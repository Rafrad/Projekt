package Models;

import Exceptions.PlayerColorException;
import Models.Pieces.*;

import javafx.util.Pair;
import java.util.List;

public class Board {
    //TODO: delete publics
    public Piece board[][];
    Piece boardMove[][];

    public Board() throws PlayerColorException {
        board = new Piece[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (i == 0 || i == 7) {
                    switch (j) {
                        case 0:
                        case 7:
                            board[i][j] = new WhiteRook();
                            break;
                        case 1:
                        case 6:
                            board[i][j] = new WhiteKnight();
                            break;
                        case 2:
                            board[i][j] = new Bishop("black");
                        case 5:
                            board[i][j] = new Bishop("white");

                            break;
                        case 3:
                            board[i][j] = new WhiteQueen();
                            break;
                        case 4:
                            board[i][j] = new WhiteKing();
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
//        board[2][1] = new WhiteKing();
        boardMove = board;
    }


    public void PrintBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
//                System.out.println(board[i][j].getClass().getSimpleName());
                if(board[i][j].getClass().getSimpleName().equals("WhitePawn")) {
                    System.out.print("w ");
                } else if(board[i][j].getClass().getSimpleName().equals("BlackPawn")) {
                    System.out.print("b ");
                } else {
                    System.out.print("x ");
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

            boardMove[row][column] = new Mark_MovableTile();
        }
    }

    public void PrintMovableBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
//                System.out.println(board[i][j].getClass().getSimpleName());
                if(board[i][j].getClass().getSimpleName().equals("WhitePawn")) {
                    System.out.print("w ");
                } else if(board[i][j].getClass().getSimpleName().equals("BlackPawn")) {
                    System.out.print("b ");
                } else if(board[i][j].getClass().getSimpleName().equals("Mark_MovableTile")) {
                    System.out.print("m ");
                }
                else {
                    System.out.print("x ");
                }
            }
            System.out.println();
        }
    }

//    public String getPiece(int a, int b) {
//        return boardTmp[a][b];
//    }

    //return Board??

    //usuwanie i dodawanie lub move
}
