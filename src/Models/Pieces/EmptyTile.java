package Models.Pieces;


import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;

public class EmptyTile implements Piece {

    @Override
    public List<Pair<Integer, Integer>> move() {
        return null;
    }

    @Override
    public boolean getPlayer() {
        return false;
    }

    @Override
    public boolean getFirstMove() {
        return false;
    }

    @Override
    public void setFirstMove(boolean move) {

    }
}