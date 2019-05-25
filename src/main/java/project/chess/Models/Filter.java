package project.chess.Models;

import javafx.util.Pair;
import project.chess.Models.Pieces.Piece;

import java.util.LinkedList;
import java.util.List;

public class Filter {
    private List<Pair<Integer, Integer>> listOfMoves;
    private int pieceRow;
    private int pieceColumn;
    private Game gameClass;
    private boolean currentPlayer;

    private int kingRow;
    private int kingColumn;

    public Filter(Game game, List<Pair<Integer, Integer>> moves, int pieceRow, int pieceColumn, boolean player) {
        gameClass = game;
        listOfMoves = moves;
        currentPlayer = player;

        this.pieceRow = pieceRow;
        this.pieceColumn = pieceColumn;
    }


    private void updateWhiteKingCoordinates() {
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                if (!gameClass.boardClass.getPiece(row, column).getClass().getSimpleName().equals("EmptyTile")) {
                    if (gameClass.boardClass.getPiece(row, column).getClass().getSimpleName().equals("WhiteKing")) {
                        kingRow = row;
                        kingColumn = column;
                        break;
                    }
                }
            }
        }
    }

    private void updateBlackKingCoordinates() {
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                if (!gameClass.boardClass.getPiece(row, column).getClass().getSimpleName().equals("EmptyTile")) {
                    if (gameClass.boardClass.getPiece(row, column).getClass().getSimpleName().equals("BlackKing")) {
                        kingRow = row;
                        kingColumn = column;
                        break;
                    }
                }
            }
        }
    }



    public List<Pair<Integer, Integer>> filterMoves() {
        List<Pair<Integer, Integer>> result = new LinkedList<>();

        for (int i = 0; i < listOfMoves.size(); i++) {
            int rowDestination = listOfMoves.get(i).getKey();
            int columnDestination = listOfMoves.get(i).getValue();

            if(currentPlayer) {
                //TODO
            }
            Piece[][] blackBoard = gameClass.boardClass.blackPlayerAttackBoard;
            Piece[][] board = gameClass.boardClass.board;
            gameClass.boardClass.clearBlackPlayerAttackBoard();


            gameClass.updateBlackPlayerAttackBoard(pieceRow, pieceColumn, rowDestination, columnDestination);

            gameClass.fillBlackPlayerAttackBoard();


            gameClass.boardClass.printChosenBoard(blackBoard);

            updateWhiteKingCoordinates();

            System.out.println("===========================");


            if(blackBoard[rowDestination][columnDestination].getClass().getSimpleName().equals("Mark_MovableTile")
            && board[rowDestination][columnDestination].getClass().getSimpleName().equals("WhiteKing")) {
                System.out.println("o ziom");
            }

            result.add(new Pair<>(rowDestination, columnDestination));
        }

        return result;
    }


}
