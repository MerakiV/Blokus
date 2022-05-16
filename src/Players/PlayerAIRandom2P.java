package Players;

import Structures.Color;

public class PlayerAIRandom2P extends Player2P {

    public PlayerAIRandom2P(Color col1, Color col2){
        pcol1 = new PlayerAIRandom(col1);
        pcol2 = new PlayerAIRandom(col2);
    }

}
