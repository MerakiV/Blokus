package Players;

import Structures.Game;
import Structures.PieceReader;

public abstract class PlayerAI extends Player {
    protected int difficultyLevel;

    // Can be used in subclasses' clone method.
    public void cloneFields(PlayerAI p2) {
        super.cloneFields(p2);
        this.difficultyLevel = p2.difficultyLevel;
    }
}
