package main.Models.Pieces;


import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;


public class BlackPawn implements Piece {
    private boolean player;
    private boolean firstMove;
    private boolean enPassant;

    public BlackPawn() {
        player = false;
        firstMove = true;
        enPassant = false;
    }

    @Override
    public List<Pair<Integer, Integer>> move() {
        LinkedList<Pair<Integer, Integer>> allowedMoves = new LinkedList<>();

        allowedMoves.add(new Pair<>(1, 0));
        allowedMoves.add(new Pair<>(1, 1));
        allowedMoves.add(new Pair<>(1, -1));
        allowedMoves.add(new Pair<>(2, 0));


        return allowedMoves;
    }

    @Override
    public boolean getPlayer() {
        return player;
    }

    public boolean getFirstMove() {
        return firstMove;
    }

    public void setFirstMove(boolean move) {
        firstMove = move;
    }

    public boolean getEnPassant() {
        return enPassant;
    }

    public void setEnPassant(boolean bool) {
        enPassant = bool;
    }


}
