package project.chess.Models;

import project.chess.Exceptions.WrongBoardException;
import project.chess.Models.Pieces.*;
import javafx.util.Pair;

import java.util.*;

public class Move {
    private Board boardClass;
    private boolean attack;

    Move() throws WrongBoardException {
    }

    Move(Board boardTmp) {
        this.boardClass = boardTmp;
    }

    private Piece[][] checkPlayerAttackBoard(String playerAttackBoard) {
        Piece[][] boardToCheck;
        Piece[][] blackPlayerAttackBoard = boardClass.blackPlayerAttackBoard;
        Piece[][] whitePlayerAttackBoard = boardClass.whitePlayerAttackBoard;
        attack = false;

        if (playerAttackBoard.equals("b")) {
            boardToCheck = blackPlayerAttackBoard;
            attack = true;
        } else if(playerAttackBoard.equals("w")) {
            boardToCheck = whitePlayerAttackBoard;
            attack = true;
        } else {
            boardToCheck = boardClass.board;
        }

        return boardToCheck;
    }

    public List<Pair<Integer, Integer>> CalculateMoves(int PieceRow, int PieceColumn, String playerAttackBoard, List<Pair<Integer, Integer>> stack) {
        Piece[][] boardToCheck;

        boardToCheck = checkPlayerAttackBoard(playerAttackBoard);

        Piece pieceChosen = boardToCheck[PieceRow][PieceColumn];
        String nameOfChosenPiece = pieceChosen.getClass().getSimpleName();
        List<Pair<Integer, Integer>> allowedPieceMovesFromVector = pieceChosen.move();
        List<Pair<Integer, Integer>> availableMoves = new LinkedList<>();

        switch (nameOfChosenPiece) {
            case "Rook":
                leftRook(PieceRow, PieceColumn, boardToCheck[PieceRow], pieceChosen, availableMoves, attack);
                upRook(PieceRow, PieceColumn, boardToCheck, pieceChosen, availableMoves, attack);
                rightRook(PieceRow, PieceColumn, boardToCheck[PieceRow], pieceChosen, availableMoves, attack);
                downRook(PieceRow, PieceColumn, boardToCheck, pieceChosen, availableMoves, attack);

                break;

            case "Knight":
                knightMoves(PieceRow, PieceColumn, pieceChosen, allowedPieceMovesFromVector, availableMoves, attack, boardToCheck);
                break;

            case "Bishop":
                for (int i = PieceRow + 1, j = PieceColumn + 1; i < 8 && j < 8; i++, j++) {
                    if (bishopMoves(pieceChosen, availableMoves, i, j, boardToCheck)) break;
                }


                for (int i = PieceRow - 1, j = PieceColumn - 1; i >= 0 && j >= 0; i--, j--) {
                    if (bishopMoves(pieceChosen, availableMoves, i, j, boardToCheck)) break;
                }


                for (int i = PieceRow + 1, j = PieceColumn - 1; i < 8 && j >= 0; i++, j--) {
                    if (bishopMoves(pieceChosen, availableMoves, i, j, boardToCheck)) break;
                }

                for (int i = PieceRow - 1, j = PieceColumn + 1; i >= 0 && j < 8; i--, j++) {
                    if (bishopMoves(pieceChosen, availableMoves, i, j, boardToCheck)) break;
                }

                break;

            case "Queen":
                leftRook(PieceRow, PieceColumn, boardToCheck[PieceRow], pieceChosen, availableMoves, attack);
                upRook(PieceRow, PieceColumn, boardToCheck, pieceChosen, availableMoves, attack);
                rightRook(PieceRow, PieceColumn, boardToCheck[PieceRow], pieceChosen, availableMoves, attack);
                downRook(PieceRow, PieceColumn, boardToCheck, pieceChosen, availableMoves, attack);

                for (int i = PieceRow + 1, j = PieceColumn + 1; i < 8 && j < 8; i++, j++) {
                    if (bishopMoves(pieceChosen, availableMoves, i, j, boardToCheck)) break;
                }


                for (int i = PieceRow - 1, j = PieceColumn - 1; i >= 0 && j >= 0; i--, j--) {
                    if (bishopMoves(pieceChosen, availableMoves, i, j, boardToCheck)) break;
                }


                for (int i = PieceRow + 1, j = PieceColumn - 1; i < 8 && j >= 0; i++, j--) {
                    if (bishopMoves(pieceChosen, availableMoves, i, j, boardToCheck)) break;
                }

                for (int i = PieceRow - 1, j = PieceColumn + 1; i >= 0 && j < 8; i--, j++) {
                    if (bishopMoves(pieceChosen, availableMoves, i, j, boardToCheck)) break;
                }

                break;

            case "WhiteKing":
            case "BlackKing":

                for (Pair<Integer, Integer> pair : allowedPieceMovesFromVector) {

                    int allowedMovesRow = 0;
                    int allowedMovesColumn = 0;


                    allowedMovesRow = pair.getKey() + PieceRow;
                    allowedMovesColumn = pair.getValue() + PieceColumn;


                    if (allowedMovesRow >= 0
                            && allowedMovesColumn >= 0
                            && allowedMovesRow <= 7
                            && allowedMovesColumn <= 7) {

                        Piece check = boardToCheck[allowedMovesRow][allowedMovesColumn];
                        String nameOfCheck = check.getClass().getSimpleName();

                        if (nameOfChosenPiece.equals("WhiteKing")) {
//                            if (!boardClass.blackPlayerAttackBoard[allowedMovesRow][allowedMovesColumn].getClass().getSimpleName().equals("Mark_MovableTile")) {
                            if (nameOfCheck.equals("EmptyTile")) {
                                availableMoves.add(new Pair<>(allowedMovesRow, allowedMovesColumn));
                            } else if (check.getPlayer() != pieceChosen.getPlayer()) {
                                availableMoves.add(new Pair<>(allowedMovesRow, allowedMovesColumn));
                            }
//                            }
                        } else if (nameOfChosenPiece.equals("BlackKing")) {
                            if (!boardClass.whitePlayerAttackBoard[allowedMovesRow][allowedMovesColumn].getClass().getSimpleName().equals("Mark_MovableTile")) {
                                if (nameOfCheck.equals("EmptyTile")) {
                                    availableMoves.add(new Pair<>(allowedMovesRow, allowedMovesColumn));
                                } else if (check.getPlayer() != pieceChosen.getPlayer()) {
                                    availableMoves.add(new Pair<>(allowedMovesRow, allowedMovesColumn));
                                }
                            }
                        }


                    }
                }



                if (nameOfChosenPiece.equals("WhiteKing")) {
                    whiteKingCastling((WhiteKing) pieceChosen, availableMoves);
                }

                if (nameOfChosenPiece.equals("BlackKing")) {
                    blackKingCastling((BlackKing) pieceChosen, availableMoves);
                }


        }


        if (nameOfChosenPiece.equals("BlackPawn") || nameOfChosenPiece.equals("WhitePawn")) {


            for (int i = 0; i < allowedPieceMovesFromVector.size(); i++) {
                int allowedMovesRow = 0;
                int allowedMovesColumn = 0;

                allowedMovesRow = allowedPieceMovesFromVector.get(i).getKey() + PieceRow;
                allowedMovesColumn = allowedPieceMovesFromVector.get(i).getValue() + PieceColumn;

                // dodajemy ruchy dozwolone

                if (allowedMovesRow >= 0
                        && allowedMovesColumn >= 0
                        && allowedMovesRow <= 7
                        && allowedMovesColumn <= 7) {

                    Piece check = boardClass.getPiece(allowedMovesRow, allowedMovesColumn);
                    String nameOfCheck = check.getClass().getSimpleName();


                    if (nameOfChosenPiece.equals("BlackPawn") || nameOfChosenPiece.equals("WhitePawn")) {

                        //tutaj bez petli (en passant)
                        if (nameOfChosenPiece.equals("WhitePawn") && !(playerAttackBoard.equals("w") || playerAttackBoard.equals("b"))) {
                            if (PieceColumn - 1 >= 0) {
                                if (boardClass.getPiece(PieceRow, PieceColumn - 1).getClass().getSimpleName().equals("BlackPawn")) {
                                    if (((BlackPawn) boardClass.board[PieceRow][PieceColumn - 1]).getEnPassant()) {
                                        availableMoves.add(new Pair<>(PieceRow - 1, PieceColumn - 1));
                                    }
                                }
                            }

                            if (PieceColumn + 1 < 8
                                    && boardClass.getPiece(PieceRow, PieceColumn + 1).getClass().getSimpleName().equals("BlackPawn")
                                    && ((BlackPawn) boardClass.board[PieceRow][PieceColumn + 1]).getEnPassant()) {
                                availableMoves.add(new Pair<>(PieceRow - 1, PieceColumn + 1));
                            }
                        }


                        //tutaj bez petli (en passant)
                        if (nameOfChosenPiece.equals("BlackPawn") && !(playerAttackBoard.equals("w") || playerAttackBoard.equals("b"))) {
                            if (PieceColumn - 1 >= 0) {
                                if (boardClass.getPiece(PieceRow, PieceColumn - 1).getClass().getSimpleName().equals("WhitePawn")) {
                                    if (((WhitePawn) boardClass.board[PieceRow][PieceColumn - 1]).getEnPassant()) {
                                        availableMoves.add(new Pair<>(PieceRow + 1, PieceColumn - 1));
                                    }
                                }
                            }

                            if (PieceColumn + 1 < 8
                                    && boardClass.getPiece(PieceRow, PieceColumn + 1).getClass().getSimpleName().equals("WhitePawn")
                                    && ((WhitePawn) boardClass.board[PieceRow][PieceColumn + 1]).getEnPassant()) {
                                availableMoves.add(new Pair<>(PieceRow + 1, PieceColumn + 1));
                            }
                        }


                        if (nameOfCheck.equals("EmptyTile") && PieceColumn == allowedMovesColumn) {
                            switch (nameOfChosenPiece) {
                                case "BlackPawn":
                                    //TODO:
                                    if (((BlackPawn) boardClass.board[PieceRow][PieceColumn]).getFirstMove()) {
                                        if (boardClass.board[PieceRow + 1][PieceColumn].getClass().getSimpleName().equals("EmptyTile")) {
                                            if (!(playerAttackBoard.equals("w") || playerAttackBoard.equals("b")))
                                                availableMoves.add(new Pair<>(allowedMovesRow, allowedMovesColumn));
                                        }
                                    }
                                    else {
                                        if (!(PieceRow == allowedMovesRow - 2)) {
                                            if (!(playerAttackBoard.equals("w") || playerAttackBoard.equals("b")))
                                                availableMoves.add(new Pair<>(allowedMovesRow, allowedMovesColumn));
                                        }
                                    }


                                    break;
                                case "WhitePawn":
                                    if (((WhitePawn) boardClass.board[PieceRow][PieceColumn]).getFirstMove()) {
                                        if (boardClass.board[PieceRow - 1][PieceColumn].getClass().getSimpleName().equals("EmptyTile")) {
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
                            boolean basicPlayer = pieceChosen.getPlayer();
                            boolean player = check.getPlayer();

                            if (basicPlayer != player) {
                                availableMoves.add(new Pair<>(allowedMovesRow, allowedMovesColumn));
                            }

                        }
                        if (PieceColumn != allowedMovesColumn && (playerAttackBoard.equals("w") || playerAttackBoard.equals("b"))) {
                            availableMoves.add(new Pair<>(allowedMovesRow, allowedMovesColumn));
                        }

                    }

                }

            }
        }


//        for (Object availableMove : availableMoves) {
//            System.out.println(availableMove);
//        }

        switch (playerAttackBoard) {
            case "w":
                boardClass.addPossibleMoves(availableMoves, "w", stack);
                break;
            case "b":
                boardClass.addPossibleMoves(availableMoves, "b", stack);
                break;
            default:
                boardClass.addPossibleMoves(availableMoves, "movable board", stack);
                break;
        }


        return availableMoves;
    }

    private void blackKingCastling(BlackKing pieceChosen, List<Pair<Integer, Integer>> availableMoves) {
        if (boardClass.getPiece(0, 7).getClass().getSimpleName().equals("Rook")
                && boardClass.getPiece(0, 6).getClass().getSimpleName().equals("EmptyTile")
                && !boardClass.whitePlayerAttackBoard[0][6].getClass().getSimpleName().equals("Mark_MovableTile")
                && boardClass.getPiece(0, 5).getClass().getSimpleName().equals("EmptyTile")
                && !boardClass.whitePlayerAttackBoard[0][5].getClass().getSimpleName().equals("Mark_MovableTile")
                && !boardClass.whitePlayerAttackBoard[0][4].getClass().getSimpleName().equals("Mark_MovableTile")
                && ((Rook) boardClass.getPiece(0, 7)).getCastling()
                && pieceChosen.getCastling()) {
            availableMoves.add(new Pair<>(0, 6));
        }
        if (boardClass.getPiece(0, 0).getClass().getSimpleName().equals("Rook")
                && boardClass.getPiece(0, 1).getClass().getSimpleName().equals("EmptyTile")
                && !boardClass.whitePlayerAttackBoard[0][1].getClass().getSimpleName().equals("Mark_MovableTile")
                && boardClass.getPiece(0, 2).getClass().getSimpleName().equals("EmptyTile")
                && !boardClass.whitePlayerAttackBoard[0][2].getClass().getSimpleName().equals("Mark_MovableTile")
                && boardClass.getPiece(0, 3).getClass().getSimpleName().equals("EmptyTile")
                && !boardClass.whitePlayerAttackBoard[0][3].getClass().getSimpleName().equals("Mark_MovableTile")
                && !boardClass.whitePlayerAttackBoard[0][4].getClass().getSimpleName().equals("Mark_MovableTile")
                && ((Rook) boardClass.getPiece(0, 0)).getCastling()
                && pieceChosen.getCastling()) {
            availableMoves.add(new Pair<>(0, 2));
        }
    }

    private void whiteKingCastling(WhiteKing pieceChosen, List<Pair<Integer, Integer>> availableMoves) {
        if (boardClass.getPiece(7, 7).getClass().getSimpleName().equals("Rook")
                && boardClass.getPiece(7, 6).getClass().getSimpleName().equals("EmptyTile")
                && !boardClass.blackPlayerAttackBoard[7][6].getClass().getSimpleName().equals("Mark_MovableTile")
                && boardClass.getPiece(7, 5).getClass().getSimpleName().equals("EmptyTile")
                && !boardClass.blackPlayerAttackBoard[7][5].getClass().getSimpleName().equals("Mark_MovableTile")
                && !boardClass.blackPlayerAttackBoard[7][4].getClass().getSimpleName().equals("Mark_MovableTile")
                && ((Rook) boardClass.getPiece(7, 7)).getCastling()
                && pieceChosen.getCastling()) {
            availableMoves.add(new Pair<>(7, 6));
        }
        if (boardClass.getPiece(7, 0).getClass().getSimpleName().equals("Rook")
                && boardClass.getPiece(7, 1).getClass().getSimpleName().equals("EmptyTile")
                && !boardClass.blackPlayerAttackBoard[7][1].getClass().getSimpleName().equals("Mark_MovableTile")
                && boardClass.getPiece(7, 2).getClass().getSimpleName().equals("EmptyTile")
                && !boardClass.blackPlayerAttackBoard[7][2].getClass().getSimpleName().equals("Mark_MovableTile")
                && boardClass.getPiece(7, 3).getClass().getSimpleName().equals("EmptyTile")
                && !boardClass.blackPlayerAttackBoard[7][3].getClass().getSimpleName().equals("Mark_MovableTile")
                && !boardClass.blackPlayerAttackBoard[7][4].getClass().getSimpleName().equals("Mark_MovableTile")
                && ((Rook) boardClass.getPiece(7, 0)).getCastling()
                && pieceChosen.getCastling()) {
            availableMoves.add(new Pair<>(7, 2));
        }
    }

//dlaczego to dziala xdXD



    private boolean bishopMoves(Piece pieceChosen, List<Pair<Integer, Integer>> availableMoves, int i, int j, Piece[][] boardToCheck) {
        if (boardToCheck[i][j].getClass().getSimpleName().equals("EmptyTile")
                ||(boardToCheck[i][j] instanceof Mark_MovableTile && attack)) {
            availableMoves.add(new Pair<>(i, j));
        } else if (boardToCheck[i][j].getPlayer() == pieceChosen.getPlayer()) {
            if(attack) {
                availableMoves.add(new Pair<>(i, j));
            }
            return true;
        } else {
            availableMoves.add(new Pair<>(i, j));
            return true;
        }
        return false;
    }

    private void knightMoves(int PieceRow,
                             int PieceColumn,
                             Piece pieceChosen,
                             List<Pair<Integer, Integer>> allowedPieceMovesFromVector,
                             List<Pair<Integer, Integer>> availableMoves,
                             boolean attack,
                             Piece[][] boardToCheck) {

        for (Pair<Integer, Integer> pair : allowedPieceMovesFromVector) {
            int allowedMovesRow = pair.getKey() + PieceRow;
            int allowedMovesColumn = pair.getValue() + PieceColumn;

            if (allowedMovesRow >= 0
                    && allowedMovesColumn >= 0
                    && allowedMovesRow <= 7
                    && allowedMovesColumn <= 7) {

                Piece check = boardToCheck[allowedMovesRow][allowedMovesColumn];
                String nameOfCheck = check.getClass().getSimpleName();


                if (nameOfCheck.equals("EmptyTile")) {
                    availableMoves.add(new Pair<>(allowedMovesRow, allowedMovesColumn));
                } else if (check.getPlayer() != pieceChosen.getPlayer()) {
                    availableMoves.add(new Pair<>(allowedMovesRow, allowedMovesColumn));
                } else if (check.getPlayer() == pieceChosen.getPlayer() && attack) {
                    availableMoves.add(new Pair<>(allowedMovesRow, allowedMovesColumn));
                }

            }
        }
    }


    private boolean upDown(int PieceColumn, Piece[][] boardToCheck, Piece pieceChosen, List<Pair<Integer, Integer>> availableMoves, int i, boolean attack) {
        if (boardToCheck[i][PieceColumn].getClass().getSimpleName().equals("EmptyTile")
           ||(boardToCheck[i][PieceColumn] instanceof Mark_MovableTile && attack)) {
            availableMoves.add(new Pair<>(i, PieceColumn));
        } else if (boardToCheck[i][PieceColumn].getPlayer() == pieceChosen.getPlayer()) {
            if(attack) {
                availableMoves.add(new Pair<>(i, PieceColumn));
            }
            return true;
        } else {
            availableMoves.add(new Pair<>(i, PieceColumn));
            return true;
        }


        return false;
    }

    private boolean rightLeft(int PieceRow, Piece[] pieces, Piece pieceChosen, List<Pair<Integer, Integer>> availableMoves, int i, boolean attack) {
        if (pieces[i].getClass().getSimpleName().equals("EmptyTile")
                ||(pieces[i] instanceof Mark_MovableTile && attack)) {
            availableMoves.add(new Pair<>(PieceRow, i));
        } else if (pieces[i].getPlayer() == pieceChosen.getPlayer()) {
            if(attack) {
                availableMoves.add(new Pair<>(PieceRow, i));
            }
            return true;
        } else {
            availableMoves.add(new Pair<>(PieceRow, i));
            return true;
        }
        return false;
    }


    private void upRook(int PieceRow, int PieceColumn, Piece[][] boardToCheck, Piece pieceChosen, List<Pair<Integer, Integer>> availableMoves, boolean attack) {
        for (int i = PieceRow - 1; i >= 0; i--) {
            if (upDown(PieceColumn, boardToCheck, pieceChosen, availableMoves, i, attack)) break;
        }
    }

    private void downRook(int PieceRow, int PieceColumn, Piece[][] boardToCheck, Piece pieceChosen, List<Pair<Integer, Integer>> availableMoves, boolean attack) {
        for (int i = PieceRow + 1; i < 8; i++) {
            if (upDown(PieceColumn, boardToCheck, pieceChosen, availableMoves, i, attack)) break;
        }
    }

    private void leftRook(int PieceRow, int PieceColumn, Piece[] pieces, Piece pieceChosen, List<Pair<Integer, Integer>> availableMoves, boolean attack) {
        for (int i = PieceColumn - 1; i >= 0; i--) {
            if (rightLeft(PieceRow, pieces, pieceChosen, availableMoves, i, attack)) break;
        }
    }

    private void rightRook(int PieceRow, int PieceColumn, Piece[] pieces, Piece pieceChosen, List<Pair<Integer, Integer>> availableMoves, boolean attack) {
        for (int i = PieceColumn + 1; i < 8; i++) {
            if (rightLeft(PieceRow, pieces, pieceChosen, availableMoves, i, attack)) break;
        }
    }

}
