package project.chess.Models.Pieces;

import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;

public class BlackPawn implements Piece {
    private boolean player;
    private boolean firstMove;
    private boolean enPassant;
    private boolean promotion;

    private char unicode;

    public BlackPawn() {
        player = false;
        firstMove = true;
        enPassant = false;
        promotion = false;

        unicode = 0x265F;

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

    @Override
    public void print() {
        System.out.print("P ");
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
        return getClass() == obj.getClass();
    }

    /**
     * @return true if pawn made its first move, important for en passant and starting rank
     */

    public boolean getFirstMove() {
        return firstMove;
    }

    /**
     * @return true if pawn is vulnerable to enPassant, false otherwise
     */

    public boolean getEnPassant() {
        return enPassant;
    }

    /**
     * @return true if pawn can promote
     */

    public boolean getPromotion() {
        return promotion;
    }

    /**
     * If first move was made, set to false
     *
     * @param firstMove set only to false
     */

    public void setFirstMove(boolean firstMove) {
        this.firstMove = firstMove;
    }

    /**
     * If pawn pass two squares from staring rank set to true, set to false in next turn
     *
     * @param enPassant is vulnerable to en passant
     */

    public void setEnPassant(boolean enPassant) {
        this.enPassant = enPassant;
    }

    /**
     * Control pawn's promote.
     *
     * @param promotion if pawn can promote, set to true
     */

    public void setPromotion(boolean promotion) {
        this.promotion = promotion;
    }

}
