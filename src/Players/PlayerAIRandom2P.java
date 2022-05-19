package Players;

import Structures.Color;

public class PlayerAIRandom2P extends Player2P {

    public PlayerAIRandom2P(Color col1, Color col2){
        long seed = System.currentTimeMillis();
        pcol1 = new PlayerAIRandom(col1, seed);
        pcol2 = new PlayerAIRandom(col2, seed+1);
    }

}
