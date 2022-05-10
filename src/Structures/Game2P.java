package Structures;

import Players.Player;
import Players.PlayerHuman2P;

import java.util.Set;

public class Game2P extends Game{
    Player p1,p2;

    public Game2P(GameSettings2P set){
        //create list of pieces from PieceReader
        PieceReader pRead = null;
        try {
            pRead = new PieceReader();
        } catch(Exception e) {
            System.err.println("FATAL ERROR : missing piece file");
            System.exit(1);
        }

        Set<Piece> masterList = pRead.getPiecesList();

        //TBI : create board
        board = new Board();

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

        //set p1 as current player
        currentPlayer = p1;
    }

    public void nextTurn(){
        if(currentPlayer == p1) currentPlayer = p2;
        else currentPlayer = p1;
    }

}
