package Structures;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class Board implements Cloneable, Serializable {
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

        availableCorners.get(0).get(2).add(new Tile(0,      0     ));
        availableCorners.get(1).get(3).add(new Tile(0,      size-1));
        availableCorners.get(2).get(0).add(new Tile(size-1, size-1));
        availableCorners.get(3).get(1).add(new Tile(size-1, 0     ));
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

    // Returns the number of avaliable corners for a given color
    public int numberOfCorners(Color col) {
        int ncol = getCorner(col);
        if (ncol==-1) { return 0; }
        int count = 0;
        for (int k = 0; k<4; k++) {
            count+= availableCorners.get(ncol).get(k).size();
        }
        return count;
    }

    public HashSet<Tile> getCorners(int ncol, int dir) {
        return availableCorners.get(ncol).get(dir);
    }
    public HashSet<Tile> getCorners(Color col, int dir) {
        return getCorners(getCorner(col), dir);
    }








    
    public boolean canPut(Shape s, int color, int ax, int ay) {
        int x = ax - s.anchorX;
        int y = ay - s.anchorY;
        // If the position is not on the grid :
        if (x<0 || y<0 || x+s.getLines()>size || y+s.getColumns()>size) {
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
                    for (int k=0; !foundCorner && k<4; k++) {
                        foundCorner = foundCorner || availableCorners.get(color).get(k).contains(t);
                    }
                }
            }
        }

        return foundCorner;
    }

    // Warning : unchecked. Puts the piece on the board without checking if it's a valid move.
    public void put(Shape s, int color, int ax, int ay) {
        int x = ax - s.anchorX;
        int y = ay - s.anchorY;
        Tile tb, ts;

        for (int i=0; i<s.getLines(); i++) {
            for (int j=0; j<s.getColumns(); j++) {
                if (!s.isEmpty(i, j)) {
                    grid[x+i][y+j] = cornerColors[color];

                    tb = new Tile(x+i, y+j);
                    for (int c=0; c<4; c++) {
                        for (int dir=0; dir<4; dir++) {
                            // Removes the tiles this piece is occupying if it's an avaliable corner for any color.
                            if (availableCorners.get(c).get(dir).remove(tb)) {
                                // System.out.println("Removed ("+tb.getX()+","+tb.getY()+") from "+cornerColors[c]+"'s "+Shape.intToDirection(dir)+" corners : a piece now covers it."); // debug
                            }
                        }
                    }

                    // Removes all corners of a color adjacent to a tile of the same color
                    for (int dir = 0; dir<4; dir++) {
                        tb = new Tile(x+i-1, y+j  );
                        if (availableCorners.get(color).get(dir).remove(tb)) {
                            // System.out.println("Removed ("+tb.getX()+","+tb.getY()+") from "+cornerColors[color]+"'s "+Shape.intToDirection(dir)+" corners : is now next to a tile of the same color."); // debug
                        }
                        tb = new Tile(x+i  , y+j+1);
                        if (availableCorners.get(color).get(dir).remove(tb)) {
                            // System.out.println("Removed ("+tb.getX()+","+tb.getY()+") from "+cornerColors[color]+"'s "+Shape.intToDirection(dir)+" corners : is now next to a tile of the same color."); // debug
                        }
                        tb = new Tile(x+i+1, y+j  );
                        if (availableCorners.get(color).get(dir).remove(tb)) {
                            // System.out.println("Removed ("+tb.getX()+","+tb.getY()+") from "+cornerColors[color]+"'s "+Shape.intToDirection(dir)+" corners : is now next to a tile of the same color."); // debug
                        }
                        tb = new Tile(x+i  , y+j-1);
                        if (availableCorners.get(color).get(dir).remove(tb)) {
                            // System.out.println("Removed ("+tb.getX()+","+tb.getY()+") from "+cornerColors[color]+"'s "+Shape.intToDirection(dir)+" corners : is now next to a tile of the same color."); // debug
                        }
                    }

                    ts = new Tile(i,j);
                    if (x+i>0      && y+j>0      && s.getCornerList(2).contains(ts) && grid[x+i-1][y+j-1]==Color.WHITE && getColor(x+i-2, y+j-1)!=cornerColors[color] && getColor(x+i-1, y+j-2)!=cornerColors[color]) {
                        tb = new Tile(x+i-1, y+j-1);
                        availableCorners.get(color).get(0).add(tb);
                        // System.out.println("Added ("+tb.getX()+","+tb.getY()+") to "+cornerColors[color]+"'s "+Shape.intToDirection(0)+" corners."); // debug
                    }
                    if (x+i>0      && y+j<size-1 && s.getCornerList(3).contains(ts) && grid[x+i-1][y+j+1]==Color.WHITE && getColor(x+i-2, y+j+1)!=cornerColors[color] && getColor(x+i-1, y+j+2)!=cornerColors[color]) {
                        tb = new Tile(x+i-1, y+j+1);
                        availableCorners.get(color).get(1).add(tb);
                        // System.out.println("Added ("+tb.getX()+","+tb.getY()+") to "+cornerColors[color]+"'s "+Shape.intToDirection(1)+" corners."); // debug
                    }
                    if (x+i<size-1 && y+j<size-1 && s.getCornerList(0).contains(ts) && grid[x+i+1][y+j+1]==Color.WHITE && getColor(x+i+2, y+j+1)!=cornerColors[color] && getColor(x+i+1, y+j+2)!=cornerColors[color]) {
                        tb = new Tile(x+i+1, y+j+1);
                        availableCorners.get(color).get(2).add(tb);
                        // System.out.println("Added ("+tb.getX()+","+tb.getY()+") to "+cornerColors[color]+"'s "+Shape.intToDirection(2)+" corners."); // debug
                    }
                    if (x+i<size-1 && y+j>0      && s.getCornerList(1).contains(ts) && grid[x+i+1][y+j-1]==Color.WHITE && getColor(x+i+2, y+j-1)!=cornerColors[color] && getColor(x+i+1, y+j-2)!=cornerColors[color]) {
                        tb = new Tile(x+i+1, y+j-1);
                        availableCorners.get(color).get(3).add(tb);
                        // System.out.println("Added ("+tb.getX()+","+tb.getY()+") to "+cornerColors[color]+"'s "+Shape.intToDirection(3)+" corners."); // debug
                    }
                }
            }
        }
    }

    // Checks if a piece of a given color can be put. If so, puts it and eturns true. Returns false if not.
    public boolean checkAndPut(Shape s, int color, int ax, int ay) {
        if (canPut(s, color, ax, ay)) {
            put(s, color, ax, ay);
            return true;
        }
        return false;
    }

    // Homonymes functions that directly take colors as their arguments.
    public boolean canPut(Shape s, Color col, int ax, int ay) {
        int c = getCorner(col);
        return ((c!=-1) ? canPut(s, c, ax, ay) : false); }
    public void put(Shape s, Color col, int ax, int ay) {
        int c = getCorner(col);
        if (c!=-1) { put(s, c, ax, ay); } }
    public boolean checkAndPut(Shape s, Color col, int ax, int ay) {
        int c = getCorner(col);
        return ((c!=-1) ? checkAndPut(s, c, ax, ay) : false); }
    // Homonymes functions that take pieces and not shapes as their arguments.
    public boolean canPut(Piece p, Color col, int ax, int ay) { return canPut(p.getShape(), col, ax, ay); }
    public void put(Piece p, Color col, int ax, int ay) { put(p.getShape(), col, ax, ay); }
    public boolean checkAndPut(Piece p, Color col, int ax, int ay) { return checkAndPut(p.getShape(), col, ax, ay); }
    public boolean canPut(Piece p, int color, int ax, int ay) { return canPut(p.getShape(), color, ax, ay); }
    public void put(Piece p, int color, int ax, int ay) { put(p.getShape(), color, ax, ay); }
    public boolean checkAndPut(Piece p, int color, int ax, int ay) { return checkAndPut(p.getShape(), color, ax, ay); }








    // Returns the set of positions where the given shape of the given color can be put.
    public HashSet<Tile> fullcheck(Shape sh, int color) {
        // System.out.println("*** Iterating on the following :"); // debug
        // sh.printShapeCorners(); // debug
        HashSet<Tile> li = new HashSet<>();
        Iterator<Tile> itb, its;
        Tile tb, ts;
        int dir, ax, ay;

        for (dir = 0; dir<4; dir++) {
            // System.out.println("****** Iterating on direction "+ Shape.intToDirection(dir) +" :"); // debug

            itb = availableCorners.get(color).get(dir).iterator();
            while (itb.hasNext()) {
                tb = itb.next();
                // System.out.println("********* Iterating on board corner ("+tb.getX()+","+tb.getY()+") :"); // debug

                its = sh.getCornerList(dir).iterator();
                while(its.hasNext()) {
                    ts = its.next();
                    // System.out.println("************ Iterating on shape corner ("+ts.getX()+","+ts.getY()+") :"); // debug

                    ax = tb.getX() - ts.getX() + sh.getAnchorX();
                    ay = tb.getY() - ts.getY() + sh.getAnchorY();
                    // System.out.println("************ Trying to put shape at ("+ax+","+ay+") :"); // debug
                    if (canPut(sh, color, ax, ay)) {
                        // System.out.println("*************** Success !"); // debug
                        li.add(new Tile(ax, ay));
                    }
                }
            }
        }

        return li;
    }

    public HashSet<Tile> fullcheck(Shape sh, Color col) { return fullcheck(sh,            getCorner(col)); }
    public HashSet<Tile> fullcheck(Piece pi, int color) { return fullcheck(pi.getShape(), color         ); }
    public HashSet<Tile> fullcheck(Piece pi, Color col) { return fullcheck(pi.getShape(), getCorner(col)); }

    // Returns the number of a possible moves for a given piece (and all its shapes)
    public int sumPiecePlacements(Piece p, int color) {
        int sum = 0;
        Shape sh;
        HashSet<Tile> hs;

        List<Shape> ls = p.getShapeList();
        Iterator<Shape> its = ls.iterator();
        while (its.hasNext()) {
            sh = its.next();
            hs = fullcheck(sh, color);
            sum+=hs.size();
        }

        return sum;
    }

    public int sumPiecePlacements(Piece p, Color col) { return sumPiecePlacements(p, getCorner(col)); }

    // Returns the sum of all possible moves with all the pieces of a given list.
    public int sumAllPlacements(List<Piece> lp, int color) {
        int sum = 0;

        Piece p;
        Iterator<Piece> itp = lp.iterator();
        while(itp.hasNext()) {
            p = itp.next();
            sum += sumPiecePlacements(p, color);
        }

        return sum;
    }

    public int sumAllPlacements(List<Piece> lp, Color col) { return sumAllPlacements(lp, getCorner(col)); }








    // Same functions as before, but only return a boolean if the piece(s) is placeable, and stop after finding one good shape.
    public boolean canPlacePiece(Piece p, int color) {
        Shape sh;
        HashSet<Tile> hs;

        List<Shape> ls = p.getShapeList();
        Iterator<Shape> its = ls.iterator();
        while (its.hasNext()) {
            sh = its.next();
            hs = fullcheck(sh, color);
            if (!hs.isEmpty()) { return true; }
        }

        return false;
    }

    public boolean canPlacePiece(Piece p, Color col) { return canPlacePiece(p, getCorner(col)); }

    public boolean canPlacePieces(List<Piece> lp, int color) {

        Piece p;
        Iterator<Piece> itp = lp.iterator();
        while(itp.hasNext()) {
            p = itp.next();
            if (canPlacePiece(p, color)) { return true; }
        }

        return false;
    }

    public boolean canPlacePieces(List<Piece> lp, Color col) { return canPlacePieces(lp, getCorner(col)); }









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

    @Override
    public Board clone() {
        Board b2 = new Board(cornerColors[0], cornerColors[1], cornerColors[2], cornerColors[3]);
        int i,j;

        for (i=0; i<size; i++) {
            for (j=0; j<size; j++) {
                b2.grid[i][j] = grid[i][j];
            }
        }

        b2.availableCorners = new ArrayList<>(4);
        for (i=0; i<availableCorners.size(); i++) {
            b2.availableCorners.add(new ArrayList<>(4));
            for (j=0; j<availableCorners.get(i).size(); j++) {
                b2.availableCorners.get(i).add( (HashSet<Tile>) availableCorners.get(i).get(j).clone());
            }
        }

        return b2;
    }
}
