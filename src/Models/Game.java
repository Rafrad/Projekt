package Models;

import Exceptions.PlayerColorException;
import Models.Pieces.EmptyTile;


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
        currentlyPlayer = true;
        isOver = false;
    }

    public void move(int row, int column, int rowDestination, int columnDestination) {
        System.out.println();
        if(boardClass.boardMove[rowDestination][columnDestination].getClass().getSimpleName().equals("Mark_MovableTile")) {
            System.out.println("mozna");
            UpdateBoard(row, column, rowDestination, columnDestination);
        } else {
            System.out.println("nie mozna");
        }

    }

    public void UpdateBoard(int row, int column, int rowDestination, int columnDestination) {
        boardClass.board[rowDestination][columnDestination] = boardClass.board[row][column];
        boardClass.board[row][column] = new EmptyTile();
    }

    public void ClearBoardWithPossibleMoves() {
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {

            }
        }
    }

    public boolean getCurrentPlayer() {
        return currentlyPlayer;
    }


}
