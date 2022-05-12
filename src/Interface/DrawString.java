package Interface;

import java.awt.*;
import java.awt.Image;

import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.*;

/*
game settings object
create game object on play button press (GameSettings2P, Game2P)

*/

public class DrawString extends JPanel{
    String text;
    int x,y;
    Graphics g;
    Graphics2D g2d;

    public DrawString(Graphics g, String t, int x, int y) {
        text = t;
        this.x = x;
        this.y = y;
        this.g = g;
    }

    public void paint(Graphics g) {
        g2d = (Graphics2D) g;
        //g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        Font font = new Font("Serif", Font.PLAIN, 20);
        g2d.setFont(font);
        g2d.setColor(Color.BLACK);
        g2d.drawString(text, x, y);
    }

}
