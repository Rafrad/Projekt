package Models;


import Exceptions.PlayerColorException;
import Exceptions.WrongBoardException;
import Models.Pieces.Piece;
import javafx.util.Pair;

import java.util.*;

public class Move {
    Board board;

    Move() throws WrongBoardException { }

    Move(Board boardTmp) {
        this.board = boardTmp;
    }

    public List<Pair<Integer, Integer>> CalculateMoves(int a, int b) {

        Piece piece = board.getPiece(a, b);
        //
        System.out.println(piece.getClass().getSimpleName());
        //
        String nameOfPiece = piece.getClass().getSimpleName();
        List<Pair<Integer, Integer>> allowedMoves = piece.move();

        List<Pair<Integer, Integer>> availableMoves = new LinkedList<>();
        System.out.println("allowed moves: " + allowedMoves.size());
        for (int i = 0; i < allowedMoves.size(); i++) {
            int key = allowedMoves.get(i).getKey() + a;
            int value = allowedMoves.get(i).getValue() + b;
//DODAJEMY RUCHY
            if(key <0|| value < 0 || key > 7 || value > 7) {

            } else {
                Piece check = board.getPiece(key, value);
                String nameOfCheck = check.getClass().getSimpleName();
                if(nameOfCheck.equals("EmptyTile") && b == value) {
//                    availableMoves.add(new Pair<>(key, value));
//                    System.out.println("?????????????????????????????");


                    switch (nameOfPiece) {
                        case "BlackPawn" :
                            //TODO :promotion, bicie z przelotem
                                if (board.board[a][b].getFirstMove()) {
                                    availableMoves.add(new Pair<>(key, value));
                                } else {
                                    if(!(a == key - 2)) {
                                        availableMoves.add(new Pair<>(key, value));
                                    }

                                }
                            break;
                        default:
                            break;
                    }


                } else if(b != value && !nameOfCheck.equals("EmptyTile")) {
                    boolean basicPlayer = piece.getPlayer();
                    boolean player = check.getPlayer();

//                    if(basicPlayer != player) { // ogolne warunki dla kazdej figury


//                    }

                }

            }

//            System.out.println(allowedMoves.get(i));
        }

        Iterator iterator = availableMoves.iterator();

        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        board.AddPossibleMoves(availableMoves);
        board.PrintMovableBoard();


        return availableMoves;
    }
    
}
