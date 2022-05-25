package Interface;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GamePlayMenu extends JPanel {
    JFrame frame;
    public GamePlayInterface game;
    public Image backGround;
    Boolean existeMenu = false;
    public HoverButton close, newgame, resume, tutorial, exit, restart;

    public GamePlayMenu(JFrame f, GamePlayInterface g) throws IOException {
        if (!existeMenu) {
            System.out.println("GamePlayMenu");
            frame = f;
            game = g;
            String bg = "images/greyBackground.png";
            backGround = new Image(frame, bg);
            this.setBounds(0, 0, frame.getWidth(), frame.getHeight());
            inItUIButton();
        }
    }

    private void inItUIButton() throws IOException {
        this.newgame = new HoverButton(this, "NG", 500, 200);
        add(this.newgame);
        this.resume = new HoverButton(this, "Resume", 500, 300);
        add(this.resume);
        this.tutorial = new HoverButton(this, "Tutorial", 500, 400);
        add(this.tutorial);
        this.restart = new HoverButton(this, "Restart", 500, 500);
        add(this.restart);
        this.exit = new HoverButton(this, "Exit", 500, 600);
        add(this.exit);
    }

    public void drawButtons(Graphics g) throws IOException {
        g.drawImage(this.newgame.getCurrentImage(), 500, 200, this);
        g.drawImage(this.resume.getCurrentImage(), 500, 300, this);
        g.drawImage(this.tutorial.getCurrentImage(), 500, 400, this);
        g.drawImage(this.exit.getCurrentImage(), 500, 600, this);
        g.drawImage(this.restart.getCurrentImage(),500,500, this);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(!existeMenu) {
            backGround.drawImg(g, 0, 0, frame.getWidth(), frame.getHeight());
        }
        existeMenu = true;
        try {
            drawButtons(g);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        g.drawImage(this.close.getCurrentImage(), 0, , this);
//        repaint();
    }

}
