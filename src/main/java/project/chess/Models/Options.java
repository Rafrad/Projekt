package project.chess.Models;

/**
 * This is container class, which contains options needed game to be started.
 */

public class Options {
    private String gameMode;
    private String versusMode;
    private String firstPlayerColor;

    public void setGameMode(String mode) {
        gameMode = mode;
    }

    public void setVersusMode(String mode) {
        versusMode = mode;
    }

    public void setFirstPlayerColor(String color) {
        firstPlayerColor = color;
    }


    public String getGameMode() {
        return gameMode;
    }

    public String getVersusMode() {
        return versusMode;
    }

    public String getFirstPlayerColor() {
        return firstPlayerColor;
    }

}
