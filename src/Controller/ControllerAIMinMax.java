package Controller;

import Players.Player;
import Structures.*;

import java.util.*;

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
            PriorityQueue<Game> moves = moves(max); //children of the config object
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
    public PriorityQueue<Game> moves(boolean max) {
        PriorityQueue<Game> pq = new PriorityQueue<>(11, new ComparatorAIMinMax(this, max));
        Game g2;
        Iterator<Piece> it1 = config.getCurrentPlayer().getPieces().iterator();
        Iterator<Shape> it2;
        Iterator<Tile> it3;
        HashSet<Tile> hs;
        Piece pi;
        Shape sh;
        Tile t;
        Color col = config.getCurrentColor();

        while (it1.hasNext()) {
            pi = it1.next();
            it2 = pi.getShapeList().iterator();
            while (it2.hasNext()) {
                sh = it2.next();

                hs = config.getBoard().fullcheck(sh, col);

                it3 = hs.iterator();
                while(it3.hasNext()) {
                    t = it3.next();
                    
                    g2 = (Game) config.clone();
                    g2.put(sh, pi.getName(), col, t.getX(), t.getY());
                    pq.add(g2);
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

    public int evaluation(Game config, boolean max){
        //(our score - opponent score) + (our possible placements - opponent placements)
        // System.out.println("Calcul heuristique de :"); // debug
        // config.getBoard().printBoard(-1); // debug

        Player p1c1 = config.getPlayerList().get(0);
        Player p1c2 = config.getPlayerList().get(2);
        Player p2c1 = config.getPlayerList().get(1);
        Player p2c2 = config.getPlayerList().get(3);

        int p1score1 = p1c1.getScore();
        int p1score2 = p1c2.getScore();
        int p2score1 = p2c1.getScore();
        int p2score2 = p2c2.getScore();
        // System.out.println("Scores : J1 : "+p1score1+" "+p1score2+" | J2 : "+p2score1+" "+p2score2); // debug

        int sumScoreP1 = p1score1 + p1score2;
        int sumScoreP2 = p2score1 + p2score2;
        int sumPlacementsP1 = sumAllPlacements(p1c1) + sumAllPlacements(p1c2);
        int sumPlacementsP2 = sumAllPlacements(p2c2) + sumAllPlacements(p2c2);
        // System.out.println("Somme placements : J1 "+sumPlacementsP1+" | J2 "+sumPlacementsP2+"\n\n\n"); // debug

        if(max){
            return (sumScoreP1 - sumScoreP2) + (sumPlacementsP1 - sumPlacementsP2);
        }
        else{
            return (sumScoreP2 - sumScoreP1) + (sumPlacementsP2 - sumPlacementsP1);
        }
    }

    // returns a list of every shapes that can be put for a given corner (unused rn)
    public Hashtable<Shape, List<Tile>> allPlacementsforCorner(List<Piece> lp, Color color, int x, int y){
        Hashtable<Shape, List<Tile>> listPlacements = new Hashtable<>();
        Board b = config.getBoard();
        Iterator<Tile> it;
        List<Tile> lt;
        Tile t1, t2;
        int k, ax, ay;
        for(int ip= 0; ip <= lp.size(); ip++) {
            Piece p = lp.get(ip);
            List<Shape> ls = p.getShapeList();
            for(Shape s: ls) {
                lt = new ArrayList<>();
                for (k=0; k<4; k++) {
                    it = s.getCornerList(k).iterator();
                    while(it.hasNext()) {
                        t1 = it.next();
                        ax = x - t1.getX() + s.getAnchorX();
                        ay = y - t1.getY() + s.getAnchorY();
                        if (b.canPut(s, color, ax, ay)) {
                            t2 = new Tile(ax, ay);
                            lt.add(t2);
                        }
                    }
                }
                if(!lt.isEmpty()){
                    listPlacements.put(s, lt);
                }
            }
        }
        return listPlacements;
    }

    // returns a list of every pieces that can be put for every corners (unused rn)
    public Hashtable<Shape, List<Tile>> allPlacements(List<Piece> lp, Color color){
        Hashtable<Shape, List<Tile>> listPlacements = new Hashtable<>();
        Board b = config.getBoard();
        Iterator<Tile> it1, it2;
        List<Tile> lt;
        Tile t1, t2, t3;
        int k, ax, ay;
        for(int ip= 0; ip < lp.size(); ip++) {
            Piece p = lp.get(ip);
            List<Shape> ls = p.getShapeList();
            for(Shape s: ls) {
                lt = new ArrayList<>();
                for (k=0; k<4; k++) {
                    it1 = s.getCornerList(k).iterator();
                    while(it1.hasNext()) {
                        t1 = it1.next();
                        it2 = b.getCorners(color, k).iterator();
                        while (it2.hasNext()) {
                            t2 = it2.next();
                            ax = t2.getX() - t1.getX() + s.getAnchorX();
                            ay = t2.getY() - t1.getY() + s.getAnchorY();
                            if (b.canPut(s, color, ax, ay)) {
                                t3 = new Tile(ax, ay);
                                lt.add(t3);
                            }
                        }
                    }
                }
                if(!lt.isEmpty()){
                    listPlacements.put(s, lt);
                }
            }
        }
        return listPlacements;
    }

    int sumAllPlacements(Player pl){
        int sum = 0;
        List<Piece> lp = pl.getPieces();
        Color color = pl.getColor();
        Board b = config.getBoard();
        Iterator<Tile> it1, it2;
        Tile t1, t2;
        int k, ax, ay;
        for(int ip= 0; ip < lp.size(); ip++) {
            Piece p = lp.get(ip);
            List<Shape> ls = p.getShapeList();
            for(Shape s: ls) {
                for (k=0; k<4; k++) {
                    it1 = s.getCornerList(k).iterator();
                    while(it1.hasNext()) {
                        t1 = it1.next();
                        it2 = b.getCorners(color, k).iterator();
                        while (it2.hasNext()) {
                            t2 = it2.next();
                            ax = t2.getX() - t1.getX() + s.getAnchorX();
                            ay = t2.getY() - t1.getY() + s.getAnchorY();
                            if (b.canPut(s, color, ax, ay)) {
                                sum++;
                            }
                        }
                    }
                }
            }
        }
        return sum;
    }

}
