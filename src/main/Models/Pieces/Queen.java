package main.Models.Pieces;

import main.Exceptions.PlayerColorException;
import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;

public class Queen implements Piece {
    private boolean player;

    public Queen() throws PlayerColorException {
        throw new PlayerColorException("Queen must be white or black!");
    }

    public Queen(boolean player) {
        this.player = player;
    }


    @Override
    public List<Pair<Integer, Integer>> move() {
        List<Pair<Integer, Integer>> allowedMoves = new LinkedList<>();

        for (int i = -7; i <= 7; i++) {
            allowedMoves.add(new Pair<>(0, i));
            allowedMoves.add(new Pair<>(i, 0));
        }

        for (int i = -7; i <= 7; i++) {
            if (i != 0) {
                allowedMoves.add(new Pair<>(i, i));
                allowedMoves.add(new Pair<>(-i, i));
            }
        }

        return allowedMoves;
    }

    @Override
    public boolean getPlayer() {
        return player;
    }
}
