package Players;
import Structures.*;

import java.util.*;

public class PlayerAIRandom extends PlayerAI {
    private final long seed;
    private final Random generator;

    public PlayerAIRandom(Color c) {
        seed = System.currentTimeMillis();
        this.generator = new Random(seed);
        difficultyLevel = 0;
        col = c;
    }

    public PlayerAIRandom(Color c, long s) {
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
    public void playPiece(Board b) {
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
                notPlaced = false;
            }
        }
    }

    @Override
    public Player clone(){
        Player p2 = new PlayerAIRandom(this.col);
        p2.score = this.score;
        p2.pieces = new ArrayList<>();
        for(Piece p : this.pieces){
            p2.pieces.add(p);
        }
        return p2;
    }
}
