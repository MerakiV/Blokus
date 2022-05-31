package Players;

import Structures.Color;
import Structures.Piece;
import Structures.PieceType;
import Structures.Board;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Player implements Cloneable, Serializable {
    Color col;
    ArrayList<Piece> pieces;
    boolean isAI, hasMoves;
    int score;

    public Color getColor(){return col;}

    public ArrayList<Piece> getPieces(){return pieces;}

    public Piece getPiece(Piece p){
        for (Piece piece: pieces){
            if (p.getName().equals(piece.getName()))
                return piece;
        }
        return p;
    }

    public int getScore(){return score;}

    public void updateScore(int add){
        score+= add;
        //System.out.println("Player Updated score : " + score);
    }

    public boolean isAI(){return isAI;}

    public void updateMoves(Board b){
        if(b.sumAllPlacements(pieces,col) == 0){
            hasMoves = false;
        }
    }

    public boolean checkForMoves(Board b){
        updateMoves(b);
        return hasMoves;
    }

    public boolean hasMoves() {
        return hasMoves;
    }

    public void setHasMoves(boolean hasMoves) {
        this.hasMoves = hasMoves;
    }

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
            score += pr.getValue();
            pieces.remove(pr);
        }
    }

    /* Abstract class to be defined for Human and AI
    * Takes a piece from the player's list, places it on the board, removes it from the list,
    * and adds the value of the piece to score.
    * */
    public abstract void playPiece(Board b);

    @Override
    abstract public Player clone();

    // Can be used in subclasses' clone method.
    public void cloneFields(Player p2) {
        this.col = p2.col;
        this.pieces = (ArrayList<Piece>) p2.pieces.clone();
        this.isAI = p2.isAI;
        this.score = p2.score;
    }

    public boolean canMove() {
        return this.hasMoves;
    }
}
