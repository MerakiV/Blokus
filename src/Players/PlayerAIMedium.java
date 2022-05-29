package Players;
import Structures.*;

import java.util.*;

public class PlayerAIMedium extends PlayerAI {
    private final long seed;
    private final Random generator;

    //int type;

    public PlayerAIMedium(Color c) {
        isAI = true;
        hasMoves = true;
        //type = t;
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

    /*public PlayerAIMedium(Color c, long s) {
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
    }*/

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
        p2.cloneFields(this);
        return p2;
    }

    // Place pieces of the best value in a random order first
    /*public void PlayPieceByValue(Game g){
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
    }*/

    /*public void PlayPieceValueAndAvCorners(g){
    }*/

    @Override
    public Move generateMove(Game g){
        Move res = null;
        Board b = g.getBoard();
        int x,y;
        List<Shape> tried;
        List<Tile> possiblePut;
        int colorCode = b.getCorner(this.col);
        int nbPuttablePieces = pieces.size();

        //System.out.println(g.getCurrentColor());

        while(true) {
            //see label of last piece
            String name = pieces.get(nbPuttablePieces - 1).getName().toString();
            String pieceValue;
            if (name.contains("FIVE")) pieceValue = "FIVE";
            else if (name.contains("FOUR")) pieceValue = "FOUR";
            else if (name.contains("THREE")) pieceValue = "THREE";
            else if (name.contains("TWO")) pieceValue = "TWO";
            else if (name.contains("ONE")) pieceValue = "ONE";
            else return null; //no remaining pieces

            List<Piece> listPieceValue = new ArrayList<Piece>();
            listPieceValue.add(pieces.get(nbPuttablePieces - 1)); //list of pieces of same value

            //fills the list with every pieces of same value
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

            //tries to put every piece of same value
            while (!listPieceValue.isEmpty()) {
                possiblePut = new ArrayList<>();
                // choose a random piece
                int idx = this.generator.nextInt(listPieceValue.size());
                Piece play = listPieceValue.get(idx);
                int countShapes = play.getShapeList().size();
                tried = new ArrayList<>();

                // tries all shapes for the randomly selected piece
                while (countShapes != tried.size()) {

                    play.setDisp(generator.nextInt(16));
                    while (tried.contains(play.getShape())) {
                        play.setDisp(generator.nextInt(16));
                    }
                    //play.getShape().printShape(); //debug
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
                    //System.out.println("COUNT SHAPES " + countShapes); //debug
                    //System.out.println("TRIED SIZE " + tried.size()); //debug
                }
                listPieceValue.remove(idx);
                nbPuttablePieces--;
            }
        }
    }
}
