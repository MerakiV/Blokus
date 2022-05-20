package Players;

import Structures.Board;
import Structures.Color;
import Structures.Game;
import Structures.PieceReader;
public class PlayerHuman extends Player {

    public PlayerHuman(Color c){
        isAI = false;
        col = c;
        score = 0;
        //create list of pieces from PieceReader
        PieceReader pRead = null;
        try {
            pRead = new PieceReader();
            pieces = pRead.getPiecesList();
        } catch(Exception e) {
            System.err.println("FATAL ERROR : missing piece file");
            System.exit(1);
        }
    }

    @Override
    public void playPiece(Game g) {
        /* connect to interface */
    }

    private PlayerHuman() {}; // empty constructor
    @Override
    public Object clone() {
        PlayerHuman p2 = new PlayerHuman();
        p2.cloneFields(this);
        return p2;
    }
}
