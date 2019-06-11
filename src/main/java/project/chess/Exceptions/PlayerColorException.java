package project.chess.Exceptions;

/**
 * Made for development and tests
 * Some pieces cannot be initialized without given color
 */

public class PlayerColorException extends Exception {
    public PlayerColorException(String message) {
        super(message);
    }
}