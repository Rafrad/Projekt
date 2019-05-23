package main.Models.Pieces;


import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;


public class WhitePawn implements Piece {
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

    public boolean getEnPassant() {
        return enPassant;
    }

    public boolean getPromotion() {
        return promotion;
    }

    public void setFirstMove(boolean firstMove) {
        this.firstMove = firstMove;
    }

    public void setEnPassant(boolean enPassant) {
        this.enPassant = enPassant;
    }

    public void setPromotion(boolean promotion) {
        this.promotion = promotion;
    }


}