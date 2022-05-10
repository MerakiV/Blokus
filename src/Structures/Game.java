package Structures;

import Players.Player;

public abstract class Game {
    Board board;
    Player currentPlayer;
    Color currentColor;

    //to be implemented
    public boolean canBePlaced(Piece p){return true;}

    public Player getCurrentPlayer(){return currentPlayer;}

    public Color getCurrentColor(){return currentColor;}

    public abstract void nextTurn();
}