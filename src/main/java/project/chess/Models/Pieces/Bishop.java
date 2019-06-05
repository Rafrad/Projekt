package project.chess.Models.Pieces;
import project.chess.Exceptions.PlayerColorException;
import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;

public class Bishop implements Piece {
    private boolean player;
    private char unicode;

    public Bishop() throws PlayerColorException{
        throw new PlayerColorException("Bishop must be white or black!");
    }

    public Bishop(boolean player) {
        this.player = player;

        if(player) {
            unicode = 0x2657;
        } else {
            unicode = 0x265D;
        }
    }

    /**
     * Bishop's moves are subset of Queen's
     * @see Queen HAHAHAHA
     * @return kappa
     */

    @Override
    public List<Pair<Integer, Integer>> move() {
        List<Pair<Integer, Integer>> allowedMoves = new LinkedList<>();
        return getBishopPairs(allowedMoves);
    }

    @Override
    public boolean getPlayer() {
        return player;
    }

    @Override
    public void print() {
        if (player) {
            System.out.print("I ");
        } else {
            System.out.print("i ");
        }
    }

    @Override
    public char getUnicode() {
        return unicode;
    }

    /**
     * Bishop's moves are subset of Queen's
     * @see Queen HAHAHAHA
     */

    static List<Pair<Integer, Integer>> getBishopPairs(List<Pair<Integer, Integer>> allowedMoves) {
        for (int i = -7; i <= 7; i++) {
            if(i != 0) {
                allowedMoves.add(new Pair<>(i, i));
                allowedMoves.add(new Pair<>(-i, i));
            }
        }
        return allowedMoves;
    }

}
