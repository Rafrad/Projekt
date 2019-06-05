package project.chess.Models.Pieces;

import javafx.scene.image.Image;
import project.chess.Exceptions.PlayerColorException;
import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;

import static project.chess.Models.Pieces.Bishop.getBishopPairs;

public class Queen implements Piece {
    private boolean player;

    private char unicode;


    public Queen() throws PlayerColorException {
        throw new PlayerColorException("Queen must be white or black!");
    }

    public Queen(boolean player) {
        this.player = player;

        if(player) {
            unicode = 0x2655;

        } else {
            unicode = 0x265B;

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
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Queen other = (Queen) obj;
        if (player == false) {
            if (other.player != false)
                return false;
        } else if (player == true) {
            if (other.player != true)
                return false;
        }
        return true;
    }

}
