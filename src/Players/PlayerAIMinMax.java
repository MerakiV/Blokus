package Players;

import java.util.*;

import Structures.ComparatorAIMinMax;
import Structures.*;

public class PlayerAIMinMax extends PlayerAI {

    private final long seed;
    private final Random generator;

    List<Move> lMovesBestHeur;

    void initPieces() {
        // create list of pieces from PieceReader
        PieceReader pRead = null;
        try {
            pRead = new PieceReader();
            pieces = pRead.getPiecesList();
        } catch (Exception e) {
            System.err.println("FATAL ERROR : missing piece file");
            System.exit(1);
        }
    }
    public PlayerAIMinMax(Color c) {
        difficultyLevel = 2;
        col = c;
        isAI = true;
        hasMoves = true;
        seed = System.currentTimeMillis();
        this.generator = new Random(seed);
        initPieces();
    }

    @Override
    public void playPiece(Board b) {
        return;
    }

    @Override
    public Move generateMove(Game g){
        g.getCurrentPlayer().getPieces().get(0).printPiece();


        Move m = new Move(null, null, null);
        lMovesBestHeur = new ArrayList<Move>();

        if(g.getPlayerList().get(0) == g.getCurrentPlayer() || g.getPlayerList().get(2) == g.getCurrentPlayer()) {
            //g.getCurrentPlayer().getPieces().get(0).printPiece();
            m = AlgoMinMax(m, g, true, 1);
        } else {
            m = AlgoMinMax(m, g, false, 1);
        }
        //if(m.getHeuristic() == 0)
            //return null;

        //hasMoves = false;
        m.getShape().printShape();
        return m;
    }

    //enum algo
    public List<Move> moves(Game config, boolean max) {
        //PriorityQueue<Move> lm = new PriorityQueue<>(11, new ComparatorAIMinMax(config, max));
        List<Move> lm = new ArrayList<Move>();
        //Game g2;
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
                    
                    //g2 = (Game) config.clone();
                    //g2.put(sh, pi.getName(), col, t.getX(), t.getY());
                    //pq.add(g2);
                    Move m2 = new Move(sh, pi.getName(), t);
                    lm.add(m2);
                }
            }
        }

        return lm;
    }

    public Move AlgoMinMax(Move move, Game config, boolean max, int depth) {
        if (depth == 0 || (depth == 0 && isLeaf(config))) {
            int h = evaluation(config, move.getPieceType(), max);
            move.setHeuristic(h);
            return move;
        }
        //return move;
        else {
            int bestHeur = (max ? Integer.MIN_VALUE : Integer.MAX_VALUE);
            List<Move> moves = moves(config, max); //children
            Move bestMove = null;
            while(!moves.isEmpty()) {
                int idx1 = this.generator.nextInt(moves.size());
                Move m = AlgoMinMax(moves.remove(idx1), config, !max, depth - 1);
                int x = m.getHeuristic();
                if (max ? x > bestHeur : x < bestHeur) {
                    bestHeur = x;
                    bestMove = m;
                    lMovesBestHeur = new ArrayList<>(); //empties the list as a better heuristic has been found
                }
                if (x == bestHeur) {
                    lMovesBestHeur.add(m);
                    //if(this.generator.nextBoolean()) bestMove = m; //bad, last move has a probability of 0.5
                }
            }
            if(moves.isEmpty()){ //decides best move every move has been tested
                int idx2 = this.generator.nextInt(lMovesBestHeur.size());
                bestMove = lMovesBestHeur.get(idx2);
                //lMovesSameVal = new ArrayList<Move>(); //empty the list for next recursive calls
            }
            return bestMove;
        }
    }

    public boolean isLeaf(Game g){
        //list of all pieces for every color are empty || no available corner for every color
        int i = 0;
        int zeroCorners = 0;
        int zeroPieces = 0;
        List<Player> players = g.getPlayerList();
        int nbPlayers = players.size();
        for(i = 0; i<nbPlayers; i++){
            if (g.getBoard().canPlacePieces(players.get(i).getPieces(), players.get(i).col))
                zeroPieces++;
            if (g.getBoard().numberOfCorners(players.get(i).getColor()) == 0)
                zeroCorners++;
        }
        return zeroPieces == nbPlayers || zeroCorners == nbPlayers;

    }

    public static int evaluation(Game config, PieceType pt, boolean max){
        //(our score - opponent score) + (our possible placements - opponent placements)

        int pieceValue = 0;
        if (pt.toString().contains("FIVE")) pieceValue = 5;
        else if (pt.toString().contains("FOUR")) pieceValue = 4;
        else if (pt.toString().contains("THREE")) pieceValue = 3;
        else if (pt.toString().contains("TWO")) pieceValue = 2;
        else if (pt.toString().contains("ONE")) pieceValue = 1;

        Player p1c1 = config.getPlayerList().get(0);
        Player p1c2 = config.getPlayerList().get(2);
        Player p2c1 = config.getPlayerList().get(1);
        Player p2c2 = config.getPlayerList().get(3);

        int p1score1 = p1c1.getScore();
        int p1score2 = p1c2.getScore();
        int p2score1 = p2c1.getScore();
        int p2score2 = p2c2.getScore();

        int sumScoreP1 = p1score1 + p1score2;
        int sumScoreP2 = p2score1 + p2score2;
        int sumPlacementsP1 = config.getBoard().sumAllPlacements(p1c1.getPieces(), p1c1.col) + config.getBoard().sumAllPlacements(p1c2.getPieces(), p1c2.col);
        int sumPlacementsP2 = config.getBoard().sumAllPlacements(p2c1.getPieces(), p2c1.col) + config.getBoard().sumAllPlacements(p2c2.getPieces(), p2c2.col);

        if(max){
            return ((sumScoreP1 - pieceValue) - sumScoreP2) + (sumPlacementsP1 - sumPlacementsP2);
        }
        else{
            return ((sumScoreP1 + pieceValue - sumScoreP2) + (sumPlacementsP1 - sumPlacementsP2));
            //return ((sumScoreP2 - pieceValue) - sumScoreP1) + (sumPlacementsP2 - sumPlacementsP1);
        }
    }

    /*

    // returns a list of every shapes that can be put for a given corner (unused rn)
    public Hashtable<Shape, List<Tile>> allPlacementsforCorner(Game config, List<Piece> lp, Color color, int x, int y){
        Hashtable<Shape, List<Tile>> listPlacements = new Hashtable<>();
        Board b = config.getBoard();
        Iterator<Tile> it;
        List<Tile> lt;
        Tile t1, t2;
        int k, ax, ay;
        for(int ip= 0; ip < lp.size(); ip++) {
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
    public Hashtable<Shape, List<Tile>> allPlacements(Game config, List<Piece> lp, Color color){
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

    int sumAllPlacements(Game config, Player pl){
        int sum = 0;
        List<Piece> lp = pl.getPieces();
        Color color = pl.getColor();
        Board b = config.getBoard();
        Iterator<Tile> it1, it2;
        Tile t1, t2;
        int k, ax, ay;
        for(int ip= 0; ip <= lp.size(); ip++) {
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

    */

    private PlayerAIMinMax(long s) {
        this.seed = s;
        this.generator = new Random(s);
    }
    @Override
    public Player clone() {
        PlayerAIMinMax p2 = new PlayerAIMinMax(this.seed);
        p2.cloneFields(this);
        return p2;
    }
}
