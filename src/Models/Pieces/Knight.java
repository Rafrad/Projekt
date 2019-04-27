package Models.Pieces;

import PlayerColorException;

public class Knight {
    boolean player;

    //TODO: UNIT TESTS
    public Knight() throws PlayerColorException {
        throw new PlayerColorException("WhitePawn must be white or black!");
    }

    //TODO: UNIT TESTS
    Knight(String playerColor) throws PlayerColorException {
        switch (playerColor) {
            case "white":
                player = true;
                break;
            case "black":
                player = false;
                break;
            default:
                throw new PlayerColorException("WhitePawn must be white or black!");
        }
    }
}
