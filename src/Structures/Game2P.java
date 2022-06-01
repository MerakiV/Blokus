package Structures;

import Players.*;

import java.util.ArrayList;

public class Game2P extends Game {
    public Player2P p1, p2;
    public Player2P currentPlayer2P;
    public GameSettings2P gs2p;

    public Game2P(GameSettings2P set) {
        gs2p = set;
        // TBI : create board
        // with GameSettings
        board = new Board(set.p1c1, set.p2c1, set.p1c2, set.p2c2);

        if (set.p1Human)
            p1 = new PlayerHuman2P(set.p1c1, set.p1c2);
        else {
            switch (set.p1AIdiff) {
                case 0:
                    p1 = new PlayerAIRandom2P(set.p1c1, set.p1c2);
                    break;
                case 1:
                    p1 = new PlayerAIMedium2P(set.p1c1, set.p1c2, 1);
                    break;
                case 2:
                    p1 = new PlayerAIMinMax2P(set.p1c1, set.p1c2, true, 5);
                    break;
                default:
            }
        }
        if (set.p2Human)
            p2 = new PlayerHuman2P(set.p2c1, set.p2c2);
        else {
            switch (set.p2AIdiff) {
                case 0:
                    p2 = new PlayerAIRandom2P(set.p2c1, set.p2c2);
                    break;
                case 1:
                    p2 = new PlayerAIMedium2P(set.p2c1, set.p2c2, 1);
                    break;
                case 2:
                    p2 = new PlayerAIMinMax2P(set.p2c1, set.p2c2, true, 4);
                    break;
                default:
            }
        }

        // put players in player list
        players = new ArrayList<Player>();
        players.add(p1.pcol1);
        players.add(p2.pcol1);
        players.add(p1.pcol2);
        players.add(p2.pcol2);

        // set p1 as current player
        currentPlayer2P = p1;
        currentPlayer = p1.pcol1;
        currentColor = p1.pcol1.getColor();

        // init history
        history = new History();

        // ended
        end = false;
    }

    public void nextTurn() {
        if (currentPlayer == p1.pcol1) {
            currentPlayer = p2.pcol1;
            currentPlayer2P = p2;
        } else if (currentPlayer == p1.pcol2) {
            currentPlayer = p2.pcol2;
            currentPlayer2P = p2;
        } else if (currentPlayer == p2.pcol1) {
            currentPlayer = p1.pcol2;
            currentPlayer2P = p1;
        } else { // currentPlayer == p2.col2
            currentPlayer = p1.pcol1;
            currentPlayer2P = p1;
        }
        currentColor = currentPlayer.getColor();
        history.future.clear();
    }

    private Game2P() {
    }

    @Override
    public Game clone() {
        Game2P g2 = new Game2P();
        g2.board = (Board) this.board.clone();

        g2.p1 = (Player2P) p1.clone();
        g2.p2 = (Player2P) p2.clone();
        if (p1 == currentPlayer2P)
            g2.currentPlayer2P = g2.p1;
        else
            g2.currentPlayer2P = g2.p2;
        g2.players = new ArrayList<Player>();
        g2.players.add(g2.p1.pcol1);
        g2.players.add(g2.p2.pcol1);
        g2.players.add(g2.p1.pcol2);
        g2.players.add(g2.p2.pcol2);

        g2.currentPlayer = null;
        int i = 0;
        while (g2.currentPlayer == null && i < players.size()) {
            if (currentPlayer == players.get(i))
                g2.currentPlayer = g2.players.get(i);
            i++;
        }
        g2.currentColor = currentColor;
        g2.history = history;

        return g2;
    }

}
