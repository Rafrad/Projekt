package main.Models.Pieces;


import main.Exceptions.PlayerColorException;
import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;

public class Rook implements Piece{
    boolean player;

    public Rook() throws PlayerColorException {
        throw new PlayerColorException("Rook must have a color (white or black)!");
    }

    public Rook(boolean color) {
        player = color;
    }

    @Override
    public List<Pair<Integer, Integer>> move() {
        List<Pair<Integer, Integer>> allowedMoves = new LinkedList<>();

        for(int i = -7; i <= 7; i++) {
            if(i != 0) {
                allowedMoves.add(new Pair<>(0, i));
                allowedMoves.add(new Pair<>(i, 0));
            }
        }

        return allowedMoves;
    }

    @Override
    public boolean getPlayer() {
        return player;
    }
}
