package Structures;

import java.io.Serializable;

public class Move implements Serializable {
    // The heuristic sums the number of cases of the player's color on the board, and the number of shapes they can still place somewhere on the board.
    // There are 400 cases on the board, and 91 total possible shapes for all the pieces.
    // 400 + 91*400 = 36 800. So the heuristic is always in the interval [-36000; 36000]
    //public static final int baseH = 36801;
    Shape shape;
    PieceType pieceT;
    Tile tile;
    //int heuristic;

    public Move(Shape s, PieceType pt, Tile t){
        this.shape = s;
        this.pieceT = pt;
        this.tile = t;
        //this.heuristic = baseH;
    }

    public Move(Piece p, Tile t){
        this.shape = p.getShape();
        this.pieceT = p.getName();
        this.tile = t;
        //this.heuristic = baseH;
    }

    public Shape getShape(){
        return shape;
    }

    public Tile getTile() {
        return tile;
    }

    //public int getHeuristic() {
        //return heuristic;
    //}

    public PieceType getPieceType() {
        return pieceT;
    }

    public void setShape(Shape sh) {
        this.shape = sh;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    /*public void setHeuristic(int h) {
        heuristic = h;
    }*/

    public void setPieceType(PieceType pieceType) {
        this.pieceT = pieceType;
    }
}
