package Structures;

public class Shape {
	int Nlin, Ncol;
	boolean [][] shape;

	public Shape(int l, int c) {
		Nlin = l;
		Ncol = c;
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
		Shape s2 = new Shape(Nlin, Ncol);
		boolean [][] tab = s2.getShape();

		for (int i=0; i<=Nlin/2; i++) {
			for (int j=0; j<Ncol; j++) {
				tab[i][j] = shape[Nlin - i - 1][j];
				tab[Nlin - i - 1][j] = shape[i][j];
			}
		}

		return s2;
	}

	public Shape flipH() {
		Shape s2 = new Shape(Nlin, Ncol);
		boolean [][] tab = s2.getShape();

		for (int i=0; i<Nlin; i++) {
			for (int j=0; j<=Ncol/2; j++) {
				tab[i][j] = shape[i][Ncol - j - 1];
				tab[i][Ncol - j - 1] = shape[i][j];
			}
		}

		return s2;
	}

	public Shape rotate90() {
		Shape s2 = new Shape(Ncol, Nlin);
		boolean [][] tab = s2.getShape();

		for (int i=0; i<Nlin; i++) {
			for (int j=0; j<Ncol; j++) {
				tab[j][Nlin - i - 1] = shape[i][j];
			}
		}

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