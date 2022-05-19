package Controller;

import java.util.Comparator;

import Structures.Game;

public class ComparatorAIMinMax implements Comparator<Game> {
    ControllerAIMinMax ctrl;
    boolean max;

    public ComparatorAIMinMax(ControllerAIMinMax ctl, boolean mx) {
        ctrl = ctl;
        max = mx;
    }

    @Override
    public int compare(Game g1, Game g2) {
        return ctrl.evaluation(g1, max) - ctrl.evaluation(g2, max);
    }
}
