package Controller;

import Players.Player;
import Structures.*;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class ControllerAIMinMax {

    Game config;
    Board board;
    boolean firstP;

    public ControllerAIMinMax(Game g) {
        this.config = g;
        board = g.getBoard();
        firstP = true;
    }

    public int AlgoMinMax(Game config, boolean max, int depth){
        if(isLeaf(config) || depth==0){
            return evaluation(config, max);
        }
        else{
            int ret = (max ? Integer.MIN_VALUE : Integer.MAX_VALUE );
            PriorityQueue<Game> moves = Moves(config); //children of the config object
            for(int i=0; i<=moves.size(); i++){
                int x = AlgoMinMax(moves.poll(), !max, depth-1);
                if(max ? x>ret : x<ret){
                    ret = x;
                }
            }
            return ret;
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

    public int evaluation(Game config, boolean max){
        //(our score - opponent score) + (our possible placements - opponent placements)

        int p1score1 = config.getPlayerList().get(0).getScore();
        int p1score2 = config.getPlayerList().get(3).getScore();
        int p2score1 = config.getPlayerList().get(1).getScore();
        int p2score2 = config.getPlayerList().get(2).getScore();

        if(max){
            return (p1score1 + p1score2 - p2score1 - p2score2); //+ possible placements
        }
        else{
            return (p2score1 + p2score2 - p1score1 - p1score1);   //+ possible placements
        }
    }

    // returns a list of every pieces that can be put for a given corner
    public List<Piece> allPlacementsforCorner(List<Piece> lp, Color color, int x, int y){
        List<Piece> listPlacements = new List<Piece>;
        //see for every shape
        //int nbcorners = board.getCorner(color);
        for(int ip= 0; ip <= lp.size(); ip++) {
            Piece p = lp.get(ip);
            if (board.canPut(p, color, x, y)) {
                listPlacements.add(p);
            }
        }
        return listPlacements;
    }

    // returns a list of every pieces that can be put for every corners
    public List<List<Piece>> allPlacements(List<Piece> lp, Color color){
        //availableCorners
    }
}
