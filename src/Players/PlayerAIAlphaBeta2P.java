package Players;

import Structures.Color;

public class PlayerAIAlphaBeta2P extends Player2P {

    public PlayerAIAlphaBeta2P(Color col1, Color col2){
        pcol1 = new PlayerAIAlphaBeta(col1);
        pcol2 = new PlayerAIAlphaBeta(col2);
    }

    private PlayerAIAlphaBeta2P() {}; // empty constructor
    @Override
    public Player2P clone() {
        PlayerAIAlphaBeta2P p2 = new PlayerAIAlphaBeta2P();
        p2.cloneFields(this);
        return p2;
    }

}
