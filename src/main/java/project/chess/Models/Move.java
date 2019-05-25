package project.chess.Models;

import project.chess.Exceptions.WrongBoardException;
import project.chess.Models.Pieces.*;
import javafx.util.Pair;

import java.util.*;

public class Move {
    private Board boardClass;
    private boolean attack = false;

    Move() throws WrongBoardException {
    }

    Move(Board boardTmp) {
        this.boardClass = boardTmp;
    }

    private Piece[][] checkPlayerAttackBoard(String playerAttackBoard) {
        Piece[][] boardToCheck;
        Piece[][] blackPlayerAttackBoard = boardClass.blackPlayerAttackBoard;
        Piece[][] whitePlayerAttackBoard = boardClass.whitePlayerAttackBoard;

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

    public List<Pair<Integer, Integer>> CalculateMoves(int PieceRow, int PieceColumn, String playerAttackBoard) {
        Piece[][] boardToCheck;

        boardToCheck = checkPlayerAttackBoard(playerAttackBoard);

        Piece pieceChosen = boardToCheck[PieceRow][PieceColumn];
        String nameOfChosenPiece = pieceChosen.getClass().getSimpleName();
        List<Pair<Integer, Integer>> allowedPieceMovesFromVector = pieceChosen.move();
        List<Pair<Integer, Integer>> availableMoves = new LinkedList<>();


        //1. normalne ruchy (bez wchodzenia na swoje pionki)
        //2. atak na przeciwnika
        //3. prawidlowe wypelnienie attackboard

        switch (nameOfChosenPiece) {
            case "Rook":
                leftRook(PieceRow, PieceColumn, boardToCheck[PieceRow], pieceChosen, availableMoves);

                for (int i = PieceRow - 1; i >= 0; i--) {
                    if (boardToCheck[i][PieceColumn].getClass().getSimpleName().equals("EmptyTile")) {
                        availableMoves.add(new Pair<>(i, PieceColumn));
                    } else if (boardToCheck[i][PieceColumn].getPlayer() == pieceChosen.getPlayer()) {
                        break;
                    } else {
                        availableMoves.add(new Pair<>(i, PieceColumn));
                        break;
                    }
                }

                //prawo
                for (int i = PieceColumn + 1; i < 8; i++) {
                    if (boardToCheck[PieceRow][i].getClass().getSimpleName().equals("EmptyTile")) {
                        availableMoves.add(new Pair<>(PieceRow, i));
                    } else if (boardToCheck[PieceRow][i].getPlayer() == pieceChosen.getPlayer()) {
                        break;
                    } else {
                        availableMoves.add(new Pair<>(PieceRow, i));
                        break;
                    }
                }


                //dol
                for (int i = PieceRow + 1; i < 8; i++) {
                    if (boardToCheck[i][PieceColumn].getClass().getSimpleName().equals("EmptyTile")) {
                        availableMoves.add(new Pair<>(i, PieceColumn));
                    } else if (boardToCheck[i][PieceColumn].getPlayer() == pieceChosen.getPlayer()) {
                        break;
                    } else {
                        availableMoves.add(new Pair<>(i, PieceColumn));
                        break;
                    }
                }




                break;

//
//            case "Knight":
//                for (int i = 0; i < allowedPieceMovesFromVector.size(); i++) {
//                    int allowedMovesRow = allowedPieceMovesFromVector.get(i).getKey() + PieceRow;
//                    int allowedMovesColumn = allowedPieceMovesFromVector.get(i).getValue() + PieceColumn;
//
//
//                    if (allowedMovesRow >= 0
//                            && allowedMovesColumn >= 0
//                            && allowedMovesRow <= 7
//                            && allowedMovesColumn <= 7) {
//
//                        Piece check = boardClass.getPiece(allowedMovesRow, allowedMovesColumn);
//                        String nameOfCheck = check.getClass().getSimpleName();
//
//
////                        if(!whiteKingCheck()) {
//
//                        if (nameOfCheck.equals("EmptyTile")) {
//                            availableMoves.add(new Pair<>(allowedMovesRow, allowedMovesColumn));
//                        } else if (check.getPlayer() != pieceChosen.getPlayer()) {
//                            availableMoves.add(new Pair<>(allowedMovesRow, allowedMovesColumn));
//                        } else if ((attack.equals("b") || attack.equals("w")) && check.getPlayer() == pieceChosen.getPlayer()) {
//                            availableMoves.add(new Pair<>(allowedMovesRow, allowedMovesColumn));
//                        }
////                        }
//                    }
//                }
////                fillBlackPlayerAttackBoard();
//                break;
//
//
//            case "Bishop":
//                for (int i = PieceRow + 1, j = PieceColumn + 1; i < 8 && j < 8; i++, j++) {
//                    if (boardClass.board[i][j].getClass().getSimpleName().equals("EmptyTile")) {
//                        availableMoves.add(new Pair<>(i, j));
//                    } else if (boardClass.board[i][j].getPlayer() == pieceChosen.getPlayer()) {
//                        break;
//                    } else {
//                        availableMoves.add(new Pair<>(i, j));
//                        break;
//                    }
//                }
//
//
//                for (int i = PieceRow - 1, j = PieceColumn - 1; i >= 0 && j >= 0; i--, j--) {
//                    if (boardClass.board[i][j].getClass().getSimpleName().equals("EmptyTile")) {
//                        availableMoves.add(new Pair<>(i, j));
//                    } else if (boardClass.board[i][j].getPlayer() == pieceChosen.getPlayer()) {
//                        break;
//                    } else {
//                        availableMoves.add(new Pair<>(i, j));
//                        break;
//                    }
//                }
//
//
//                for (int i = PieceRow + 1, j = PieceColumn - 1; i < 8 && j >= 0; i++, j--) {
//                    if (boardClass.board[i][j].getClass().getSimpleName().equals("EmptyTile")) {
//                        availableMoves.add(new Pair<>(i, j));
//                    } else if (boardClass.board[i][j].getPlayer() == pieceChosen.getPlayer()) {
//                        break;
//                    } else {
//                        availableMoves.add(new Pair<>(i, j));
//                        break;
//                    }
//                }
//
//                for (int i = PieceRow - 1, j = PieceColumn + 1; i >= 0 && j < 8; i--, j++) {
//                    if (boardClass.board[i][j].getClass().getSimpleName().equals("EmptyTile")) {
//                        availableMoves.add(new Pair<>(i, j));
//                    } else if (boardClass.board[i][j].getPlayer() == pieceChosen.getPlayer()) {
//                        break;
//                    } else {
//                        availableMoves.add(new Pair<>(i, j));
//                        break;
//                    }
//                }
//
//                break;
//
//
//            case "Queen":
//
//
//                for (int i = PieceRow + 1; i < 8; i++) {
//                    if (boardClass.board[i][PieceColumn].getClass().getSimpleName().equals("EmptyTile")) {
//                        availableMoves.add(new Pair<>(i, PieceColumn));
//                    } else if (boardClass.board[i][PieceColumn].getPlayer() == pieceChosen.getPlayer()) {
//                        break;
//                    } else {
//                        availableMoves.add(new Pair<>(i, PieceColumn));
//                        break;
//                    }
//                }
//
//                for (int i = PieceRow - 1; i >= 0; i--) {
//                    if (boardClass.board[i][PieceColumn].getClass().getSimpleName().equals("EmptyTile")) {
//                        availableMoves.add(new Pair<>(i, PieceColumn));
//                    } else if (boardClass.board[i][PieceColumn].getPlayer() == pieceChosen.getPlayer()) {
//                        break;
//                    } else {
//                        availableMoves.add(new Pair<>(i, PieceColumn));
//                        break;
//                    }
//                }
//
//
//                for (int i = PieceColumn - 1; i >= 0; i--) {
//                    if (boardClass.board[PieceRow][i].getClass().getSimpleName().equals("EmptyTile")) {
//                        availableMoves.add(new Pair<>(PieceRow, i));
//                    } else if (boardClass.board[PieceRow][i].getPlayer() == pieceChosen.getPlayer()) {
//                        break;
//                    } else {
//                        availableMoves.add(new Pair<>(PieceRow, i));
//                        break;
//                    }
//                }
//
//
//                for (int i = PieceColumn + 1; i < 8; i++) {
//                    if (boardClass.board[PieceRow][i].getClass().getSimpleName().equals("EmptyTile")) {
//                        availableMoves.add(new Pair<>(PieceRow, i));
//                    } else if (boardClass.board[PieceRow][i].getPlayer() == pieceChosen.getPlayer()) {
//                        break;
//                    } else {
//                        availableMoves.add(new Pair<>(PieceRow, i));
//                        break;
//                    }
//                }
//
//
//                for (int i = PieceRow + 1, j = PieceColumn + 1; i < 8 && j < 8; i++, j++) {
//                    if (boardClass.board[i][j].getClass().getSimpleName().equals("EmptyTile")) {
//                        availableMoves.add(new Pair<>(i, j));
//                    } else if (boardClass.board[i][j].getPlayer() == pieceChosen.getPlayer()) {
//                        break;
//                    } else {
//                        availableMoves.add(new Pair<>(i, j));
//                        break;
//                    }
//                }
//
//
//                for (int i = PieceRow - 1, j = PieceColumn - 1; i >= 0 && j >= 0; i--, j--) {
//                    if (boardClass.board[i][j].getClass().getSimpleName().equals("EmptyTile")) {
//                        availableMoves.add(new Pair<>(i, j));
//                    } else if (boardClass.board[i][j].getPlayer() == pieceChosen.getPlayer()) {
//                        break;
//                    } else {
//                        availableMoves.add(new Pair<>(i, j));
//                        break;
//                    }
//                }
//
//
//                for (int i = PieceRow + 1, j = PieceColumn - 1; i < 8 && j >= 0; i++, j--) {
//                    if (boardClass.board[i][j].getClass().getSimpleName().equals("EmptyTile")) {
//                        availableMoves.add(new Pair<>(i, j));
//                    } else if (boardClass.board[i][j].getPlayer() == pieceChosen.getPlayer()) {
//                        break;
//                    } else {
//                        availableMoves.add(new Pair<>(i, j));
//                        break;
//                    }
//                }
//
//                for (int i = PieceRow - 1, j = PieceColumn + 1; i >= 0 && j < 8; i--, j++) {
//                    if (boardClass.board[i][j].getClass().getSimpleName().equals("EmptyTile")) {
//                        availableMoves.add(new Pair<>(i, j));
//                    } else if (boardClass.board[i][j].getPlayer() == pieceChosen.getPlayer()) {
//                        break;
//                    } else {
//                        availableMoves.add(new Pair<>(i, j));
//                        break;
//                    }
//                }
//
//                break;
//
//
//            //dopracowac
//            case "WhiteKing":
//            case "BlackKing":
//
//                for (int i = 0; i < allowedPieceMovesFromVector.size(); i++) {
//
//                    int allowedMovesRow = 0;
//                    int allowedMovesColumn = 0;
//
//
//                    allowedMovesRow = allowedPieceMovesFromVector.get(i).getKey() + PieceRow;
//                    allowedMovesColumn = allowedPieceMovesFromVector.get(i).getValue() + PieceColumn;
//
//
//                    if (allowedMovesRow >= 0
//                            && allowedMovesColumn >= 0
//                            && allowedMovesRow <= 7
//                            && allowedMovesColumn <= 7) {
////                        System.out.println(boardClass.blackPlayerAttackBoard[allowedMovesRow][allowedMovesColumn].getClass().getSimpleName());
//
//                        Piece check = boardClass.getPiece(allowedMovesRow, allowedMovesColumn);
//                        String nameOfCheck = check.getClass().getSimpleName();
//
//                        if (nameOfChosenPiece.equals("WhiteKing")) {
////                            if (!boardClass.blackPlayerAttackBoard[allowedMovesRow][allowedMovesColumn].getClass().getSimpleName().equals("Mark_MovableTile")) {
//                            if (nameOfCheck.equals("EmptyTile")) {
//                                availableMoves.add(new Pair<>(allowedMovesRow, allowedMovesColumn));
//                            } else if (check.getPlayer() != pieceChosen.getPlayer()) {
//                                availableMoves.add(new Pair<>(allowedMovesRow, allowedMovesColumn));
//                            }
////                            }
//                        } else if (nameOfChosenPiece.equals("BlackKing")) {
//                            if (!boardClass.whitePlayerAttackBoard[allowedMovesRow][allowedMovesColumn].getClass().getSimpleName().equals("Mark_MovableTile")) {
//                                if (nameOfCheck.equals("EmptyTile")) {
//                                    availableMoves.add(new Pair<>(allowedMovesRow, allowedMovesColumn));
//                                } else if (check.getPlayer() != pieceChosen.getPlayer()) {
//                                    availableMoves.add(new Pair<>(allowedMovesRow, allowedMovesColumn));
//                                }
//                            }
//                        }
//
//
//                    }
                }
//
//
//                /**
//                 * castling
//                 */
//
//                if (nameOfChosenPiece.equals("WhiteKing")) {
//                    if (boardClass.getPiece(7, 7).getClass().getSimpleName().equals("Rook")
//                            && boardClass.getPiece(7, 6).getClass().getSimpleName().equals("EmptyTile")
//                            && !boardClass.blackPlayerAttackBoard[7][6].getClass().getSimpleName().equals("Mark_MovableTile")
//                            && boardClass.getPiece(7, 5).getClass().getSimpleName().equals("EmptyTile")
//                            && !boardClass.blackPlayerAttackBoard[7][5].getClass().getSimpleName().equals("Mark_MovableTile")
//                            && !boardClass.blackPlayerAttackBoard[7][4].getClass().getSimpleName().equals("Mark_MovableTile")
//                            && ((Rook) boardClass.getPiece(7, 7)).getCastling()
//                            && ((WhiteKing) pieceChosen).getCastling()) {
//                        availableMoves.add(new Pair<>(7, 6));
//                    }
//                    if (boardClass.getPiece(7, 0).getClass().getSimpleName().equals("Rook")
//                            && boardClass.getPiece(7, 1).getClass().getSimpleName().equals("EmptyTile")
//                            && !boardClass.blackPlayerAttackBoard[7][1].getClass().getSimpleName().equals("Mark_MovableTile")
//                            && boardClass.getPiece(7, 2).getClass().getSimpleName().equals("EmptyTile")
//                            && !boardClass.blackPlayerAttackBoard[7][2].getClass().getSimpleName().equals("Mark_MovableTile")
//                            && boardClass.getPiece(7, 3).getClass().getSimpleName().equals("EmptyTile")
//                            && !boardClass.blackPlayerAttackBoard[7][3].getClass().getSimpleName().equals("Mark_MovableTile")
//                            && !boardClass.blackPlayerAttackBoard[7][4].getClass().getSimpleName().equals("Mark_MovableTile")
//                            && ((Rook) boardClass.getPiece(7, 0)).getCastling()
//                            && ((WhiteKing) pieceChosen).getCastling()) {
//                        availableMoves.add(new Pair<>(7, 2));
//                    }
//                }
//
//                if (nameOfChosenPiece.equals("BlackKing")) {
//                    if (boardClass.getPiece(0, 7).getClass().getSimpleName().equals("Rook")
//                            && boardClass.getPiece(0, 6).getClass().getSimpleName().equals("EmptyTile")
//                            && !boardClass.whitePlayerAttackBoard[0][6].getClass().getSimpleName().equals("Mark_MovableTile")
//                            && boardClass.getPiece(0, 5).getClass().getSimpleName().equals("EmptyTile")
//                            && !boardClass.whitePlayerAttackBoard[0][5].getClass().getSimpleName().equals("Mark_MovableTile")
//                            && !boardClass.whitePlayerAttackBoard[0][4].getClass().getSimpleName().equals("Mark_MovableTile")
//                            && ((Rook) boardClass.getPiece(0, 7)).getCastling()
//                            && ((BlackKing) pieceChosen).getCastling()) {
//                        availableMoves.add(new Pair<>(0, 6));
//                    }
//                    if (boardClass.getPiece(0, 0).getClass().getSimpleName().equals("Rook")
//                            && boardClass.getPiece(0, 1).getClass().getSimpleName().equals("EmptyTile")
//                            && !boardClass.whitePlayerAttackBoard[0][1].getClass().getSimpleName().equals("Mark_MovableTile")
//                            && boardClass.getPiece(0, 2).getClass().getSimpleName().equals("EmptyTile")
//                            && !boardClass.whitePlayerAttackBoard[0][2].getClass().getSimpleName().equals("Mark_MovableTile")
//                            && boardClass.getPiece(0, 3).getClass().getSimpleName().equals("EmptyTile")
//                            && !boardClass.whitePlayerAttackBoard[0][3].getClass().getSimpleName().equals("Mark_MovableTile")
//                            && !boardClass.whitePlayerAttackBoard[0][4].getClass().getSimpleName().equals("Mark_MovableTile")
//                            && ((Rook) boardClass.getPiece(0, 0)).getCastling()
//                            && ((BlackKing) pieceChosen).getCastling()) {
//                        availableMoves.add(new Pair<>(0, 2));
//                    }
//                }
//
//
//        }
//
//
//        if (nameOfChosenPiece.equals("BlackPawn") || nameOfChosenPiece.equals("WhitePawn")) {
//
//
//            for (int i = 0; i < allowedPieceMovesFromVector.size(); i++) {
//                int allowedMovesRow = 0;
//                int allowedMovesColumn = 0;
//
//                allowedMovesRow = allowedPieceMovesFromVector.get(i).getKey() + PieceRow;
//                allowedMovesColumn = allowedPieceMovesFromVector.get(i).getValue() + PieceColumn;
//
//                // dodajemy ruchy dozwolone
//
//                if (allowedMovesRow >= 0
//                        && allowedMovesColumn >= 0
//                        && allowedMovesRow <= 7
//                        && allowedMovesColumn <= 7) {
//
//                    Piece check = boardClass.getPiece(allowedMovesRow, allowedMovesColumn);
//                    String nameOfCheck = check.getClass().getSimpleName();
//
//
//                    if (nameOfChosenPiece.equals("BlackPawn") || nameOfChosenPiece.equals("WhitePawn")) {
//
//                        //tutaj bez petli (en passant)
//                        if (nameOfChosenPiece.equals("WhitePawn") && !(attack.equals("w") || attack.equals("b"))) {
//                            if (PieceColumn - 1 >= 0) {
//                                if (boardClass.getPiece(PieceRow, PieceColumn - 1).getClass().getSimpleName().equals("BlackPawn")) {
//                                    if (((BlackPawn) boardClass.board[PieceRow][PieceColumn - 1]).getEnPassant()) {
//                                        availableMoves.add(new Pair<>(PieceRow - 1, PieceColumn - 1));
//                                    }
//                                }
//                            }
//
//                            if (PieceColumn + 1 < 8
//                                    && boardClass.getPiece(PieceRow, PieceColumn + 1).getClass().getSimpleName().equals("BlackPawn")
//                                    && ((BlackPawn) boardClass.board[PieceRow][PieceColumn + 1]).getEnPassant()) {
//                                availableMoves.add(new Pair<>(PieceRow - 1, PieceColumn + 1));
//                            }
//                        }
//
//
//                        //tutaj bez petli (en passant)
//                        if (nameOfChosenPiece.equals("BlackPawn") && !(attack.equals("w") || attack.equals("b"))) {
//                            if (PieceColumn - 1 >= 0) {
//                                if (boardClass.getPiece(PieceRow, PieceColumn - 1).getClass().getSimpleName().equals("WhitePawn")) {
//                                    if (((WhitePawn) boardClass.board[PieceRow][PieceColumn - 1]).getEnPassant()) {
//                                        availableMoves.add(new Pair<>(PieceRow + 1, PieceColumn - 1));
//                                    }
//                                }
//                            }
//
//                            if (PieceColumn + 1 < 8
//                                    && boardClass.getPiece(PieceRow, PieceColumn + 1).getClass().getSimpleName().equals("WhitePawn")
//                                    && ((WhitePawn) boardClass.board[PieceRow][PieceColumn + 1]).getEnPassant()) {
//                                availableMoves.add(new Pair<>(PieceRow + 1, PieceColumn + 1));
//                            }
//                        }
//
//
//                        if (nameOfCheck.equals("EmptyTile")
//                                && PieceColumn == allowedMovesColumn) {
//                            switch (nameOfChosenPiece) {
//                                case "BlackPawn":
//                                    if (((BlackPawn) boardClass.board[PieceRow][PieceColumn]).getFirstMove()) {
//                                        if (boardClass.board[PieceRow + 1][PieceColumn].getClass().getSimpleName().equals("EmptyTile")) {
//                                            if (!(attack.equals("w") || attack.equals("b")))
//                                                availableMoves.add(new Pair<>(allowedMovesRow, allowedMovesColumn));
//                                        }
//                                    } else {
//                                        if (!(PieceRow == allowedMovesRow - 2)) {
//                                            if (!(attack.equals("w") || attack.equals("b")))
//                                                availableMoves.add(new Pair<>(allowedMovesRow, allowedMovesColumn));
//                                        }
//                                    }
//
//
//                                    break;
//                                case "WhitePawn":
//                                    if (((WhitePawn) boardClass.board[PieceRow][PieceColumn]).getFirstMove()) {
//                                        if (boardClass.board[PieceRow - 1][PieceColumn].getClass().getSimpleName().equals("EmptyTile")) {
//                                            availableMoves.add(new Pair<>(allowedMovesRow, allowedMovesColumn));
//                                        }
//                                    } else {
//                                        if (!(PieceRow == allowedMovesRow + 2)) {
//                                            availableMoves.add(new Pair<>(allowedMovesRow, allowedMovesColumn));
//                                        }
//
//                                    }
//                                    break;
//                                default:
//                                    break;
//                            }
//
//
//                        } else if (PieceColumn != allowedMovesColumn && !nameOfCheck.equals("EmptyTile")) {
//                            boolean basicPlayer = pieceChosen.getPlayer();
//                            boolean player = check.getPlayer();
//
//                            if (basicPlayer != player) {
//                                availableMoves.add(new Pair<>(allowedMovesRow, allowedMovesColumn));
//                            }
//
//                        }
//                        if (PieceColumn != allowedMovesColumn && (attack.equals("w") || attack.equals("b"))) {
//                            availableMoves.add(new Pair<>(allowedMovesRow, allowedMovesColumn));
//                        }
//
//                    }
//
//                }
//
//            }
//        }


        for (Object availableMove : availableMoves) {
//            System.out.println(availableMove);
        }

        if (playerAttackBoard.equals("w")) {
            boardClass.addPossibleMoves(availableMoves, "w");
        } else if (playerAttackBoard.equals("b")) {
            boardClass.addPossibleMoves(availableMoves, "b");
        } else if (playerAttackBoard.equals("f")) {
            boardClass.addPossibleMoves(availableMoves, "f");
        } else {
//            System.out.println(pieceChosen.getClass().getSimpleName());
//            System.out.println("allowed moves: " + allowedPieceMovesFromVector.size());
            boardClass.addPossibleMoves(availableMoves, "movable board");
//            System.out.println("movable boardClass: ");
//            boardClass.printBoard(false);
        }


        return availableMoves;
    }

    private void leftRook(int PieceRow, int PieceColumn, Piece[] pieces, Piece pieceChosen, List<Pair<Integer, Integer>> availableMoves) {
        for (int i = PieceColumn - 1; i >= 0; i--) {
            if (pieces[i].getClass().getSimpleName().equals("EmptyTile")) {
                availableMoves.add(new Pair<>(PieceRow, i));
            } else if (pieces[i].getPlayer() == pieceChosen.getPlayer()) {
                break;
            } else {
                availableMoves.add(new Pair<>(PieceRow, i));
                break;
            }
        }
    }


}
