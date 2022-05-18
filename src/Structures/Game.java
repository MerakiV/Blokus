package Structures;

import Players.Player;
import Players.Player2P;

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
        int i = board.getCorner(c);
        if(board.canPut(s, i, x, y)){
            board.put(s, i, x, y);
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

    @Override
    public Game clone() {
        return null;
    }

}