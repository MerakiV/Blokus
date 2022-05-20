package Players;

import Structures.Board;
import Structures.Color;
import Structures.Game;

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
}