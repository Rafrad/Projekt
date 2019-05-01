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
        boardClass.PrintBoard();
        //TODO: CHANGE
        currentlyPlayer = false;
        isOver = false;
    }

    public void move(int row, int column, int rowDestination, int columnDestination) {
        if (boardClass.boardOfPossibleMoves[rowDestination][columnDestination].getClass().getSimpleName().equals("Mark_MovableTile")) {
            switch (boardClass.board[row][column].getClass().getSimpleName()) {
                case "BlackPawn":
                    if (boardClass.board[row][column].getFirstMove()) {
                        boardClass.board[row][column].setFirstMove(false);
                    }
                    break;
            }

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
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (boardClass.boardOfPossibleMoves[i][j].getClass().getSimpleName().equals("Mark_MovableTile")) {
                    boardClass.boardOfPossibleMoves[i][j] = new EmptyTile();
                }
            }
        }
    }

    public boolean getCurrentPlayer() {
        return currentlyPlayer;
    }


}
