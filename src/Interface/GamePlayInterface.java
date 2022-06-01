package Interface;

import Controller.ControllerGamePlay;
import GamePanels.BoardPanel;
import GamePanels.ColorPanel;
import Players.Player;
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
    public HoverButton menu, hint, undo, redo, save;

    DrawString currentPlayer, player1, player2, player1Score, player2Score, player1bottom, player2bottom, errorMessage;
    String text;
    public int boardSize, tileSize, widthFrame, heightFrame;
    int topLeftX, topLeftY, topRightX, topRightY, bottomLeftX, bottomLeftY, bottomRightX, bottomRightY, boardX, boardY,
            menuX, menuY, hintX, hintY, redoX, redoY, undoX, undoY, saveX, saveY, arrowSize, p1Score = 0, p2Score = 0;
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
        frame.setResizable(false);
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
        this.addMouseListener(new GameMouseAdapter(this, menu,hint,redo,undo,save));

    }

    public GamePlayInterface(JFrame f, ControllerGamePlay c, Game2P g2p) throws IOException {
        controller = c;
        controller.gamePlayInterface = this;
        frame = f;
        frame.setResizable(false);
        this.g2p = g2p;
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
        this.addMouseListener(new GameMouseAdapter(this, menu,hint,redo,undo,save));
        // Begins the game
        controller.startGame();
    }


    public GamePlayInterface(JFrame f, ControllerGamePlay c) throws IOException {
        controller = c;
        controller.gamePlayInterface = this;
        frame = f;
        frame.setResizable(false);
        g2p = (Game2P) controller.game;
        gs2p = g2p.gs2p;
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
        this.addMouseListener(new GameMouseAdapter(this, menu,hint,redo,undo,save));
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
    void setSize() {
        // Frame
        widthFrame = frame.getWidth();
        heightFrame = frame.getHeight();

        // Board Size
        boardSize = (int) (Math.min(heightFrame, widthFrame) * 0.62);
        tileSize = boardSize / 20;
        boardSize = tileSize * 20;
        boardX = (widthFrame - boardSize) / 2;
        boardY = (int) (heightFrame * 0.2);


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

        // Button Positions
        arrowSize = (int) (heightFrame * 0.055);
        menuX = (int) (widthFrame * 0.91);
        menuY = (int) (heightFrame * 0.08);
        hintX = (int) (widthFrame * 0.05);
        hintY = (int) (heightFrame * 0.08);
        redoX = (int) (widthFrame * 0.5 + boardSize/4 - (3*arrowSize)/4);
        redoY = (int) (heightFrame * 0.86);
        undoX = (int) (widthFrame * 0.5 - boardSize /4);
        undoY = (int) (heightFrame * 0.86);
        saveX = (int) (widthFrame * 0.5 - ((boardSize * 0.15)/2));
        saveY = (int) (heightFrame * 0.86);

    }

    /**
     *  Set Bounds
     *      Sets the bounds of components in GamePlayInterface
     *      - useful for resizing
     * */
    private void setBound(){
        // Board
        boardPanel.setLocation(boardX, boardY);

        // Color Panels
        topLeftPanel.setBounds(topLeftX, topLeftY, colorPanelSize.width, colorPanelSize.height);
        bottomLeftPanel.setBounds(bottomLeftX, bottomLeftY, colorPanelSize.width, colorPanelSize.height);
        topRightPanel.setBounds(topRightX, topRightY, colorPanelSize.width, colorPanelSize.height);
        bottomRightPanel.setBounds(bottomRightX, bottomRightY, colorPanelSize.width, colorPanelSize.height);

        // Buttons
        menu.setBounds(menuX, menuY, (int) (heightFrame * 0.07), (int) (heightFrame * 0.07));
        hint.setBounds(hintX, hintY, (int) (heightFrame * 0.06), (int) (heightFrame * 0.06));
        redo.setBounds(redoX, redoY, arrowSize, arrowSize);
        undo.setBounds(undoX, undoY, arrowSize,arrowSize);
        save.setBounds(saveX, saveY, (int) (boardSize * 0.1), (int)(boardSize * 0.05));

        // Current Player
        setCurrentPlayer();
        currentPlayer.setFontSize((int) (Math.min(widthFrame * 0.02, heightFrame * 0.05)));
        // Player 1
        player1.setCoords( (topLeftX + colorPanelSize.width/3), (int) (heightFrame * 0.18));
        player1.setFontSize((int) (Math.min(widthFrame * 0.02, heightFrame * 0.05)));
        // Player 1 Score
        player1Score.setCoords(topLeftX + (int)(colorPanelSize.width * 0.8), (int) (heightFrame * 0.18));
        player1Score.setFontSize((int) (Math.min(widthFrame * 0.02, heightFrame * 0.05)));
        // Player 2
        player2.setCoords((topRightX + colorPanelSize.width/3), (int) (heightFrame * 0.18));
        player2.setFontSize((int) (Math.min(widthFrame * 0.02, heightFrame * 0.05)));
        // Player 2 Score
        player2Score.setCoords(topRightX - (int)(colorPanelSize.width * 0.2), (int) (heightFrame * 0.18));
        player2Score.setFontSize((int) (Math.min(widthFrame * 0.02, heightFrame * 0.05)));
        // Player 1 bottom
        player1bottom.setCoords((bottomRightX + colorPanelSize.width/3),
                (int) (bottomRightY - (heightFrame *0.015) ));
        player1bottom.setFontSize((int) (Math.min(widthFrame * 0.02, heightFrame * 0.05)));
        // Player 2 bottom
        player2bottom.setCoords((bottomLeftX + colorPanelSize.width/3),
                (int) (bottomLeftY - (heightFrame *0.015) ));
        player2bottom.setFontSize((int) (Math.min(widthFrame * 0.02, heightFrame * 0.05)));
        // Error Message
        setError();
        errorMessage.setFontSize((int) (Math.min(widthFrame * 0.02, heightFrame * 0.05)));
    }

    private void setCurrentPlayer(){
        currentPlayer.setStringSize();
        if(currentPlayer.stringSize == 19) // RED
            currentPlayer.setCoords((int) (widthFrame * 0.41), (int) (heightFrame * 0.18));
        if(currentPlayer.stringSize == 20) // BLUE
            currentPlayer.setCoords((int) (widthFrame * 0.405), (int) (heightFrame * 0.18));
        if(currentPlayer.stringSize == 21) // GREEN
            currentPlayer.setCoords((int) (widthFrame * 0.4), (int) (heightFrame * 0.18));
        if(currentPlayer.stringSize == 22) // YELLOW
            currentPlayer.setCoords((int) (widthFrame * 0.39), (int) (heightFrame * 0.18));
        if (currentPlayer.stringSize == 16) // END
            currentPlayer.setCoords((int) (widthFrame * 0.42), (int) (heightFrame * 0.18));
    }

    private void setError(){
        errorMessage.setStringSize();
        System.out.println("String size : " + errorMessage.stringSize);
        if (errorMessage.stringSize == 14 || errorMessage.stringSize == 15)
            errorMessage.setCoords((int) (widthFrame * 0.43), (int) (heightFrame * 0.85));
        else if (errorMessage.stringSize == 16 || errorMessage.stringSize == 17)
            errorMessage.setCoords((int) (widthFrame * 0.42), (int) (heightFrame * 0.85));
        else if (errorMessage.stringSize == 18 || errorMessage.stringSize == 19)
            errorMessage.setCoords((int) (widthFrame * 0.41), (int) (heightFrame * 0.85));
        else if (errorMessage.stringSize == 20)
            errorMessage.setCoords((int) (widthFrame * 0.405), (int) (heightFrame * 0.85));
        else if (errorMessage.stringSize == 21)
            errorMessage.setCoords((int) (widthFrame * 0.41), (int) (heightFrame * 0.85));
        else if (errorMessage.stringSize == 22)
            errorMessage.setCoords((int) (widthFrame * 0.40), (int) (heightFrame * 0.85));
        else if (errorMessage.stringSize == 23 || errorMessage.stringSize == 24)
            errorMessage.setCoords((int) (widthFrame * 0.4), (int) (heightFrame * 0.85));
        else if (errorMessage.stringSize == 25 || errorMessage.stringSize == 26)
            errorMessage.setCoords((int) (widthFrame * 0.38), (int) (heightFrame * 0.85));
        else if (errorMessage.stringSize == 27 || errorMessage.stringSize == 28 || errorMessage.stringSize == 29)
            errorMessage.setCoords((int) (widthFrame * 0.37), (int) (heightFrame * 0.85));
        else if (errorMessage.stringSize == 30 || errorMessage.stringSize == 31 || errorMessage.stringSize == 32)
            errorMessage.setCoords((int) (widthFrame * 0.36), (int) (heightFrame * 0.85));
        else if (errorMessage.stringSize == 33 || errorMessage.stringSize == 34)
            errorMessage.setCoords((int) (widthFrame * 0.35), (int) (heightFrame * 0.85));
        else if (errorMessage.stringSize == 35 || errorMessage.stringSize == 36)
            errorMessage.setCoords((int) (widthFrame * 0.345), (int) (heightFrame * 0.85));
        else if (errorMessage.stringSize == 37)
            errorMessage.setCoords((int) (widthFrame * 0.342), (int) (heightFrame * 0.85));
        else if (errorMessage.stringSize == 38)
            errorMessage.setCoords((int) (widthFrame * 0.34), (int) (heightFrame * 0.85));
        else if (errorMessage.stringSize == 39)
            errorMessage.setCoords((int) (widthFrame * 0.338), (int) (heightFrame * 0.85));
        else
            errorMessage.setCoords((widthFrame/2 - (controller.errorMessage.length()*11/2)), (int) (heightFrame * 0.85));
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

    public ColorPanel getColorPanel(){
        if (controller.game.getCurrentPlayer() == controller.game.getPlayerList().get(0))
            return topLeftPanel;
        if (controller.game.getCurrentPlayer() == controller.game.getPlayerList().get(1))
            return topRightPanel;
        if (controller.game.getCurrentPlayer() == controller.game.getPlayerList().get(2))
            return bottomRightPanel;
        if (controller.game.getCurrentPlayer() == controller.game.getPlayerList().get(3))
            return bottomLeftPanel;
        return null;
    }

    /**
     *   Initialise Buttons
     *       Calls functions to initialise the buttons on the screen
     *      - Menu: Opens menu for new game, continue, restart, tutorial, quit
     *      - Hints: Enables/disables hints for the player
     *      - Undo: Reverts the game back to the previous turn of the player
     *      - Redo: Replaces the undone turn
     *      - Save : Saves the game
     * */
    private void initialiseButtons() throws IOException {
        System.out.println("Size :" + widthFrame + " " + heightFrame);
        menu = new HoverButton("Menu", menuX, menuY);
        add(this.menu);
        hint = new HoverButton("Hints", hintX, hintY);
        add(this.hint);
        redo = new HoverButton("Clockwise", redoX,redoY);
        add(this.redo);
        undo = new HoverButton("CounterClockwise", undoX, undoY);
        add(this.undo);
        save = new HoverButton("Save", saveX, saveY);
        add(this.save);
    }

    /**
     *   Player Turn
     *       Draws the string above the board with the current player's turn
     *       written in the corresponding player's color
     * */
    public void playerTurn() {
        // Score of each player
        p1Score = ((Game2P) (controller.game)).p1.get2PScore();
        System.out.println("P1 Score : " + p1Score);
        p2Score = ((Game2P) (controller.game)).p2.get2PScore();
        System.out.println("P2 Score : " + p2Score);

        // If the current player is player 1
        if (g2p.currentPlayer2P == g2p.p1 && !controller.game.end) {
            text = "Player 1 " + controller.currentColor + "'s turn";
            System.out.println(text);
            currentPlayer = new DrawString(text, transformColor());
            // If the current player is player 2
        } else if (g2p.currentPlayer2P == g2p.p2 && !controller.game.end) {
            text = "Player 2 " + controller.currentColor + "'s turn";
            System.out.println(text);
            currentPlayer = new DrawString(text, transformColor());
        }
    }

    /**
     *  Error Message
     *      Initialises the error message string
     * */
    public void errorMessage(){
        errorMessage = new DrawString(controller.errorMessage);
    }

    /**
     *  Player Strings
     *      Initialises the player and score strings
     * */
    public void initialisePlayerStrings(){
        player1 = new DrawString("Player 1");
        player1Score = new DrawString("Score: " + p1Score);
        player2 = new DrawString("Player 2");
        player2Score = new DrawString("Score: " + p2Score);

        // Bottom
        player1bottom = new DrawString("Player 1");
        player2bottom = new DrawString("Player 2");
    }

    /**
     *   Check End Game
     *       If the game has ended, print end game message
     * */
    public void checkEndGame() {
        if (controller.game.end) {
            if (p2Score < p1Score)
                currentPlayer = new DrawString("PLAYER 1 WON !!!");
            else if (p2Score > p1Score)
                currentPlayer = new DrawString("PLAYER 2 WON !!!");
            else
                currentPlayer = new DrawString("PLAYERS TIED !!!");
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
        currentPlayer.paint(g);
        player1.paint(g);
        player1Score.paint(g);
        player2.paint(g);
        player2Score.paint(g);
        player1bottom.paint(g);
        player2bottom.paint(g);
        errorMessage.paint(g);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Get the current player
        playerTurn();
        checkEndGame();
        initialisePlayerStrings();

        // Message under board
        errorMessage();
        setBound();
        // Strings
        drawStrings(g);

        // Buttons
        g.drawImage(this.redo.getCurrentImage(), redoX, redoY, arrowSize,arrowSize,this);
        g.drawImage(this.undo.getCurrentImage(), undoX, undoY, arrowSize, arrowSize,this);
        g.drawImage(this.save.getCurrentImage(), saveX , saveY,this);
        g.drawImage(this.menu.getCurrentImage(), menuX, menuY, (int) (heightFrame * 0.06), (int) (heightFrame * 0.06),frame);
        g.drawImage(this.hint.getCurrentImage(), hintX, hintY, (int) (heightFrame * 0.07), (int) (heightFrame * 0.07),frame);

        // Background
        backGround.drawImg(g, 0, 0, widthFrame, heightFrame);
        logo.drawImg(g, (int) (widthFrame * 0.425), 0, (int) (widthFrame * 0.15), (int) (heightFrame * 0.15));

        // TODO : center the Menu in Game
        try {
            drawMenu();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}