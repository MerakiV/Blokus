package Controller;

import Players.Player;
import Players.PlayerAI;
import Structures.Game;
import Structures.Move;

public class PlayerTurn {
    public final Game game ;
    private final Player player;
    boolean turnPlayed;
    private Thread playerThread;
    private Move selectedMove;
    private ControllerGamePlay controller;
    private long startDateTimeInMillis = 0;

    PlayerTurn(Player p, Game game, ControllerGamePlay controller){
        this.controller = controller;
        if(p == null) throw new RuntimeException("Player should not be null");
        this.player = p;
        this.turnPlayed = false;
        this.playerThread = null;
        this.selectedMove = null;
        this.game = game;
    }

    /**
     * canExecuteMove :
     *      checks if player can execute any moves.
     */
    public boolean canExecuteMove(){
        return this.player.hasMoves();
    }

    /**
     * startTurn :
     *      starts a players turn. if the player is an AI, starts a thread to generate a move.
     */
    public void startTurn(){
        if(player.isAI()){
            playerThread = new Thread(new AITurn((PlayerAI) this.player, this.game, this));
            playerThread.start();
        }
        this.startDateTimeInMillis = System.currentTimeMillis();
    }

    /**
     * hasTerminated :
     *      checks if this player turn has ended (has generated a valid move).
     */
    public boolean hasTerminated(){
        return this.turnPlayed;
    }

    /**
     * pause :
     *      pauses (interrupts) a player's (AI) turn
     */
    public void pause(){
        if(this.playerThread != null && this.playerThread.isAlive()){
            this.playerThread.interrupt();
        }
    }


    /**
     *  resume :
     *     resumes (restarts) a player's (AI) turn
     */
    public void resume(){
        this.playerThread.start();
    }

    public long getRemainingTimeOfMinimalMillisecondTurnDuration() {
        long duration = System.currentTimeMillis() - this.startDateTimeInMillis;
        return 500 - duration;
    }

    public void terminateTurn(){
        if (this.playerThread != null){
            // TODO : check how to terminate correctly a thread in java.
            playerThread = null;
        }
        if(this.selectedMove != null) this.controller.paintImage(this.selectedMove, this.player.getColor());
    }

    /**
     * getSelectedMove :
     *      gets the player's (generated (AI) or chosen (Human)) move for this turn.
     */
    public Move getSelectedMove() {
        return this.selectedMove;
    }

    /**
     *  setMove :
     *      sets the players chosen move and sets turnPlayed to true if the move is valid.
     */
    public void setMove(Move currentMove) {
        //System.out.println("Going into set Move");
        this.selectedMove = currentMove;
        if (currentMove != null && game.getBoard().canPut(currentMove.getShape(), this.player.getColor()
                , currentMove.getTile().getX() , currentMove.getTile().getY())){
            this.turnPlayed = true;
            //System.out.println("Setting Move Completed");
        } else {
            if (currentMove == null)
                System.out.println("Current Move = null");
            if (!game.getBoard().canPut(currentMove.getShape(), this.player.getColor()
                    , currentMove.getTile().getX() , currentMove.getTile().getY())){
                System.out.println(currentMove.getPieceType().name());
                System.out.println(this.player.getColor().name());
                System.out.println(currentMove.getTile().getX() + " " + currentMove.getTile().getY());
                System.out.println("Cannot put piece");
            }
            System.out.println("Setting Move FAILED !!!!!!!!!!!!");

        }
    }

    /**
     * class AITurn :
     *      private class for PlayerTurn that generates a move for an AI.
     */
    private class AITurn implements Runnable {

        private final PlayerTurn playerTurn;
        private final  PlayerAI player;
        private final Game game;

        AITurn(PlayerAI player, Game game, PlayerTurn playerTurn){
            this.player = player;
            this.game = game;
            this.playerTurn = playerTurn;
        }

        @Override
        public void run() {
            Move currentMove = player.generateMove(game);
            System.out.println("Move generated");
            this.playerTurn.setMove(currentMove);
        }
    }

}
