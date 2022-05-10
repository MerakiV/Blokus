package Players;

import Structures.Color;
import Structures.Piece;

import java.util.Set;

/*
to do: initialize Set<Piece>
 */

public class PlayerHuman2P extends Player {
    Color c1, c2;
    Set<Piece> c1Pieces, c2Pieces;

    public PlayerHuman2P(Color col1, Color col2){
        c1 = col1; c2 = col2;
    }

}
