package Interface;

import Controller.ControllerGamePlay;
import GamePanels.BoardPanel;
import GamePanels.ColorPanel;
import Players.Player;
import Structures.Color;
import Structures.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;


public class GamePlayInterface extends JPanel {

    public JFrame frame;
    // Background images
    Image backGround, logo;
    // Buttons
    public HoverButton menu, hint, vertical,horizontal, clockwise, counterclockwise;

    public int boardSize,tileSize,height,width,widthFrame,heightFrame;
    int topLeftX, topLeftY, topRightX, topRightY,bottomLeftX, bottomLeftY, bottomRightX, bottomRightY, boardX, boardY;

    Dimension size;
    public Dimension colorPanelSize;
    public BoardPanel boardPanel;

    // Color Panels
    ColorPanel topLeftPanel, bottomLeftPanel, topRightPanel, bottomRightPanel;

    // Controller
    ControllerGamePlay controller;

    public GamePlayInterface(JFrame f, ControllerGamePlay c) throws IOException {
        controller = c;
        frame = f;
        setSize();
        this.setLayout(new FlowLayout());
        this.addMouseListener(new GameMouseAdapter(this));
        boardPanel = new BoardPanel(this, controller);
        this.add(boardPanel);
        initialiseColorPanels();
        initialiseButtons();
        backGround = new Image(frame, "images/border.png");
        logo = new Image(frame, "images/logo.png");
        System.out.println("Finished GamePlayInterface");
    }


    private void setSize(){
        widthFrame = frame.getWidth();
        heightFrame = frame.getHeight();
        boardSize = (int) (Math.min(heightFrame, widthFrame) * 0.6);
        size = new Dimension(boardSize, boardSize);
        tileSize = boardSize/20;
        colorPanelSize = new Dimension((int) (boardSize * 0.72), boardSize / 2); // Changes the size of components in the colorPanel
        topLeftX = (int) (widthFrame * 0.06) - 10;
        topLeftY = (int) (heightFrame * 0.2);
        topRightX = (int)(0.94 * widthFrame - boardSize * 0.72) - 10;
        topRightY = (int) (heightFrame * 0.2);
        bottomLeftX = (int) (widthFrame * 0.06) - 10;
        bottomLeftY = (int)(heightFrame * 0.2 + boardSize/2);
        bottomRightX = (int)(0.94 * widthFrame - boardSize * 0.72) - 10;
        bottomRightY = (int)(heightFrame * 0.2 + boardSize/2);
        boardX = (widthFrame - size.width) / 2;
        boardY = (int) (heightFrame * 0.2);
    }

    private void initialiseColorPanels() throws IOException {
        topLeftPanel = new ColorPanel(this, controller, controller.game.getPlayerList().get(0));
        topLeftPanel.setPreferredSize(colorPanelSize);
        this.add(topLeftPanel);
        bottomLeftPanel = new ColorPanel(this, controller, controller.game.getPlayerList().get(2));
        bottomLeftPanel.setPreferredSize(colorPanelSize);
        this.add(bottomLeftPanel);
        topRightPanel = new ColorPanel(this, controller, controller.game.getPlayerList().get(1));
        topRightPanel.setPreferredSize(colorPanelSize);
        this.add(topRightPanel);
        bottomRightPanel = new ColorPanel(this, controller, controller.game.getPlayerList().get(3));
        bottomRightPanel.setPreferredSize(colorPanelSize);
        this.add(bottomRightPanel);
    }

    // TODO : icons to SVG
    private void initialiseButtons() throws IOException{
        height = frame.getHeight();
        width = frame.getWidth();

        menu = new HoverButton(this, "Menu", (int) (width * 0.91), (int)(height * 0.08));
        add(this.menu);
        hint = new HoverButton(this, "Hints", (int)(width * 0.05), (int)(height * 0.08));
        add(this.hint);
        vertical = new HoverButton(this, "Vertical", (int) (width * 0.65) - 50, (int)(height * 0.85));
        add(this.vertical);
        horizontal = new HoverButton(this, "Horizontal", (int) (width * 0.35) - 50, (int)(height * 0.85));
        add(this.horizontal);
        clockwise = new HoverButton(this, "Clockwise", (int) (width * 0.55) - 50, (int)(height * 0.85));
        add(this.clockwise);
        counterclockwise= new HoverButton(this, "CounterClockwise", (int) (width * 0.45)- 50, (int)(height * 0.85));
        add(this.counterclockwise);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        boardPanel.setLocation(boardX, boardY);
        topLeftPanel.setBounds(topLeftX, topLeftY, colorPanelSize.width, colorPanelSize.height);
        bottomLeftPanel.setBounds(bottomLeftX,  bottomLeftY, colorPanelSize.width, colorPanelSize.height);
        topRightPanel.setBounds(topRightX, topRightY,  colorPanelSize.width, colorPanelSize.height);
        bottomRightPanel.setBounds(bottomRightX, bottomRightY, colorPanelSize.width, colorPanelSize.height);
        // Sizes
        height = frame.getHeight();
        width = frame.getWidth();
        int iconSize = clockwise.getCurrentImageWidth()/2;
        // Background
        backGround.drawImg(g,0, 0, width,height);
        logo.drawImg(g, (int)(width * 0.425) ,0, (int)(width * 0.15), (int)(height * 0.15));

        // TODO: Merge previous Menu
        g.drawImage(this.menu.getCurrentImage(), (int) (width * 0.91), (int)(height * 0.08), frame);
        // Hint
        g.drawImage(this.hint.getCurrentImage(), (int)(width * 0.05), (int)(height * 0.08), frame);
        // TODO: Undo
        // TODO: Redo

        // Piece Orientation Buttons
        g.drawImage(this.clockwise.getCurrentImage(), (int) (width * 0.55) - iconSize, (int)(height * 0.85), this);
        g.drawImage(this.counterclockwise.getCurrentImage(), (int) (width * 0.45)- iconSize, (int)(height * 0.85), this);
        g.drawImage(this.vertical.getCurrentImage(), (int) (width * 0.65)- iconSize, (int)(height * 0.85), this);
        g.drawImage(this.horizontal.getCurrentImage(), (int) (width * 0.35)- iconSize, (int)(height * 0.85), this);

    }
}