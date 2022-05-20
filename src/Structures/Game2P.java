package Structures;

import Players.*;

import java.util.ArrayList;

public class Game2P extends Game{
    public Player2P p1,p2;
    public Player2P currentPlayer2P;

    public Game2P(GameSettings2P set){
        board = new Board(set.p1c1, set.p2c1, set.p1c2, set.p2c2);

        //TBI : create players
        if(set.p1Human) p1 = new PlayerHuman2P(set.p1c1, set.p1c2);
        else p1 = new PlayerAIRandom2P(set.p1c1, set.p1c2);
        else {
            switch(set.p1AIdiff){
                case 0:
                    p1 = new PlayerAIRandom2P(set.p1c1, set.p1c2);
                    break;
                case 1:
                    //p1 = new PlayerAIMedium2P(set.p1c1, set.p1c2, 1);
                    p1 = new PlayerAIMinMax2P(set.p1c1, set.p1c2);
                    break;
                case 2:
                    p1 = new PlayerAIMinMax2P(set.p1c1, set.p1c2);
                    break;
                default:
            }
        }
        if(set.p2Human) p2 = new PlayerHuman2P(set.p2c1, set.p2c2);
        else p2 = new PlayerAIRandom2P(set.p2c1, set.p2c2);
        else {
            switch(set.p2AIdiff){
                case 0:
                    p2 = new PlayerAIRandom2P(set.p2c1, set.p2c2);
                    break;
                case 1:
                    //p2 = new PlayerAIMedium2P(set.p2c1, set.p2c2, 1);
                    p2 = new PlayerAIMinMax2P(set.p2c1, set.p2c2);
                    break;
                case 2:
                    p2 = new PlayerAIMinMax2P(set.p2c1, set.p2c2);
                    break;
                default:
            }
        }

        //put players in player list
        players = new ArrayList<Player>();
        players.add(p1.pcol1);
        players.add(p2.pcol1);
        players.add(p1.pcol2);
        players.add(p2.pcol2);


        //set p1 as current player
        currentPlayer2P = p1;
        currentPlayer = p1.pcol1;
        currentColor = p1.pcol1.getColor();

        //init history
        history = new History();
    }

    public void nextTurn(){
        pushToPast();
        if(currentPlayer == p1.pcol1){
            currentPlayer = p2.pcol1;
            currentPlayer2P = p2;
        }
        else if(currentPlayer == p1.pcol2){
            currentPlayer = p2.pcol2;
            currentPlayer2P = p2;
        }
        else if(currentPlayer == p2.pcol1){
            currentPlayer = p1.pcol2;
            currentPlayer2P = p1;
        }
        else{ //currentPlayer == p2.col2
            currentPlayer = p1.pcol1;
            currentPlayer2P = p1;
        }
        currentColor = currentPlayer.getColor();
        history.future.clear();
    }

    private Game2P() {}
    @Override
    public Object clone() {
        Game2P g2 = new Game2P();

        g2.cloneFields(this);
        g2.p1 = (Player2P) this.p1.clone();
        g2.p2 = (Player2P) this.p2.clone();
        if (this.p1==this.currentPlayer2P) { g2.currentPlayer2P = g2.p1; }
        else                               { g2.currentPlayer2P = g2.p2; }

        return g2;
    }



}
