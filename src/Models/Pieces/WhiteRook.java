package Models.Pieces;


import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;

public abstract class WhiteRook implements Piece{
    @Override
    public List<Pair<Integer, Integer>> move() {
        List<Pair<Integer, Integer>> allowedMoves = new LinkedList<>();

        for(int i = -7; i <= 7; i++) {
            allowedMoves.add(new Pair<>(0, i));
            allowedMoves.add(new Pair<>(i, 0));
        }

        return allowedMoves;
    }

    @Override
    public boolean getPlayer() {
        return false;
    }
}
