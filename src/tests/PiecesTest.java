package tests;

import javafx.util.Pair;
import main.Exceptions.PlayerColorException;
import main.Models.Pieces.*;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;

public class PiecesTest {

    private final ByteArrayOutputStream outputContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private static List<Pair<Integer, Integer>> allowedMoves = new LinkedList<>();

    @Before
    public void setCustomOutputStream() {
        System.setOut(new PrintStream(outputContent));
    }

    @Test
    public void testKing() throws PlayerColorException {

        BlackKing testBlackKing = new BlackKing();
        assertFalse(testBlackKing.getPlayer());

        allowedMoves.clear();
        allowedMoves.add(new Pair<>(1, 0));
        allowedMoves.add(new Pair<>(1, 1));
        allowedMoves.add(new Pair<>(0, 1));
        allowedMoves.add(new Pair<>(-1, 0));
        allowedMoves.add(new Pair<>(-1, -1));
        allowedMoves.add(new Pair<>(0, -1));
        allowedMoves.add(new Pair<>(1, -1));
        allowedMoves.add(new Pair<>(-1, 1));
        assertEquals(allowedMoves, testBlackKing.move());

        WhiteKing testWhiteKing = new WhiteKing();
        assertTrue(testWhiteKing.getPlayer());

        allowedMoves.clear();
        allowedMoves.add(new Pair<>(1, 0));
        allowedMoves.add(new Pair<>(1, 1));
        allowedMoves.add(new Pair<>(0, 1));
        allowedMoves.add(new Pair<>(-1, 0));
        allowedMoves.add(new Pair<>(-1, -1));
        allowedMoves.add(new Pair<>(0, -1));
        allowedMoves.add(new Pair<>(1, -1));
        allowedMoves.add(new Pair<>(-1, 1));
        assertEquals(allowedMoves, testWhiteKing.move());

    }

    @Test
    public void testPawn() throws PlayerColorException {

        BlackPawn testBlackPawn = new BlackPawn();
        assertFalse(testBlackPawn.getPlayer());
//        assertFalse(testBlackPawn.getFirstMove());
        assertFalse(testBlackPawn.getEnPassant());

        allowedMoves.clear();
        allowedMoves.add(new Pair<>(1, 0));
        allowedMoves.add(new Pair<>(1, 1));
        allowedMoves.add(new Pair<>(1, -1));
        allowedMoves.add(new Pair<>(2, 0));
        assertEquals(allowedMoves, testBlackPawn.move());

        WhitePawn testWhitePawn = new WhitePawn();
        assertTrue(testWhitePawn.getPlayer());
        assertTrue(testWhitePawn.getFirstMove());
        assertFalse(testWhitePawn.getEnPassant());

        allowedMoves.clear();
        allowedMoves.add(new Pair<>(-1, 0));
        allowedMoves.add(new Pair<>(-1, -1));
        allowedMoves.add(new Pair<>(-1, 1));
        allowedMoves.add(new Pair<>(-2, 0));
        assertEquals(allowedMoves, testWhitePawn.move());
    }

    @Test
    public void testBishop() throws PlayerColorException {

        Bishop testBishop = new Bishop(true);
        assertTrue(testBishop.getPlayer());

        allowedMoves.clear();
        for (int i = -7; i <= 7; i++) {
            if(i != 0) {
                allowedMoves.add(new Pair<>(i, i));
                allowedMoves.add(new Pair<>(-i, i));
            }
        }
        assertEquals(allowedMoves, testBishop.move());
    }

    @Test
    public void testKnight() throws PlayerColorException {

        Knight testKnight = new Knight(true);
        assertTrue(testKnight.getPlayer());

        allowedMoves.clear();
        allowedMoves.add(new Pair<>(2, 1));
        allowedMoves.add(new Pair<>(2, -1));
        allowedMoves.add(new Pair<>(1, 2));
        allowedMoves.add(new Pair<>(-1, 2));
        allowedMoves.add(new Pair<>(-1, -2));
        allowedMoves.add(new Pair<>(-2, -1));
        allowedMoves.add(new Pair<>(-2, 1));
        allowedMoves.add(new Pair<>(1, -2));
        assertEquals(allowedMoves, testKnight.move());
    }

    @Test
    public void testQueen() throws PlayerColorException {

        Queen testQueen = new Queen(true);
        assertTrue(testQueen.getPlayer());

        allowedMoves.clear();
        for(int i = -7; i <= 7; i++) {
            allowedMoves.add(new Pair<>(0, i));
            allowedMoves.add(new Pair<>(i, 0));
        }

        for (int i = -7; i <= 7; i++) {
            if(i != 0) {
                allowedMoves.add(new Pair<>(i, i));
                allowedMoves.add(new Pair<>(-i, i));
            }
        }
        assertEquals(allowedMoves, testQueen.move());
    }

    @Test
    public void testRook() throws PlayerColorException {

        Rook testRook = new Rook(true);
        assertTrue(testRook.getPlayer());

        allowedMoves.clear();
        for(int i = -7; i <= 7; i++) {
            if(i != 0) {
                allowedMoves.add(new Pair<>(0, i));
                allowedMoves.add(new Pair<>(i, 0));
            }
        }
        assertEquals(allowedMoves, testRook.move());
    }

    @Test
    public void testMark_MovableTile() {

        Mark_MovableTile testMovableTile = new Mark_MovableTile();
        assertNull(testMovableTile.move());
        assertFalse(testMovableTile.getPlayer());
    }

    @Test
    public void testEmptyTile() {

        EmptyTile testEmptyTile = new EmptyTile();
        assertNull(testEmptyTile.move());
        assertFalse(testEmptyTile.getPlayer());
    }

    @After
    public void setOriginalOutputStream() {
        System.setOut(originalOut);
    }

}
