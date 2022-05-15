package Interface;

import GamePanels.BoardPanel;
import GamePanels.ColorPanel;
import Players.Player;
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

    int widthFrame;
    int heightFrame;
    public int boardSize;
    int tileSize;
    int height;
    int width;
    Dimension size;
    public Dimension colorPanelSize;

    // Game Variables
    Game game;
    GameSettings2P gameSettings2P;

    // Board Variables
    public Board board;
    public BoardPanel boardPanel;

    // Player List
    List<Player> players;
    public Player currentPlayer;

    // Color Panels
    ColorPanel topLeftPanel, bottomLeftPanel, topRightPanel, bottomRightPanel;

    public GamePlayInterface(JFrame f) throws IOException {
        frame = f;
        setSize();
        initialiseGame();
        this.setLayout(new FlowLayout());
        boardPanel = new BoardPanel(this);
        this.add(boardPanel);
        initialiseColorPanels();
        initialiseButtons();
        backGround = new Image(frame, "images/border.png");
        logo = new Image(frame, "images/logo.png");
        System.out.println("Finished GamePlayInterface");
    }

    private void initialiseGame(){
        // Game Settings + Create Game
        setUpGameSettings();

        // Players
        players = game.getPlayerList();
        currentPlayer = game.getCurrentPlayer();

        // Board
        board = game.getBoard();
        // TODO : remove these test lines once implemented actual game
        board.addColor(3,3);
        board.addColor(19,18);
        System.out.println("Finished InitialiseGame");
    }

    // TODO : to change once game settings completed
    public void setUpGameSettings(){
        gameSettings2P = new GameSettings2P();
        gameSettings2P.setP1Color1(Structures.Color.YELLOW);
        gameSettings2P.setP1Color2(Structures.Color.RED);
        gameSettings2P.setP2Color1(Structures.Color.GREEN);
        gameSettings2P.setP2Color2(Structures.Color.BLUE);
        System.out.println("Finished Color");
        game = new Game2P(gameSettings2P);
        System.out.println("Finished SetUpGameSettings");
    }

    private void setSize(){
        widthFrame = frame.getWidth();
        heightFrame = frame.getHeight();
        boardSize = (int) (Math.min(heightFrame, widthFrame) * 0.63);
        size = new Dimension(boardSize, boardSize);
        tileSize = boardSize/20;
        colorPanelSize = new Dimension((int) (boardSize * 0.72), boardSize / 2); // Changes the size of components in the colorPanel
    }

    private void initialiseColorPanels() throws IOException {
        topLeftPanel = new ColorPanel(this, players.get(0));
        topLeftPanel.setPreferredSize(colorPanelSize);
        this.add(topLeftPanel);
        bottomLeftPanel = new ColorPanel(this, players.get(2));
        bottomLeftPanel.setPreferredSize(colorPanelSize);
        this.add(bottomLeftPanel);
        topRightPanel = new ColorPanel(this, players.get(1));
        topRightPanel.setPreferredSize(colorPanelSize);
        this.add(topRightPanel);
        bottomRightPanel = new ColorPanel(this, players.get(3));
        bottomRightPanel.setPreferredSize(colorPanelSize);
        this.add(bottomRightPanel);
    }

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
        boardPanel.setBounds((widthFrame - size.width) / 2, (int) (heightFrame * 0.2), size.width, size.height);
        topLeftPanel.setBounds((int) (widthFrame * 0.06) - 10, (int) (heightFrame * 0.2), (int) (boardSize * 0.72), boardSize / 2);
        bottomLeftPanel.setBounds((int) (widthFrame * 0.06) - 10, (int) (int)(heightFrame * 0.2 + boardSize/2), (int) (boardSize * 0.72), boardSize / 2);
        topRightPanel.setBounds((int)(0.94 * widthFrame - boardSize * 0.72) - 10, (int) (heightFrame * 0.2), (int) (boardSize * 0.72), boardSize / 2);
        bottomRightPanel.setBounds((int)(0.94 * widthFrame - boardSize * 0.72) - 10, (int)(heightFrame * 0.2 + boardSize/2), (int) (boardSize * 0.72), boardSize / 2);
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
