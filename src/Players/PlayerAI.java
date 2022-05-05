package Players;

import game.rules.Board;

public abstract class PlayerAI extends Player {
    protected int difficultyLevel;

    @Override
    public Board play() {
        return null;
    }

    @Override
    public boolean canPlay() {
        return false;
    }
}
