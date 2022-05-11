package Interface;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class MenuInterface extends JComponent {
    Image backGround;
    public HoverButton newGame;
    public HoverButton conti;
    public HoverButton tutorial;
    public HoverButton exit;
    public JFrame frame;
    int x ;
    int height;
    int width;

    public MenuInterface(JFrame f) throws IOException {
        frame = f;
        String bg = "images/background.png";
        backGround = new Image(frame, bg);
        x = frame.getWidth()/2;
        inItUIButton();
    }


    private void inItUIButton() throws IOException {
        x = frame.getWidth()/2;
        this.newGame = new HoverButton(this, "NG", x-202, 280);
        add(this.newGame);
        this.conti = new HoverButton(this, "Continue", x-202, 390);
        add(this.conti);
        this.tutorial = new HoverButton(this, "Tutorial",x-202, 500);
        add(this.tutorial);
        this.exit = new HoverButton(this, "Exit", x-202, 610);
        add(this.exit);
    }

    public void drawButtons(Graphics g){
        x = frame.getWidth()/2;
        int widthImage = this.newGame.getCurrentImageWidth() / 2;
        g.drawImage(this.newGame.getCurrentImage(), x- widthImage, 280, this);
        g.drawImage(this.conti.getCurrentImage(), x- widthImage, 390, this);
        g.drawImage(this.tutorial.getCurrentImage(), x- widthImage, 500, this);
        g.drawImage(this.exit.getCurrentImage(), x- widthImage,610, this);
    }

    public void paintComponent(Graphics g) {
        height = frame.getHeight();
        width = frame.getWidth();
        backGround.drawImg(g,0, 0, width-50,height-50);
        drawButtons(g);
    }

}