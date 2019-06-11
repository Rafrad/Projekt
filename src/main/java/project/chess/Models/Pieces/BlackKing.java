package project.chess.Models.Pieces;

import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;

public class BlackKing implements Piece {
    private boolean castling;
    private char unicode;

    public BlackKing() {
        castling = true;
        unicode = 0x265A;
    }

    @Override
    public List<Pair<Integer, Integer>> move() {
        return getPairs();
    }

    /**
     * Black and White king moves are the same.
     *
     * @return king's movement
     * @see WhiteKing
     */

    static List<Pair<Integer, Integer>> getPairs() {
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
        System.out.print("k ");
    }

    @Override
    public char getUnicode() {
        return unicode;
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

    /**
     * @return true if castling was done, false otherwise
     */

    public boolean getCastling() {
        return castling;
    }

    /**
     * Controls castling, set only to false.
     *
     * @param castling if castling was done, set to false
     */

    public void setCastling(boolean castling) {
        this.castling = castling;
    }

}
