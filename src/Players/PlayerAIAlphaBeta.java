package Players;

import Structures.Board;
import Structures.Color;
import Structures.Game;
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

    private PlayerAIAlphaBeta() {}; // empty constructor
    @Override
    public Player clone() {
        Player p2 = new PlayerAIAlphaBeta();
        p2.cloneFields(this);
        return p2;
    }

    @Override
    public Move generateMove(Game g){
        return null;
    }

    @Override
    public Move generateMove(Board b){
        return null;
    }
}