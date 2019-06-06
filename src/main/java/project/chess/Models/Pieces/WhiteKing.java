package project.chess.Models.Pieces;

import javafx.util.Pair;

import java.util.List;

import static project.chess.Models.Pieces.BlackKing.getPairs;

public class WhiteKing implements Piece {
    private boolean castling;
    private char unicode;


    public WhiteKing() {
        castling = true;
        unicode = 0x2654;
    }

    /**
     * /**
     * We  start in (0, 0) and then moves are calculated;
     * treating moves like vectors
     * eg. black pawn can move to
     * [1, 0]
     * [2, 0]
     * [1, -1]
     * [1, 1]
     * <p>
     * Black and White king moves are the same.
     *
     * @return king's movement
     * @see BlackKing
     */

    @Override
    public List<Pair<Integer, Integer>> move() {
        return getPairs();
    }

    @Override
    public boolean getPlayer() {
        return true;
    }

    @Override
    public void print() {
        System.out.print("Y ");
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
