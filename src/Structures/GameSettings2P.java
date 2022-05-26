package Structures;

/*
to do : add more settings if needed
default colors?
 */

import java.awt.desktop.SystemEventListener;
import java.io.Serializable;

public class GameSettings2P implements Serializable {
    public boolean p1Human;
    public boolean p2Human;
    public int p1AIdiff;
    public int p2AIdiff;
    public Color p1c1, p1c2, p2c1, p2c2;

    public GameSettings2P(){
        //default values
        p1Human = true;
        p2Human = true;
    }

    public void setP1Human(){p1Human = true;}
    public void setP1AI(int diff){p1Human = false; p1AIdiff = diff;}
    public void setP2Human(){p2Human = true;}
    public void setP2AI(int diff){p2Human = false; p2AIdiff = diff;}

    public void setP1Color1(Color c){p1c1 = c;}
    public void setP1Color2(Color c){p1c2 = c;}
    public void setP2Color1(Color c){p2c1 = c;}
    public void setP2Color2(Color c){p2c2 = c;}

    public void printSettings(){
        System.out.println("Game Settings:");

        System.out.println("Player 1 Human : "+p1Human);
        System.out.println("Player 2 Human : "+p2Human);

        System.out.println();
        System.out.println("Colors:");
        System.out.println("Top-left (Player 1) : "+p1c1);
        System.out.println("Top-right (Player 2) : "+p2c1);
        System.out.println("Bottom-right (Player 1) : "+p1c2);
        System.out.println("Bottom-left (Player 2) : "+p2c2);

    }
}
