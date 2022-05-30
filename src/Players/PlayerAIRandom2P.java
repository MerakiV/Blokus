package Players;

import Structures.Color;

public class PlayerAIRandom2P extends Player2P {

    public PlayerAIRandom2P(Color col1, Color col2){
        long seed = System.currentTimeMillis();
        pcol1 = new PlayerAIRandom(col1, seed);
        pcol2 = new PlayerAIRandom(col2, seed+1);
    }

    private PlayerAIRandom2P() {}; // empty constructor
    @Override
    public Player2P clone() {
        PlayerAIRandom2P p2 = new PlayerAIRandom2P();
        p2.cloneFields(this);
        return p2;
    }

}
