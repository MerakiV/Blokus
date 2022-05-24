package Players;
import Structures.*;

import java.util.*;

public class PlayerAIMedium extends PlayerAI {
    private final long seed;
    private final Random generator;

    int type;

    public PlayerAIMedium(Color c, int t) {
        isAI = true;
        score = 0;
        type = t;
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

    public PlayerAIMedium(Color c, long s) {
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
        difficultyLevel = 1;
        col = c;
    }

    @Override
    public void playPiece(Board b) {
        /*
        switch(type){
            case 1:
                PlayPieceByValue(g);
                break;
            case 2:
                PlayPieceValueAndAvCorners(g);
                break;
        }
         */
        return;
    }

    @Override
    public Player clone() {
        return null;
    }

    // Place pieces of the best value in a random order first
    public void PlayPieceByValue(Game g){
        Board b = g.getBoard();
        int x,y;
        List<Shape> tried = new ArrayList<>();
        List<Tile> possiblePut;
        int colorCode = b.getCorner(this.col);
        boolean notPlaced = true;

        //see label of last piece
        String name = pieces.get(pieces.size()-1).getName().toString();
        String pieceValue;
        if(name.contains("FIVE")) pieceValue = "FIVE";
        else if(name.contains("FOUR")) pieceValue = "FOUR";
        else if(name.contains("THREE")) pieceValue = "THREE";
        else if(name.contains("TWO")) pieceValue = "TWO";
        else if(name.contains("ONE")) pieceValue = "ONE";
        else return; //no remaining pieces

        int pieceCount = 1; //count number of pieces of same value
        for(int i = pieces.size()-2; i>=0; i--){
            if(pieces.get(i).getName().toString().contains(pieceValue)) {
                System.out.println(pieces.get(i).getName().toString());
                pieceCount++;
                System.out.println(pieceCount);
            }
        }

        while(notPlaced) {

            possiblePut = new ArrayList<>();
            int idx = this.generator.nextInt(pieceCount);
            Piece play = pieces.get(pieces.size()-1-idx);
            play.setDisp(generator.nextInt(16));
            while(tried.contains(play.getShape())){
                idx = this.generator.nextInt(pieceCount);
                play = pieces.get(pieces.size()-1-idx);
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

    /*public void PlayPieceValueAndAvCorners(g){
    }*/

    @Override
    public Move generateMove(Game g){
        Move res = null;
        Board b = g.getBoard();
        int x,y;
        List<Shape> tried = new ArrayList<>();
        List<Tile> possiblePut;
        int colorCode = b.getCorner(this.col);
        boolean notPlaced = true;

        //see label of last piece
        String name = pieces.get(pieces.size()-1).getName().toString();
        String pieceValue;
        if(name.contains("FIVE")) pieceValue = "FIVE";
        else if(name.contains("FOUR")) pieceValue = "FOUR";
        else if(name.contains("THREE")) pieceValue = "THREE";
        else if(name.contains("TWO")) pieceValue = "TWO";
        else if(name.contains("ONE")) pieceValue = "ONE";
        else return null; //no remaining pieces

        int pieceCount = 1; //count number of pieces of same value
        for(int i = pieces.size()-2; i>=0; i--){
            if(pieces.get(i).getName().toString().contains(pieceValue)) {
                System.out.println(pieces.get(i).getName().toString());
                pieceCount++;
                System.out.println(pieceCount);
            }
        }


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
                res = new Move(play, putTile);
                notPlaced = false;
            }
        }
        return res;
    }
}
