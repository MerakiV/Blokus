package Structures;

import java.util.ArrayList;

//to do: change Shape names too (and enums?)

public class Shape {
	int Nlin, Ncol;
	boolean [][] shape; //values only 0 or 1
	int anchorX, anchorY;
	TileType name;

    // Note : 0 is northeast, 1 is northwest, 2 is southwest, 3 is southeast
    ArrayList<ArrayList<Tile>> avaliableCorners;

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
				if (isEmpty(i-1, j-1)) { avaliableCorners.get(0).add(new Tile(i,j)); }
			}
		}
	}

	int getLines() {
		return Nlin;
	}

	int getColumns() {
		return Ncol;
	}

	public void setShape(boolean [][] s){
		shape = s;
	}

	boolean [][] getShape() {
		return shape;
	}

	boolean isEmpty(int i, int j) {
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

		return new Shape(Ncol, Nlin, anchorX, anchorY, tab);
	}

	public Shape flipH() {
		boolean [][] tab = new boolean[Nlin][Ncol];

		for (int i=0; i<Nlin; i++) {
			for (int j=0; j<=Ncol/2; j++) {
				tab[i][j] = shape[i][Ncol - j - 1];
				tab[i][Ncol - j - 1] = shape[i][j];
			}
		}

		return new Shape(Ncol, Nlin, anchorX, anchorY, tab);
	}

	public Shape rotate90() {
		boolean [][] tab = new boolean[Nlin][Ncol];

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

		if (Nlin!=s2.getLines() || Ncol!=s2.getColumns()) {
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
}
