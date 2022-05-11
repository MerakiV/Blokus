package Structures;

import Players.Player;
import Players.Player2P;

import java.util.List;

public abstract class Game {
    Board board;
    List<Player> players;
    Player currentPlayer;
    Color currentColor;

    History history;

    //to be implemented
    public boolean canBePlaced(Piece p){return true;}

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

    public void PushInPastStack(){
    }

    public abstract void nextTurn();


}