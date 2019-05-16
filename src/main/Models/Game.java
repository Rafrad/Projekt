package main.Models;

import main.Exceptions.PlayerColorException;
import main.Models.Pieces.BlackPawn;
import main.Models.Pieces.EmptyTile;
import main.Models.Pieces.WhitePawn;
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

    public void move(int row, int column, int rowDestination, int columnDestination) {
        deleteEnPassant();


        if (boardClass.boardOfPossibleMoves[rowDestination][columnDestination].getClass().getSimpleName().equals("Mark_MovableTile")) {
            switch (boardClass.board[row][column].getClass().getSimpleName()) {
                case "BlackPawn":
                    if (((BlackPawn) boardClass.board[row][column]).getFirstMove()) {
                        ((BlackPawn) boardClass.board[row][column]).setFirstMove(false);
                        if (Math.abs(row - rowDestination) == 2) {
                            ((BlackPawn) boardClass.board[row][column]).setEnPassant(true);
                        }
                    }

                    if (row + 1 == rowDestination && column - 1 == columnDestination) {
                        boardClass.board[row][column - 1] = new EmptyTile();
                    } else if (row + 1 == rowDestination && column + 1 == columnDestination) {
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

                    if (row - 1 == rowDestination && column - 1 == columnDestination) {
                        boardClass.board[row][column - 1] = new EmptyTile();
                    } else if (row - 1 == rowDestination && column + 1 == columnDestination) {
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
        for(int row = 0; row < 8; row++) {
            for(int column = 0; column < 8; column++) {
                if(!boardClass.getPiece(row, column).getClass().getSimpleName().equals("EmptyTile")
                && boardClass.getPiece(row, column).getPlayer() == player) {
                    attackList = moveClass.CalculateMoves(row, column);
                }

            }
        }


        return attackList;
    }


}



