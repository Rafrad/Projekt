package Models.Pieces;


import Exceptions.PlayerColorException;
import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;

public class Knight implements Piece {
    boolean player;

    //TODO: UNIT TESTS
    public Knight() throws PlayerColorException {
        throw new PlayerColorException("Knight must be white or black!");
    }

    //TODO: UNIT TESTS
    public Knight(boolean playerColor) throws PlayerColorException {
        this.player = playerColor;
    }

    @Override
    public List<Pair<Integer, Integer>> move() {
        List<Pair<Integer, Integer>> allowedMoves = new LinkedList<>();
        allowedMoves.add(new Pair<>(2, 1));
        allowedMoves.add(new Pair<>(2, -1));
        allowedMoves.add(new Pair<>(1, 2));
        allowedMoves.add(new Pair<>(-1, 2));
        allowedMoves.add(new Pair<>(-1, -2));
        allowedMoves.add(new Pair<>(-2, -1));
        allowedMoves.add(new Pair<>(-2, 1));
        allowedMoves.add(new Pair<>(1, -2));
        return allowedMoves;
    }

    @Override
    public boolean getPlayer() {
        return player;
    }
}
