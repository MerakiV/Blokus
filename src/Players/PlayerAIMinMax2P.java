package Players;

import Structures.Color;

public class PlayerAIMinMax2P extends Player2P {

    public PlayerAIMinMax2P(Color col1, Color col2){
        pcol1 = new PlayerAIMinMax(col1);
        pcol2 = new PlayerAIMinMax(col2);
    }
}
