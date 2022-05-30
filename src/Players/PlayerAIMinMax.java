package Players;

import java.util.*;

import Structures.*;

public class PlayerAIMinMax extends PlayerAI {

    static int MAX = Integer.MAX_VALUE; //for AlphaBeta
    static int MIN = Integer.MIN_VALUE; //for AlphaBeta
    private final long seed;
    private final Random generator;

    Move bestMove;

    boolean isAlphaBeta = false;

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
    
    public PlayerAIMinMax(Color c, boolean ab) {
        difficultyLevel = 2;
        col = c;
        isAI = true;
        hasMoves = true;
        isAlphaBeta = ab;
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

        // At the start of the game: aim at the center of the board //
        if(pieces.size() > 17){ // more than 4 times is useless
            //boolean StartPut = this.generator.nextBoolean(); // chooses if getting nearer to the center or not
            //if(StartPut){
                bestMove = opening(g);
                if(bestMove != null){
                    return bestMove; //to cover the case a color can't reach the center if blocked
                }
            }

        // MinMax and AlphaBeta //
        // To explore a whole turn, the depth should be at least 4. A whole another turn is every multiple of 4
        // The maximum depth is 84 (every player has put every piece)

        if(g.getPlayerList().get(0) == g.getCurrentPlayer() || g.getPlayerList().get(2) == g.getCurrentPlayer()) {
            if(this.isAlphaBeta == true) AlgoAlphaBeta(null, g, true, 1, MIN, MAX);
            else AlgoMinMax(null, g, true, 1);
        } else {
            if (this.isAlphaBeta == true) AlgoAlphaBeta(null, g, false, 1, MIN, MAX);
            else AlgoMinMax(null, g, false, 1);
        }
        return bestMove;
    }

    //Manhattan distance from a tile to the center of the board
    int manhattanDist(Tile t, int centerX, int centerY){
        return Math.abs(t.getX() - centerX) + Math.abs(t.getY() - centerY);
    }

    //Decide if for a corner tile of a shape, its Manhattan distance is enough given the number of remaining pieces
    //for the player.
    boolean decideManhattan(int mh){
        switch(pieces.size()){
            case 21:
                if(mh < 15) return true;
                break;
            case 20:
                if(mh < 11) return true;
                break;
            case 19:
                if(mh < 8) return true;
                break;
            case 18:
                if(mh < 4) return true;
                break;
        }
        return false;
    }

    public List<Move> movesOpening(List<Piece> lp, int currPlayer, Board b) {
        List<Move> lm = new ArrayList<Move>();
        Iterator<Piece> it1 = lp.iterator();
        Iterator<Shape> it2;
        Iterator<Tile> it3;
        HashSet<Tile> hs;
        Piece pi;
        Shape sh;
        Tile t;

        while (it1.hasNext()) {
            pi = it1.next();
            it2 = pi.getShapeList().iterator();
            while (it2.hasNext()) {
                sh = it2.next();

                hs = b.fullcheck(sh, col);

                it3 = hs.iterator();
                while(it3.hasNext()) {
                    t = it3.next();
                    int mh;
                    List<Tile> cornerList = null;
                    int i=0;
                    boolean added = false;
                    switch(currPlayer){
                        case 0:
                            cornerList = sh.getCornerList(0);
                            Collections.shuffle(cornerList);
                            while(i<cornerList.size() && !added){
                                Tile corner = cornerList.get(i);
                                int cornerToBoardX = t.getX() - sh.getAnchorX() + corner.getX();
                                int cornerToBoardY = t.getY() - sh.getAnchorY() + corner.getY();
                                Tile cornerToBoard = new Tile(cornerToBoardX, cornerToBoardY);
                                mh = manhattanDist(cornerToBoard, 9, 9);
                                boolean toAdd = decideManhattan(mh);
                                if(toAdd){
                                    Move m2 = new Move(sh, pi.getName(), t);
                                    lm.add(m2);
                                    added = true;
                                }
                                i++;
                            }
                            break;
                        case 1:
                            cornerList = sh.getCornerList(1);
                            Collections.shuffle(cornerList);
                            while(i<cornerList.size() && !added){ //add random
                                Tile corner = cornerList.get(i); //verify if exists
                                int cornerToBoardX = t.getX() - sh.getAnchorX() + corner.getX();
                                int cornerToBoardY = t.getY() - sh.getAnchorY() + corner.getY();
                                Tile cornerToBoard = new Tile(cornerToBoardX, cornerToBoardY);
                                mh = manhattanDist(cornerToBoard, 9, 10);
                                boolean toAdd = decideManhattan(mh);
                                if(toAdd){
                                    Move m2 = new Move(sh, pi.getName(), t);
                                    lm.add(m2);
                                    added = true;
                                }
                                i++;
                            }
                            break;
                        case 2:
                            cornerList = sh.getCornerList(2);
                            Collections.shuffle(cornerList);
                            while(i<cornerList.size() && !added){
                                Tile corner = cornerList.get(i); //verify if exists
                                int cornerToBoardX = t.getX() - sh.getAnchorX() + corner.getX();
                                int cornerToBoardY = t.getY() - sh.getAnchorY() + corner.getY();
                                Tile cornerToBoard = new Tile(cornerToBoardX, cornerToBoardY);
                                mh = manhattanDist(cornerToBoard, 10, 10);
                                boolean toAdd = decideManhattan(mh);
                                if(toAdd){
                                    Move m2 = new Move(sh, pi.getName(), t);
                                    lm.add(m2);
                                    added = true;
                                }
                                i++;
                            }
                            break;
                        case 3:
                            cornerList = sh.getCornerList(3);
                            Collections.shuffle(cornerList);
                            while(i<cornerList.size() && !added){
                                Tile corner = cornerList.get(i); //verify if exists
                                int cornerToBoardX = t.getX() - sh.getAnchorX() + corner.getX();
                                int cornerToBoardY = t.getY() - sh.getAnchorY() + corner.getY();
                                Tile cornerToBoard = new Tile(cornerToBoardX, cornerToBoardY);
                                mh = manhattanDist(cornerToBoard, 10, 9);
                                boolean toAdd = decideManhattan(mh);
                                if(toAdd){
                                    Move m2 = new Move(sh, pi.getName(), t);
                                    lm.add(m2);
                                    added = true;
                                }
                                i++;
                            }
                            break;
                    }
                }
            }
        }

        return lm;
    }

    Move opening(Game g) {
        Board b = g.getBoard();

        //take all pieces of value 5
        int valFive = 0;
        List<Piece> lp = new ArrayList<Piece>();
        for (int i = pieces.size() - 1; i >= 0; i--) {
            if (pieces.get(i).getName().toString().contains("FIVE")) {
                valFive++;
                lp.add(pieces.get(i));
            } else {
                break;
            }
        }

        //search for current player
        int i = 0;
        boolean foundPlayer = false;
        int currPlayer = -1;
        while (i < 4 && !foundPlayer) {
            if (g.getPlayerList().get(i) == g.getCurrentPlayer()) {
                currPlayer = i;
                foundPlayer = true;
            }
            i++;
        }

        //decide move randomly
        List<Move> lm = movesOpening(lp, currPlayer, g.getBoard());
        if(lm.isEmpty()) return null;
        int idx = 0;
        long seed2 = seed;
        if(currPlayer != 0){
            if(currPlayer == 1) seed2+= 500;
            if(currPlayer == 2) seed2+= 1000;
            if(currPlayer == 3) seed2+= 1500;
            Random generator2 = new Random(seed2);
            idx = generator2.nextInt(lm.size());
        }
        else {
            idx = this.generator.nextInt(lm.size()); //decide a random move
        }
        Move m = lm.get(idx);

        return m;
    }

    //enum algo
    public ArrayList<Move> moves(Game config) {
        //PriorityQueue<Move> lm = new PriorityQueue<>(11, new ComparatorAIMinMax(config, max));
        ArrayList<Move> lm = new ArrayList<Move>();
        //Game g2;
        //int idx = this.generator.nextInt(pieces.size());
        //idx = ;
        Iterator<Piece> it1 = config.getCurrentPlayer().getPieces().iterator();;
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
                    //config.put(sh, pi.getName(), col, t.getX(), t.getY());
                    //pq.add(g2);
                    Move m2 = new Move(sh, pi.getName(), t);
                    lm.add(m2);
                }
            }
        }

        return lm;
    }

    // instead of removing and then shift every element O(n), just pick a certain piece O(1)
    public Move poll_rdm(ArrayList<Move> li) {
        int i = generator.nextInt(li.size());
        Move mo = li.get(i);
        Move m_end = li.remove(li.size()-1);
        if (i<li.size()) { li.set(i, m_end); }
        return mo;
    }

    public int AlgoAlphaBeta(Move move, Game config, boolean max, int depth, int alpha, int beta) {
        //Game g2 = config.clone();
        //if(move !=null){
        //config.getCurrentPlayer(); //change turn
        //}
        if (move != null && (depth == 0 || isLeaf(config))) { // move!=null to avoid a crash at the very first call
            int h = evaluation(config, move, max);
            //move.setHeuristic(h);
            return h;
        }
        else {
            int bestHeur = (max ? Integer.MIN_VALUE : Integer.MAX_VALUE);
            ArrayList<Move> moves = moves(config); //children
            //ArrayList<Move> lMovesBestHeur = null;
            Move bestM = null;
            while(!moves.isEmpty()) {
                //TODO: PLAY
                Game g2 = config.clone();
                Move m = poll_rdm(moves);
                g2.put(m.getShape(), m.getPieceType(), g2.getCurrentColor(), m.getTile().getX(), m.getTile().getY());
                g2.nextTurn();
                // ALGO //
                int x = AlgoAlphaBeta(m, g2, !max, depth - 1, alpha, beta); // not return m
                // TODO: UNPLAY
                //config = g2.clone();
                //add index piece unmoved
                //config.unput();
                //config.undo();
                //int x = m.getHeuristic();
                if (max ? x > bestHeur : x < bestHeur) {
                    bestHeur = x;
                    bestM = m; //adapt to current Player?
                    //lMovesBestHeur = new ArrayList<>(); //empty the list as a better heuristic has been found
                }
                /*if (x == bestHeur) {
                    lMovesBestHeur.add(m);
                }*/

                if(max && bestHeur > alpha){
                    alpha = bestHeur;
                }
                if(!max && bestHeur < beta){
                    beta = bestHeur;
                }
                if(beta <= alpha){
                    break;
                }
            }
            /*if(moves.isEmpty() || beta <= alpha){ //decides best move every move has been tested //modify for alphabeta?
                int idx2 = this.generator.nextInt(lMovesBestHeur.size());
                //bestM = lMovesBestHeur.get(idx2);
            }*/
            bestMove = bestM;
            return bestHeur;
        }
    }

    public int AlgoMinMax(Move move, Game config, boolean max, int depth) {
        if (move != null && (depth == 0 || isLeaf(config))) { // move!=null to avoid a crash at the very first call
            int h = evaluation(config, move, max);
            //move.setHeuristic(h); //useless
            return h;
        }
        else {
            int bestHeur = (max ? Integer.MIN_VALUE : Integer.MAX_VALUE);
            ArrayList<Move> moves = moves(config); //children
            Move bestM = null;
            while(!moves.isEmpty()) {
                // No need to unplay here as it's a clone
                Game g2 = config.clone();
                Move m = poll_rdm(moves);
                g2.put(m.getShape(), m.getPieceType(), g2.getCurrentColor(), m.getTile().getX(), m.getTile().getY());
                g2.nextTurn();
                int x = AlgoMinMax(m, g2, !max, depth - 1);
                if (max ? x > bestHeur : x < bestHeur) {
                    bestHeur = x;
                    bestM = m;
                }
            }
            bestMove = bestM;
            return bestHeur;
        }
    }

    /*public Move AlgoAlphaBeta(Move move, Game config, boolean max, int depth, int alpha, int beta) {
        //Game g2 = config.clone();
        //if(move !=null){
            //config.getCurrentPlayer(); //change turn
        //}
        if (move != null && (depth == 0 || isLeaf(config))) { // move!=null to avoid a crash at the very first call
            int h = evaluation(config, move, max);
            move.setHeuristic(h);
            return move;
        }
        else {
            int bestHeur = (max ? Integer.MIN_VALUE : Integer.MAX_VALUE);
            ArrayList<Move> moves = moves(config); //children
            Move bestMove = null;
            ArrayList<Move> lMovesBestHeur = null;
            while(!moves.isEmpty()) {
                //TODO: PLAY
                //Game g2 = config.clone();
                Move m = poll_rdm(moves);
                config.put(m.getShape(), m.getPieceType(), config.getCurrentColor(), m.getTile().getX(), m.getTile().getY());
                config.nextTurn();
                // ALGO //
                m = AlgoAlphaBeta(m, config, !max, depth - 1, alpha, beta); // not return m
                // TODO: UNPLAY
                //config = g2.clone();
                config.undo();
                int x = m.getHeuristic();
                if (max ? x > bestHeur : x < bestHeur) {
                    bestHeur = x;
                    bestMove = m;
                    lMovesBestHeur = new ArrayList<>(); //empty the list as a better heuristic has been found
                }
                if (x == bestHeur) {
                    lMovesBestHeur.add(m);
                }

                if(max && bestHeur > alpha){
                    alpha = bestHeur;
                }
                if(!max && bestHeur < beta){
                    beta = bestHeur;
                }
                if(beta <= alpha){
                    break;
                }
            }
            if(moves.isEmpty() || beta <= alpha){ //decides best move every move has been tested //modify for alphabeta?
                int idx2 = this.generator.nextInt(lMovesBestHeur.size());
                bestMove = lMovesBestHeur.get(idx2);
            }
            return bestMove;
        }
    }*/

    /*public Move AlgoMinMax(Move move, Game config, boolean max, int depth) {
        Game g2 = config.clone();
        if (depth == 0 || move != null && isLeaf(g2)) { // move!=null to avoid a crash at the very first call
            int h = evaluation(g2, move, max);
            move.setHeuristic(h);
            return move;
        }
        else {
            int bestHeur = (max ? Integer.MIN_VALUE : Integer.MAX_VALUE);
            ArrayList<Move> moves = moves(g2); //children
            Move bestMove = null;
            ArrayList<Move> lMovesBestHeur = null;
            while(!moves.isEmpty()) {
                //int idx1 = this.generator.nextInt(moves.size());
                Move m = AlgoMinMax(poll_rdm(moves), g2, !max, depth - 1);
                int x = m.getHeuristic();
                if (max ? x > bestHeur : x < bestHeur) {
                    bestHeur = x;
                    bestMove = m;
                    lMovesBestHeur = new ArrayList<>(); //empty the list as a better heuristic has been found
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
    }*/

    public boolean isLeaf(Game g){
        //list of all pieces for every color are empty || no available corner for every color
        int i = 0;
        int zeroCorners = 0;
        int zeroPieces = 0;
        List<Player> players = g.getPlayerList();
        int nbPlayers = players.size();
        for(i = 0; i<nbPlayers; i++){
            //if (!g.getBoard().canPlacePieces(players.get(i).getPieces(), players.get(i).col));
                //zeroCorners++;
            if(players.get(i).getPieces().size() == 0)
                zeroPieces++;
            if (g.getBoard().numberOfCorners(players.get(i).getColor()) == 0)
                zeroCorners++;
        }
        return zeroPieces == nbPlayers || zeroCorners == nbPlayers;
        //return zeroCorners == nbPlayers;

        //Player p = g.getCurrentPlayer();
        //return g.getBoard().canPlacePieces(p.getPieces(), p.col) == false;
    }

    public static int evaluation(Game config, Move m, boolean max){
        //(our score - opponent score) + (our possible placements - opponent placements)
        //Game g2 = config.clone();
        //g2.put(m.getShape(), m.getPieceType(), config.getCurrentColor(), m.getTile().getX(), m.getTile().getY());

        PieceType pt = m.getPieceType();
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

        //heur 2 & 3 & 4: apply coefficients to current player
        if(p1c1 == config.getCurrentPlayer()){
            p1score1 *= 2;
        } else if(p1c2 == config.getCurrentPlayer()) {
            p1score2 *= 2;
        } else if(p2c1 == config.getCurrentPlayer()) {
            p2score1 *= 2;
        } else{ //p2c2 == config.getCurrentPlayer()
            p2score2 *= 2;
        }

        int sumScoreP1 = p1score1 + p1score2;
        int sumScoreP2 = p2score1 + p2score2;
        int sumPlacementsP1 = config.getBoard().sumAllPlacements(p1c1.getPieces(), p1c1.col) + config.getBoard().sumAllPlacements(p1c2.getPieces(), p1c2.col);
        int sumPlacementsP2 = config.getBoard().sumAllPlacements(p2c1.getPieces(), p2c1.col) + config.getBoard().sumAllPlacements(p2c2.getPieces(), p2c2.col);

        if(max){
            return ((sumScoreP1 - pieceValue*16) - sumScoreP2) + (sumPlacementsP1 - sumPlacementsP2); //heur 4
            //return ((sumScoreP1 - pieceValue*8) - sumScoreP2) + (sumPlacementsP1 - sumPlacementsP2); //heur 3
            //return ((sumScoreP1 - pieceValue) - sumScoreP2) + (sumPlacementsP1 - sumPlacementsP2); //heur 2
            //return ((sumScoreP1 - sumScoreP2) + (sumPlacementsP1 - sumPlacementsP2); // heur 1
        }
        else{
            return ((sumScoreP1 + pieceValue*16) - sumScoreP2) + (sumPlacementsP1 - sumPlacementsP2); //heur 4
            //return ((sumScoreP1 + pieceValue*8) - sumScoreP2) + (sumPlacementsP1 - sumPlacementsP2); //heur 3
            //return ((sumScoreP2 + pieceValue - sumScoreP1) + (sumPlacementsP2 - sumPlacementsP1)); //heur 2
            //return ((sumScoreP2 - sumScoreP1) + (sumPlacementsP2 - sumPlacementsP1); // heur 1
        }
    }

    private PlayerAIMinMax(long s) {
        this.seed = s;
        this.generator = new Random(s);
    }
    @Override
    public Player clone() {
        PlayerAIMinMax p2 = new PlayerAIMinMax(this.seed);
        p2.isAlphaBeta = this.isAlphaBeta;
        p2.cloneFields(this);
        return p2;
    }
}
