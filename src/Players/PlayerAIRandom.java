package Players;
import Structures.Board;
import Structures.Color;
import Structures.Piece;
import Structures.Tile;

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


    // This constructor will allow to do reproductible tests
    /*public PlayerAIRandom(long seed) {
        this.seed = seed;
        this.generator = new Random(seed);
    }*/

    @Override
    public void playPiece(Board b) {
        int x,y;
        Set<Piece> tried = new HashSet<>();
        List<Tile> possiblePut;
        int colorCode = b.getCorner(this.col);
        boolean notPlaced = true;
        while(notPlaced) {
            possiblePut = new ArrayList<>();
            int idx = this.generator.nextInt(pieces.size());
            Piece play = pieces.get(idx);
            while(tried.contains(play)){
                idx = this.generator.nextInt(pieces.size());
                play = pieces.get(idx);
            }
            tried.add(play);
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
}
