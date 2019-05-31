package project.chess.Models.Pieces;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;

import static project.chess.Models.Pieces.BlackKing.getPairs;

public class WhiteKing implements Piece {
    private boolean check;
    private boolean castling;
    private char unicode;
    private Image image;


    public WhiteKing() {
        castling = true;
        check = false;
        unicode = 0x2654;
        image = new Image("Images/white_king.png");
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
    public ImageView getImageView() {
        return new ImageView(image);
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
