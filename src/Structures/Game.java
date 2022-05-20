package Structures;

import Players.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import java.io.Serializable;

public abstract class Game implements Serializable, Cloneable {
    Board board;
    List<Player> players;
    Player currentPlayer;
    Color currentColor;

    History history;

    public boolean put(Piece p, Color c, int x, int y) {
        return put(p.getShape(), p.getName(), c, x, y);
    }

    public boolean put(Shape s, PieceType pt, Color c, int x, int y){
        if(board.checkAndPut(s, c, x, y)){
            currentPlayer.removePiece(pt);
            return true;
        }
        else{
            return false;
        }
    }

    public Player getCurrentPlayer(){return currentPlayer;}

    public Color getCurrentColor(){return currentColor;}

    public List<Player> getPlayerList(){return players;}

    public Board getBoard(){return board;}

    public void undo(){
        GameState previous = history.undo();
        board = previous.board;
        currentPlayer = previous.player;
    }

    public void redo(){
        GameState next = history.redo();
        board = next.board;
        currentPlayer = next.player;
    }

    void pushToPast(){
        GameState gs = new GameState(board, currentPlayer);
        history.pushToPast(gs);
    }

    void pushToFuture(){
        GameState gs = new GameState(board, currentPlayer);
        history.pushToFuture(gs);
    }

    public abstract void nextTurn();

    // Must be overwritten in subclasses.
    @Override
    public Object clone() { return null; }
    
    // Can be used in subclasses' clone method.
    public void cloneFields(Game g2) {
        this.board = (Board) g2.board.clone();
        this.currentPlayer = null;

        this.players = new ArrayList<>(g2.players.size());
        for (int i=0; i<g2.players.size(); i++) {
            this.players.add( (Player) g2.players.get(i).clone());
            if (g2.players.get(i)==g2.currentPlayer) {
                this.currentPlayer = this.players.get(i);
            }
        }

        this.currentColor = g2.currentColor;
        this.history = g2.history;
    }

    public void printGame(boolean boardOnly) {
        System.out.println("---- Gameboard :");
        board.printBoard(-1);

        if (!boardOnly) {
            System.out.println();

            int p = 1;
            Iterator<Player> it1 = players.iterator();
            Player pl;
            Iterator<Piece> it2;
            Piece pi;

            while(it1.hasNext()) {
                pl = it1.next();
                System.out.println("---- Color "+p+" pieces :");
                p++;

                it2 = pl.getPieces().iterator();
                while (it2.hasNext()) {
                    pi = it2.next();
                    pi.getShape().printShape();
                    System.out.println();
                }
            }
        }
    }

}