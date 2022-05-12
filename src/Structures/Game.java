package Structures;

import Players.Player;
import Players.Player2P;

import java.util.List;

import java.io.Serializable;

public abstract class Game implements Serializable {
    Board board;
    List<Player> players;
    Player currentPlayer;
    Color currentColor;

    History history;

    public boolean put(Piece p, Color c, int x, int y){
        int i = board.getCorner(c);
        if(board.canPut(p, i, x, y)){
            board.put(p, i, x, y);
            currentPlayer.removePiece(p.getName());
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

}