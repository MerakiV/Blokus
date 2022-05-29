package GamePanels;

import Controller.ControllerGamePlay;
import Interface.GamePlayInterface;
import Structures.Board;
import Structures.Color;
import Structures.Game2P;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 *  Board Panel
 *   - Shows the board and all proper modifications as the game is being played
 * */
public class BoardPanel extends JPanel{
    public JFrame frame;
    GamePlayInterface gamePlayInterface;
    ControllerGamePlay controller;
    JLabel label;

    int widthFrame, heightFrame, tileSize;
    public int boardSize;
    Dimension size;
    public Hashtable<String, JLabel> labels;
    public ArrayList<Icon> originalImages;

    public boolean orientated = false;

    public BoardPanel(GamePlayInterface g, ControllerGamePlay c) {
        controller = c;
        gamePlayInterface = g;
        frame = g.frame;
        setSize();
        this.setLayout(new GridLayout(20,20));
        this.setBounds(0, 0, tileSize*20,tileSize*20);
        labels = new Hashtable<>();
        originalImages = new ArrayList<>();
        addBoardTiles();
        controller.setBoardPanel(this);
        // System.out.println("Board created");
    }

    /**
     *  Add Board Tiles
     *      Creates and stores JLabels for each of the tiles on the board
     *      Named using the position of the tile on the board
     * */
    private void addBoardTiles(){
        for (int y = 0; y < 20 ; y++){
            for (int x = 0; x < 20 ; x++){
                Image resizedImage = getImage(y,x);
                ImageIcon iconImage = new ImageIcon(resizedImage);
                label = new JLabel(iconImage);
                label.setSize(tileSize,tileSize);
                label.setName(x + " " + y);
                label.addMouseListener(new BoardMouseAdapter(controller, gamePlayInterface, label));
                add(label);
                labels.put(label.getName(), label);
            }
        }
    }

    /**
     *  Get Image
     *      Input : line, column
     *      Returns the corresponding image of the tile at the position given in input
     **/
    private Image getImage(int i, int j) {
        BufferedImage img;
        Image resizedImage = null;
        try {
            InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(getPath(i,j));
            assert in != null;
            img = ImageIO.read(in);
            resizedImage = img.getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH);
        } catch (Exception e) {
            System.out.println("erreur dans le chargement des images:" + e);
        }
        return resizedImage;
    }

    /**
     *  Get Path
     *      Input : line, column
     *      Returns the path to the corresponding image in resources
     * */
    private String getPath(int i, int j){
        //System.out.println(board.getColor(i,j));
        switch(controller.game.getBoard().getColor(i,j)){
            case RED:
                return "tiles/RedBloc.png";
            case YELLOW:
                return "tiles/YellowBloc.png";
            case GREEN:
                return "tiles/GreenBloc.png";
            case BLUE:
                return "tiles/BlueBloc.png";
            case WHITE:
                Game2P g2p = (Game2P) controller.game;
                Color p1c1 = g2p.gs2p.p1c1;
                Color p1c2 = g2p.gs2p.p1c2;
                Color p2c1 = g2p.gs2p.p2c1;
                Color p2c2 = g2p.gs2p.p2c2;
                if(i == 0 && j == 0){
                    return "tiles/boardTile"+p1c1+"TopLeft.png";
                }else if(i == 0 && j == 19){
                    return "tiles/boardTile"+p2c1+"TopRight.png";
                }else if(i == 19 && j == 0){
                    return "tiles/boardTile"+p2c2+"BotLeft.png";
                }else if(i == 19 && j == 19){
                    return "tiles/boardTile"+p1c2+"BotRight.png";
                }
                else {
                    return "tiles/boardTile.png";
                }
        }
        return null;
    }

    /**
     *  Set Size
     *      Sets the size of components
     * */
    private void setSize(){
        widthFrame = frame.getWidth();
        heightFrame = frame.getHeight();
        boardSize = (int) (Math.min(heightFrame, widthFrame) * 0.62);
        size = new Dimension(boardSize, boardSize);
        tileSize = boardSize/20;
        boardSize = tileSize*20;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
