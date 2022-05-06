package Structures;

import Players.Player;
import Players.PlayerHuman;
import Players.PlayerAI;

public class TwoPlayerGame extends Game{
    Player p1,p2;

    public TwoPlayerGame(boolean p1Human, boolean p2Human){
        //TBI : create board
        board = new Board();
        //TBI : create players
        if(p1Human) p1 = new PlayerHuman();
        else //p1 = new PlayerAI(); -> need to fix
        if(p2Human) p2 = new PlayerHuman();
        else //p2 = new PlayerAI(); -> need to fix
        //set p1 as current player
        currentPlayer = p1;
    }

    public void nextTurn(){
        if(currentPlayer == p1) currentPlayer = p2;
        else currentPlayer = p1;
    }

}
