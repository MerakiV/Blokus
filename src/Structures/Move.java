package Structures;

public class Move {
    Shape shape;
    Tile tile;
    int heuristic;

    public Move(Shape s, Tile t){
        this.shape = s;
        this.tile = t;
        this.heuristic = Integer.MIN_VALUE;
    }

    public Shape getPiece(){
        return shape;
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
