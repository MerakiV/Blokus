package Interface;

import java.awt.*;
import javax.swing.*;

public class DrawString extends JPanel {
    String text;
    int x, y, stringSize;
    Graphics g;
    Graphics2D g2d;

    Font font;
    Color color;

    public DrawString(String text) {
        this.text = text;
    }

    public DrawString(Graphics g, String text, int x, int y, int fontSize) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.g = g;
        font = new Font("ABeeZee-Regular", Font.PLAIN, fontSize);
        getStringSize();
    }

    public DrawString(String text, Color color){
        this.text = text;
        this.color = color;
    }
    public DrawString(Graphics g, String text, Color color, int x, int y, int fontSize) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.g = g;
        this.color = color;
        font = new Font("ABeeZee-Regular", Font.PLAIN, fontSize);
        getStringSize();
    }

    public void paint(Graphics g) {
        g2d = (Graphics2D) g;
        g2d.setFont(font);
        // Select the color of text
        if (this.color == null)
            g2d.setColor(Color.BLACK);
        else
            g2d.setColor(this.color);
        g2d.drawString(text, x, y);
    }

    public void setFontSize(int fontSize) {
        font = new Font("ABeeZee-Regular", Font.PLAIN, fontSize);
    }

    public void setCoords(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setGraphics(Graphics g) {
        this.g = g;
    }

    public void getStringSize(){
        stringSize = text.length();
    }
}
