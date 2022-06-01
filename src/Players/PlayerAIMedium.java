package Players;
import Structures.*;

import java.util.*;

public class PlayerAIMedium extends PlayerAI {
    private final long seed;
    private final Random generator;

    int mode = 1;

    public PlayerAIMedium(Color c, int m) {
        isAI = true;
        hasMoves = true;
        mode = m;
        PieceReader pRead = null;
        try {
            pRead = new PieceReader();
            pieces = pRead.getPiecesList();
        } catch(Exception e) {
            System.err.println("FATAL ERROR : missing piece file");
            System.exit(1);
        }

        seed = System.currentTimeMillis();
        this.generator = new Random(seed);
        difficultyLevel = 1;
        col = c;
    }

    @Override
    public void playPiece(Board b) {
        return;
    }

    private PlayerAIMedium(long s) {
        this.seed = s;
        this.generator = new Random(s);
    }
    @Override
    public Player clone() {
        PlayerAIMedium p2 = new PlayerAIMedium(this.seed);
        p2.mode = this.mode;
        p2.cloneFields(this);
        return p2;
    }

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


    int evaluationByPieceValue(PieceType pt){
        if (pt.toString().contains("FIVE")) return 5;
        else if (pt.toString().contains("FOUR")) return 4;
        else if (pt.toString().contains("THREE")) return 3;
        else if (pt.toString().contains("TWO")) return 2;
        else if (pt.toString().contains("ONE")) return 1;
        else return 0;

    }

    // try to create openings for itself
    int evaluationByOpenings(Game g, Move m, int currPl){
        Game g2 = g.clone();
        g2.directPut(m.getShape(), m.getPieceType(), col, m.getTile().getX(), m.getTile().getY());
        Board b = g2.getBoard();
        List<Player> lp = g2.getPlayerList();
        if(currPl == 1)
            return b.sumAllPlacements(lp.get(0).getPieces(), lp.get(0).col) + b.sumAllPlacements(lp.get(2).getPieces(), lp.get(2).col);
        else
            return b.sumAllPlacements(lp.get(1).getPieces(), lp.get(1).col) + b.sumAllPlacements(lp.get(3).getPieces(), lp.get(3).col);
    }

    // try to block the opponent
    int evaluationByBlocks(Game g, Move m, int currPl){
        Game g2 = g.clone();
        g2.directPut(m.getShape(), m.getPieceType(), col, m.getTile().getX(), m.getTile().getY());
        Board b = g2.getBoard();
        List<Player> lp = g2.getPlayerList();
        if(currPl == 1)
            return b.sumAllPlacements(lp.get(1).getPieces(), lp.get(1).col) + b.sumAllPlacements(lp.get(3).getPieces(), lp.get(3).col);
        else
            return b.sumAllPlacements(lp.get(0).getPieces(), lp.get(0).col) + b.sumAllPlacements(lp.get(2).getPieces(), lp.get(2).col);
    }

    public Move poll_rdm(ArrayList<Move> li) {
        int i = generator.nextInt(li.size());
        Move mo = li.get(i);
        Move m_end = li.remove(li.size()-1);
        if (i<li.size()) { li.set(i, m_end); }
        return mo;
    }

    @Override
    public Move generateMove(Game g){

        if(mode == 0){
            return generatePieceByValue(g);
        }

        int currPl = 0; //current player
        if(g.getCurrentPlayer() == g.getPlayerList().get(0) || g.getCurrentPlayer() == g.getPlayerList().get(2))
            currPl = 1;
        else
            currPl = 2;

        ArrayList<Move> lm = moves(g);
        Move currMove = null;
        ArrayList<Move> bestMoves = new ArrayList<Move>();
        if(mode==1) {
            int bestHeur = 0;
            int heur;
            while (!lm.isEmpty()) {
                currMove = poll_rdm(lm);
                heur = evaluationByOpenings(g, currMove, currPl);
                if (heur > bestHeur) {
                    bestHeur = heur;
                    bestMoves = new ArrayList<Move>(); // a better one has been found, we must erase everything now
                }
                if (heur == bestHeur) {
                    bestMoves.add(currMove);
                }
            }
        } else if(mode==2){
            int bestHeur = Integer.MAX_VALUE;
            int heur;
            while(!lm.isEmpty()){
                currMove = poll_rdm(lm);
                heur = evaluationByBlocks(g, currMove, currPl);
                if (heur < bestHeur){
                    bestHeur = heur;
                    bestMoves = new ArrayList<Move>(); // a better one has been found, we must erase everything now
                }
                if (heur == bestHeur){
                    bestMoves.add(currMove);
                }
            }
        } else{
            System.out.println("The specified mode for AI Medium does not exist.");
            System.exit(1);
        }
        if(bestMoves.isEmpty()) return null;
        int idx = this.generator.nextInt(bestMoves.size());
        return bestMoves.get(idx);
    }

    // return a random Move with the maximum piece value among those still in the list of pieces of the current player
    public Move generatePieceByValue(Game g){
        Move res = null;
        Board b = g.getBoard();
        int x,y;
        List<Shape> tried;
        List<Tile> possiblePut;
        int colorCode = b.getCorner(this.col);
        int nbPuttablePieces = pieces.size();

        while(true) {
            //see label of last piece
            if(nbPuttablePieces == 0) return null;
            String name = pieces.get(nbPuttablePieces - 1).getName().toString();
            String pieceValue = new String();
            if (name.contains("FIVE")) pieceValue = "FIVE";
            else if (name.contains("FOUR")) pieceValue = "FOUR";
            else if (name.contains("THREE")) pieceValue = "THREE";
            else if (name.contains("TWO")) pieceValue = "TWO";
            else if (name.contains("ONE")) pieceValue = "ONE";
            else return null; //no remaining pieces

            List<Piece> listPieceValue = new ArrayList<Piece>();
            listPieceValue.add(pieces.get(nbPuttablePieces - 1)); //list of pieces of same value

            //fill the list with every piece of same value
            int i = nbPuttablePieces - 2;
            boolean isSameValue = true;
            while(i >= 0 && isSameValue){
                if (pieces.get(i).getName().toString().contains(pieceValue)) {
                    listPieceValue.add(pieces.get(i));
                    i--;
                }
                else {
                    isSameValue = false;
                }
            }

            //try to put every piece of same value
            while (!listPieceValue.isEmpty()) {
                possiblePut = new ArrayList<>();
                // choose a random piece
                int idx = this.generator.nextInt(listPieceValue.size());
                Piece play = listPieceValue.get(idx);
                int countShapes = play.getShapeList().size();
                tried = new ArrayList<>();

                // try all shapes for the randomly selected piece
                while (countShapes != tried.size()) {

                    play.setDisp(generator.nextInt(16));
                    while (tried.contains(play.getShape())) {
                        play.setDisp(generator.nextInt(16));
                    }
                    tried.add(play.getShape());

                    //look at every possible puts for a shape
                    for (x = 0; x < 20; x++) {
                        for (y = 0; y < 20; y++) {
                            if (b.canPut(play, colorCode, x, y)) {
                                possiblePut.add(new Tile(x, y));
                            }
                        }
                    }

                    //randomly choose where to put if exists
                    if (!possiblePut.isEmpty()) {
                        int idxPut = this.generator.nextInt(possiblePut.size());
                        Tile putTile = possiblePut.get(idxPut);
                        res = new Move(play, putTile);
                        return res;
                    }
                }
                listPieceValue.remove(idx);
                nbPuttablePieces--;
            }
        }
    }
}
