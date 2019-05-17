package main.Models.Pieces;


import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;


public class WhitePawn implements Piece  {
    private boolean player;
    private boolean firstMove;
    private boolean enPassant;
    private boolean promotion;

    public WhitePawn() {
        player = true;
        firstMove = true;
        enPassant = false;
        promotion = false;
    }

    @Override
    public List<Pair<Integer, Integer>> move() {
        LinkedList<Pair<Integer, Integer>> allowedMoves = new LinkedList<>();

        allowedMoves.add(new Pair<>(-1, 0));
        allowedMoves.add(new Pair<>(-1, -1));
        allowedMoves.add(new Pair<>(-1, 1));
        allowedMoves.add(new Pair<>(-2, 0));

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

    public boolean getPromotion() {
        return promotion;
    }

    public void setPromotion(boolean promoted) {
        promotion = promoted;
    }


}
