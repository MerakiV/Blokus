package Structures;

import Players.Player;
import Players.Player2P;
import Players.PlayerHuman2P;

import java.util.ArrayList;

public class Game2P extends Game{
    public Player2P p1,p2;
    public Player2P currentPlayer2P;

    public Game2P(GameSettings2P set){
        //TBI : create board
        //with GameSettings
        board = new Board(set.p1c1, set.p2c1, set.p1c2, set.p2c2);

        //TBI : create players
        /* once AI is implemented
        if(set.p1Human) p1 = new PlayerHuman2P();
        else //p1 = new PlayerAI(); -> need to fix
        if(set.p2Human) p2 = new PlayerHuman2P();
        else //p2 = new PlayerAI(); -> need to fix
        */
        //temp human v human only
        p1 = new PlayerHuman2P(set.p1c1, set.p1c2);
        p2 = new PlayerHuman2P(set.p2c1, set.p2c2);

        //put players in player list
        players = new ArrayList<Player>();
        players.add(p1.pcol1);
        players.add(p2.pcol1);
        players.add(p1.pcol2);
        players.add(p2.pcol2);


        //set p1 as current player
        currentPlayer2P = p1;
        currentPlayer = p1.pcol1;
        currentColor = p1.pcol1.getColor();

        history = new History();
    }

    public void nextTurn(){
        pushToPast();
        if(currentPlayer == p1.pcol1){
            currentPlayer = p2.pcol1;
            currentColor = p2.pcol1.getColor();
            currentPlayer2P = p2;
        }
        else if(currentPlayer == p1.pcol2){
            currentPlayer = p2.pcol2;
            currentColor = p2.pcol2.getColor();
            currentPlayer2P = p2;
        }
        else if(currentPlayer == p2.pcol1){
            currentPlayer = p1.pcol2;
            currentColor = p1.pcol2.getColor();
            currentPlayer2P = p1;
        }
        else{ //currentPlayer == p2.col2
            currentPlayer = p1.pcol1;
            currentColor = p1.pcol1.getColor();
            currentPlayer2P = p1;
        }
        history.future.clear();
    }



}
