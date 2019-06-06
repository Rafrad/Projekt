package project.chess;

import javafx.util.Pair;
import project.chess.Exceptions.PlayerColorException;
import project.chess.Models.Board;
import project.chess.Models.Move;
import project.chess.Models.Pieces.*;
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

    public Piece[][] board;
    private final String[] rowsOfPieces = {
            "r k b q x b k r ",
            "b b b y b b b b ",
            "x x x x x x x x ",
            "x x x w x x x x ",
            "x x x x x x x x ",
            "x x x x x x x x ",
            "w w w w x w w w ",
            "R K B Q Y B K R "
    };

    public void setUp() {
        board = new Piece[8][8];
        for (int i = 0; i < 8; i++) {
            if (i != 4) {
                board[6][i] = new WhitePawn();
            } else {
                board[3][3] = new WhitePawn();
            }
        }

        try {
            board[7][0] = new Rook(true);
            board[7][7] = new Rook(true);
            board[7][1] = new Knight(true);
            board[7][6] = new Knight(true);
            board[7][2] = new Bishop(true);
            board[7][5] = new Bishop(true);
            board[7][3] = new Queen(true);
            board[7][4] = new WhiteKing();
            board[6][4] = new EmptyTile();
            for (int i = 0; i < 8; i++) {
                if (i != 3) {
                    board[1][i] = new BlackPawn();
                }
            }

            board[0][0] = new Rook(false);
            board[0][7] = new Rook(false);
            board[0][1] = new Knight(false);
            board[0][6] = new Knight(false);
            board[0][2] = new Bishop(false);
            board[0][5] = new Bishop(false);
            board[0][3] = new Queen(false);
            board[1][3] = new BlackKing();
            board[0][4] = new EmptyTile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 2; i < 6; i++) {
            for (int j = 0; j  < 8; j++) {
                if (i != 3 || j != 3) {
                    board[i][j] = new EmptyTile();
                }
            }
        }

    }

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

        allowedMoves.clear();//kuba filip test
        setUp();
        Board b = new Board();
        b.board = board;
        Move move = new Move(b);
        allowedMoves.add(new Pair<>(2, 3));
        allowedMoves.add(new Pair<>(0, 4));
        List<Pair<Integer, Integer>> stack = new LinkedList<>();
        assertEquals(allowedMoves, move.CalculateMoves(1,3,"", stack));
        //Expected :[2=3, 0=4]
        //Actual   :[2=3, 2=4, 2=2, 0=4]

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
