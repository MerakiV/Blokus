package Players;

public abstract class Player2P implements Cloneable {
    public Player pcol1, pcol2;

    // Must be overwritten in subclasses.
    @Override
    public Object clone() { return null; }
    
    // Can be used in subclasses' clone method.
    public void cloneFields(Player2P p2) {
        this.pcol1 = (Player) p2.pcol1.clone();
        this.pcol2 = (Player) p2.pcol2.clone();
    }

}
