package Interface;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GamePlay extends JComponent {
    // Background images
    Image backGround;
    String bg;
    Image logo;
    String lo;
    Image board;
    String tableau;
    Image redBackground;
    String red;
    Image yellowBackground;
    String yellow;
    Image greenBackground;
    String green;
    Image blueBackground;
    String blue;
    public HoverButton menu;
    public HoverButton hint;
    public HoverButton vertical;
    public HoverButton horizontal;
    public HoverButton clockwise;
    public HoverButton counterclockwise;
    int height;
    int width;

    // Background for buttons

    public JFrame frame;

    public GamePlay(JFrame f) throws IOException{
        bg = "images/border.png";
        lo = "images/logo.png";
        tableau = "images/board.png";
        red = "images/redBackground.png";
        yellow = "images/yellowBackground.png";
        green = "images/greenBackground.png";
        blue = "images/blueBackground.png";
        frame = f;
        backGround = new Image(frame, bg);
        logo = new Image(frame, lo);
        board = new Image(frame, tableau);
        redBackground = new Image(frame, red);
        yellowBackground = new Image(frame, yellow);
        greenBackground = new Image(frame, green);
        blueBackground = new Image(frame, blue);
        initialiseButtons();
    }

    private void initialiseButtons() throws IOException{
        height = frame.getHeight();
        width = frame.getWidth();

        menu = new HoverButton(this, "Menu", (int) (width * 0.91), (int)(height * 0.08));
        hint = new HoverButton(this, "Hints", (int)(width * 0.05), (int)(height * 0.08));
        vertical = new HoverButton(this, "Vertical", 1300, 900);
        horizontal = new HoverButton(this, "Horizontal", 1200, 900);
        clockwise = new HoverButton(this, "Clockwise", 1100, 160);
        counterclockwise= new HoverButton(this, "CounterClockwise", 1000, 160);
    }

    public void paintComponent(Graphics g){
        // Sizes
        height = frame.getHeight();
        width = frame.getWidth();
        int iconSize = clockwise.getCurrentImageWidth()/2;
        int tilePanelHeight = (int)(height / 3);
        int tilePanelWidth = (int)(width * 0.25);

        // Background + Board
        backGround.drawImg(g,0, 0, width,height);
        logo.drawImg(g, (int)(width * 0.425) ,0, (int)(width * 0.15), (int)(height * 0.15));
        board.drawImg(g,(int)(width/3.1), (int)(height / 6.75), (int)(width/2.8), (int)(height / 1.5));

        // Player 1
        yellowBackground.drawImg(g, (int)(width * 0.06), (int)(height * 0.15), tilePanelWidth, tilePanelHeight);
        redBackground.drawImg(g, (int)(width * 0.06), (int)(height * 0.15 + tilePanelHeight), tilePanelWidth, tilePanelHeight);
        // Player 2
        greenBackground.drawImg(g, (int)(width * 0.94 - tilePanelWidth), (int)(height * 0.15), tilePanelWidth, tilePanelHeight);
        blueBackground.drawImg(g, (int)(width * 0.94 - tilePanelWidth), (int)(height * 0.15 + tilePanelHeight), tilePanelWidth, tilePanelHeight);
        // Menu
        g.drawImage(this.menu.getCurrentImage(), (int) (width * 0.91), (int)(height * 0.08), this);
        // Hint
        g.drawImage(this.hint.getCurrentImage(), (int)(width * 0.05), (int)(height * 0.08), this);
        // Undo

        // Redo

        // Piece Orientation Buttons
        g.drawImage(this.clockwise.getCurrentImage(), (int) (width * 0.55) - iconSize, (int)(height * 0.83), this);
        g.drawImage(this.counterclockwise.getCurrentImage(), (int) (width * 0.45)- iconSize, (int)(height * 0.83), this);
        g.drawImage(this.vertical.getCurrentImage(), (int) (width * 0.65)- iconSize, (int)(height * 0.83), this);
        g.drawImage(this.horizontal.getCurrentImage(), (int) (width * 0.35)- iconSize, (int)(height * 0.83), this);
    }

}
