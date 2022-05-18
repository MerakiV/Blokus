package Controller;

import Players.Player;
import Structures.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

public class ControllerAIMinMax {

    Game config;

    public ControllerAIMinMax(Game g) {
        this.config = g;
    }

    public int AlgoMinMax(Game config, boolean max, int depth){
        if(isLeaf(config) || depth==0){
            return evaluation(config, max);
        }
        else{
            int ret = (max ? Integer.MIN_VALUE : Integer.MAX_VALUE );
            PriorityQueue<Game> moves = moves(config, max); //children of the config object
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
    public PriorityQueue<Game> moves(Game g, boolean max) {
        PriorityQueue<Game> pq = new PriorityQueue<>(11, new ComparatorAIMinMax(max));
        Game g2 = g.clone();
        Board b = g.getBoard();
        Iterator<Piece> it1 = g.getCurrentPlayer().getPieces().iterator();
        Iterator<Shape> it2;
        Iterator<Tile> it3, it4;
        Piece pi;
        Shape sh;
        Tile t1, t2;
        Color col = g.getCurrentColor();
        int k, ax, ay;

        while (it1.hasNext()) {
            pi = it1.next();
            it2 = pi.getShapeList().iterator();
            while (it2.hasNext()) {
                sh = it2.next();
                for (k=0; k<4; k++) {
                    it3 = sh.getCornerList(k).iterator();
                    while (it3.hasNext()) {
                        t1 = it3.next();
                        it4 = b.getCorners(col, k).iterator();
                        while (it4.hasNext()) {
                            t2 = it4.next();
                            ax = t2.getX() - t1.getX() + sh.getAnchorX();
                            ay = t2.getY() - t1.getY() + sh.getAnchorY();
                            // Automatically checking and then putting
                            if (g2.put(sh, pi.getName(), col, ax, ay)) {
                                pq.add(g2);
                                g2 = g.clone();
                            }
                        }
                    }
                }
            }
        }

        return pq;
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

    public static int evaluation(Game config, boolean max){
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
        List<Piece> listPlacements = new ArrayList<>();
        //see for every shape
        //int nbcorners = board.getCorner(color);
        for(int ip= 0; ip <= lp.size(); ip++) {
            Piece p = lp.get(ip);
            if (config.getBoard().canPut(p, color, x, y)) {
                listPlacements.add(p);
            }
        }
        return listPlacements;
    }

    // returns a list of every pieces that can be put for every corners
    public List<List<Piece>> allPlacements(List<Piece> lp, Color color){
        //availableCorners
        return null;
    }
}
