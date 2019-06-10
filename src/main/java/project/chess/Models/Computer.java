package project.chess.Models;

import javafx.util.Pair;
import project.chess.Controllers.GameController;
import project.chess.Exceptions.PlayerColorException;
import project.chess.Models.Pieces.Mark_MovableTile;
import project.chess.Models.Pieces.Piece;

import java.net.MalformedURLException;
import java.util.*;

import static java.lang.Integer.*;

public class Computer {
    private final int DEPTH = 2;

    private Game session;
    private Game simulation;

    private boolean is_white;

    private int[] bestMove;

    GameController gameControllerClass;

    public Computer (Game session, boolean isWhite, GameController gameControllerClass) throws PlayerColorException{
        this.session = session;
        this.is_white = isWhite;
        bestMove = new int[4];
        simulation = new Game(session.boardClass);
        this.gameControllerClass = gameControllerClass;
    }

   /* public void makeAMove() throws MalformedURLException {
        bodyDisposal(session.boardClass.board, piece_list);
        Pair<Pair<Integer, Integer>, Piece> element;
        Pair<Integer, Integer> position;
        List<Pair<Integer, Integer>> available_moves;
        Iterator<Pair<Pair<Integer, Integer>, Piece>> iter = piece_list.iterator();
        boolean draw = true;

        Piece bestMovePiece = null;  // defaultowy piece zeby pozbyc sie bestMovePiece not inizialized
        int currMove[];
        int bestMove[] = new int[5];

        while(iter.hasNext()) {
            element = iter.next();
            position = element.getKey();
            List<Pair<Integer, Integer>> dummy = new LinkedList<>();
            available_moves = session.moveClass.CalculateMoves(position.getKey(), position.getValue(),"", dummy);
            if(!available_moves.isEmpty()) {
                currMove = selectBest(available_moves, element.getValue());
                if(currMove[2] > bestMove[4]) {
                    bestMove[0] = position.getKey();
                    bestMove[1] = position.getValue();
                    bestMove[2] = currMove[0];
                    bestMove[3] = currMove[1];
                    bestMove[4] = currMove[2];
                    bestMovePiece = element.getValue();
                }
            } else draw = false;
        }

        if(draw) return;  // Brak ruchów
        session.move(bestMove[0], bestMove[1], bestMove[2], bestMove[3]);
        piece_list.add(new Pair<> (new Pair<>(bestMove[2], bestMove[3]), bestMovePiece));
        System.out.println(bestMove[0] +" "+ bestMove[1]+" "+ bestMove[2]+ " " + bestMove[3] + " " + bestMove[4]);
    }
*/

    public void move() throws MalformedURLException {
        //simulation.boardClass = ;
        minmax(DEPTH, new Board(session.boardClass), MIN_VALUE, MAX_VALUE,true);
        System.out.println(bestMove[0] + " " +  bestMove[1] + " " + bestMove[2] + " " +  bestMove[3]);
        session.move(bestMove[0], bestMove[1], bestMove[2], bestMove[3]);
        gameControllerClass.updateMatchHistory(bestMove[2], bestMove[3], "");
        if (!session.getCurrentPlayer()) {
            session.whiteClock.setTimePerRound(gameControllerClass.timePerRound);
            session.whiteClock.updateTime();
        } else {
            session.blackClock.setTimePerRound(gameControllerClass.timePerRound);
            session.blackClock.updateTime();
        }
    }

    int minmax (int depth, Board currConfig, int alpha, int beta, boolean isComputer) throws MalformedURLException{
        List<Pair<Integer, Integer>> moves;
        Piece piece;
        boolean currentColor;

        int value;
        int newValue;

        if(depth == 0) return evaluate(currConfig);         // Recursion core

        if(isComputer) {
            value = MIN_VALUE;
            currentColor = is_white;
        }
        else {
            value = MAX_VALUE;
            currentColor = !is_white;
        }

        simulation.boardClass = new Board(currConfig);      // Just for method UpdateBoard
        simulation.moveClass.boardClass = new Board(currConfig);       // Just for method CalculateMove

        for(int i = 0; i < 8; i++) {                        // Iterating through board
            for(int j = 0; j < 8; j++) {                    //
                piece = currConfig.board[i][j];
                if(piece.getPlayer() == currentColor && piece.getUnicode() != 0) {              // Selecting only pieces of current color
                    moves = simulation.moveClass.CalculateMoves(i, j, "", new LinkedList<>());
//                    moves = mainFiler(i, j, moves, simulation);
                    for(Pair<Integer, Integer> next_pos : moves) {
                        simulation.UpdateBoard(i, j, next_pos.getKey(), next_pos.getValue());
                        newValue = minmax(depth - 1, simulation.boardClass, alpha, beta, !isComputer);

                        simulation.boardClass = new Board(currConfig);

                        if(isComputer) {                    // Maximizing
                            if(value < newValue && depth == DEPTH) {
                                bestMove[0] = i;            // Saving best move for maximizing player
                                bestMove[1] = j;
                                bestMove[2] = next_pos.getKey();
                                bestMove[3] = next_pos.getValue();

                            }
                            value = max(value, newValue);
                            alpha = max(alpha, newValue);
                            if(beta <= alpha) return value;
                        }
                        else {                              // Minimizing
                            value = min(value, newValue);
                            beta = min(beta, newValue);
                            if(beta <= alpha) return value;
                        }
                    }
                }

            }
        }
        return value;
    }

    private void bodyDisposal(Piece[][] board, List<Pair<Pair<Integer, Integer>, Piece>> piece_list) {
        Pair<Pair<Integer, Integer>, Piece> element;
        Pair<Integer, Integer> position;
        Iterator<Pair<Pair<Integer, Integer>, Piece>> iter = piece_list.iterator();

        while(iter.hasNext()) {             //wystarczy zeby znalazlo max 2 elementy
            element = iter.next();
            position = element.getKey();
            if(board[position.getKey()][position.getValue()] != element.getValue()) {
                iter.remove();
            }
        }
    }

   /* private int[] selectBest(List<Pair<Integer, Integer>> am, Piece p) {
        int points;
        int[] move = new int[3];

        for(Pair<Integer, Integer> position : am) {
            points = evaluatePoints(session.boardClass.board[position.getKey()][position.getValue()]);
            points += positionBonus(position, p);
            if(points >= move[2]) {
                move[0] = position.getKey();
                move[1] =  position.getValue();
                move[2] = points;
            }
        }
        return move;
    }*/

    private int evaluate(Board board) {
        int points = 0;
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                points += evaluatePoints(i, j, board.board[i][j]);
            }
        }
        return points;
    }

    private int evaluatePoints(int x, int y, Piece p) {
        switch (p.getClass().getSimpleName()) {
            case "WhitePawn":
            case "BlackPawn": {
                if(p.getPlayer() == is_white) return 150 + positionBonus(x, y, p);
                else return -150 - positionBonus(x, y, p);
            }
            case "Knight": {
                if(p.getPlayer() == is_white) return 180 + positionBonus(x, y, p);
                else return -130 - positionBonus(x, y, p);
            }
            case "Bishop": {
                if(p.getPlayer() == is_white) return 190 + positionBonus(x, y, p);
                else return -190 - positionBonus(x, y, p);
            }
            case "Rook" : {
                if(p.getPlayer() == is_white) return 210 + positionBonus(x, y, p);
                else return -210 - positionBonus(x, y, p);
            }
            case "Queen": {
                if(p.getPlayer() == is_white) return 300 + positionBonus(x, y, p);
                else return -300 - positionBonus(x, y, p);
            }
            case "WhiteKing":
            case "BlackKing": {
                if(p.getPlayer() == is_white) return 1000 + positionBonus(x, y, p);
                else return -1000 - positionBonus(x, y, p);
            }
            default : return 0;
        }
    }

    private int positionBonus(int posx, int posy, Piece p) {
        int corrected;
        if(is_white) corrected = posx*8 + posy;
        else corrected = 63 - posx*8 - posy;
        int[] king = {
                -30,-40,-40,-50,-50,-40,-40,-30,
                -30,-40,-40,-50,-50,-40,-40,-30,
                -30,-40,-40,-50,-50,-40,-40,-30,
                -30,-40,-40,-50,-50,-40,-40,-30,
                -20,-30,-30,-40,-40,-30,-30,-20,
                -10,-20,-20,-20,-20,-20,-20,-10,
                20, 20,  0,  0,  0,  0, 20, 20,
                20, 30, 10,  0,  0, 10, 30, 20
        };
        int[] queen = {
                -20,-10,-10, -5, -5,-10,-10,-20,
                -10,  0,  0,  0,  0,  0,  0,-10,
                -10,  0,  5,  5,  5,  5,  0,-10,
                -5,  0,  5,  5,  5,  5,  0, -5,
                0,  0,  5,  5,  5,  5,  0, -5,
                -10,  5,  5,  5,  5,  5,  0,-10,
                -10,  0,  5,  0,  0,  0,  0,-10,
                -20,-10,-10, -5, -5,-10,-10,-20
        };
        int[] pawn = {
                0,  0,  0,  0,  0,  0,  0,  0,
                50, 50, 50, 0, 0, 50, 50, 50,
                10, 10, 20, 30, 30, 20, 10, 10,
                5,  5, 10, 25, 25, 10,  5,  5,
                0,  0,  0, 20, 20,  0,  0,  0,
                5, -5,-10,  0,  0,-10, -5,  5,
                5, 10, 10,-20,-20, 10, 10,  5,
                250, 250, 250, 250, 250, 250, 250, 250,
        };
        int[] knight = {
                -40,-30,-20,-20,-20,-20,-30,-40,
                -40,-20,  0,  0,  0,  0,-20,-40,
                -30,  0, 10, 15, 15, 10,  0,-30,
                -30,  5, 15, 20, 20, 15,  5,-30,
                -30,  0, 15, 20, 20, 15,  0,-30,
                -30,  5, 10, 15, 15, 10,  5,-30,
                -40,-20,  0,  5,  5,  0,-20,-40,
                -40,-30,-20,-20,-20,-20,-30,-40
        };
        int[] bishop = {
                -20,-10,-10,-10,-10,-10,-10,-20,
                -10,  0,  0,  0,  0,  0,  0,-10,
                -10,  0,  5, 10, 10,  5,  0,-10,
                -10,  5,  5, 10, 10,  5,  5,-10,
                -10,  0, 10, 10, 10, 10,  0,-10,
                -10, 10, 10, 10, 10, 10, 10,-10,
                -10,  5,  0,  0,  0,  0,  5,-10,
                -20,-10,-10,-10,-10,-10,-10,-20
        };
        int[] rook = {
                0,  0,  0,  0,  0,  0,  0,  0,
                5, 10, 10, 10, 10, 10, 10,  5,
                -5,  0,  0,  0,  0,  0,  0, -5,
                -5,  0,  0,  0,  0,  0,  0, -5,
                -5,  0,  0,  0,  0,  0,  0, -5,
                -5,  0,  0,  0,  0,  0,  0, -5,
                -5,  0,  0,  0,  0,  0,  0, -5,
                0,  0,  0,  5,  5,  0,  0,  0
        };

        switch (p.getClass().getSimpleName()) {
            case "WhitePawn":
            case "BlackPawn": return pawn[corrected];
            case "Knight": return knight[corrected];
            case "Bishop": return bishop[corrected];
            case "Rook" : return rook[corrected];
            case "Queen": return queen[corrected];
            case "WhiteKing":
            case "BlackKing": return king[corrected];
            default : return 0;
        }
    }

    public static List<Pair<Integer, Integer>> mainFiler(int row, int column, List<Pair<Integer, Integer>> moves, Game game) {
        Filter filter = new Filter(game, moves, row, column);
        moves = filter.filterMoves();

        game.boardClass.clearPossibleMoves();

        for (Pair<Integer, Integer> move : moves) {
            int rowMove = move.getKey();
            int columnMove = move.getValue();

            game.boardClass.boardOfPossibleMoves[rowMove][columnMove] = new Mark_MovableTile();
        }
        return moves;
    }
}