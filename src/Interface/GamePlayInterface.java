package Interface;

import Controller.ControllerGamePlay;
import GamePanels.BoardPanel;
import GamePanels.ColorPanel;
import Structures.Game2P;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GamePlayInterface extends JPanel {

    public JFrame frame;
    // Background images
    Image backGround, logo, backGroundImg;
    // Buttons
    public HoverButton menu, hint, undo, redo;

    public int boardSize, tileSize, height, width, widthFrame, heightFrame;
    int topLeftX, topLeftY, topRightX, topRightY, bottomLeftX, bottomLeftY, bottomRightX, bottomRightY, boardX, boardY,
            p1Score = 0, p2Score = 0;

    Dimension size;
    public Dimension colorPanelSize;
    public BoardPanel boardPanel;

    public GamePlayMenu playMenu;
    public Boolean play = false;

    // Color Panels
    ColorPanel topLeftPanel, bottomLeftPanel, topRightPanel, bottomRightPanel;

    // Controller
    ControllerGamePlay controller;
    Game2P g2p;

    public GamePlayInterface(JFrame f, ControllerGamePlay c) throws IOException {
        controller = c;
        frame = f;
        g2p = (Game2P) controller.game;
        setSize();
        this.setLayout(new FlowLayout());
        boardPanel = new BoardPanel(this, controller);
        controller.setBoardPanel(boardPanel);
        this.add(boardPanel);
        controller.setBoardPanel(boardPanel);
        backGround = new Image(frame, "images/border.png");
        backGroundImg = new Image(frame, "images/whiteBackground.png");
        logo = new Image(frame, "images/logo.png");

        initialiseColorPanels();
        initialiseButtons();
        this.addMouseListener(new GameMouseAdapter(this, menu));
        this.addMouseListener(new GameMouseAdapter(this, hint));
        this.addMouseListener(new GameMouseAdapter(this, undo));
        this.addMouseListener(new GameMouseAdapter(this, redo));

        System.out.println(controller.game.getCurrentPlayer().toString());
        System.out.println("Finished GamePlayInterface");
        controller.startGame();
    }

    public void drawMenu(Graphics g) throws IOException {
        if (play) {
            playMenu = new GamePlayMenu(frame, this);
            playMenu.setBackground(null);
            playMenu.setOpaque(false);
            // backGroundImg.drawImg(g, 0, 0, frame.getWidth(), frame.getHeight());

            this.add(playMenu, 0);
        }
    }

    public void removeMenu(GamePlayMenu gM) {
        play = false;
        gM.existeMenu = false;
        this.remove(gM);
        this.frame.revalidate();
        this.frame.repaint();
    }

    private void setSize() {
        widthFrame = frame.getWidth();
        heightFrame = frame.getHeight();
        boardSize = (int) (Math.min(heightFrame, widthFrame) * 0.6);
        size = new Dimension(boardSize, boardSize);
        tileSize = boardSize / 20;
        colorPanelSize = new Dimension((int) (boardSize * 0.72), boardSize / 2); // Changes the size of components in
                                                                                 // the colorPanel
        topLeftX = (int) (widthFrame * 0.06) - 10;
        topLeftY = (int) (heightFrame * 0.2);
        topRightX = (int) (0.94 * widthFrame - boardSize * 0.72) - 10;
        topRightY = (int) (heightFrame * 0.2);
        bottomLeftX = (int) (widthFrame * 0.06) - 10;
        bottomLeftY = (int) (heightFrame * 0.28 + boardSize / 2);
        bottomRightX = (int) (0.94 * widthFrame - boardSize * 0.72) - 10;
        bottomRightY = (int) (heightFrame * 0.28 + boardSize / 2);
        boardX = (widthFrame - size.width) / 2;
        boardY = (int) (heightFrame * 0.2);
    }

    private void initialiseColorPanels() throws IOException {
        topLeftPanel = new ColorPanel(this, controller, controller.game.getPlayerList().get(0));
        topLeftPanel.setPreferredSize(colorPanelSize);
        this.add(topLeftPanel);
        bottomLeftPanel = new ColorPanel(this, controller, controller.game.getPlayerList().get(3));
        bottomLeftPanel.setPreferredSize(colorPanelSize);
        this.add(bottomLeftPanel);
        topRightPanel = new ColorPanel(this, controller, controller.game.getPlayerList().get(1));
        topRightPanel.setPreferredSize(colorPanelSize);
        this.add(topRightPanel);
        bottomRightPanel = new ColorPanel(this, controller, controller.game.getPlayerList().get(2));
        bottomRightPanel.setPreferredSize(colorPanelSize);
        this.add(bottomRightPanel);
    }

    // TODO : icons to SVG
    private void initialiseButtons() throws IOException {
        height = frame.getHeight();
        width = frame.getWidth();

        menu = new HoverButton(this, "Menu", (int) (width * 0.91), (int) (height * 0.08));
        add(this.menu);
        hint = new HoverButton(this, "Hints", (int) (width * 0.05), (int) (height * 0.08));
        add(this.hint);
        undo = new HoverButton(this, "Clockwise", (int) (width * 0.55) - 50, (int) (height * 0.85));
        add(this.undo);
        redo = new HoverButton(this, "CounterClockwise", (int) (width * 0.45) - 50, (int) (height * 0.85));
        add(this.redo);
    }

    public void playerTurn(Graphics g) {
        DrawString currentPlayer;
        if (g2p.currentPlayer2P == g2p.p1 && !controller.game.end) {
            currentPlayer = new DrawString(g, "Player 1 " + controller.currentColor + "'s turn", transformColor(),
                    (int) (width * 0.4), (int) (height * 0.18), 25);
            System.out.println("Size of string : " + currentPlayer.getWidth());
            p1Score = g2p.p1.get2PScore();
            currentPlayer.paint(g);
        } else if (g2p.currentPlayer2P == g2p.p2 && !controller.game.end) {
            currentPlayer = new DrawString(g, "Player 2 " + controller.currentColor + "'s turn", transformColor(),
                    (int) (width * 0.4), (int) (height * 0.18), 25);
            System.out.println("Size of string : " + currentPlayer.getWidth());
            p2Score = g2p.p2.get2PScore();
            currentPlayer.paint(g);
        }
        if (controller.game.end) {
            currentPlayer = new DrawString(g, "END OF GAME", (int) (width * 0.4), (int) (height * 0.18), 25);
            System.out.println("Size of string : " + currentPlayer.getWidth());
            currentPlayer.paint(g);
        }
    }

    public void checkEndGame(Graphics g) {
        if (controller.game.end) {
            DrawString currentPlayer = new DrawString(g, "END OF GAME", transformColor(), (int) (width * 0.4),
                    boardY + boardSize + 20, 25);
            System.out.println("I PRINTED THE STRING");
            currentPlayer.paint(g);
        }
    }

    /*
     * Transform Color
     * takes the current color of the controller and returns the corresponding
     * java.awt color for string color
     */
    Color transformColor() {
        switch (controller.currentColor) {
            case RED:
                return new Color(207, 14, 17);
            case YELLOW:
                return new Color(220, 184, 62);
            case GREEN:
                return new Color(0, 128, 0);
            case BLUE:
                return new Color(19, 70, 130);
        }
        return null;
    }

    /*
     * Draw Strings
     * Function that groups all of the strings to be printed
     */
    void drawStrings(Graphics g) {
        DrawString player1 = new DrawString(g, "Player 1", (int) (width * 0.15), (int) (height * 0.18), 25);
        DrawString player1Score = new DrawString(g, "Score: " + p2Score, (int) (width * 0.3), (int) (height * 0.18),
                25);
        DrawString player2 = new DrawString(g, "Player 2", topRightX + colorPanelSize.width / 3, (int) (height * 0.18),
                25);
        DrawString player2Score = new DrawString(g, "Score: " + p1Score, (int) (width * 0.62), (int) (height * 0.18),
                25);

        // Bottom
        DrawString player1bottom = new DrawString(g, "Player 1", bottomRightX + colorPanelSize.width / 3,
                (int) (heightFrame * 0.26 + boardSize / 2), 25);
        DrawString player2bottom = new DrawString(g, "Player 2", (int) (width * 0.15),
                (int) (heightFrame * 0.26 + boardSize / 2), 25);

        player1.paint(g);
        player1Score.paint(g);
        player2.paint(g);
        player2Score.paint(g);
        player1bottom.paint(g);
        player2bottom.paint(g);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        boardPanel.setLocation(boardX, boardY);
        topLeftPanel.setBounds(topLeftX, topLeftY, colorPanelSize.width, colorPanelSize.height);
        bottomLeftPanel.setBounds(bottomLeftX, bottomLeftY, colorPanelSize.width, colorPanelSize.height);
        topRightPanel.setBounds(topRightX, topRightY, colorPanelSize.width, colorPanelSize.height);
        bottomRightPanel.setBounds(bottomRightX, bottomRightY, colorPanelSize.width, colorPanelSize.height);

        // Sizes
        height = frame.getHeight();
        width = frame.getWidth();
        int iconSize = undo.getCurrentImageWidth() / 2;

        g.drawImage(this.undo.getCurrentImage(), (int) (width * 0.55) - iconSize, (int) (height * 0.85), this);
        g.drawImage(this.redo.getCurrentImage(), (int) (width * 0.45) - iconSize, (int) (height * 0.85), this);
        g.drawImage(this.menu.getCurrentImage(), (int) (width * 0.91), (int) (height * 0.08), frame);
        g.drawImage(this.hint.getCurrentImage(), (int) (width * 0.05), (int) (height * 0.08), frame);

        // Background
        backGround.drawImg(g, 0, 0, width, height);
        logo.drawImg(g, (int) (width * 0.425), 0, (int) (width * 0.15), (int) (height * 0.15));

        // Players
        playerTurn(g);

        // Strings
        drawStrings(g);
        checkEndGame(g);
        try {
            drawMenu(g);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}