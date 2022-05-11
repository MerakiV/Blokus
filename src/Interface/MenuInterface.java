package Interface;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class MenuInterface extends JComponent {
    Image backGround;
    Image whiteBack;
    Image logo;
    public HoverButton newGame;
    public HoverButton conti;
    public HoverButton tutorial;
    public HoverButton exit;
    public JFrame frame;
    int x,y ;
    int height;
    int width;

    public MenuInterface(JFrame f) throws IOException {
        frame = f;
        String bg = "images/background.png";
        backGround = new Image(frame, bg);
        whiteBack = new Image(frame, "images/whiteBackground.png");
        logo = new Image(frame, "images/logo.png");
        x = frame.getWidth()/2;
        inItUIButton();
    }


    private void inItUIButton() throws IOException {
        x = frame.getWidth()/2;
        y = frame.getHeight();
        this.newGame = new HoverButton(this, "NG", x-202, (int)(y * 0.35));
        add(this.newGame);
        this.conti = new HoverButton(this, "Continue", x-202, (int)(y * 0.45));
        add(this.conti);
        this.tutorial = new HoverButton(this, "Tutorial",x-202, (int)(y * 0.55));
        add(this.tutorial);
        this.exit = new HoverButton(this, "Exit", x-202, (int)(y * 0.65));
        add(this.exit);
    }

    public void drawButtons(Graphics g){
        x = frame.getWidth()/2;
        y = frame.getHeight();
        int widthImage = this.newGame.getCurrentImageWidth() / 2;
        g.drawImage(this.newGame.getCurrentImage(), x- widthImage, (int)(y * 0.35), this);
        g.drawImage(this.conti.getCurrentImage(), x- widthImage, (int)(y * 0.45), this);
        g.drawImage(this.tutorial.getCurrentImage(), x- widthImage, (int)(y * 0.55), this);
        g.drawImage(this.exit.getCurrentImage(), x- widthImage,(int)(y * 0.65), this);
    }

    public void paintComponent(Graphics g) {
        height = frame.getHeight();
        width = frame.getWidth();
        backGround.drawImg(g,0, 0, width,height);
        whiteBack.drawImg(g,(int)(width * 0.01), (int)(height * 0.01), (int)(width * 0.98), (int)(height * 0.98));
        logo.drawImg(g, (int)(width * 0.375) ,(int)(height * 0.05), (int)(width * 0.25), (int)(height * 0.25));
        drawButtons(g);
    }

}