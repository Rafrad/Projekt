package model.pieces;

import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;

//pionek
public class Pawn implements Piece {
    @Override
    public List<Pair<Integer, Integer>> move() {
        LinkedList<Pair<Integer, Integer>> allowedMoves = new LinkedList<>();
        allowedMoves.add(new Pair<>(1, 0));
        allowedMoves.add(new Pair<>(2, 0));
        allowedMoves.add(new Pair<>(1, 1));
        allowedMoves.add(new Pair<>(-1, -1));


        return allowedMoves;
    }
}
