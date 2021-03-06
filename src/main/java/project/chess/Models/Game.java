package project.chess.Models;

import javafx.util.Pair;
import project.chess.Controllers.GameController;
import project.chess.Exceptions.PlayerColorException;
import project.chess.Models.Pieces.*;

import java.util.LinkedList;
import java.util.List;

/**
 * This class defines almost every behaviour of the game.
 * Changes have direct effect on GameController class
 * @see GameController
 */

public class Game {
    private boolean currentlyPlayer;
    public boolean isOver;


    public Board boardClass;
    public Move moveClass;
    public CustomClock whiteClock;
    public CustomClock blackClock;

    public Game() throws PlayerColorException {
        boardClass = new Board();
    }

    public Game(CustomClock whiteClock, CustomClock blackClock) throws PlayerColorException {
        boardClass = new Board();
        moveClass = new Move(boardClass);
        boardClass.printChosenBoard(boardClass.board);
        currentlyPlayer = true;
        isOver = false;

        this.whiteClock = whiteClock;
        this.blackClock = blackClock;
        whiteClock.setTime();
        blackClock.setTime();
        whiteClock.start();

    }

    public Game(Board board) throws PlayerColorException {
        boardClass = new Board(board);
        moveClass = new Move(boardClass);
        boardClass.printChosenBoard(boardClass.board);
        currentlyPlayer = true;
        isOver = false;

        this.whiteClock = null;
        this.blackClock = null;
    }

    /**
     * Creates new thread. May be bugged.
     */

    private void checkGameOver() {

        new Thread(() -> {
            int numberOfWhiteMoves = 0;
            int numberOfBlackMoves = 0;

            for (int row = 0; row < 8; row++) {
                for (int column = 0; column < 8; column++) {
                    if (boardClass.board[row][column].getPlayer()) {
                        numberOfWhiteMoves = getNumberOfMoves(numberOfWhiteMoves, row, column);
                    } else if (!boardClass.board[row][column].getPlayer()) {
                        numberOfBlackMoves = getNumberOfMoves(numberOfBlackMoves, row, column);
                    }

                }
            }

            if (numberOfBlackMoves == 0 || numberOfWhiteMoves == 0) {
                isOver = true;
                whiteClock.stop();
                blackClock.stop();
            }
        }).start();

    }


    public int getNumberOfMoves(int numberOfMoves, int row, int column) {
        List<Pair<Integer, Integer>> dummy = new LinkedList<>();
        List<Pair<Integer, Integer>> moves = moveClass.CalculateMoves(row, column, "", dummy);

        Filter filter = new Filter(this, moves, row, column);
        moves = filter.filterMoves();

        numberOfMoves += moves.size();
        boardClass.clearPossibleMoves();
        return numberOfMoves;
    }


    private void checkPromotions(int row, int column, int rowDestination) {
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

        if(whiteClock != null) swapClocks(piece);

        removeCastling(row, column, columnDestination, nameOfMovedPiece, piece);


        if (true) {
            checkPawnsFirstMove(row, column, rowDestination, columnDestination);

            UpdateBoard(row, column, rowDestination, columnDestination);
            checkGameOver();



            currentlyPlayer = !currentlyPlayer;
        }


        boardClass.clearPossibleMoves();
    }



    private void swapClocks(Piece piece) {
        if (piece.getPlayer()) {
            whiteClock.stop();
            blackClock.start();
        } else {
            blackClock.stop();
            whiteClock.start();
        }
    }

    private void checkPawnsFirstMove(int row, int column, int rowDestination, int columnDestination) {
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
    }

    private void removeCastling(int row, int column, int columnDestination, String nameOfMovedPiece, Object piece) {
        switch (nameOfMovedPiece) {
            case "WhiteKing":
                ((WhiteKing) piece).setCastling(false);
                if (Math.abs(column - columnDestination) == 2 && columnDestination == 6) {
                    boardClass.board[7][7] = new EmptyTile();
                    boardClass.board[7][5] = new Rook(true);
                } else if (Math.abs(column - columnDestination) == 2 && columnDestination == 2) {
                    boardClass.board[7][0] = new EmptyTile();
                    boardClass.board[7][3] = new Rook(true);
                }
                break;
            case "BlackKing":
                ((BlackKing) piece).setCastling(false);
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
    }

    public void UpdateBoard(int row, int column, int rowDestination, int columnDestination) {
        boardClass.board[rowDestination][columnDestination] = boardClass.board[row][column];
        boardClass.board[row][column] = new EmptyTile();
    }

    void updateBlackPlayerAttackBoard(int row, int column, int rowDestination, int columnDestination) {
        boardClass.blackPlayerAttackBoard[rowDestination][columnDestination] = boardClass.blackPlayerAttackBoard[row][column];
        boardClass.blackPlayerAttackBoard[row][column] = new EmptyTile();
    }

    void updateWhitePlayerAttackBoard(int row, int column, int rowDestination, int columnDestination) {
        boardClass.whitePlayerAttackBoard[rowDestination][columnDestination] = boardClass.whitePlayerAttackBoard[row][column];
        boardClass.whitePlayerAttackBoard[row][column] = new EmptyTile();
    }


    public boolean getCurrentPlayer() {
        return currentlyPlayer;
    }

    private void deleteEnPassant() {
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                switch (boardClass.board[row][column].getClass().getSimpleName()) {
                    case "BlackPawn":
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


    void fillWhitePlayerAttackBoard() {
        List<Pair<Integer, Integer>> stack = new LinkedList<>();

        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                if (boardClass.whitePlayerAttackBoard[row][column].getPlayer()
                        && !boardClass.whitePlayerAttackBoard[row][column].getClass().getSimpleName().equals("EmptyTile")) {
                    moveClass.CalculateMoves(row, column, "w", stack);
                }
            }
        }

        for (Pair<Integer, Integer> pair : stack) {
            int row = pair.getKey();
            int column = pair.getValue();
            boardClass.whitePlayerAttackBoard[row][column] = new Mark_MovableTile();
        }
    }


    void fillBlackPlayerAttackBoard() {
        List<Pair<Integer, Integer>> stack = new LinkedList<>();

        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                if (!boardClass.blackPlayerAttackBoard[row][column].getPlayer()
                        && !boardClass.blackPlayerAttackBoard[row][column].getClass().getSimpleName().equals("EmptyTile")) {
                    moveClass.CalculateMoves(row, column, "b", stack);
                }
            }
        }

        for (Pair<Integer, Integer> pair : stack) {
            int row = pair.getKey();
            int column = pair.getValue();
            boardClass.blackPlayerAttackBoard[row][column] = new Mark_MovableTile();
        }
    }
}