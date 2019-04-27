package Models.Pieces;

import javafx.util.Pair;

import java.lang.management.PlatformLoggingMXBean;
import java.util.LinkedList;
import java.util.List;
import Exception.PlayerColorExeption;

public class Pawn implements Piece {
    boolean player;

    //TODO: UNIT TESTS
    public Pawn() throws PlayerColorExeption {
        throw new PlayerColorExeption("Pawn must be white or black!");
    }

    //TODO: UNIT TESTS
    Pawn(String playerColor) throws PlayerColorExeption {
        switch (playerColor) {
            case "white":
                player = true;
                break;
            case "black":
                player = false;
                break;
            default:
                throw new PlayerColorExeption("Pawn must be white or black!");
        }
    }

    @Override
    public List<Pair<Integer, Integer>> move() {
        LinkedList<Pair<Integer, Integer>> allowedMoves = new LinkedList<>();

        //TODO: UNIT TESTS
        allowedMoves.add(new Pair<>(1, 0));
        allowedMoves.add(new Pair<>(1, 1));
        allowedMoves.add(new Pair<>(1, -1));
        allowedMoves.add(new Pair<>(2, 0));

        return allowedMoves;
    }
}
