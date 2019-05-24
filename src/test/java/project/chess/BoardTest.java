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
    @Before
    public void setUp() {
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
        try {
            boardInstance = new Board();
            //System.out.println("boardInstance: " + boardInstance.getPiece(7,3).getClass().getSimpleName());
            //System.out.println("board: " + board[0][3].getClass().getSimpleName());
            assertEquals(board[7][3].getClass().getSimpleName(),boardInstance.getPiece(7,3).getClass().getSimpleName());
            //System.out.println("boardInstance: " + boardInstance.getPiece(7,3).getPlayer());
            //System.out.println("board: " + board[0][3].getPlayer());
            assertEquals(board[7][3].getPlayer(),boardInstance.getPiece(7,3).getPlayer());

        } catch (PlayerColorException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void setPiece() {
    }

    @Test
    public void addPossibleMoves() {
    }

    @Test
    public void printBoard() {
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