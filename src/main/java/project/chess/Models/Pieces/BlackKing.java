package project.chess.Models.Pieces;

import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;

public class BlackKing implements Piece {
    private boolean castling;
    private boolean check;

    public BlackKing() {
        castling = true;
        check = false;
    }

    @Override
    public List<Pair<Integer, Integer>> move() {
        List<Pair<Integer, Integer>> allowedMoves = new LinkedList<>();
        allowedMoves.add(new Pair<>(1, 0));
        allowedMoves.add(new Pair<>(1, 1));
        allowedMoves.add(new Pair<>(0, 1));
        allowedMoves.add(new Pair<>(-1, 0));
        allowedMoves.add(new Pair<>(-1, -1));
        allowedMoves.add(new Pair<>(0, -1));
        allowedMoves.add(new Pair<>(1, -1));
        allowedMoves.add(new Pair<>(-1, 1));

        return allowedMoves;
    }
    @Override
    public boolean getPlayer() {
        return false;
    }

    @Override
    public void print() {
        System.out.print("y ");
    }

    public boolean getCastling() {
        return castling;
    }

    public boolean getCheck() {
        return check;
    }

    public void setCastling(boolean castling) {
        this.castling = castling;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
