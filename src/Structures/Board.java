package Structures;

import java.util.ArrayList;
import java.util.HashSet;

public class Board {
    public static final int size = 20;
    // Contents of the board
    Color [][] grid;

    // Colors of every corner
    // Note : 0 is top left, 1 is top right, 2 is bottom left, 3 is bottom right
    Color [] cornerColors;

    // For every color and every direction, a set of all potential corners avaliable for putting a new piece
    // Note : the first index relates to the color of the pieces (and its position in cornerColors)
    //        the second represents the orientation of the corner (same as before).
    ArrayList<ArrayList<HashSet<Tile>>> availableCorners;

    public Board(Color tlColor, Color trColor, Color brColor, Color blColor) {
        grid = new Color[size][size];
        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                grid[i][j] = Color.WHITE;
            }
        }

        cornerColors = new Color[4];
        cornerColors[0] = tlColor;
        cornerColors[1] = trColor;
        cornerColors[2] = brColor;
        cornerColors[3] = blColor;

        availableCorners = new ArrayList<>(4);
        for (int i=0; i<4; i++) {
            availableCorners.add(new ArrayList<>(4));
            for (int j=0; j<4; j++) {
                availableCorners.get(i).add(new HashSet<>());
            }
        }

        availableCorners.get(0).get(0).add(new Tile(0,      0     ));
        availableCorners.get(1).get(1).add(new Tile(0,      size-1));
        availableCorners.get(2).get(2).add(new Tile(size-1, 0     ));
        availableCorners.get(3).get(3).add(new Tile(size-1, size-1));
    }

    // Returns the color on the given indexes, or WHITE if it's out of bounds.
    public Color getColor(int i, int j) {
        if (i<0 || i>=size || j<0 || j>=size) {
            return Color.WHITE;
        }
        return grid[i][j];
    }

    // Returns the index of the corner associated to this color, or -1 if there is none.
    public int getCorner(Color col) {
        for (int i=0; i<4; i++) {
            if (col==cornerColors[i]) {
                return i;
            }
        }
        return -1;
    }

    public boolean canPut(Piece p, int color, int x, int y) {
        Shape s = p.getShape();
        // If the position is not on the grid :
        if (x<0 || y<0 || x+s.getLines()>=size || y+s.getColumns()>=size) {
            return false;
        }

        boolean foundCorner = false;
        Tile t;

        for (int i=0; i<s.getLines(); i++) {
            for (int j=0; j<s.getColumns(); j++) {
                if (!s.isEmpty(i, j)) {
                    // Checking if there is already something under the piece.
                    if (grid[x+i][y+j]!=Color.WHITE) { return false; }

                    // Checking if one of our piece' tiles touches a tile of the same color.
                    if (getColor(x+i+1, y+j  )==cornerColors[color]) { return false; }
                    if (getColor(x+i  , y+j+1)==cornerColors[color]) { return false; }
                    if (getColor(x+i-1, y+j  )==cornerColors[color]) { return false; }
                    if (getColor(x+i  , y+j-1)==cornerColors[color]) { return false; }

                    t = new Tile(x+i, y+j);
                    for (int k=0; foundCorner || k<4; k++) {
                        foundCorner = foundCorner || availableCorners.get(color).get(k).contains(t);
                    }
                }
            }
        }

        return foundCorner;
    }

    // Warning : unchecked. Puts the piece on the board without checking if it's a valid move.
    public void put(Piece p, int color, int x, int y) {
        Shape s = p.getShape();
        Tile t;

        for (int i=0; i<s.getLines(); i++) {
            for (int j=0; j<s.getLines(); j++) {
                if (!s.isEmpty(i, j)) {
                    grid[x+i][y+j] = cornerColors[color];

                    t = new Tile(x+i, y+j);
                    for (int k=0; k<4; k++) {
                        for (int l=0; l<4; l++) {
                            // Removes the tiles this piece is occupying if it's an avaliable corner for any color.
                            availableCorners.get(k).get(l).remove(t);
                        }
                    }

                    if (x+i>0      && y+j>0      && s.isEmpty(i-1, j) && s.isEmpty(i, j-1) && s.isEmpty(i-1, j-1) && grid[x+i-1][y+j-1]==Color.WHITE) {
                        availableCorners.get(color).get(0).add(new Tile(x+i-1, y+j-1));
                    }
                    if (x+i>0      && y+j<size-1 && s.isEmpty(i-1, j) && s.isEmpty(i, j+1) && s.isEmpty(i-1, j+1) && grid[x+i-1][y+j+1]==Color.WHITE) {
                        availableCorners.get(color).get(1).add(new Tile(x+i-1, y+j+1));
                    }
                    if (x+i<size-1 && y+j<size-1 && s.isEmpty(i+1, j) && s.isEmpty(i, j+1) && s.isEmpty(i+1, j+1) && grid[x+i+1][y+j+1]==Color.WHITE) {
                        availableCorners.get(color).get(0).add(new Tile(x+i+1, y+j+1));
                    }
                    if (x+i<size-1 && y+j>0      && s.isEmpty(i+1, j) && s.isEmpty(i, j-1) && s.isEmpty(i+1, j-1) && grid[x+i+1][y+j-1]==Color.WHITE) {
                        availableCorners.get(color).get(3).add(new Tile(x+i+1, y+j-1));
                    }
                }
            }
        }
    }

    // Checks if a piece of a given color can be put. If so, puts it and eturns true. Returns false if not.
    public boolean checkAndPut(Piece p, int color, int x, int y) {
        if (canPut(p, color, x, y)) {
            put(p, color, x, y);
            return true;
        }
        return false;
    }

    // Prints the grid on standard output.
    // if seefor!=-1, shows the avaliable corners for this player to put a piece.
    public void printBoard(int seeFor) {
        Tile t;
        boolean b;

        for (int i=0; i<size; i++) {
            for (int j=0; j<size; j++) {
                switch(grid[i][j]) {
                    case BLUE:
                        System.out.print("B");
                        break;
                    case RED:
                        System.out.print("R");
                        break;
                    case GREEN:
                        System.out.print("G");
                        break;
                    case YELLOW:
                        System.out.print("Y");
                        break;
                    case WHITE:
                        if (seeFor==-1) {
                            System.out.print("-");
                        } else {
                            t = new Tile(i,j);
                            b = false;
                            for (int k=0; !b && k<4; k++) {
                                b = b || availableCorners.get(seeFor).get(k).contains(t);
                            }
                            if (b) {
                                System.out.print("x");
                            } else {
                                System.out.print("-");
                            }
                        }
                        break;
                }
            }
            System.out.print("\n");
        }
    }
}
