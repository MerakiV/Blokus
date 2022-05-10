package Structures;

//to do: change Shape names too (and enums?)

public class Shape {
	int Nlin, Ncol;
	boolean [][] shape; //values only 0 or 1
	int anchorX, anchorY;
	String name;
	public Shape(int l, int c, int ax, int ay) {
		Nlin = l;
		Ncol = c;
		anchorX = ax;
		anchorY = ay;
		shape = new boolean[l][c];
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
		Shape s2 = new Shape(Nlin, Ncol, anchorX, anchorY);
		boolean [][] tab = s2.getShape();

		for (int i=0; i<=Nlin/2; i++) {
			for (int j=0; j<Ncol; j++) {
				tab[i][j] = shape[Nlin - i - 1][j];
				tab[Nlin - i - 1][j] = shape[i][j];
			}
		}

		s2.anchorX = s2.Nlin-anchorX-1;
		s2.anchorY = anchorY;

		return s2;
	}

	public Shape flipH() {
		Shape s2 = new Shape(Nlin, Ncol, anchorX, anchorY);
		boolean [][] tab = s2.getShape();

		for (int i=0; i<Nlin; i++) {
			for (int j=0; j<=Ncol/2; j++) {
				tab[i][j] = shape[i][Ncol - j - 1];
				tab[i][Ncol - j - 1] = shape[i][j];
			}
		}

		s2.anchorX = anchorX;
		s2.anchorY = s2.Ncol-anchorY-1;

		return s2;
	}

	public Shape rotate90() {
		Shape s2 = new Shape(Ncol, Nlin, anchorX, anchorY);
		boolean [][] tab = new boolean[Ncol][Nlin];

		for (int i=0; i<Nlin; i++) {
			for (int j=0; j<Ncol; j++) {
				tab[j][Nlin - i - 1] = shape[i][j];
			}
		}

		s2.setShape(tab);

		s2.anchorX = anchorY;
		s2.anchorY = s2.Ncol-anchorX-1;

		return s2;
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

	public void setName(String s){
		name = s;
	}
	public String getName(){
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
