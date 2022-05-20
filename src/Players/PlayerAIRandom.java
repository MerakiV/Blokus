package Players;
import Structures.*;

import java.util.*;

public class PlayerAIRandom extends PlayerAI {
    private final long seed;
    private final Random generator;

    public PlayerAIRandom(Color c) {
        isAI = true;
        score = 0;
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
        difficultyLevel = 0;
        col = c;
    }

    public PlayerAIRandom(Color c, long s) {
        isAI = true;
        score = 0;
        PieceReader pRead = null;
        try {
            pRead = new PieceReader();
            pieces = pRead.getPiecesList();
        } catch(Exception e) {
            System.err.println("FATAL ERROR : missing piece file");
            System.exit(1);
        }

        seed = s;
        this.generator = new Random(s);
        difficultyLevel = 0;
        col = c;
    }

    // This constructor will allow to do reproductible tests
    /*public PlayerAIRandom(long seed) {
        this.seed = seed;
        this.generator = new Random(seed);
    }*/

    @Override
    public void playPiece(Game g) {
        Board b = g.getBoard();
        int x,y;
        List<Shape> tried = new ArrayList<>();
        List<Tile> possiblePut;
        int colorCode = b.getCorner(this.col);
        boolean notPlaced = true;
        while(notPlaced) {
            possiblePut = new ArrayList<>();
            int idx = this.generator.nextInt(pieces.size());
            Piece play = pieces.get(idx);
            play.setDisp(generator.nextInt(16));
            while(tried.contains(play.getShape())){
                idx = this.generator.nextInt(pieces.size());
                play = pieces.get(idx);
                play.setDisp(generator.nextInt(16));
            }
            tried.add(play.getShape());
            //test for can put
            for (x=0; x<20; x++) {
                for (y=0; y<20; y++) {
                    if (b.canPut(play, colorCode, x, y)) {
                        possiblePut.add(new Tile(x,y));
                    }
                }
            }
            //randomly choose where to put if exists
            if(!possiblePut.isEmpty()) {
                int idxPut = this.generator.nextInt(possiblePut.size());
                Tile putTile = possiblePut.get(idxPut);
                b.checkAndPut(play, colorCode, putTile.getX(), putTile.getY());
                pieces.remove(play);
                notPlaced = false;
            }
        }
    }

    private PlayerAIRandom(long s) {
        seed = s;
        this.generator = new Random(s); }
    @Override
    public Object clone() {
        PlayerAIRandom p2 = new PlayerAIRandom(this.seed);
        p2.cloneFields(this);
        return p2;
    }
}
