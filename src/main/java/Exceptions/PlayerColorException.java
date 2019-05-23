package main.java.Exceptions;

/**
 * Made for development & tests
 * Some pieces cannot be initialized without given color
 */

public class PlayerColorException extends Exception {
    public PlayerColorException(String message) {
        super(message);
    }
}