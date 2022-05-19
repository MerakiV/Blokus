package Interface;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class PauseMenu extends JComponent {
    JFrame frame;
    Image backGround;
    Image whiteBack;
    public HoverButton button;
    int width, height;

    public PauseMenu (JFrame f) throws IOException{
        frame = f;
        backGround = new Image(frame, "images/background.png");
        whiteBack = new Image(frame, "images/whiteBackground.png");
    }
    public void paintComponent(Graphics g){
        height = frame.getHeight();
        width = frame.getWidth();
        backGround.drawImg(g,0, 0, width,height);
        whiteBack.drawImg(g,0, 0, width,height);
    }

}
