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

    int heur;

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

    public PlayerAIMinMax(Color c, boolean ab, int h) {
        difficultyLevel = 2;
        col = c;
        isAI = true;
        hasMoves = true;
        isAlphaBeta = ab;
        heur = h;
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
        // To explore a whole turn, the depth should be at least 3. A whole another turn is n with n%4==3
        // The maximum depth is 84 (every player has put every piece)

        int depth = 0;
        if(pieces.size() > 13){
            depth = 1;
        } else if(pieces.size() > 9){
            depth = 2;
        } else if(pieces.size() > 5){
            depth = 3;
        } else{
            depth = 7;
        }

        /*Player p1c1 = g.getPlayerList().get(0);
        Player p2c1 = g.getPlayerList().get(1);
        Player p1c2 = g.getPlayerList().get(2);
        Player p2c2 = g.getPlayerList().get(3);
        int sum = g.getBoard().sumAllPlacements(p1c1.getPieces(), p1c1.getColor()) + g.getBoard().sumAllPlacements(p2c1.getPieces(), p2c1.getColor()) + g.getBoard().sumAllPlacements(p1c2.getPieces(), p1c2.getColor()) + g.getBoard().sumAllPlacements(p2c2.getPieces(), p2c2.getColor());
        int depth = 0;
        if(sum > 600){
            depth = 1;
        } else if(sum > 350){
            depth = 2;
        } else if(sum > 200){
            depth = 3;
        } else if(sum > 75) {
            depth = 4;
        } else {
            depth = 7;
        }*/

        if(g.getPlayerList().get(0) == g.getCurrentPlayer() || g.getPlayerList().get(2) == g.getCurrentPlayer()) {
            if(this.isAlphaBeta == true) AlgoAlphaBeta(null, g, true, depth, MIN, MAX);
            else AlgoMiniMax(null, g, true, 1);
        } else {
            if (this.isAlphaBeta == true) AlgoAlphaBeta(null, g, false, depth, MIN, MAX);
            else AlgoMiniMax(null, g, false, 1);
        }
        return bestMove;
    }


    /************************************************************/
    /*                   OPENING GENERATION                     */
    /************************************************************/


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

    boolean decideDistance(int distX, int distY){
        switch(pieces.size()){
            case 21:
                if(distX < 8 && distY < 8) return true;
                break;
            case 20:
                if(distX < 6 && distY < 6) return true;
                break;
            case 19:
                if(distX < 4 && distY < 4) return true;
                break;
            case 18:
                if(distX < 2 && distY < 2) return true;
                else if(distX < 3 && distY < 3) return true;
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
                    boolean toAdd = false;
                    switch(currPlayer){
                        case 0:
                            cornerList = sh.getCornerList(0);
                            Collections.shuffle(cornerList);
                            while(i<cornerList.size() && !toAdd){
                                Tile corner = cornerList.get(i);
                                int cornerToBoardX = t.getX() - sh.getAnchorX() + corner.getX();
                                int cornerToBoardY = t.getY() - sh.getAnchorY() + corner.getY();


                                //Tile cornerToBoard = new Tile(cornerToBoardX, cornerToBoardY);
                                //mh = manhattanDist(cornerToBoard, 9, 9);
                                int distX = Math.abs(cornerToBoardX - 9);
                                int distY = Math.abs(cornerToBoardY - 9);

                                //toAdd = decideManhattan(mh);
                                toAdd = decideDistance(distX, distY);
                                if(toAdd){
                                    Move m2 = new Move(sh, pi.getName(), t);
                                    lm.add(m2);
                                }
                                i++;
                            }
                            break;
                        case 1:
                            cornerList = sh.getCornerList(1);
                            Collections.shuffle(cornerList);
                            while(i<cornerList.size() && !toAdd){ //add random
                                Tile corner = cornerList.get(i); //verify if exists
                                int cornerToBoardX = t.getX() - sh.getAnchorX() + corner.getX();
                                int cornerToBoardY = t.getY() - sh.getAnchorY() + corner.getY();

                                //Tile cornerToBoard = new Tile(cornerToBoardX, cornerToBoardY);
                                //mh = manhattanDist(cornerToBoard, 9, 10);
                                int distX = Math.abs(cornerToBoardX - 9);
                                int distY = Math.abs(cornerToBoardY - 10);

                                //toAdd = decideManhattan(mh);
                                toAdd = decideDistance(distX, distY);

                                if(toAdd){
                                    Move m2 = new Move(sh, pi.getName(), t);
                                    lm.add(m2);
                                }
                                i++;
                            }
                            break;
                        case 2:
                            cornerList = sh.getCornerList(2);
                            Collections.shuffle(cornerList);
                            while(i<cornerList.size() && !toAdd){
                                Tile corner = cornerList.get(i); //verify if exists
                                int cornerToBoardX = t.getX() - sh.getAnchorX() + corner.getX();
                                int cornerToBoardY = t.getY() - sh.getAnchorY() + corner.getY();

                                //Tile cornerToBoard = new Tile(cornerToBoardX, cornerToBoardY);
                                //mh = manhattanDist(cornerToBoard, 10, 10);
                                int distX = Math.abs(cornerToBoardX - 10);
                                int distY = Math.abs(cornerToBoardY - 10);

                                //toAdd = decideManhattan(mh);
                                toAdd = decideDistance(distX, distY);

                                if(toAdd){
                                    Move m2 = new Move(sh, pi.getName(), t);
                                    lm.add(m2);
                                }
                                i++;
                            }
                            break;
                        case 3:
                            cornerList = sh.getCornerList(3);
                            Collections.shuffle(cornerList);
                            while(i<cornerList.size() && !toAdd){
                                Tile corner = cornerList.get(i); //verify if exists
                                int cornerToBoardX = t.getX() - sh.getAnchorX() + corner.getX();
                                int cornerToBoardY = t.getY() - sh.getAnchorY() + corner.getY();

                                //Tile cornerToBoard = new Tile(cornerToBoardX, cornerToBoardY);
                                //mh = manhattanDist(cornerToBoard, 10, 9);
                                int distX = Math.abs(cornerToBoardX - 10);
                                int distY = Math.abs(cornerToBoardY - 9);

                                //toAdd = decideManhattan(mh);
                                toAdd = decideDistance(distX, distY);

                                if(toAdd){
                                    Move m2 = new Move(sh, pi.getName(), t);
                                    lm.add(m2);
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

        //search for current player according to its order
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


    /************************************************************/
    /*                   ALPHABETA AND MINMAX                   */
    /************************************************************/


    //enum algo
    public ArrayList<Move> moves(Game config) {
        ArrayList<Move> lm = new ArrayList<Move>();
        Iterator<Piece> it1 = config.getCurrentPlayer().getPieces().iterator();;
        Iterator<Shape> it2;
        Iterator<Tile> it3;
        HashSet<Tile> hs;
        Piece pi;
        Shape sh;
        Tile t;
        Color col = config.getCurrentColor();
        Board b = config.getBoard();

        while (it1.hasNext()) {
            pi = it1.next();
            it2 = pi.getShapeList().iterator();
            while (it2.hasNext()) {
                sh = it2.next();
                hs = b.fullcheck(sh, col);
                it3 = hs.iterator();
                while(it3.hasNext()) {
                    t = it3.next();
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
        if ((depth == 0 || isLeaf(config))) {
            int h = evaluation(config, move, max);
            return h;
        }
        else {
            int bestHeur = (max ? Integer.MIN_VALUE : Integer.MAX_VALUE);
            ArrayList<Move> moves = moves(config); //children
            if(depth > 0 && moves.isEmpty()){ //if the current player cannot put a piece
                Game g2 = config.clone();
                g2.nextTurn();
                int x = AlgoAlphaBeta(null, g2, !max, depth - 1, alpha, beta);
                if (max ? x > bestHeur : x < bestHeur) {
                    bestHeur = x;
                }
                if(max && bestHeur > alpha){
                    alpha = bestHeur;
                }
                if(!max && bestHeur < beta){
                    beta = bestHeur;
                }
            }
            else{
                Move bestM = null;
                while(!moves.isEmpty()) {
                    Game g2 = config.clone();
                    Move m = poll_rdm(moves);
                    g2.directPut(m.getShape(), m.getPieceType(), g2.getCurrentColor(), m.getTile().getX(), m.getTile().getY());
                    g2.nextTurn();
                    int x = AlgoAlphaBeta(m, g2, !max, depth - 1, alpha, beta);
                    if (max ? x > bestHeur : x < bestHeur) {
                        bestHeur = x;
                        bestM = m;
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
                bestMove = bestM;
            }
            return bestHeur;
        }
    }

    public int AlgoMiniMax(Move move, Game config, boolean max, int depth) {
        if (move != null && (depth == 0 || isLeaf(config))) { // move!=null to avoid a crash at the very first call
            int h = evaluation(config, move, max);
            return h;
        }
        else {
            int bestHeur = (max ? Integer.MIN_VALUE : Integer.MAX_VALUE);
            ArrayList<Move> moves = moves(config); //children
            if(depth > 0 && moves.isEmpty()){ //if the current player cannot put a piece
                Game g2 = config.clone();
                g2.nextTurn();
                int x = AlgoMiniMax(null, g2, !max, depth - 1);
                if (max ? x > bestHeur : x < bestHeur) {
                    bestHeur = x;
                }
            }
            else{
                Move bestM = null;
                while(!moves.isEmpty()) {
                    Game g2 = config.clone();
                    Move m = poll_rdm(moves);
                    g2.directPut(m.getShape(), m.getPieceType(), g2.getCurrentColor(), m.getTile().getX(), m.getTile().getY());
                    g2.nextTurn();
                    int x = AlgoMiniMax(m, g2, !max, depth - 1);
                    if (max ? x > bestHeur : x < bestHeur) {
                        bestHeur = x;
                        bestM = m;
                    }
                }
                bestMove = bestM;
            }
            return bestHeur;
        }
    }

    public boolean isLeaf(Game g){
        int i = 0;
        int zeroCorners = 0;
        int zeroPieces = 0;
        List<Player> players = g.getPlayerList();
        int nbPlayers = players.size();
        for(i = 0; i<nbPlayers; i++){
            if(players.get(i).getPieces().size() == 0)
                zeroPieces++;
            if (g.getBoard().numberOfCorners(players.get(i).getColor()) == 0)
                zeroCorners++;
        }
        return zeroPieces == nbPlayers || zeroCorners == nbPlayers;
    }

    public int evaluation(Game config, Move m, boolean max){

        int pieceValue = 0;
        if(m != null) {
            PieceType pt = m.getPieceType();
            if (pt.toString().contains("FIVE")) pieceValue = 5;
            else if (pt.toString().contains("FOUR")) pieceValue = 4;
            else if (pt.toString().contains("THREE")) pieceValue = 3;
            else if (pt.toString().contains("TWO")) pieceValue = 2;
            else if (pt.toString().contains("ONE")) pieceValue = 1;
        }

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
            if(heur == 4) return ((sumScoreP1 - pieceValue*16) - sumScoreP2) + (sumPlacementsP1 - sumPlacementsP2); //heur 4
            else if(heur == 3) return ((sumScoreP1 - pieceValue*8) - sumScoreP2) + (sumPlacementsP1 - sumPlacementsP2); //heur 3
            else if(heur == 2) return ((sumScoreP1 - pieceValue) - sumScoreP2) + (sumPlacementsP1 - sumPlacementsP2); //heur 2
            else return (sumScoreP1 - sumScoreP2) + (sumPlacementsP1 - sumPlacementsP2); // heur 1
        }
        else{
            if(heur == 4) return ((sumScoreP1 + pieceValue*16) - sumScoreP2) + (sumPlacementsP1 - sumPlacementsP2); //heur 4
            else if(heur == 3) return ((sumScoreP1 + pieceValue*8) - sumScoreP2) + (sumPlacementsP1 - sumPlacementsP2); //heur 3
            else if(heur == 2) return ((sumScoreP2 + pieceValue - sumScoreP1) + (sumPlacementsP2 - sumPlacementsP1)); //heur 2
            else return (sumScoreP2 - sumScoreP1) + (sumPlacementsP2 - sumPlacementsP1); // heur 1
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
        p2.heur = this.heur;
        p2.cloneFields(this);
        return p2;
    }
}
