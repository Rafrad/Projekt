package Models.Pieces;


import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;


public class BlackPawn implements Piece {
    boolean player;

    public BlackPawn() {
        player = false;
    }

    @Override
    public List<Pair<Integer, Integer>> move() {
        LinkedList<Pair<Integer, Integer>> allowedMoves = new LinkedList<>();

        //TODO: UNIT TESTS
        allowedMoves.add(new Pair<>(1, 0));
        allowedMoves.add(new Pair<>(1, 1));
        allowedMoves.add(new Pair<>(1, -1));
        allowedMoves.add(new Pair<>(2, 0));



        return allowedMoves;
    }

    @Override
    public boolean getPlayer() {
        return player;
    }
}
