package Players;

import Structures.Board;
import Structures.Color;
import Structures.Move;

public class PlayerAIAlphaBeta extends PlayerAI {

    public PlayerAIAlphaBeta(Color c) {
        difficultyLevel = 2;
        col = c;
    }

    @Override
    public void playPiece(Board b) {
        return;
    }

    @Override
    public Player clone() {
        return null;
    }

    @Override
    public Move generateMove(Board b){
        return null;
    }
}