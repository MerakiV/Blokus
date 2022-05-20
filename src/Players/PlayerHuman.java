package Players;

import Structures.*;

import java.util.ArrayList;
import java.util.Set;

/*
to do: initialize Set<Piece>
 */

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
    public void playPiece(Board b) {
        /* connect to interface */


    }

    @Override
    public Player clone(){
        Player p2 = new PlayerHuman(this.col);
        p2.score = this.score;
        p2.pieces = new ArrayList<>();
        for(Piece p : this.pieces){
            p2.pieces.add(p);
        }
        return p2;
    }
}
