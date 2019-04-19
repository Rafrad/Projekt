package model;

public class Board {
    String board[][] = new String[8][8];

    public Board() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (i == 0 || i == 7) {
                    switch (j) {
                        case 0:
                        case 7:
                            board[i][j] = "W";
                            break;
                        case 1:
                        case 6:
                            board[i][j] = "K";
                            break;
                        case 2:
                        case 5:
                            board[i][j] = "G";
                            break;
                        case 3:
                            board[i][j] = "Q";
                            break;
                        case 4:
                            board[i][j] = "T";
                            break;
                    }
                } else if (i == 1 || i == 6) {
                    board[i][j] = "P";
                } else {
                    board[i][j] = "X";
                }
            }

        }
    }

    public void PrintBoard() {
        for (int i = 7; i >= 0; i--) {
            for (int j = 0; j < 8; j++) {
                System.out.print("[" + i + ", " + j + "]");
//                System.out.print();
//                System.out.print(board[i][j]);
//                System.out.print(" ");
            }
            System.out.println();
        }
    }

}
