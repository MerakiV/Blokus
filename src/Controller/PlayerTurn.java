package Controller;

import Players.Player;
import Players.PlayerAI;
import Structures.Game;
import Structures.Move;

public class PlayerTurn {
    private final Game game ;
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

    public boolean canExecuteMove(){
        return this.player.canMove();
    }

    public void startTurn(){
        if(player.isAI()){
            playerThread = new Thread(new IaTurn((PlayerAI) this.player, this.game, this));
            playerThread.start();
        }
        this.startDateTimeInMillis = System.currentTimeMillis();
    }

    public boolean hasTerminated(){
        return this.turnPlayed;
    }

    public void pause(){
        if(this.playerThread != null && this.playerThread.isAlive()){
            this.playerThread.interrupt();
        }
    }

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

    public Move getSelectedMove() {
        return this.selectedMove;
    }

    private class IaTurn implements Runnable {

        private final PlayerTurn playerTurn;
        private final  PlayerAI player;
        private final Game game;

        IaTurn(PlayerAI player, Game game, PlayerTurn playerTurn){
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

    public void setMove(Move currentMove) {
        this.selectedMove = currentMove;
        if (currentMove != null && game.getBoard().canPut(currentMove.getShape(), this.player.getColor()
                , currentMove.getTile().getX() , currentMove.getTile().getY())){
            this.turnPlayed = true;
        }
    }
}
