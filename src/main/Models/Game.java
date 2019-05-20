package main.Models;

import main.Exceptions.PlayerColorException;
import main.Models.Pieces.*;


public class Game {
    boolean currentlyPlayer;
    boolean isOver;


    public Board boardClass;
    public Move moveClass;

    public Game() throws PlayerColorException {
        boardClass = new Board();
        moveClass = new Move(boardClass);
        boardClass.printBoard(true);
        currentlyPlayer = true;
        isOver = false;
    }

    public void checkPromotions(int row, int column, int rowDestination) {
        if (rowDestination == 0 && boardClass.getPiece(row, column).getClass().getSimpleName().equals("WhitePawn")) {
            ((WhitePawn) boardClass.getPiece(row, column)).setPromotion(true);
        } else if (rowDestination == 7 && boardClass.getPiece(row, column).getClass().getSimpleName().equals("BlackPawn")) {
            ((BlackPawn) boardClass.getPiece(row, column)).setPromotion(true);
        }
    }

    public void move(int row, int column, int rowDestination, int columnDestination) {
        deleteEnPassant();
        checkPromotions(row, column, rowDestination);

        String nameOfMovedPiece = boardClass.board[row][column].getClass().getSimpleName();
        Piece piece = boardClass.getPiece(row, column);
        

        switch (nameOfMovedPiece) {
            case "WhiteKing":
                ((WhiteKing) boardClass.getPiece(row, column)).setCastling(false);
                if (Math.abs(column - columnDestination) == 2 && columnDestination == 6) {
                    boardClass.board[7][7] = new EmptyTile();
                    boardClass.board[7][5] = new Rook(true);
                } else if (Math.abs(column - columnDestination) == 2 && columnDestination == 2) {
                    boardClass.board[7][0] = new EmptyTile();
                    boardClass.board[7][3] = new Rook(true);
                }
                break;
            case "BlackKing":
                ((BlackKing) boardClass.getPiece(row, column)).setCastling(false);
                if (Math.abs(column - columnDestination) == 2 && columnDestination == 6) {
                    boardClass.board[0][7] = new EmptyTile();
                    boardClass.board[0][5] = new Rook(false);
                } else if (Math.abs(column - columnDestination) == 2 && columnDestination == 2) {
                    boardClass.board[0][0] = new EmptyTile();
                    boardClass.board[0][3] = new Rook(false);
                }
                break;
            case "Rook":
                ((Rook) boardClass.getPiece(row, column)).setCastling(false);
                break;
        }


        if (boardClass.boardOfPossibleMoves[rowDestination][columnDestination].getClass().getSimpleName().equals("Mark_MovableTile")) {
            switch (boardClass.board[row][column].getClass().getSimpleName()) {
                case "BlackPawn":
                    if (((BlackPawn) boardClass.board[row][column]).getFirstMove()) {
                        ((BlackPawn) boardClass.board[row][column]).setFirstMove(false);
                        if (Math.abs(row - rowDestination) == 2) {
                            ((BlackPawn) boardClass.board[row][column]).setEnPassant(true);
                        }
                    }

                    if (row + 1 == rowDestination && column - 1 == columnDestination
                            && boardClass.board[row + 1][column - 1].getClass().getSimpleName().equals("EmptyTile")) {
                        boardClass.board[row][column - 1] = new EmptyTile();
                    } else if (row + 1 == rowDestination && column + 1 == columnDestination
                            && boardClass.board[row + 1][column + 1].getClass().getSimpleName().equals("EmptyTile")) {
                        boardClass.board[row][column + 1] = new EmptyTile();
                    }


                    break;
                case "WhitePawn":
                    if (((WhitePawn) boardClass.board[row][column]).getFirstMove()) {
                        ((WhitePawn) boardClass.board[row][column]).setFirstMove(false);
                        if (Math.abs(row - rowDestination) == 2) {
                            ((WhitePawn) boardClass.board[row][column]).setEnPassant(true);
                        }
                    }

                    if (row - 1 == rowDestination
                            && column - 1 == columnDestination
                            && boardClass.board[row - 1][column - 1].getClass().getSimpleName().equals("EmptyTile")) {
                        boardClass.board[row][column - 1] = new EmptyTile();
                    } else if (row - 1 == rowDestination && column + 1 == columnDestination
                            && boardClass.board[row - 1][column + 1].getClass().getSimpleName().equals("EmptyTile")) {
                        boardClass.board[row][column + 1] = new EmptyTile();
                    }
                    break;
            }

            UpdateBoard(row, column, rowDestination, columnDestination);
            currentlyPlayer = !currentlyPlayer;

        }
    }

    public void UpdateBoard(int row, int column, int rowDestination, int columnDestination) {
        boardClass.board[rowDestination][columnDestination] = boardClass.board[row][column];
        boardClass.board[row][column] = new EmptyTile();
    }


    public boolean getCurrentPlayer() {
        return currentlyPlayer;
    }

    public void deleteEnPassant() {

        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                switch (boardClass.board[row][column].getClass().getSimpleName()) {
                    case "BlackPawn":
                        System.out.println(((BlackPawn) boardClass.board[row][column]).getEnPassant());
                        if (((BlackPawn) boardClass.board[row][column]).getEnPassant()) {
                            ((BlackPawn) boardClass.board[row][column]).setEnPassant(false);
                        }
                        break;
                    case "WhitePawn":
                        if (((WhitePawn) boardClass.board[row][column]).getEnPassant()) {
                            ((WhitePawn) boardClass.board[row][column]).setEnPassant(false);
                        }
                        break;
                }
            }
        }
    }

    public void fillAttackBoard(boolean player) {
        player = !player;
        boardClass.clearAttackBoard();
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                if (boardClass.board[row][column].getPlayer() == player) {
                    moveClass.CalculateMoves(row, column, true);
                }
            }
        }

        System.out.print("attack board for " + player + " player");
        System.out.println();
        boardClass.printAttack();
    }


}



