package project.chess;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import project.chess.Exceptions.PlayerColorException;
import project.chess.Models.Board;
import project.chess.Models.Pieces.Bishop;
import project.chess.Models.Pieces.BlackKing;
import project.chess.Models.Pieces.BlackPawn;
import project.chess.Models.Pieces.EmptyTile;
import project.chess.Models.Pieces.Knight;
import project.chess.Models.Pieces.Piece;
import project.chess.Models.Pieces.Queen;
import project.chess.Models.Pieces.Rook;
import project.chess.Models.Pieces.WhiteKing;
import project.chess.Models.Pieces.WhitePawn;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class BoardTest {
    public Piece[][] board;
    public Board boardInstance;
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


    public void setUp2() {
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
    public void setUp() {
        board = new Piece[8][8];
        //WhitePawn whitePawn = mock(WhitePawn.class);
        // Application.launch(BoardTestStartClass.class);

        for (int i = 0; i < 8; i++) {
            board[6][i] = new WhitePawn();
            //board[6][i] = mock(WhitePawn.class);
        }
        board[7][0] = new Rook(true);
        board[7][7] = new Rook(true);
        try {
            board[7][1] = new Knight(true);
            board[7][6] = new Knight(true);
        } catch (PlayerColorException e) {
            e.printStackTrace();
        }
        board[7][2] = new Bishop(true);
        board[7][5] = new Bishop(true);
        board[7][3] = new Queen(true);
        board[7][4] = new WhiteKing();

        for (int i = 0; i < 8; i++) {
            board[1][i] = new BlackPawn();
        }
        board[0][0] = new Rook(false);
        board[0][7] = new Rook(false);
        try {
            board[0][1] = new Knight(false);
            board[0][6] = new Knight(false);
        } catch (PlayerColorException e) {
            e.printStackTrace();
        }
        board[0][2] = new Bishop(false);
        board[0][5] = new Bishop(false);
        board[0][3] = new Queen(false);
        board[0][4] = new BlackKing();

        for (int i = 2; i < 6; i++) {
            for (int j = 0; j  < 8; j++) {
                board[i][j] = new EmptyTile();
            }
        }
        try {
            boardInstance = new Board();
        } catch (PlayerColorException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getPiece() {

        //setUp();

        assertNotNull(boardInstance.board);
        assertNotNull(boardInstance.boardOfPossibleMoves);
        //System.out.println("boardInstance: " + boardInstance.getPiece(7,3).getClass().getSimpleName());
        //System.out.println("board: " + board[0][3].getClass().getSimpleName());
        assertEquals(board[7][3].getClass().getSimpleName(),boardInstance.getPiece(7,3).getClass().getSimpleName());
        //System.out.println("boardInstance: " + boardInstance.getPiece(7,3).getPlayer());
        //System.out.println("board: " + board[0][3].getPlayer());
        assertEquals(board[7][3].getPlayer(),boardInstance.getPiece(7,3).getPlayer());

        assertEquals(board[0][0].getClass().getSimpleName(),boardInstance.getPiece(0,0).getClass().getSimpleName());
        assertEquals(board[0][0].getPlayer(),boardInstance.getPiece(0,0).getPlayer());

        assertEquals(board[7][7].getClass().getSimpleName(),boardInstance.getPiece(7,7).getClass().getSimpleName());
        assertEquals(board[7][7].getPlayer(),boardInstance.getPiece(7,7).getPlayer());

        assertEquals(board[7][0].getClass().getSimpleName(),boardInstance.getPiece(7,0).getClass().getSimpleName());
        assertEquals(board[7][0].getPlayer(),boardInstance.getPiece(7,0).getPlayer());

        assertEquals(board[0][7].getClass().getSimpleName(),boardInstance.getPiece(0,7).getClass().getSimpleName());
        assertEquals(board[0][7].getPlayer(),boardInstance.getPiece(0,7).getPlayer());

        assertEquals(board[3][4].getClass().getSimpleName(),boardInstance.getPiece(3,4).getClass().getSimpleName());
        assertEquals(board[3][4].getPlayer(),boardInstance.getPiece(3,4).getPlayer());

    }

    @Test
    public void addPossibleMoves() {
    }

    @Test
    public void printBoard() {
        //setUp();

        try {
            boardInstance = new Board();
        } catch (PlayerColorException e) {
            e.printStackTrace();
        }
        for (int i  = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                assertTrue(boardInstance.board[i][j].equals(board[i][j]));
            }
        }
    }

    @Test
    public void printChosenBoard() {
    }

    @Test
    public void clearPossibleMoves() {
        setUp2();
        try {
            Board b = new Board();
            b.board = this.board;
            b.clearPossibleMoves();
            Assert.assertArrayEquals(b.boardOfPossibleMoves, this.board);
        } catch (PlayerColorException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void clearWhitePlayerAttackBoard() {
        setUp2();
        try {
            Board b = new Board();
            b.board = this.board;
            Method m = Board.class.getDeclaredMethod("clearWhitePlayerAttackBoard");
            m.setAccessible(true);
            m.invoke(b);
            //b.clearWhitePlayerAttackBoard();
            Assert.assertArrayEquals(b.whitePlayerAttackBoard, this.board);
        } catch (PlayerColorException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void clearBlackPlayerAttackBoard() {
        setUp2();
        try {
            Board b = new Board();
            b.board = this.board;
            Method m = Board.class.getDeclaredMethod("clearBlackPlayerAttackBoard");
            m.setAccessible(true);
            m.invoke(b);
            //b.clearBlackPlayerAttackBoard();

            Assert.assertArrayEquals(b.blackPlayerAttackBoard, this.board);
        } catch (PlayerColorException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}