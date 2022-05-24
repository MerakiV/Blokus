package Controller;

import java.util.Comparator;

import Players.PlayerAIMinMax;
import Structures.Game;

public class ComparatorAIMinMax implements Comparator<Game> {
    PlayerAIMinMax play;
    boolean max;

    public ComparatorAIMinMax(PlayerAIMinMax ply, boolean mx) {
        play = ply;
        max = mx;
    }

    @Override
    public int compare(Game g1, Game g2) {
        return play.evaluation(g2, max) - play.evaluation(g1, max);
    }
}
