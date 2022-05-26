package Structures;

import java.io.Serializable;

public class Move implements Serializable {
    Piece piece;
    Tile tile;

    public Move(Piece p, Tile t){
        this.piece = p;
        this.tile = t;
    }

    public Piece getPiece(){
        return piece;
    }

    public Tile getTile() {
        return tile;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }
}
