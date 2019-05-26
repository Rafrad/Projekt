package project.chess.Models;

import javafx.util.Pair;
import project.chess.Models.Pieces.*;

import java.util.LinkedList;
import java.util.List;

public class Filter {
    private List<Pair<Integer, Integer>> listOfMoves;
    private int pieceRow;
    private int pieceColumn;
    private Game gameClass;

    private int kingRow;
    private int kingColumn;

    public Filter(Game game, List<Pair<Integer, Integer>> moves, int pieceRow, int pieceColumn) {
        gameClass = game;
        listOfMoves = moves;

        this.pieceRow = pieceRow;
        this.pieceColumn = pieceColumn;
    }

    private void updateWhiteKingCoordinates() {
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                if (!(gameClass.boardClass.blackPlayerAttackBoard[row][column] instanceof EmptyTile)) {
                    if (gameClass.boardClass.blackPlayerAttackBoard[row][column] instanceof WhiteKing) {
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
                if (!(gameClass.boardClass.whitePlayerAttackBoard[row][column] instanceof EmptyTile)) {
                    if (gameClass.boardClass.whitePlayerAttackBoard[row][column] instanceof BlackKing) {
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

        for (Pair<Integer, Integer> listOfMove : listOfMoves) {
            int rowDestination = listOfMove.getKey();
            int columnDestination = listOfMove.getValue();

            if (gameClass.boardClass.board[pieceRow][pieceColumn].getPlayer()) {
                Piece[][] blackBoard = gameClass.boardClass.blackPlayerAttackBoard;

                gameClass.boardClass.clearBlackPlayerAttackBoard();
                gameClass.updateBlackPlayerAttackBoard(pieceRow, pieceColumn, rowDestination, columnDestination);
                gameClass.fillBlackPlayerAttackBoard();

                updateWhiteKingCoordinates();


                if (blackBoard[kingRow][kingColumn] instanceof WhiteKing) {
                    result.add(new Pair<>(rowDestination, columnDestination));
                }

            } else {
                Piece[][] whiteBoard = gameClass.boardClass.whitePlayerAttackBoard;

                gameClass.boardClass.clearWhitePlayerAttackBoard();
                gameClass.updateWhitePlayerAttackBoard(pieceRow, pieceColumn, rowDestination, columnDestination);
                gameClass.fillWhitePlayerAttackBoard();

                updateBlackKingCoordinates();

                if (whiteBoard[kingRow][kingColumn] instanceof BlackKing) {
                    result.add(new Pair<>(rowDestination, columnDestination));
                }
            }
        }

        return result;
    }


}
