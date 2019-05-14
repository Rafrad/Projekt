package Exceptions;

/*

 * Made for development & tests
 * Board must be given by Game class

 */

public class WrongBoardException extends Exception {
    public WrongBoardException(String message) {
        super(message);
    }
}
