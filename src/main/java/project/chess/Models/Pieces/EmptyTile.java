package project.chess.Models.Pieces;


import javafx.util.Pair;

import java.util.List;

/**
 * Defines empty tile.
 */

public class EmptyTile implements Piece {

    /**
     * Empty tile has no movement.
     *
     * @return null
     */

    @Override
    public List<Pair<Integer, Integer>> move() {
        return null;
    }

    /**
     * Empty tile has no owner, false is just like ghost variable for this class
     *
     * @return false
     */

    @Override
    public boolean getPlayer() {
        return false;
    }

    @Override
    public void print() {
        System.out.print("x ");
    }

    /**
     * Empty Tile has no unicode, its nothing.
     *
     * @return 0
     */

    @Override
    public char getUnicode() {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        return getClass() == obj.getClass();
    }

}