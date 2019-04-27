package Models;

import Models.Pieces.*;
import Exception.PlayerColorExeption;

public class Board {
    Piece board[][];

    public Board() throws PlayerColorExeption {
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
                } else if (i == 1 || i == 6) {
                    board[i][j] = new Pawn();
                } else {
                    board[i][j] = new EmptyTile();
                }
            }

        }
//        board[2][1] = new WhiteKing();
    }


    public void PrintBoard() {
        for (int i = 7; i >= 0; i--) {
            for (int j = 0; j < 8; j++) {
                System.out.print("[" + i + ", " + j + "]");
                System.out.print(" ");
//                System.out.print(boardTmp[i][j]);
//                System.out.print(" ");
            }
            System.out.println();
        }
    }


    public Piece getPiece(int a, int b) {
        return board[a][b];
    }

//    public String getPiece(int a, int b) {
//        return boardTmp[a][b];
//    }

    //return Board??

    //usuwanie i dodawanie lub move
}
