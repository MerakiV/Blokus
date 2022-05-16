package Interface;

import java.awt.*;
import java.awt.Image;

import java.awt.geom.AffineTransform;
import java.io.File;
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
    int x;
    int y;
    float fontsize;
    Graphics g;
    Graphics2D g2d;

    Font font;


    public DrawString(Graphics g, String t, int x, int y, float f) {
        text = t;
        this.x = x;
        this.y = y;
        this.g = g;
        this.fontsize = f;
        font = new Font("ABeeZee-Regular", Font.PLAIN, 20);
    }

    public void paint(Graphics g) {
        g2d = (Graphics2D) g;
        //g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setFont(font);
        g2d.setColor(Color.BLACK);
        g2d.drawString(text, x, y);
    }

}
