package Structures;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class Piece {
	int disp;
	int [] dispositionList;
	List<Shape> shapeList;

	 public Piece(Shape base) {
		disp = 0;
		dispositionList = new int[16];
		shapeList = new ArrayList<>();

		int i, d, t;
		Shape s = base;
		Shape [] allDisp = new Shape[16];
		// Calculating all possible rotations/flips
		for (i=0; i<4; i++) {
			allDisp[i]     = s;
			allDisp[4 + i] = s.flipV();
			allDisp[8 + i] = s.flipH();
			allDisp[12+ i] = allDisp[8 + i].flipV();
			s = s.rotate90();
		}

		// Finding dupes
		for (i=0; i<16; i++) {
			s = allDisp[i];
			d = shapeList.indexOf(s);
			if (d==-1) {
				shapeList.add(s);
				dispositionList[i] = shapeList.size() - 1;
			} else {
				dispositionList[i] = d;
			}
		}
	}

	public void flipV() {
		int r, fv, fh, newbit;
		r  = disp & ((1 << 0) | (1 << 1));
		fv = disp & (1 << 2);
		fh = disp & (1 << 3);

		if (fv==0) {
			newbit = (1 << 2);
		} else {
			newbit = 0;
		}

		disp = r | newbit | fh;
	}

	public void flipH() {
		int r, fv, fh, newbit;
		r  = disp & ((1 << 0) | (1 << 1));
		fv = disp & (1 << 2);
		fh = disp & (1 << 3);

		if (fh==0) {
			newbit = (1 << 3);
		} else {
			newbit = 0;
		}

		disp = r | fv | newbit;
	}

	public void rotateClockwise() {
		int r, fv, fh, newbit;
		r  = disp & ((1 << 0) | (1 << 1));
		fv = disp & (1 << 2);
		fh = disp & (1 << 3);

		newbit = (r+1)%4;

		disp = newbit | fv | fh;
	}

	public void rotateCounterclockwise() {
		int r, fv, fh, newbit;
		r  = disp & ((1 << 0) | (1 << 1));
		fv = disp & (1 << 2);
		fh = disp & (1 << 3);

		newbit = (r-1)%4;

		disp = newbit | fv | fh;
	}

	public void printPiece() {
		Shape s;
		int i = 0;
		System.out.println("--All possible shapes :");
		Iterator<Shape> it = shapeList.iterator();
		while(it.hasNext()) {
			System.out.println("- " + i + (i==disp ? " (current)" : ""));
			i++;
			s = it.next();
			s.printShape();
		}

		System.out.print("\n--Contents of dispositionList :\n- ");
		for (i=0; i<16; i++) {
			System.out.print(dispositionList[i] + " ");
		}

		System.out.print("\n");
	}
}