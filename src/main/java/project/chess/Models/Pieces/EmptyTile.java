package project.chess.Models.Pieces;


import javafx.scene.image.ImageView;
import javafx.util.Pair;

import java.util.List;

public class EmptyTile implements Piece {

    @Override
    public List<Pair<Integer, Integer>> move() {
        return null;
    }

    @Override
    public boolean getPlayer() {
        return false;
    }

    @Override
    public void print() {
        System.out.print("x ");
    }

    @Override
    public char getUnicode() {
        return 0;
    }

    @Override
    public ImageView getImageView() {
        return null;
    }

}