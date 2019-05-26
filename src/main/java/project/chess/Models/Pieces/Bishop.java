package project.chess.Models.Pieces;

import project.chess.Exceptions.PlayerColorException;
import javafx.util.Pair;
import java.util.LinkedList;
import java.util.List;

public class Bishop implements Piece {
    private boolean player;

    public Bishop() throws PlayerColorException{
        throw new PlayerColorException("Bishop must be white or black!");
    }

    public Bishop(boolean player) throws PlayerColorException {
        this.player = player;
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

    @Override
    public void print(boolean player) {
        if (player) {
            System.out.print("I ");
        } else {
            System.out.print("i ");
        }
    }

}
