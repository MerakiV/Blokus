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
            pushToPast();
            board.put(s, i, x, y);
            currentPlayer.removePiece(pt);

            //sets the new score for a player
            if(pt.toString().contains("FIVE")) currentPlayer.updateScore(5);
            else if(pt.toString().contains("FOUR")) currentPlayer.updateScore(4);
            else if(pt.toString().contains("THREE")) currentPlayer.updateScore(3);
            else if(pt.toString().contains("TWO")) currentPlayer.updateScore(2);
            else if(pt.toString().contains("ONE")) currentPlayer.updateScore(1);

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
        pushToFuture();
        GameState previous = history.undo();
        board = previous.board;
        currentPlayer = previous.player;
    }

    public void redo(){
        pushToPast();
        GameState next = history.redo();
        board = next.board;
        currentPlayer = next.player;
    }

    void pushToPast(){
        GameState gs = new GameState(board.clone(), currentPlayer.clone());
        history.pushToPast(gs);
    }

    void pushToFuture(){
        GameState gs = new GameState(board.clone(), currentPlayer.clone());
        history.pushToFuture(gs);
    }

    public abstract void nextTurn();

    @Override
    public Game clone() {
        return null;
    }

}