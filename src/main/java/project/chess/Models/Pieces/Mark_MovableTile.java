package project.chess.Models.Pieces;

import javafx.scene.image.Image;
import javafx.util.Pair;

import java.util.List;

public class Mark_MovableTile implements Piece {


    public Mark_MovableTile() {

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

}
