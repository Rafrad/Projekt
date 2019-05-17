package tests;

import main.Exceptions.PlayerColorException;
import main.Models.Board;
import main.Models.Game;
import main.Models.Pieces.*;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class GeneralModelsTest {

    private final ByteArrayOutputStream outputContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final String[] rowsOfPieces = {
            "r k b q y b k r ",
            "b b b b b b b b ",
            "x x x x x x x x ",
            "x x x x x x x x ",
            "x x x x x x x x ",
            "x x x x x x x x ",
            "w w w w w w w w ",
            "R K B Q Y B K R "
    };

    @Before
    public void setCustomOutputStream() {
        System.setOut(new PrintStream(outputContent));
    }

    @Test
    public void testGame() throws PlayerColorException {

        Game testGame = new Game();
        assertNotNull(testGame.boardClass);
        assertTrue(isStandardBoard());
        assertTrue(testGame.getCurrentPlayer());

        testGame.UpdateBoard(6, 0, 5, 0);
        assertSame(testGame.boardClass.getPiece(5, 0).getClass(), WhitePawn.class);
        assertSame(testGame.boardClass.getPiece(6, 0).getClass(), EmptyTile.class);
        testGame.UpdateBoard(5, 0, 6, 0);
        testGame.boardClass.PrintBoard(true);
        assertTrue(isStandardBoard());

        testGame.moveClass.CalculateMoves(6, 0, false);
        outputContent.reset();
        testGame.move(6, 0, 5, 0);
        outputContent.reset();
        testGame.boardClass.PrintBoard(true);
        assertTrue(outputContent.toString().replaceAll("\n", "").matches(".*w x x x x x x x.*"));
        assertFalse(isStandardBoard());
        assertFalse(testGame.getCurrentPlayer());

        testGame.UpdateBoard(5, 0, 6, 0);
        testGame.boardClass.PrintBoard(true);
        assertTrue(isStandardBoard());

        testGame.moveClass.CalculateMoves(1, 0, false);
        outputContent.reset();
        testGame.move(1, 0, 2, 0);
        outputContent.reset();
        testGame.boardClass.PrintBoard(true);
        assertTrue(outputContent.toString().replaceAll("\n", "").matches(".*b x x x x x x x.*"));
        assertFalse(isStandardBoard());
        assertTrue(testGame.getCurrentPlayer());

        //TODO: add UpdateCheck and showAttackedTiles methods tests later
    }

    @Test
    public void testBoard() throws PlayerColorException {

        Board testBoard = new Board();
        assertNotNull(testBoard);

        testBoard.PrintBoard(true);
        assertTrue(isStandardBoard());
        testBoard.PrintBoard(false);
        assertTrue(isStandardBoard());

        assertSame(testBoard.getPiece(0, 4).getClass(), BlackKing.class);

        testBoard.boardOfPossibleMoves[0][0] = new Bishop(true);
        testBoard.PrintBoard(false);
        assertFalse(isStandardBoard());
        testBoard.ClearPossibleMoves();
        testBoard.PrintBoard(false);
        assertTrue(isStandardBoard());
    }

    private boolean isStandardBoard() {

        String[] boardRows = outputContent.toString().split("\n");
        outputContent.reset();
        for(int i = 0; i < 8; i++) {
            if (!boardRows[i].equals(rowsOfPieces[i]))
                return false;
        }
        return true;
    }

    @Test
    public void testMove() throws PlayerColorException {

        Game testMove = new Game();
        assertFalse(testMove.moveClass.CalculateMoves(6, 0, false).isEmpty());
        assertTrue(outputContent.toString().replaceAll("\n", "").matches(".*m x x x x x x x m x x x x x x x.*"));
        outputContent.reset();

        // TODO: add more Move tests later
    }

    @After
    public void setOriginalOutputStream() {
        System.setOut(originalOut);
    }
}
