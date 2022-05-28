package Structures;

import java.util.Comparator;

import Players.PlayerAIMinMax;
import Structures.Game;
import Structures.Move;

public class ComparatorAIMinMax implements Comparator<Move> {
    Game base;
    boolean max;

    public ComparatorAIMinMax(Game gm, boolean mx) {
        base = gm;
        max = mx;
    }

    @Override
    public int compare(Move m1, Move m2) {
        if (m1.getHeuristic()==Move.baseH) {
            Game g1 = base.clone();
            g1.put(m1.getShape(), m1.getPieceType(), g1.getCurrentColor(), m1.getTile().getX(), m1.getTile().getY());
            m1.setHeuristic(PlayerAIMinMax.evaluation(g1, max));
        }

        if (m2.getHeuristic()==Move.baseH) {
            Game g2 = base.clone();
            g2.put(m2.getShape(), m2.getPieceType(), g2.getCurrentColor(), m2.getTile().getX(), m2.getTile().getY());
            m2.setHeuristic(PlayerAIMinMax.evaluation(g2, max));
        }
        
        return m2.getHeuristic() - m1.getHeuristic();
    }
}
