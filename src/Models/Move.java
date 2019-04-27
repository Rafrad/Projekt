package Models;

import Models.Pieces.EmptyTile;
import Models.Pieces.Piece;
import javafx.util.Pair;

import java.net.InterfaceAddress;
import java.util.*;

public class Move {
//    Move() {
//        Board boardTmp = new Board();
//    }

    Board board;

    Move(Board boardTmp) {
        this.board = boardTmp;
    }

    //chyba pozinno zwracac tablice mozliwych ruchow
    public boolean canMove(int a, int b) {

        Piece piece = board.getPiece(a, b);
        System.out.println(piece.getClass().getSimpleName());
        String nameOfPiece = piece.getClass().getSimpleName();

        List<Pair<Integer, Integer>> allowedMoves = piece.move();
//        Iterator iterator = allowedMoves.iterator();

        List<Pair<Integer, Integer>> kappa = new LinkedList<>();
        System.out.println("allowed moves: " + allowedMoves.size());
        for (int i = 0; i < allowedMoves.size(); i++) {
            int key = allowedMoves.get(i).getKey() + a;
            int value = allowedMoves.get(i).getValue() + b;
//            System.out.println(allowedMoves.get(i).getKey());

//DODAJEMY RUCHY

            if(key <0|| value < 0) {

            } else {
                Piece check = board.getPiece(key, value);
                String nameOfCheck = check.getClass().getSimpleName();
                if(nameOfCheck.equals("EmptyTile") && b == value) {
                    kappa.add(new Pair<>(key, value));
                } else if(b != value && !nameOfCheck.equals("EmptyTile")) {
                    kappa.add(new Pair<>(key, value));
                }

            }

//            System.out.println(allowedMoves.get(i));
        }

        Iterator iterator = kappa.iterator();

        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

//        for(int  i = 0; i < kappa.size(); ++i) {
//            if()
//        }

        switch (nameOfPiece) {
            case "Pawn":

                break;
        }

        return false;
    }
}
