package Interface;

import Controller.ControllerGamePlay;
import GamePanels.BoardPanel;
import GamePanels.ColorPanel;
import Structures.Game2P;
import Structures.GameSettings2P;

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
    public Boolean play = false, existMenu = false;

    // Color Panels
    ColorPanel topLeftPanel, bottomLeftPanel, topRightPanel, bottomRightPanel;

    // Controller
    ControllerGamePlay controller;
    public Game2P g2p;
    public GameSettings2P gs2p;

    public GamePlayInterface(JFrame f, ControllerGamePlay c, GameSettings2P sp) throws IOException {
        controller = c;
        controller.gamePlayInterface = this;
        frame = f;
        g2p = (Game2P) controller.game;
        gs2p = sp;
        setSize();
        this.setLayout(new FlowLayout());
        // Images
        backGround = new Image(frame, "images/border.png");
        backGroundImg = new Image(frame, "images/whiteBackground.png");
        logo = new Image(frame, "images/logo.png");
        // Initialise Panels
        initialiseBoardPanel();
        initialiseColorPanels();
        initialiseButtons();
        // Add Listeners for Buttons
        this.addMouseListener(new GameMouseAdapter(this, menu,hint,redo,undo));
        // Begins the game
        controller.startGame();
    }

    public GamePlayInterface(JFrame f, ControllerGamePlay c) throws IOException {
        controller = c;
        controller.gamePlayInterface = this;
        frame = f;
        g2p = (Game2P) controller.game;
        setSize();
        this.setLayout(new FlowLayout());
        // Images
        backGround = new Image(frame, "images/border.png");
        backGroundImg = new Image(frame, "images/whiteBackground.png");
        logo = new Image(frame, "images/logo.png");
        // Initialise Panels
        initialiseBoardPanel();
        initialiseColorPanels();
        initialiseButtons();
        // Add Listeners for Buttons
        this.addMouseListener(new GameMouseAdapter(this, menu,hint,redo,undo));
        // Begins the game
        controller.startGame();
    }

    /**
    *   Initialise Board Panel
    *       Calls functions to initialise the board on the screen
    * */
    public void initialiseBoardPanel(){
        boardPanel = new BoardPanel(this, controller);
        controller.setBoardPanel(boardPanel);
        this.add(boardPanel);
    }

    public void drawMenu() throws IOException {
        if (play && !existMenu){
            playMenu = new GamePlayMenu(frame, this);
            playMenu.setBackground(null);
            playMenu.setOpaque(false);
            existMenu = true;
            //backGroundImg.drawImg(g, 0, 0, frame.getWidth(), frame.getHeight());
            this.add(playMenu,0);
        }
    }


    public void removeMenu(GamePlayMenu gM){
        play = false;
        existMenu = false;
        this.remove(gM);
        this.frame.revalidate();
        this.frame.repaint();
    }

    /**
     *  Set Size
     *      Sets the size of components
     * */
    private void setSize() {
        // Frame
        widthFrame = frame.getWidth();
        heightFrame = frame.getHeight();

        // Board Size
        boardSize = (int) (Math.min(heightFrame, widthFrame) * 0.62);
        size = new Dimension(boardSize, boardSize);
        boardX = (widthFrame - size.width) / 2;
        boardY = (int) (heightFrame * 0.2);
        tileSize = boardSize / 20;
        boardSize = tileSize * 20;

        // Color Panel Size
        colorPanelSize = new Dimension((int) (boardSize * 0.7), (int)(boardSize*0.45));

        // Color Panel Positions
        topLeftX = boardX - colorPanelSize.width - (int)(widthFrame * 0.02);
        topLeftY = (int) (heightFrame * 0.2);
        topRightX = boardX + tileSize*20 + (int)(widthFrame * 0.02);
        topRightY = (int) (heightFrame * 0.2);
        bottomLeftX = boardX - colorPanelSize.width - (int)(widthFrame * 0.02);
        bottomLeftY = boardY + boardSize - colorPanelSize.height;
        bottomRightX = boardX + tileSize*20 + (int)(widthFrame * 0.02);
        bottomRightY = boardY + boardSize - colorPanelSize.height;
    }

    /**
     *   Initialise Color Panels
     *       Calls functions to initialise the 4 color panels on the screen
     * */
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

    /**
     *   Initialise Buttons
     *       Calls functions to initialise the buttons on the screen
     *      - Menu: Opens menu for new game, continue, restart, tutorial, quit
     *      - Hints: Enables/disables hints for the player
     *      - Undo: Reverts the game back to the previous turn of the player
     *      - Redo: Replaces the undone turn
     * */
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

    /**
    *   Player Turn
    *       Draws the string above the board with the current player's turn
    *       written in the corresponding player's color
    * */
    public void playerTurn(Graphics g) {
        DrawString currentPlayer;
        // Score of each player
        p1Score = g2p.p1.get2PScore();
        //System.out.println("P1 Score : " + p1Score);
        p2Score = g2p.p2.get2PScore();
        //System.out.println("P2 Score : " + p2Score);

        // If the current player is player 1
        String text;
        if (g2p.currentPlayer2P == g2p.p1 && !controller.game.end) {
            text = "Player 1 " + controller.currentColor + "'s turn";
            currentPlayer = new DrawString(g, text, transformColor(),
                    (int) (width * 0.5 - (text.length()*12/2)), (int) (height * 0.18), 25);
            currentPlayer.paint(g);
        // If the current player is player 2
        } else if (g2p.currentPlayer2P == g2p.p2 && !controller.game.end) {
            text = "Player 2 " + controller.currentColor + "'s turn";
            currentPlayer = new DrawString(g, text, transformColor(),
                    (int) (width * 0.5 - (text.length()*12/2)), (int) (height * 0.18), 25);
            currentPlayer.paint(g);
        }
    }

    public void errorMessage(Graphics g){
        DrawString errorMessage = new DrawString(g, controller.errorMessage, (widthFrame/2 - (controller.errorMessage.length()*11/2)), (int) (height * 0.85), 25);
        errorMessage.paint(g);
    }


    /**
    *   Check End Game
    *       If the game has ended, print end game message
    * */
    public void checkEndGame(Graphics g) {
        if (controller.game.end) {
            DrawString currentPlayer;
            if (p2Score < p1Score)
                currentPlayer = new DrawString(g, "PLAYER 1 WON !!!", (int) (width * 0.45),
                        (int) (height * 0.18), 25);
            else if (p2Score > p1Score)
                currentPlayer = new DrawString(g, "PLAYER 2 WON !!!", (int) (width * 0.45),
                        (int) (height * 0.18), 25);
             else
                currentPlayer = new DrawString(g, "PLAYERS TIED !!!", (int) (width * 0.45),
                        (int) (height * 0.18), 25);
            currentPlayer.paint(g);
        }
    }

    /**
     * Transform Color
     *      takes the current color of the controller and returns the corresponding
     *      java.awt color for string color
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

    /**
     *  Draw Strings
     *      Function that groups all the strings to be painted on the panel
     */
    void drawStrings(Graphics g) {
        DrawString player1 = new DrawString(g, "Player 1", (topLeftX + colorPanelSize.width/2 - (8*11/2)), (int) (height * 0.18), 25);
        DrawString player1Score = new DrawString(g, "Score: " + p1Score, (int) (width * 0.3), (int) (height * 0.18),
                25);
        DrawString player2 = new DrawString(g, "Player 2", (topRightX + colorPanelSize.width/2 - (8*11/2)), (int) (height * 0.18),
                25);
        DrawString player2Score = new DrawString(g, "Score: " + p2Score, (int) (width * 0.62), (int) (height * 0.18),
                25);

        // Bottom
        DrawString player1bottom = new DrawString(g, "Player 1", (bottomRightX + colorPanelSize.width/2 - (8*11/2)),
                (int) (bottomRightY - (heightFrame *0.015) ), 25);
        DrawString player2bottom = new DrawString(g, "Player 2", (bottomLeftX + colorPanelSize.width/2 - (8*11/2)),
                (int) (bottomLeftY - (heightFrame *0.015) ), 25);

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

        // Color Panels
        topLeftPanel.setBounds(topLeftX, topLeftY, colorPanelSize.width, colorPanelSize.height);
        bottomLeftPanel.setBounds(bottomLeftX, bottomLeftY, colorPanelSize.width, colorPanelSize.height);
        topRightPanel.setBounds(topRightX, topRightY, colorPanelSize.width, colorPanelSize.height);
        bottomRightPanel.setBounds(bottomRightX, bottomRightY, colorPanelSize.width, colorPanelSize.height);

        // Buttons
        int iconSize = undo.getCurrentImageWidth() / 2;
        g.drawImage(this.undo.getCurrentImage(), (int) (widthFrame * 0.55) - iconSize, (int) (heightFrame * 0.85), this);
        g.drawImage(this.redo.getCurrentImage(), (int) (widthFrame * 0.45) - iconSize, (int) (heightFrame * 0.85), this);
        g.drawImage(this.menu.getCurrentImage(), (int) (widthFrame * 0.91), (int) (heightFrame * 0.08), frame);
        g.drawImage(this.hint.getCurrentImage(), (int) (widthFrame * 0.05), (int) (heightFrame * 0.08), frame);

        // Background
        backGround.drawImg(g, 0, 0, widthFrame, height);
        logo.drawImg(g, (int) (widthFrame * 0.425), 0, (int) (widthFrame * 0.15), (int) (heightFrame * 0.15));

        // Players
        playerTurn(g);

        // Message under board
        errorMessage(g);

        // Strings
        drawStrings(g);
        checkEndGame(g);
        try {
            drawMenu();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}