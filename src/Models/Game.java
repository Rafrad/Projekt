package Models;

import Exceptions.PlayerColorException;
import Models.Pieces.BlackPawn;
import Models.Pieces.EmptyTile;
import Models.Pieces.WhitePawn;


public class Game {
    //which player has move, white starts; white = true
    boolean currentlyPlayer;
    //game over (?)
    boolean isOver;

    //TODO: clock

    //TODO: delete publics
    public Board boardClass;
    public Move moveClass;

    public Game() throws PlayerColorException {

        boardClass = new Board();
        moveClass = new Move(boardClass);
        boardClass.PrintBoard();
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
                    break;
                case "WhitePawn":
                    if (((WhitePawn) boardClass.board[row][column]).getFirstMove()) {
                        ((WhitePawn) boardClass.board[row][column]).setFirstMove(false);
                        if (Math.abs(row - rowDestination) == 2) {
                            ((WhitePawn) boardClass.board[row][column]).setEnPassant(true);
                        }
                    }
                    break;
            }

            UpdateBoard(row, column, rowDestination, columnDestination);
            if (currentlyPlayer) {
                currentlyPlayer = false;
            } else {
                currentlyPlayer = true;
            }

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
                    case "Black Pawn":
                        if(((BlackPawn)boardClass.board[row][column]).getEnPassant()) {
                            ((BlackPawn)boardClass.board[row][column]).setEnPassant(false);
                        }
                        break;
                    case "White Pawn":
                        if(((WhitePawn)boardClass.board[row][column]).getEnPassant()) {
                            ((WhitePawn)boardClass.board[row][column]).setEnPassant(false);
                        }
                        break;
                }
            }
        }
    }
}



