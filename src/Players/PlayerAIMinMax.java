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
    public Player clone(){
        return null;
    }

    @Override
    public Move generateMove(Board b){
        return null;
    }
}
