import java.util.PriorityQueue;

import Controller.ControllerAIMinMax;
import Structures.Game;
import Structures.Game2P;
import Structures.GameSettings2P;

public class TestAIMM {
    public static void main(String [] args) {
        GameSettings2P gs = new GameSettings2P();
        Game2P g = new Game2P(gs);
        //g.printGame(false); // debug
        
        ControllerAIMinMax ctrl = new ControllerAIMinMax(g);
        //System.out.println("flag1"); // debug
        PriorityQueue<Game> pq = ctrl.moves(true);
        //System.out.println("flag2"); // debug
        Game ga;
        //System.out.println("flag3"); // debug

        while(!pq.isEmpty()) {
            //System.out.println("flag4"); // debug
            ga = pq.poll();
            ga.printGame(true);
        }
    }
}
