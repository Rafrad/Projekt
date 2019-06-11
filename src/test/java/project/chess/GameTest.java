package project.chess;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import project.chess.Controllers.GameController;
import project.chess.Exceptions.PlayerColorException;
import project.chess.Models.Board;
import project.chess.Models.Game;
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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class GameTest {
    public Board boardClass;
    public Piece[][] board;
    public Board boardInstance;
    @Before
    public void setUp() {
        try {
            boardClass = new Board();
        } catch (PlayerColorException e) {
            e.printStackTrace();
        }
    }

    public void setUp2() {
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
    public void checkPawnsFirstMove() throws NoSuchMethodException, PlayerColorException {
        setUp2();
        Game game = new Game();
        Method m = game.getClass().getDeclaredMethod("checkPawnsFirstMove", int.class, int.class, int.class, int.class);

        m.setAccessible(true);

        try {
           // game.boardClass.board= this.board;
            m.invoke(game,1,1,3,1);
            assertThat(((BlackPawn) game.boardClass.board[1][1]).getFirstMove(), is(false));

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void deleteEnPassant() throws NoSuchMethodException {
        setUp2();
        GameController gc = new GameController();
        Method m = GameController.class.getDeclaredMethod("setCustomClock");

        m.setAccessible(true);
        try {
            m.invoke(gc);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        Game game = gc.game;
        boardClass.board = this.board;
        try {
            //boardClass.board =
            m = Game.class.getDeclaredMethod("deleteEnPassant");
            m.setAccessible(true);
            m.invoke(game);
            assertThat(((BlackPawn)boardClass.board[1][1]).getEnPassant(), is(false));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    @After
    public void tearDown() {
        this.board = null;
        this.boardClass = null;
        this.boardInstance = null;
    }


}