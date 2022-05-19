package Players;

import Structures.Board;
import Structures.Color;

public class PlayerAIMinMax extends PlayerAI {

    public PlayerAIMinMax(Color c) {
        difficultyLevel = 1;
        col = c;
    }

    @Override
    public void playPiece(Board b) {
        return;
    }

    private PlayerAIMinMax() {}; // empty constructor
    @Override
    public Object clone() {
        PlayerAIMinMax p2 = new PlayerAIMinMax();
        p2.cloneFields(this);
        return p2;
    }
}
