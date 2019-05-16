package main.Models.Pieces;

import main.Exceptions.PlayerColorException;
import javafx.util.Pair;


import java.util.LinkedList;
import java.util.List;

public class Bishop implements Piece {
    private boolean player;
    //true - white
    //false - black

    public Bishop() throws PlayerColorException{
        throw new PlayerColorException("Bishop must have a color!");
    }

    public Bishop(boolean playerColor) throws PlayerColorException {
        this.player = playerColor;
    }

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

    @Override
    public boolean getPlayer() {
        return player;
    }
}
