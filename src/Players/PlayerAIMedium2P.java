package Players;

import Structures.Color;

public class PlayerAIMedium2P extends Player2P {

    public PlayerAIMedium2P(Color col1, Color col2, int type){
        pcol1 = new PlayerAIMedium(col1, type);
        pcol2 = new PlayerAIMedium(col2, type);
    }
}
