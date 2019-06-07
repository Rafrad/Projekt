package project.chess.Models.Pieces;

import javafx.util.Pair;

import java.util.List;

/**
 * Defines piece's movement.
 */

public class Mark_MovableTile implements Piece {


    public Mark_MovableTile() {
    }

    /**
     * Mark Movable Tile has no movement.
     *
     * @return null
     */

    @Override
    public List<Pair<Integer, Integer>> move() {
        return null;
    }

    /**
     * Mark Movable Tile has no owner, false is just like ghost variable for this class
     *
     * @return false
     */

    @Override
    public boolean getPlayer() {
        return false;
    }

    @Override
    public void print() {
        System.out.print("m ");
    }

    /**
     * Mark Movable Tile has no unicode, its nothing.
     *
     * @return 0
     */

    @Override
    public char getUnicode() {
        return 0;
    }

}
