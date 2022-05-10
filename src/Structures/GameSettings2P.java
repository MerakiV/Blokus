package Structures;

/*
to do : add more settings if needed
default colors?
 */

public class GameSettings2P {
    public boolean p1Human;
    public boolean p2Human;
    public Color p1c1, p1c2, p2c1, p2c2;

    public GameSettings2P(){
        //default values
        p1Human = true;
        p2Human = true;
    }

    public void setP1Human(){p1Human = true;}
    public void setP1AI(){p1Human = false;}
    public void setP2Human(){p2Human = true;}
    public void setP2AI(){p2Human = false;}

    public void setP1Color1(Color c){p1c1 = c;}
    public void setP1Color2(Color c){p1c2 = c;}
    public void setP2Color1(Color c){p2c1 = c;}
    public void setP2Color2(Color c){p2c2 = c;}
}
