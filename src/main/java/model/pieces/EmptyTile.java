package model.pieces;

import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;

public class EmptyTile implements Piece{
    @Override
    public List<Pair<Integer, Integer>> move() {
        return new LinkedList<>();
    }
}
