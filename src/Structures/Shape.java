package Structures;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

//to do: change Shape names too (and enums?)

public class Shape implements Serializable {
	public int Nlin, Ncol;
	public boolean [][] shape; //values only 0 or 1
	public int anchorX, anchorY;
	TileType name;

	// Note : 0 is northeast, 1 is northwest, 2 is southwest, 3 is southeast
	ArrayList<ArrayList<Tile>> avaliableCorners;

	// Mainly for debugging purposes.
	public static String intToDirection(int dir) {
		switch(dir) {
			case 0:
				return "northwest";
			case 1:
				return "northeast";
			case 2:
				return "southeast";
			case 3:
				return "southwest";
			default:
				return "unknown";
		}
	}

	public Shape(int l, int c, int ax, int ay, boolean [][] s) {
		Nlin = l;
		Ncol = c;
		anchorX = ax;
		anchorY = ay;
		shape = s;
		avaliableCorners = new ArrayList<>(4);
		for (int i=0; i<4; i++) {
			avaliableCorners.add(new ArrayList<>(3));
		}
		for (int i=0; i<Nlin; i++) {
			for (int j=0; j<Ncol; j++) {
				if (!isEmpty(i, j)) {
					if (isEmpty(i-1, j) && isEmpty(i, j-1) && isEmpty(i-1, j-1)) { avaliableCorners.get(2).add(new Tile(i, j)); }
					if (isEmpty(i-1, j) && isEmpty(i, j+1) && isEmpty(i-1, j+1)) { avaliableCorners.get(3).add(new Tile(i, j)); }
					if (isEmpty(i+1, j) && isEmpty(i, j+1) && isEmpty(i+1, j+1)) { avaliableCorners.get(0).add(new Tile(i, j)); }
					if (isEmpty(i+1, j) && isEmpty(i, j-1) && isEmpty(i+1, j-1)) { avaliableCorners.get(1).add(new Tile(i, j)); }
				}
			}
		}
	}

	public int getLines() {
		return Nlin;
	}

	public int getColumns() {
		return Ncol;
	}

	public int getAnchorX() {
		return anchorX;
	}

	public int getAnchorY() {
		return anchorY;
	}

	public void setShape(boolean [][] s){
		shape = s;
	}

	public boolean [][] getShape() {
		return shape;
	}

	public ArrayList<Tile> getCornerList(int dir) {
		return avaliableCorners.get(dir);
	}

	public boolean isEmpty(int i, int j) {
		return (i<0 || i>=Nlin || j<0 || j>=Ncol || !shape[i][j]);
	}

	public Shape flipV() {
		boolean [][] tab = new boolean[Nlin][Ncol];

		for (int i=0; i<=Nlin/2; i++) {
			for (int j=0; j<Ncol; j++) {
				tab[i][j] = shape[Nlin - i - 1][j];
				tab[Nlin - i - 1][j] = shape[i][j];
			}
		}

		return new Shape(Nlin, Ncol, Nlin - anchorX - 1, anchorY, tab);
	}

	public Shape flipH() {
		boolean [][] tab = new boolean[Nlin][Ncol];

		for (int i=0; i<Nlin; i++) {
			for (int j=0; j<=Ncol/2; j++) {
				tab[i][j] = shape[i][Ncol - j - 1];
				tab[i][Ncol - j - 1] = shape[i][j];
			}
		}

		return new Shape(Nlin, Ncol, anchorX, Ncol - anchorY - 1, tab);
	}

	public Shape rotate90() {
		boolean [][] tab = new boolean[Ncol][Nlin];

		for (int i=0; i<Nlin; i++) {
			for (int j=0; j<Ncol; j++) {
				tab[j][Nlin - i - 1] = shape[i][j];
			}
		}

		return new Shape(Ncol, Nlin, anchorY, Nlin - anchorX - 1, tab);
	}

	@Override
	public boolean equals(Object o) {
		Shape s2 = (Shape) o;

		if (Nlin!=s2.getLines() || Ncol!=s2.getColumns() || anchorX!=s2.anchorX || anchorY!=s2.anchorY) {
			return false;
		}

		boolean [][] tab = s2.getShape();
		for (int i=0; i<Nlin; i++) {
			for (int j=0; j<Ncol; j++) {
				if (shape[i][j]!=tab[i][j]) {
					return false;
				}
			}
		}

		return true;
	}

	public void setName(TileType tt){
		name = tt;
	}
	public TileType getName(){
		return name;
	}

	public void printShape() {
		for (int i=0; i<Nlin; i++) {
			for (int j=0; j<Ncol; j++) {
				if (shape[i][j]) {
					if(i == anchorX && j == anchorY)
						System.out.print("X");
					else
						System.out.print("#");
				} else {
					System.out.print("-");
				}
			}
			System.out.print("\n");
		}
	}

	public void printShapeCorners() {
		int bin;

		for (int i=-1; i<Nlin+1; i++) {
			for (int j=-1; j<Ncol+1; j++) {
				if (!isEmpty(i, j)) {
					if(i == anchorX && j == anchorY)
						System.out.print("X");
					else
						System.out.print("#");
				} else {
					bin = 0;

					bin = bin | ((avaliableCorners.get(2).contains(new Tile(i+1, j+1))) ? 1 : 0) << 0;
					bin = bin | ((avaliableCorners.get(3).contains(new Tile(i+1, j-1))) ? 1 : 0) << 1;
					bin = bin | ((avaliableCorners.get(0).contains(new Tile(i-1, j-1))) ? 1 : 0) << 2;
					bin = bin | ((avaliableCorners.get(1).contains(new Tile(i-1, j+1))) ? 1 : 0) << 3;

					System.out.print((bin==0) ? "-" : Integer.toHexString(bin));
				}
			}
			System.out.print("\n");
		}
	}

	@Override
	/*
	Hashes each line of the shape array and puts the hashcodes into an array of int and then hashes that array.
	 */
	public int hashCode(){
		int [] lineHash = new int[Nlin];
		int hash ;
		for(int i = 0 ; i < Nlin ; i++) {
			lineHash[i] = Arrays.hashCode(shape[i]);
		}
		hash = Arrays.hashCode(lineHash);
		return hash;
	}

	public int getValue(){
		int sum = 0;
		for(int i = 0 ; i < Nlin ; i++){
			for(int j = 0 ; j < Ncol ; j++){
				if(shape[i][j]) sum++;
			}
		}
		return sum;
	}

}
