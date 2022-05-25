package Structures;

public class Move {
    PieceType pieceType;
    Shape shape;
    Tile tile;
    int heuristic;

    public Move(Shape s, PieceType pt,  Tile t){
        this.shape = s;
        this.pieceType = pt;
        this.tile = t;
        this.heuristic = Integer.MIN_VALUE;
    }

    public Move(Piece p, Tile t){
        this.shape = p.getShape();
        this.pieceType = p.getName();
        this.tile = t;
        this.heuristic = Integer.MIN_VALUE;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public void setPieceType(PieceType pieceType) {
        this.pieceType = pieceType;
    }

    public Shape getShape(){
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public Tile getTile() {
        return tile;
    }

    public int getHeuristic() {
        return heuristic;
    }

    public void setPiece(Shape sh) {
        this.shape = sh;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public void setHeuristic(int h) {
        heuristic = h;
    }
}
