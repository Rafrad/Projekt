package project.chess.Models.Pieces;

import javafx.util.Pair;

import java.util.List;

public interface Piece {
    /**
     * We  start in (0, 0) and then moves are calculated;
     * treating moves like vectors
     * eg. black pawn can move to
     * [1, 0]
     * [2, 0]
     * [1, -1]
     * [1, 1]
     *
     * @return movement of the piece, starting from (0, 0) point
     */

    List<Pair<Integer, Integer>> move();

    /**
     * Variable player defines where piece belongs to
     *
     * @return true when player is white, false otherwise (empty tile and movable tile are false)
     */

    boolean getPlayer();

    /**
     * Prints in console unique letter for piece.
     * Made for development and tests.
     * Black player has lowercase letters, white player has uppercase letters
     * <p>
     * Bishop - b or B
     * King - k or K
     * Knight - n or N
     * Queen - q or Q
     * Rook - r or R
     * White Pawn - W
     * Black Pawn - P
     * <p>
     * Empty Tile - x
     * Mark Movable Tile - m
     */

    void print();

    /**
     * Get unicode which is shown in match history.
     *
     * @return unique unicode for every piece, shown in match history
     */

    char getUnicode();

    /**
     * Made for tests
     *
     * @param obj object to check if is equal
     * @return returns true if equal, false otherwise
     */

    boolean equals(Object obj);
}
