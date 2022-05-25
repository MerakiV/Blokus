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

public class DrawString extends JPanel {
    String text;
    int x;
    int y;
    int fontSize;
    Graphics g;
    Graphics2D g2d;

    Font font;
    Color color;

    public DrawString(Graphics g, String t, int x, int y, int f) {
        text = t;
        this.x = x;
        this.y = y;
        this.g = g;
        this.fontSize = f;
        font = new Font("ABeeZee-Regular", Font.PLAIN, fontSize);
    }

    public DrawString(Graphics g, String t, Color color, int x, int y, int f) {
        text = t;
        this.x = x;
        this.y = y;
        this.g = g;
        this.fontSize = f;
        this.color = color;
        font = new Font("ABeeZee-Regular", Font.PLAIN, fontSize);
    }

    public void paint(Graphics g) {
        g2d = (Graphics2D) g;
        g2d.setFont(font);
        // g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
        // RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Select the color of text
        if (this.color == null)
            g2d.setColor(Color.BLACK);
        else
            g2d.setColor(this.color);
        g2d.drawString(text, x, y);
    }

}
