package Interface;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GamePlay extends JComponent {
    // Background images
    Image backGround;
    Image logo;
    Image board;
    Image redBackground;
    Image yellowBackground;
    Image greenBackground;
    Image blueBackground;
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
        frame = f;
        backGround = new Image(frame, "images/border.png");
        logo = new Image(frame, "images/logo.png");
        board = new Image(frame, "images/board.png");
        redBackground = new Image(frame, "images/redBackground.png");
        yellowBackground = new Image(frame, "images/yellowBackground.png");
        greenBackground = new Image(frame, "images/greenBackground.png");
        blueBackground = new Image(frame, "images/blueBackground.png");
        initialiseButtons();
    }

    private void initialiseButtons() throws IOException{
        height = frame.getHeight();
        width = frame.getWidth();

        menu = new HoverButton(this, "Menu", (int) (width * 0.91), (int)(height * 0.08));
        add(this.menu);
        hint = new HoverButton(this, "Hints", (int)(width * 0.05), (int)(height * 0.08));
        add(this.hint);
        vertical = new HoverButton(this, "Vertical", (int) (width * 0.65) - 50, (int)(height * 0.83));
        add(this.vertical);
        horizontal = new HoverButton(this, "Horizontal", (int) (width * 0.35) - 50, (int)(height * 0.83));
        add(this.horizontal);
        clockwise = new HoverButton(this, "Clockwise", (int) (width * 0.55) - 50, (int)(height * 0.83));
        add(this.clockwise);
        counterclockwise= new HoverButton(this, "CounterClockwise", (int) (width * 0.45)- 50, (int)(height * 0.83));
        add(this.counterclockwise);
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
