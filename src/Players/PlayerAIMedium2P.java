package Players;

import Structures.Color;

public class PlayerAIMedium2P extends Player2P {

    public PlayerAIMedium2P(Color col1, Color col2, int mode){
        pcol1 = new PlayerAIMedium(col1, mode);
        pcol2 = new PlayerAIMedium(col2, mode);
    }

    private PlayerAIMedium2P() {}; // empty constructor
    @Override
    public Player2P clone(){
        Player2P p2 = new PlayerAIMedium2P();
        p2.cloneFields(this);
        return p2;
    }
}