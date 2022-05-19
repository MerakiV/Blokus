package Structures;

/**
 *  Points
 *      - Used to give x,y position on the frame for interface
 * */
public class Points {
    private int x, y;
    public Points(){
        this.x = 0;
        this.y = 0;
    }
    public Points(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }
}
