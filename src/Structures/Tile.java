package Structures;

public class Tile {
    int x, y;

    Tile(int xx, int yy) {
        x = xx;
        y = yy;
    }

    int getX() {
        return x;
    }

    int getY() {
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