package project.chess.Models.Pieces;

import javafx.scene.image.Image;
import javafx.util.Pair;

import java.util.List;

import static project.chess.Models.Pieces.BlackKing.getPairs;

public class WhiteKing implements Piece {
    private boolean check;
    private boolean castling;
    private char unicode;



    public WhiteKing() {
        castling = true;
        check = false;
        unicode = 0x2654;

    }

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

    public boolean getCheck() {
        return check;
    }

    public void setCheck(boolean checkTmp) {
        check = checkTmp;
    }

    public boolean getCastling() {
        return castling;
    }

    public void setCastling(boolean bool) {
        castling = bool;
    }
}
