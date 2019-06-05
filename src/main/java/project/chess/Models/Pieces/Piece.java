package project.chess.Models.Pieces;
import javafx.scene.image.ImageView;
import javafx.util.Pair;
import java.util.List;
//*** getFirstMove() ***
//checks if a piece made its first move

//*** setFirstMove() ***
//sets first move to true

//*** getCheck() ***
//returns true if king is checked, false otherwise

//*** enPassant() ***
//return true if pawn is vulnerable to enPassant, false otherwise

public interface Piece {
    /**
     * we  start in (0, 0) and then moves are calculated;
     * treating moves like vectors
     * eg. black pawn can move to
     * [1, 0]
     * [2, 0]
     * [1, -1]
     * [1, 1]
     *
     * @return list of allowed moves, starting from (0, 0) point
     */

    List<Pair<Integer, Integer>> move();

    /**
     * variable player defines where piece belongs to
     * @return true when player is white, false otherwise (empty tile and movable tile are false)
     */

    boolean getPlayer();

    /**
     * prints in console unique letter for piece
     * made for development and tests
     */

    void print();

    /**
     * to simplify program
     * @return unique unicode for every piece, shown in match history
     */

    char getUnicode();

}
