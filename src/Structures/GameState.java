package Structures;

import Players.Player;

public class GameState {

    Board board;
    Player player;

    GameState(Board b, Player p){
        board = b;
        player = p;
    }

}
