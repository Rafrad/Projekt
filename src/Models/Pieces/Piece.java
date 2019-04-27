package Models.Pieces;

import javafx.util.Pair;
import java.util.List;

//we  start in [0, 0] and we calculate moves
//treating moves like vectors
//eg. pawn can move to
//[1, 0]
//[2, 0]
//[1, -1]
//[1, 1]

//variable player in every piece define where piece belongs to
//true - white player
//false - black player

public interface Piece {
    List<Pair<Integer, Integer>> move();
}
