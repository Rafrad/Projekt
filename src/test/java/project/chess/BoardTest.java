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
    private void setUp() {
        board = new Piece[8][8];
        for (int i = 0; i < 8; i++) {
            board[6][i] = new WhitePawn();
        }
        board[7][0] = new Rook(true);
        board[7][7] = new Rook(true);
        board[7][1] = new Knight(true);
        board[7][6] = new Knight(true);
        board[7][2] = new Bishop(true);
        board[7][5] = new Bishop(true);
        board[7][3] = new Queen(true);
        board[7][4] = new WhiteKing();

        for (int i = 0; i < 8; i++) {
            board[1][i] = new BlackPawn();
        }
        board[0][0] = new Rook(false);
        board[0][7] = new Rook(false);
        board[0][1] = new Knight(false);
        board[0][6] = new Knight(false);
        board[0][2] = new Bishop(false);
        board[0][5] = new Bishop(false);
        board[0][3] = new Queen(false);
        board[0][4] = new BlackKing();

        for (int i = 2; i < 6; i++) {
            for (int j = 0; j  < 8; j++) {
                board[i][j] = new EmptyTile();
            }
        }
    }

    @Test
    public void getPiece() {

        setUp();

        try {
            boardInstance = new Board();
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


        } catch (PlayerColorException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void setPiece() {
        try {
            boardInstance = new Board();
            boardInstance.setPiece(4,4, new WhitePawn());
            assertEquals(WhitePawn.class.getSimpleName(), boardInstance.getPiece(4,4).getClass().getSimpleName());
            boardInstance.setPiece(3,4, new BlackPawn());
            assertEquals(BlackPawn.class.getSimpleName(), boardInstance.getPiece(3,4).getClass().getSimpleName());
            boardInstance.setPiece(3,3, new Rook(true));
            assertEquals(Rook.class.getSimpleName(), boardInstance.getPiece(3,3).getClass().getSimpleName());
            boardInstance.setPiece(4,5, new Knight(true));
            assertEquals(Knight.class.getSimpleName(), boardInstance.getPiece(4,5).getClass().getSimpleName());
            boardInstance.setPiece(4,6, new Bishop(true));
            assertEquals(Bishop.class.getSimpleName(), boardInstance.getPiece(4,6).getClass().getSimpleName());
            boardInstance.setPiece(4,7, new Queen(true));
            assertEquals(Queen.class.getSimpleName(), boardInstance.getPiece(4,7).getClass().getSimpleName());
            boardInstance.setPiece(4,1, new WhiteKing());
            assertEquals(WhiteKing.class.getSimpleName(), boardInstance.getPiece(4,1).getClass().getSimpleName());
            boardInstance.setPiece(4,0, new BlackKing());
            assertEquals(BlackKing.class.getSimpleName(), boardInstance.getPiece(4,0).getClass().getSimpleName());
            boardInstance.setPiece(3,3, new Rook(false));
            assertEquals(Rook.class.getSimpleName(), boardInstance.getPiece(3,3).getClass().getSimpleName());
            boardInstance.setPiece(3,5, new Knight(false));
            assertEquals(Knight.class.getSimpleName(), boardInstance.getPiece(3,5).getClass().getSimpleName());
            boardInstance.setPiece(3,6, new Bishop(false));
            assertEquals(Bishop.class.getSimpleName(), boardInstance.getPiece(4,6).getClass().getSimpleName());
            boardInstance.setPiece(3,7, new Queen(false));
            assertEquals(Queen.class.getSimpleName(), boardInstance.getPiece(4,7).getClass().getSimpleName());
        } catch(PlayerColorException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addPossibleMoves() {
    }

    @Test
    public void printBoard() {
        setUp();

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
    public void PPPPPP() {
    }

    @Test
    public void clearPossibleMoves() {
    }

    @Test
    public void clearWhitePlayerAttackBoard() {
    }

    @Test
    public void clearBlackPlayerAttackBoard() {
    }

    @Test
    public void clearFakeBoard() {
    }
}