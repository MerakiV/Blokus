package Players;

import Structures.Board;
import Structures.Game;
import Structures.Move;

public abstract class PlayerAI extends Player {
    protected int difficultyLevel;

    public abstract Move generateMove(Game g);

    // Can be used in subclasses' clone method.
    public void cloneFields(PlayerAI p2) {
        super.cloneFields(p2);
        this.difficultyLevel = p2.difficultyLevel;
    }

}
