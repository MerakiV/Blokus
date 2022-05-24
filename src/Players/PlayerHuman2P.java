package Players;

import Structures.Color;

/*
to do: initialize Set<Piece>
 */

public class PlayerHuman2P extends Player2P {

    public PlayerHuman2P(Color col1, Color col2){
        pcol1 = new PlayerHuman(col1);
        pcol2 = new PlayerHuman(col2);
        score = pcol1.score + pcol2.score;
    }

    private PlayerHuman2P() {}; // empty constructor
    @Override
    public Object clone() {
        PlayerHuman2P p2 = new PlayerHuman2P();
        p2.cloneFields(this);
        return p2;
    }

}
