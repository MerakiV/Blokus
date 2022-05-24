package Players;

import Structures.Board;
import Structures.Color;
import Structures.Move;

public class PlayerAIMinMax extends PlayerAI {

    public PlayerAIMinMax(Color c) {
        difficultyLevel = 1;
        col = c;
    }

    @Override
    public void playPiece(Board b) {
        return;
    }

    @Override
    public Move generateMove(Board b){
        return null;
    }

    private PlayerAIMinMax() {}; // empty constructor
    @Override
    public Player clone() {
        PlayerAIMinMax p2 = new PlayerAIMinMax();
        p2.cloneFields(this);
        return p2;
    }
}
