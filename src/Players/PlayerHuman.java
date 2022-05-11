package Players;

import Structures.Color;
import Structures.Piece;
import Structures.PieceReader;

import java.util.Set;

/*
to do: initialize Set<Piece>
 */

public class PlayerHuman extends Player {

    public PlayerHuman(Color c){
        isAI = false;
        col = c;
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

}
