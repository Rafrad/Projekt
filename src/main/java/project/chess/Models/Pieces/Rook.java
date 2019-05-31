package project.chess.Models.Pieces;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import project.chess.Exceptions.PlayerColorException;
import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;

public class Rook implements Piece {
    private boolean player;
    private boolean castling;

    private char unicode;
    private Image image;

    public Rook() throws PlayerColorException {
        throw new PlayerColorException("Rook must be white or black!");
    }

    public Rook(boolean color) {
        player = color;
        castling = true;

        if(player) {
            unicode = 0x2656;
            image = new Image("Images/white_rook.png");
        } else {
            unicode = 0x265C;
            image = new Image("Images/black_rook.png");
        };
    }

    @Override
    public List<Pair<Integer, Integer>> move() {
        List<Pair<Integer, Integer>> allowedMoves = new LinkedList<>();

        for (int i = -7; i <= 7; i++) {
            if (i != 0) {
                allowedMoves.add(new Pair<>(0, i));
                allowedMoves.add(new Pair<>(i, 0));
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
            System.out.print("R ");
        } else {
            System.out.print("r ");
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

    public boolean getCastling() {
        return castling;
    }

    public void setCastling(boolean castling) {
        this.castling = castling;
    }
}
