package Players;

import Structures.Color;
import Structures.Piece;

import java.util.Set;

public abstract class Player2P {
    public Player pcol1, pcol2;
    public int score;

    public void updateScore(){
        score = pcol1.score + pcol2.score;
    }

    public int get2PScore(){
        updateScore();
        return score;
    }

}
