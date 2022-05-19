import java.util.List;
import java.util.Random;

import Structures.*;

class TestPiece {
    public static void main(String [] args) {
        Random r = new Random();

        try {
            PieceReader pr = new PieceReader();

            List<Piece> l = pr.getPiecesList();
            Piece p = l.get(r.nextInt(l.size()));

            System.out.println("Random piece gotten :");
            p.printPiece();
            int operation;

            for (int i = 0; i<5; i++) {
                operation = r.nextInt(4);
                switch(operation) {
                    case 0:
                        System.out.println("Rotating it clockwise :");
                        p.rotateClockwise();
                        p.printPiece();
                        break;
                    case 1:
                        System.out.println("Rotating it counter-clockwise :");
                        p.rotateCounterclockwise();
                        p.printPiece();
                        break;
                    case 2:
                        System.out.println("Flipping it horizontally :");
                        p.flipH();
                        p.printPiece();
                        break;
                    default:
                        System.out.println("Flipping it vertically :");
                        p.flipV();
                        p.printPiece();
                        break;
                }
            }
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}