package Models;
import Exception.PlayerColorExeption;
public class Game {
    //jaki gracz ma ruch
    //czy koniec
    //zegar

    Board board;
    public Move move;
    public Game() throws PlayerColorExeption {
        board = new Board();
        move = new Move(board);
        board.PrintBoard();
    }

    public void move(int a, int b, int aDest, int bDest) {
        if(move.canMove(a, b)) {
            System.out.println("mozna");
            UpdateBoard();
        } else {
            System.out.println("nie mozna");
        }

    }

    public void UpdateBoard() {

    }
}
