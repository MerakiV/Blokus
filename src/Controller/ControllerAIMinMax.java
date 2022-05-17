package Controller;

import Players.Player;
import Structures.Game;
import Structures.GameState;

import java.util.List;
import java.util.PriorityQueue;

public class ControllerAIMinMax {

    Game config;
    boolean firstP;

    public ControllerAIMinMax(Game g) {
        this.config = g;
        firstP = true;
    }

    public int AlgoMinMax(Game config, boolean max, int depth){
        if(isLeaf(config) || depth==0){
            return evaluation(config);
        }
        else{
            PriorityQueue<Game> moves = Moves(config); //children of the config object
            for(int i=0; i<=moves.size(); i++){

            }



        }
    }

    //enum algo
    public PriorityQueue<Game> Moves(Game g){
        return null;
    }

    public boolean isLeaf(Game g){
        //list of all pieces for every color are empty || no available corner for every color
        int i = 0;
        int zeroCorners = 0;
        int zeroPieces = 0;
        Player player;
        List<Player> players = g.getPlayerList();
        int nbPlayers = players.size();
        for(i = 0; i<=nbPlayers; i++){
            if (players.get(i).getPieces().isEmpty())
                zeroPieces++;
            if (g.getBoard().numberOfCorners(players.get(i).getColor()) == 0)
                zeroCorners++;
        }
        return zeroPieces == nbPlayers || zeroCorners == nbPlayers;

    }

    public int evaluation(Game config){
        //(our score - opponent score) + (our possible placements - opposent placements)
        return 0;
    }
}
