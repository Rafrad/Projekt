package Models.Pieces;

import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;

//skoczek
public class WhiteKnight implements Piece{
    @Override
    public List<Pair<Integer, Integer>> move() {
        List<Pair<Integer, Integer>> allowedMoves = new LinkedList<>();
        allowedMoves.add(new Pair<>(2, 1));
        allowedMoves.add(new Pair<>(2, -1));
        allowedMoves.add(new Pair<>(1, 2));
        allowedMoves.add(new Pair<>(-1, 2));
        allowedMoves.add(new Pair<>(-2, 1));
        allowedMoves.add(new Pair<>(-2, -1));

        return allowedMoves;
    }
    @Override
    public boolean getPlayer() {
        return false;
    }
}