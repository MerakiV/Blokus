package Players;

public abstract class Player2P implements Cloneable {
    public Player pcol1, pcol2;
    public int score;

    public void updateScore(){
        score = pcol1.score + pcol2.score;
        System.out.println("The score has been updated: "+ score);
        System.out.println("The pcol1 score has been updated: "+ pcol1.score);
        System.out.println("The pcol2 score has been updated: "+ pcol2.score);
    }

    public int get2PScore(){
        updateScore();
        return score;
    }

    // Must be overwritten in subclasses.
    @Override
    public Object clone() { return null; }
    // Can be used in subclasses' clone method.
    public void cloneFields(Player2P p2) {
        this.pcol1 = (Player) p2.pcol1.clone();
        this.pcol2 = (Player) p2.pcol2.clone();
    }

}
