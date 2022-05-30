package Structures;

import java.io.Serializable;
import java.util.ArrayList;

import Players.Player;

public class GameState implements Serializable {

    Board board;
    ArrayList<Player> players;
    int currentPlayer;
    int heuristic;

    GameState(Board b, ArrayList<Player> pl, int curpl){
        board = b;
        players = pl;
        currentPlayer = curpl;
        heuristic = Integer.MAX_VALUE; // default uninitialized value
    }

    GameState(Game2P g2p){
        board = g2p.board.clone();
        players = new ArrayList<>();
        for(Player p : g2p.players){
            players.add(p.clone());
        }
        int pl = -1;
        for (int i = 0; i < g2p.players.size() && pl == -1; i++) {
            if (g2p.currentPlayer ==  g2p.players.get(i)) {
                pl = i;
            }
        }
        currentPlayer = pl;
        heuristic = Integer.MAX_VALUE; // default uninitialized value
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayer);
    }

    public int getCPindex(){
        return currentPlayer;
    }

    public Board getBoard() {
        return board;
    }

    public void nextPlayer() {
        currentPlayer = (currentPlayer + 1) % players.size();
    }

    public int getHeuristic() {
        if (heuristic==Integer.MAX_VALUE) {
            // initialize heuristic
        }
        return heuristic;
    }

    public GameState clone() {
        ArrayList<Player> pl2 = new ArrayList<>(this.players.size());
        for (int i=0; i<this.players.size(); i++) {
            pl2.add(this.players.get(i).clone());
        }

        return new GameState(this.board.clone(), pl2, this.currentPlayer);
    }

}
