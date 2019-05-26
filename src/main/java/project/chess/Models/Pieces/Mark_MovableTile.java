package project.chess.Models.Pieces;

import javafx.util.Pair;

import java.util.List;

public class Mark_MovableTile implements Piece {
    @Override
    public List<Pair<Integer, Integer>> move() {
        return null;
    }

    @Override
    public boolean getPlayer() {
        return false;
    }

    @Override
    public void print() {
        System.out.print("m ");
    }
}
