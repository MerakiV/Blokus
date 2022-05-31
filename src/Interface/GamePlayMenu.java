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
        this.newgame = new HoverButton(this, "NG", 500, 150);
        add(this.newgame);
        this.resume = new HoverButton(this, "Resume", 500, 250);
        add(this.resume);
        this.tutorial = new HoverButton(this, "Tutorial", 500, 350);
        add(this.tutorial);
        this.restart = new HoverButton(this, "Restart", 500, 450);
        add(this.restart);
        this.exit = new HoverButton(this, "Exit", 500, 550);
        add(this.exit);
    }

    public void playMenudrawButtons(Graphics g) throws IOException {
        g.drawImage(this.newgame.getCurrentImage(), 500, 150, this);
        g.drawImage(this.resume.getCurrentImage(), 500, 250, this);
        g.drawImage(this.tutorial.getCurrentImage(), 500, 350, this);
        g.drawImage(this.restart.getCurrentImage(), 500, 450, this);
        g.drawImage(this.exit.getCurrentImage(), 500, 550, this);
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
