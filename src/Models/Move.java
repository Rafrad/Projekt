package Models;

import Exceptions.PlayerColorException;
import Exceptions.WrongBoardException;
import Models.Pieces.BlackPawn;
import Models.Pieces.Piece;
import Models.Pieces.WhitePawn;
import javafx.util.Pair;

import java.util.*;

public class Move {
    Board board;

    Move() throws WrongBoardException {
    }

    Move(Board boardTmp) {
        this.board = boardTmp;
    }

    public List<Pair<Integer, Integer>> CalculateMoves(int PieceRow, int PieceColumn) {

        Piece piece = board.getPiece(PieceRow, PieceColumn);
        //
        System.out.println(piece.getClass().getSimpleName());
        //
        String nameOfPiece = piece.getClass().getSimpleName();
        List<Pair<Integer, Integer>> allowedMoves = piece.move();
        List<Pair<Integer, Integer>> availableMoves = new LinkedList<>();
        //
        System.out.println("allowed moves: " + allowedMoves.size());
        //

        for (int i = 0; i < allowedMoves.size(); i++) {
            int allowedMovesRow = 0;
            int allowedMovesColumn = 0;

            allowedMovesRow = allowedMoves.get(i).getKey() + PieceRow;
            allowedMovesColumn = allowedMoves.get(i).getValue() + PieceColumn;

            // dodajemy ruchy dozwolone

            if (allowedMovesRow >= 0
                    && allowedMovesColumn >= 0
                    && allowedMovesRow <= 7
                    && allowedMovesColumn <= 7) {

                Piece check = board.getPiece(allowedMovesRow, allowedMovesColumn);
                String nameOfCheck = check.getClass().getSimpleName();



                if(nameOfPiece.equals("Rook")) {

                }

                if (nameOfPiece.equals("BlackPawn") || nameOfPiece.equals("WhitePawn")) {

                    if (nameOfCheck.equals("EmptyTile")
                            && PieceColumn == allowedMovesColumn) {
                        switch (nameOfPiece) {
                            case "BlackPawn":
                                //TODO :promotion, bicie z przelotem
                                if (((BlackPawn) board.board[PieceRow][PieceColumn]).getFirstMove()) {
                                    if (board.board[PieceRow + 1][PieceColumn].getClass().getSimpleName().equals("EmptyTile")) {
                                        availableMoves.add(new Pair<>(allowedMovesRow, allowedMovesColumn));
                                    }
                                } else {
                                    if (!(PieceRow == allowedMovesRow - 2)) {
                                        availableMoves.add(new Pair<>(allowedMovesRow, allowedMovesColumn));
                                    }

                                }
                                break;
                            case "WhitePawn":
                                System.out.println("xd");
                                if (((WhitePawn) board.board[PieceRow][PieceColumn]).getFirstMove()) {
                                    if (board.board[PieceRow - 1][PieceColumn].getClass().getSimpleName().equals("EmptyTile")) {
                                        availableMoves.add(new Pair<>(allowedMovesRow, allowedMovesColumn));
                                    }
                                } else {
                                    if (!(PieceRow == allowedMovesRow + 2)) {
                                        availableMoves.add(new Pair<>(allowedMovesRow, allowedMovesColumn));
                                    }

                                }
                                break;
                            default:
                                break;
                        }


                    } else if (PieceColumn != allowedMovesColumn && !nameOfCheck.equals("EmptyTile")) {
                        boolean basicPlayer = piece.getPlayer();
                        boolean player = check.getPlayer();

                        if (basicPlayer != player) {
                            availableMoves.add(new Pair<>(allowedMovesRow, allowedMovesColumn));
                        }

                    }

                }

            }

        }

        for (Object availableMove : availableMoves) {
            System.out.println(availableMove);
        }

        board.AddPossibleMoves(availableMoves);
        System.out.println("movable board: ");
        board.PrintMovableBoard();

        return availableMoves;
    }

    public List<Pair<Integer, Integer>> RookHelper(int PieceRow, int PieceColumn) {
        List<Pair<Integer, Integer>> pair = new LinkedList<>();

        if(PieceRow > 0) {

        }
        return pair;
    }
}
