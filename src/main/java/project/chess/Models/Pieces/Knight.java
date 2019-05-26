package project.chess.Models.Pieces;


import project.chess.Exceptions.PlayerColorException;
import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;

public class Knight implements Piece {
    private boolean player;

    public Knight() throws PlayerColorException {
        throw new PlayerColorException("Knight must be white or black!");
    }

    public Knight(boolean player) throws PlayerColorException {
        this.player = player;
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

    @Override
    public void print() {
        if (player) {
            System.out.print("K ");
        } else {
            System.out.print("k ");
        }
    }
}
