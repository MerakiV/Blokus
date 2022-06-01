package Interface;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Random;

public class GamePlayMenu extends JPanel {
    JFrame frame;
    public GamePlayInterface game;
    public Image backGround;
    public boolean finishdrawButton = false;
    public boolean backGroundexited = false;
    public HoverButton close, newgame, resume, tutorial, exit, restart;

    public GamePlayMenu(JFrame f, GamePlayInterface g) throws IOException {
        System.out.println("GamePlayMenu");
        frame = f;
        frame.setResizable(true);
        game = g;
        String bg = "images/greyBackground.png";
        backGround = new Image(frame, bg);
        this.setBounds(0, 0, frame.getWidth(), frame.getHeight());
        gameMenuinItUIButton();
    }

    private void gameMenuinItUIButton() throws IOException {
        this.newgame = new HoverButton(this, "NG", 0, 0);
        add(this.newgame);
        this.resume = new HoverButton(this, "Resume", 0, 0);
        add(this.resume);
        this.tutorial = new HoverButton(this, "Tutorial", 0, 0);
        add(this.tutorial);
        this.restart = new HoverButton(this, "Restart", 0, 0);
        add(this.restart);
        this.exit = new HoverButton(this, "Exit", 0, 0);
        add(this.exit);
    }

    private void resetBound() {
        Integer x = frame.getWidth() / 2;
        Integer y = frame.getHeight();
        int widthImage = this.newgame.getWidth() / 2;
        this.newgame.setBounds(x - widthImage, (int) (y * 0.2), (int) (frame.getWidth()*0.3), (int) (frame.getHeight()* 0.116));
        this.resume.setBounds(x - widthImage, (int) (y * 0.35),(int) (frame.getWidth()*0.3), (int) (frame.getHeight()* 0.116));
        this.tutorial.setBounds(x - widthImage, (int) (y * 0.5), (int) (frame.getWidth()*0.3), (int) (frame.getHeight()* 0.116));
        this.restart.setBounds(x - widthImage, (int) (y * 0.65), (int) (frame.getWidth()*0.3), (int) (frame.getHeight()* 0.116));
        this.exit.setBounds(x - widthImage, (int) (y * 0.8), (int) (frame.getWidth()*0.3), (int) (frame.getHeight()* 0.116));
    }

    public void playMenudrawButtons(Graphics g) throws IOException {
        resetBound();
        g.drawImage(this.newgame.getCurrentImage(), newgame.getX(), newgame.getY(), newgame.getWidth(), newgame.getHeight(),this);
        g.drawImage(this.resume.getCurrentImage(), resume.getX(), resume.getY(), resume.getWidth(), resume.getHeight(),this);
        g.drawImage(this.tutorial.getCurrentImage(), tutorial.getX(), tutorial.getY(), tutorial.getWidth(), tutorial.getHeight(),this);
        g.drawImage(this.restart.getCurrentImage(), restart.getX(), restart.getY(), restart.getWidth(), restart.getHeight(),this);
        g.drawImage(this.exit.getCurrentImage(), exit.getX(), exit.getY(), exit.getWidth(), exit.getHeight(),this);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        backGround.drawImg(g, 0, 0, frame.getWidth(), frame.getHeight());
//        backGroundexited = true;

        try {
            playMenudrawButtons(g);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
