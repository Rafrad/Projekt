package model;

public class Tile {
    int tileCoordinate;
    boolean occupiedTile;

    Tile(int tileCoordinate) {
        this.tileCoordinate = tileCoordinate;
        occupiedTile = false;
    }

    boolean IsTileOccupied() {
        return occupiedTile;
    }
}
