package Players;

import Structures.Board;
import Structures.Color;
import Structures.Game;
import Structures.PieceReader;
import Controller.ControllerAIMinMax;

import java.util.List;

public class PlayerAIMinMax extends PlayerAI {

    ControllerAIMinMax cAIMM;
    public PlayerAIMinMax(Color c) {
        isAI = true;
        score = 0;
        PieceReader pRead = null;
        try {
            pRead = new PieceReader();
            pieces = pRead.getPiecesList();
        } catch(Exception e) {
            System.err.println("FATAL ERROR : missing piece file");
            System.exit(1);
        }

        difficultyLevel = 1;
        col = c;
    }

    @Override
    public void playPiece(Board b) {
        System.out.println("Minimax");

        int res;

        //verify if there's a MinMax and add in every type of player the controller
        //verify if GameSettings2P set to 2 players
        /*if(config.getPlayerList().get(0).isAI()){ //
            res = cAIMM.AlgoMinMax(config, true, 3);
        }
        else{
            res = cAIMM.AlgoMinMax(config, false, 3);
        }*/

        //cAIMM.determineMove();
        return;
    }

    @Override
    public Player clone(){
        return null;
    }
}
