package Structures;

public class Tile {
    int x, y;

    public Tile(int xx, int yy) {
        x = xx;
        y = yy;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        Tile t2 = (Tile) o;
        return (x==t2.x && y==t2.y);
    }

    @Override
    public int hashCode() {
        return Board.size * x + y;
    }
}