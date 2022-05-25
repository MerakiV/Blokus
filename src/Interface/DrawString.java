package Interface;

import java.awt.*;
import javax.swing.*;

public class DrawString extends JPanel {
    String text;
    int x;
    int y;
    Graphics g;
    Graphics2D g2d;

    Font font;

    public DrawString(Graphics g, String text, int x, int y, int fontSize) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.g = g;
        font = new Font("ABeeZee-Regular", Font.PLAIN, fontSize);
    }

    public void paint(Graphics g) {
        g2d = (Graphics2D) g;
        g2d.setFont(font);
        g2d.setColor(Color.BLACK);
        g2d.drawString(text, x, y);
    }

    public void changeFontSize(int fontSize) {
        font = new Font("ABeeZee-Regular", Font.PLAIN, fontSize);
    }

}
