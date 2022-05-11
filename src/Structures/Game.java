package Structures;

import Players.Player;
import Players.Player2P;

public abstract class Game {
    Board board;
    Player currentPlayer;
    Color currentColor;

    //to be implemented
    public boolean canBePlaced(Piece p){return true;}

    public Player getCurrentPlayer(){return currentPlayer;}

    public Color getCurrentColor(){return currentColor;}

    public Board getBoard(){return board;}

    public abstract void nextTurn();
}