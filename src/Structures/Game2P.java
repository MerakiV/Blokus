package Structures;

import Players.Player;
import Players.Player2P;
import Players.PlayerAIRandom2P;
import Players.PlayerHuman2P;

import java.util.ArrayList;

public class Game2P extends Game{
    public Player2P p1,p2;
    public Player2P currentPlayer2P;
    public GameSettings2P gs2p;

    public Game2P(GameSettings2P set){
        gs2p = set;
        //TBI : create board
        //with GameSettings
        board = new Board(set.p1c1, set.p2c1, set.p1c2, set.p2c2);

        //TBI : create players
        //AI random for now
        if(set.p1Human) p1 = new PlayerHuman2P(set.p1c1, set.p1c2);
        else p1 = new PlayerAIRandom2P(set.p1c1, set.p1c2);
        if(set.p2Human) p2 = new PlayerHuman2P(set.p2c1, set.p2c2);
        else p2 = new PlayerAIRandom2P(set.p2c1, set.p2c2);

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

        //init history
        history = new History();
    }

    public void nextTurn(){
        pushToPast();
        if(currentPlayer == p1.pcol1){
            currentPlayer = p2.pcol1;
            currentPlayer2P = p2;
        }
        else if(currentPlayer == p1.pcol2){
            currentPlayer = p2.pcol2;
            currentPlayer2P = p2;
        }
        else if(currentPlayer == p2.pcol1){
            currentPlayer = p1.pcol2;
            currentPlayer2P = p1;
        }
        else{ //currentPlayer == p2.col2
            currentPlayer = p1.pcol1;
            currentPlayer2P = p1;
        }
        currentColor = currentPlayer.getColor();
        history.future.clear();
    }



}
