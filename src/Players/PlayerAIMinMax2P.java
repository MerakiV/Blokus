package Players;

import Structures.Color;

public class PlayerAIMinMax2P extends Player2P {

    public PlayerAIMinMax2P(Color col1, Color col2, boolean ab, int heur){
        pcol1 = new PlayerAIMinMax(col1, ab, heur);
        pcol2 = new PlayerAIMinMax(col2, ab, heur);
    }
 
    private PlayerAIMinMax2P() {}; // empty constructor
    @Override
    public Player2P clone() {
        PlayerAIMinMax2P p2 = new PlayerAIMinMax2P();
        p2.cloneFields(this);
        return p2;
    }
}
