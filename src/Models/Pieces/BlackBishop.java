package Models.Pieces;

import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;

public class BlackBishop implements Piece {
    @Override
    public List<Pair<Integer, Integer>> move() {
        List<Pair<Integer, Integer>> allowedMoves = new LinkedList<>();
        for (int i = -7; i <= 7; i++) {
            if(i != 0) {
                allowedMoves.add(new Pair<>(i, i));
                allowedMoves.add(new Pair<>(-i, i));
            }
        }

        return allowedMoves;
    }
}