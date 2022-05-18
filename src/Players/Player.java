package Players;

import Structures.Color;
import Structures.Piece;
import Structures.PieceType;
import Structures.Board;

import java.util.List;

public abstract class Player {
    Color col;
    List<Piece> pieces;
    boolean isAI;
    int score;

    public Color getColor(){return col;}

    public List<Piece> getPieces(){return pieces;}

    public int getScore(){return score;}

    public boolean isAI(){return isAI;}

    public void printPlayer(){
        System.out.println("Color : "+col.toString());

        System.out.println("List of pieces:");

        for (Piece p : pieces) {
            System.out.println(p.getName());
        }
    }

    /*
    Takes piece from player's current list of pieces according to PieceType given in parameter.
    If the piece is not in the list, returns null.
     */
    public Piece takePiece(PieceType pt){
        Piece res;
        for(Piece p : pieces){
            if(p.getName().equals(pt)){
                res = p;
                //pieces.remove(p);
                return res;
            }
        }
        System.out.println("Piece "+pt.toString()+" not found");
        return null;
    }

    public void removePiece(Piece p){
        removePiece(p.getName());
    }

    public void removePiece(PieceType pt){
        Piece pr = null;
        boolean found = false;
        for(Piece p : pieces){
            if(p.getName().equals(pt)){
                found = true;
                pr = p;
            }
        }
        if(!found){
            System.out.println("Piece "+pt.toString()+" not found");
        } else{
            pieces.remove(pr);
        }
    }

    /* Abstract class to be defined for Human and AI
    * Takes a piece from the player's list, places it on the board, removes it from the list,
    * and adds the value of the piece to score.
    * */
    public abstract void playPiece(Board b);
}
