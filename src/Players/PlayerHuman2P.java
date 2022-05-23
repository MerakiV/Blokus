package Players;

import Structures.Color;
import Structures.Piece;
import Structures.PieceReader;

import java.util.Set;

/*
to do: initialize Set<Piece>
 */

public class PlayerHuman2P extends Player2P {

    public PlayerHuman2P(Color col1, Color col2){
        pcol1 = new PlayerHuman(col1);
        pcol2 = new PlayerHuman(col2);
        score = pcol1.score + pcol2.score;
    }

}
