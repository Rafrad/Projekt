package main.Models;

import main.Exceptions.PlayerColorException;
import main.Models.Pieces.*;
import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;


public class Game {
    //which player has move, white starts; white = true
    boolean currentlyPlayer;
    //game over (?)
    boolean isOver;

    //TODO: clock

    public Board boardClass;
    public Move moveClass;

    public Game() throws PlayerColorException {
        boardClass = new Board();
        moveClass = new Move(boardClass);
        boardClass.PrintBoard(true);
        currentlyPlayer = true;
        isOver = false;
    }

    public void move(int row, int column, int rowDestination, int columnDestination) throws PlayerColorException {
        deleteEnPassant();

        if (rowDestination == 0 && boardClass.getPiece(row, column).getClass().getSimpleName().equals("WhitePawn")) {
            ((WhitePawn) boardClass.getPiece(row, column)).setPromotion(true);
        } else if (rowDestination == 7 && boardClass.getPiece(row, column).getClass().getSimpleName().equals("BlackPawn")) {
            ((BlackPawn) boardClass.getPiece(row, column)).setPromotion(true);
        }

        switch (boardClass.board[row][column].getClass().getSimpleName()) {
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

        } else { //delete
            System.out.println("nie mozna");
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


    public void UpdateCheck() {
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                switch (boardClass.getPiece(row, column).getClass().getSimpleName()) {
                    case "WhiteKing":

                        break;
                }
            }
        }
    }

    public List<Pair<Integer, Integer>> showAttackedTiles(boolean player) {
        List<Pair<Integer, Integer>> attackList = new LinkedList<>();
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                if (!boardClass.getPiece(row, column).getClass().getSimpleName().equals("EmptyTile")
                        && boardClass.getPiece(row, column).getPlayer() == player) {
                    attackList = moveClass.CalculateMoves(row, column, true);
                }

            }
        }


        return attackList;
    }


}



