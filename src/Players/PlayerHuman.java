package Players;

import game.rules.Board;

public class PlayerHuman extends Player {
    @Override
    public Board play() {
        return null;
    }

    @Override
    public boolean canPlay() {
        return false;
    }
}
