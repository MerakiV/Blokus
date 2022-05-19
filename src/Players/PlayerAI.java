package Players;

import Structures.PieceReader;

public abstract class PlayerAI extends Player {
    protected int difficultyLevel;

    public PlayerAI(){
        isAI = true;
        difficultyLevel = 0; //default value: 0 for easy (AIRandom), ...(todo)
        //col = c;
        score = 0;
        PieceReader pRead = null;
        try {
            pRead = new PieceReader();
            pieces = pRead.getPiecesList();
        } catch(Exception e) {
            System.err.println("FATAL ERROR : missing piece file");
            System.exit(1);
        }
    }

    // Can be used in subclasses' clone method.
    public void cloneFields(PlayerAI p2) {
        super.cloneFields(p2);
        this.difficultyLevel = p2.difficultyLevel;
    }
}
