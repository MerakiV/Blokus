import Structures.Color;

import java.util.Iterator;
import java.util.Set;

import Structures.Board;
import Structures.Piece;
import Structures.PieceReader;


// Not finished

class TestBoard {
    public static void main(String [] args) {
        Board b = new Board(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
        b.printBoard(-1);

        try {
            PieceReader pr = new PieceReader();
            pr.readPieces();

            Set<Piece> l = pr.getPiecesList();
            Iterator<Piece> it = l.iterator();
            if (it.hasNext()) {
                Piece p = it.next();
                p.printPiece();
            } else {
                System.out.println("Itérateur vide.");
            }
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}