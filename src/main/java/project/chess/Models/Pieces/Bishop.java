package project.chess.Models.Pieces;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import project.chess.Exceptions.PlayerColorException;
import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;

public class Bishop implements Piece {
    private boolean player;
    private char unicode;
    private Image image;

    public Bishop() throws PlayerColorException{
        throw new PlayerColorException("Bishop must be white or black!");
    }

    public Bishop(boolean player) {
        this.player = player;

        if(player) {
            unicode = 0x2657;
            image = new Image("Images/white_bishop.png");
        } else {
            unicode = 0x265D;
            image = new Image("Images/black_bishop.png");
        }
    }

    @Override
    public List<Pair<Integer, Integer>> move() {
        List<Pair<Integer, Integer>> allowedMoves = new LinkedList<>();
        return getBishopPairs(allowedMoves);
    }

    static List<Pair<Integer, Integer>> getBishopPairs(List<Pair<Integer, Integer>> allowedMoves) {
        for (int i = -7; i <= 7; i++) {
            if(i != 0) {
                allowedMoves.add(new Pair<>(i, i));
                allowedMoves.add(new Pair<>(-i, i));
            }
        }
        return allowedMoves;
    }

    @Override
    public boolean getPlayer() {
        return player;
    }

    @Override
    public void print() {
        if (player) {
            System.out.print("I ");
        } else {
            System.out.print("i ");
        }
    }

    @Override
    public char getUnicode() {
        return unicode;
    }

    @Override
    public ImageView getImageView() {
        return new ImageView(image);
    }

}
