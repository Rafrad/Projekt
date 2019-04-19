package model.Pieces;

import javafx.util.Pair;
import java.util.List;

//zaczynamy w 0,0 i obliczamy ruchy
public interface Piece {
    List<Pair<Integer, Integer>> move();
}
