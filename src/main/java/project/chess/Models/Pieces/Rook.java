package project.chess.Models.Pieces;


import project.chess.Exceptions.PlayerColorException;
import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;

public class Rook implements Piece {
    private boolean player;
    private boolean castling;

    public Rook() throws PlayerColorException {
        throw new PlayerColorException("Rook must be white or black!");
    }

    public Rook(boolean color) {
        player = color;
        castling = true;
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

    public boolean getCastling() {
        return castling;
    }

    public void setCastling(boolean castling) {
        this.castling = castling;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Rook other = (Rook) obj;
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
