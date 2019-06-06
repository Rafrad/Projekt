package project.chess.Models.Pieces;


import javafx.scene.image.Image;
import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;


public class WhitePawn implements Piece {
    private boolean player;
    private boolean firstMove;
    private boolean enPassant;
    private boolean promotion;
    private char unicode;


    public WhitePawn() {
        player = true;
        firstMove = true;
        enPassant = false;
        promotion = false;

        unicode = 0x2659;

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

    @Override
    public void print() {
        System.out.print("w ");
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

    public char getUnicode() {
        return unicode;
    }


}
