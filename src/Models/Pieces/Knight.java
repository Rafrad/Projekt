package Models.Pieces;

import Exception.PlayerColorExeption;

public class Knight {
    boolean player;

    //TODO: UNIT TESTS
    public Knight() throws PlayerColorExeption {
        throw new PlayerColorExeption("WhitePawn must be white or black!");
    }

    //TODO: UNIT TESTS
    Knight(String playerColor) throws PlayerColorExeption {
        switch (playerColor) {
            case "white":
                player = true;
                break;
            case "black":
                player = false;
                break;
            default:
                throw new PlayerColorExeption("WhitePawn must be white or black!");
        }
    }
}
