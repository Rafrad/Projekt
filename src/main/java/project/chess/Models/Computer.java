package project.chess.Models;

import javafx.util.Pair;
import project.chess.Models.Pieces.Piece;

import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static project.chess.Controllers.GameController.mainFiler;

public class Computer {
    private Game session;
    private boolean is_white;
    private List<Pair<Pair<Integer, Integer>, Piece>> piece_list;
    private int depth;

    public Computer (Game session, boolean isWhite) {
        this.session = session;
        this.is_white = isWhite;
        piece_list = new LinkedList<>();
        initPieceList();
    }

    private List<Pair<Integer, Integer>> filterMoves(int row, int column, List<Pair<Integer, Integer>> moves) {
        return mainFiler(row, column, moves, session);
    }

    public void makeAMove() throws MalformedURLException {
        bodyDisposal();
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
//            available_moves = filterMoves(position.getKey(), position.getValue(), available_moves); // filtrowanie
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

    int[] minmax(int depth, boolean isComputer) {
        int[] e = new int[5];
        if(depth == 0) {

        }
        return e;
    }

    private void bodyDisposal() {
        Piece[][] board = session.boardClass.board;
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

    private int[] selectBest(List<Pair<Integer, Integer>> am, Piece p) {
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
    }


    private int evaluatePoints(Piece p) {
        switch (p.getClass().getSimpleName()) {
            case "WhitePawn":
            case "BlackPawn": return 10;
            case "Knight": return 20;
            case "Bishop": return 30;
            case "Rook" : return 40;
            case "Queen": return 100;
            case "WhiteKing":
            case "BlackKing": return 1000;
            default : return 0;
        }
    }

    private void initPieceList() {
        Piece[][] board = session.boardClass.board;
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(board[i][j].getPlayer() == is_white) {
                    piece_list.add(new Pair<> (new Pair <> (i, j), board[i][j]));
                }
            }
        }
    }



    private int positionBonus(Pair<Integer, Integer> pos, Piece p) {
        int corrected;
        if(is_white) corrected = pos.getKey()*8 + pos.getValue();
        else corrected = 63 - pos.getKey()*8 - pos.getValue();
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
                50, 50, 50, 50, 50, 50, 50, 50,
                10, 10, 20, 30, 30, 20, 10, 10,
                5,  5, 10, 25, 25, 10,  5,  5,
                0,  0,  0, 20, 20,  0,  0,  0,
                5, -5,-10,  0,  0,-10, -5,  5,
                5, 10, 10,-20,-20, 10, 10,  5,
                0,  0,  0,  0,  0,  0,  0,  0
        };
        int[] knight = {
                -50,-40,-30,-30,-30,-30,-40,-50,
                -40,-20,  0,  0,  0,  0,-20,-40,
                -30,  0, 10, 15, 15, 10,  0,-30,
                -30,  5, 15, 20, 20, 15,  5,-30,
                -30,  0, 15, 20, 20, 15,  0,-30,
                -30,  5, 10, 15, 15, 10,  5,-30,
                -40,-20,  0,  5,  5,  0,-20,-40,
                -50,-40,-30,-30,-30,-30,-40,-50
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
}