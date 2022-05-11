package Players;

import Structures.Color;
import Structures.Piece;
import Structures.PieceType;

import java.util.Set;

public abstract class Player {
    Color col;
    Set<Piece> pieces;
    boolean isAI;

    public Color getColor(){return col;}

    public Set<Piece> getPieces(){return pieces;}

    public void printPlayer(){
        System.out.println("Color : "+col.toString());

        System.out.println("List of pieces:");

        for (Piece p : pieces) {
            System.out.println(p.getName());
        }
    }

    public Piece takePiece(PieceType pt){
        Piece res;
        for(Piece p : pieces){
            if(p.getName().equals(pt)){
                res = p;
                pieces.remove(p);
                return res;
            }
        }
        System.out.println("Piece "+pt.toString()+" not found");
        return null;
    }

}
