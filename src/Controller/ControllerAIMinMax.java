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
        Game g2 = (Game) config.clone();
        Board b = config.getBoard();
        Iterator<Piece> it1 = config.getCurrentPlayer().getPieces().iterator();
        Iterator<Shape> it2;
        Iterator<Tile> it3, it4;
        Piece pi;
        Shape sh;
        Tile t1, t2;
        Color col = config.getCurrentColor();
        int k, ax, ay;

        while (it1.hasNext()) {
            pi = it1.next();
            System.out.println("--- Iterating on piece "+pi.getName()); // debug
            it2 = pi.getShapeList().iterator();
            while (it2.hasNext()) {
                sh = it2.next();
                System.out.println("------ Iterating on following shape :"); // debug
                sh.printShape(); // debug
                for (k=0; k<4; k++) {
                    System.out.print("--------- Iterating on direction "); // debug
                    switch(k) { // debug
                        case 0: //0 is northeast, 1 is northwest, 2 is southwest, 3 is southeast // debug
                            System.out.println("northeast"); // debug
                            break; // debug
                        case 1:  // debug
                            System.out.println("northwest"); // debug
                            break; // debug
                        case 2:  // debug
                            System.out.println("southwest"); // debug
                            break; // debug
                        case 3:  // debug
                            System.out.println("southeast"); // debug
                            break; // debug
                        default: // debug
                            System.out.println("how did i get here ?"); // debug
                    } // debug
                    it3 = sh.getCornerList(k).iterator();
                    while (it3.hasNext()) {
                        t1 = it3.next();
                        System.out.println("------------ Iterating on shape corner ("+t1.getX()+","+t1.getY()+")"); // debug
                        it4 = b.getCorners(col, k).iterator();
                        while (it4.hasNext()) {
                            t2 = it4.next();
                            System.out.println("--------------- Iterating on board corner ("+t2.getX()+","+t2.getY()+")"); // debug
                            ax = t2.getX() - t1.getX() + sh.getAnchorX();
                            ay = t2.getY() - t1.getY() + sh.getAnchorY();
                            // Automatically checking and then putting
                            if (g2.put(sh, pi.getName(), col, ax, ay)) {
                                System.out.println("------------------ It matches !!!"); // debug
                                pq.add(g2);
                                g2 = (Game) config.clone();
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

    public int evaluation(Game config, boolean max){
        //(our score - opponent score) + (our possible placements - opponent placements)

        Player p1c1 = config.getPlayerList().get(0);
        Player p1c2 = config.getPlayerList().get(2);
        Player p2c1 = config.getPlayerList().get(1);
        Player p2c2 = config.getPlayerList().get(3);

        int p1score1 = p1c1.getScore();
        int p1score2 = p1c2.getScore();
        int p2score1 = p2c1.getScore();
        int p2score2 = p2c2.getScore();

        int sumScoreP1 = p1score1 + p1score2;
        int sumScoreP2 = p2score1 - p2score2;
        int sumPlacementsP1 = sumAllPlacements(p1c1) + sumAllPlacements(p1c2);
        int sumPlacementsP2 = sumAllPlacements(p2c2) + sumAllPlacements(p2c2);

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
