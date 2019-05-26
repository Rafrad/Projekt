package project.chess.Models.Pieces;


import javafx.util.Pair;

import java.util.List;

//*** move() ***
//we  start in [0, 0] and then moves are calculated
//treating moves like vectors
//eg. black pawn can move to
//[1, 0]
//[2, 0]
//[1, -1]
//[1, 1]

//*** getPlayer() ***
//variable player in every piece define where piece belongs to
//true - white player
//false - black player

//*** getFirstMove() ***
//checks if a piece made its first move

//*** setFirstMove() ***
//sets first move to true

//*** getCheck() ***
//returns true if king is checked, false otherwise

//*** enPassant() ***
//return true if pawn is vulnerable to enPassant, false otherwise

public interface Piece {
    List<Pair<Integer, Integer>> move();

    boolean getPlayer();
    void print(boolean player);
}
