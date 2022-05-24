package Players;

import Structures.Board;
import Structures.Move;
import Structures.PieceReader;

public abstract class PlayerAI extends Player {
    protected int difficultyLevel;

    public abstract Move generateMove(Board b);

}
