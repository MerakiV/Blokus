package Players;

import Structures.Color;

public class PlayerAIMedium2P extends Player2P {

    public PlayerAIMedium2P(Color col1, Color col2){
        pcol1 = new PlayerAIMedium(col1);
        pcol2 = new PlayerAIMedium(col2);
    }
}