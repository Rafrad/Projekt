package project.chess.Models.Pieces;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Pair;

import java.util.List;

public class Mark_MovableTile implements Piece {
    private Image image;

    public Mark_MovableTile() {
        image = new Image("Images/dot.png");
    }
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
        System.out.print("m ");
    }

    @Override
    public char getUnicode() {
        return 0;
    }

    @Override
    public ImageView getImageView() {
        return new ImageView(image);
    }
}
