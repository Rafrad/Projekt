package project.chess.Exceptions;

/**
 * Made for development and tests
 * Board must be given by Game class
 */

public class WrongBoardException extends Exception {
    public WrongBoardException(String message) {
        super(message);
    }
}
