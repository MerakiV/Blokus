package Structures;

import Players.Player;

public abstract class Game {
    Board board;
    Player currentPlayer;

    //to be implemented
    public boolean canBePlaced(Piece p){return true;}

    public Player getCurrentPlayer(){return currentPlayer;};

    public abstract void nextTurn();
}