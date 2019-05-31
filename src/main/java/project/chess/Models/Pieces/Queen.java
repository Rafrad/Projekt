package project.chess.Models.Pieces;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import project.chess.Exceptions.PlayerColorException;
import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;

import static project.chess.Models.Pieces.Bishop.getBishopPairs;

public class Queen implements Piece {
    private boolean player;

    private char unicode;
    private Image image;

    public Queen() throws PlayerColorException {
        throw new PlayerColorException("Queen must be white or black!");
    }

    public Queen(boolean player) {
        this.player = player;

        if(player) {
            unicode = 0x2655;
            image = new Image("Images/white_queen.png");
        } else {
            unicode = 0x265B;
            image = new Image("Images/black_queen.png");
        }
    }


    @Override
    public List<Pair<Integer, Integer>> move() {
        List<Pair<Integer, Integer>> allowedMoves = new LinkedList<>();

        for (int i = -7; i <= 7; i++) {
            allowedMoves.add(new Pair<>(0, i));
            allowedMoves.add(new Pair<>(i, 0));
        }

        return getBishopPairs(allowedMoves);
    }

    @Override
    public boolean getPlayer() {
        return player;
    }

    @Override
    public void print() {
        if (player) {
            System.out.print("Q ");
        } else {
            System.out.print("q ");
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
