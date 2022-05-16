package Players;

import Structures.Board;
import Structures.Color;

public class PlayerAIAlphaBeta extends PlayerAI {

    public PlayerAIAlphaBeta(Color c) {
        difficultyLevel = 2;
        col = c;
    }

    @Override
    public void playPiece(Board b) {
        return;
    }
}