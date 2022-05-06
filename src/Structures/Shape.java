package Structures;

public class Shape {
	int Nlin, Ncol;
	boolean [][] shape;
	int anchorX, anchorY;

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

	boolean [][] getShape() {
		return shape;
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
		Shape s2 = new Shape(Nlin, Ncol, anchorX, anchorY);
		boolean [][] tab = s2.getShape();

		for (int i=0; i<Nlin; i++) {
			for (int j=0; j<Ncol; j++) {
				tab[j][Nlin - i - 1] = shape[i][j];
			}
		}

		s2.anchorX = anchorY;
		s2.anchorY = s2.Nlin-anchorX-1;

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

	public void printShape() {
		for (int i=0; i<Nlin; i++) {
			for (int j=0; j<Ncol; j++) {
				if (shape[i][j]) {
					System.out.print("#");
				} else {
					System.out.print("-");
				}
			}
			System.out.print("\n");
		}
	}
}