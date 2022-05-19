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

    @Override
    public Player clone(){
        return null;
    }
}
