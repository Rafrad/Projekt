package project.chess.Models.Pieces;

import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;

public class WhiteKing implements Piece {
    private boolean check;
    private boolean castling;

    public WhiteKing() {
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
        return true;
    }

    public boolean getCheck() {
        return check;
    }

    public void setCheck(boolean checkTmp) {
        check = checkTmp;
    }

    public boolean getCastling() {
        return castling;
    }

    public void setCastling(boolean bool) {
        castling = bool;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        return true;
    }
}
