package project.chess.Models.Pieces;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import project.chess.Exceptions.PlayerColorException;
import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;

public class Knight implements Piece {
    private boolean player;
    private char unicode;
    private Image image;

    public Knight() throws PlayerColorException {
        throw new PlayerColorException("Knight must be white or black!");
    }

    public Knight(boolean player) throws PlayerColorException {
        this.player = player;

        if(player) {
            unicode = 0x2658;
            image = new Image("Images/white_knight.png");
        } else {
            unicode = 0x265E;
            image = new Image("Images/black_knight.png");
        }
    }

    @Override
    public List<Pair<Integer, Integer>> move() {
        List<Pair<Integer, Integer>> allowedMoves = new LinkedList<>();
        allowedMoves.add(new Pair<>(2, 1));
        allowedMoves.add(new Pair<>(2, -1));
        allowedMoves.add(new Pair<>(1, 2));
        allowedMoves.add(new Pair<>(-1, 2));
        allowedMoves.add(new Pair<>(-1, -2));
        allowedMoves.add(new Pair<>(-2, -1));
        allowedMoves.add(new Pair<>(-2, 1));
        allowedMoves.add(new Pair<>(1, -2));
        return allowedMoves;
    }

    @Override
    public boolean getPlayer() {
        return player;
    }

    @Override
    public void print() {
        if (player) {
            System.out.print("K ");
        } else {
            System.out.print("k ");
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
