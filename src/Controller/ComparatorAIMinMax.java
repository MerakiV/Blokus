package Controller;

import java.util.Comparator;

import Structures.Game;

public class ComparatorAIMinMax implements Comparator<Game> {
    boolean max;

    public ComparatorAIMinMax(boolean mx) {
        max = mx;
    }

    @Override
    public int compare(Game g1, Game g2) {
        return ControllerAIMinMax.evaluation(g1, max) - ControllerAIMinMax.evaluation(g2, max);
    }
}
