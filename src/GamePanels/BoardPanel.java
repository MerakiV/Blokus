package GamePanels;

import Controller.ControllerGamePlay;
import Interface.GamePlayInterface;
import Interface.WheelEvent;
import Structures.Board;
import Structures.Color;
import Structures.Game2P;
import Structures.Tile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;

/**
 *  Board Panel
 *   - Shows the board and all proper modifications as the game is being played
 * */
public class BoardPanel extends JPanel{
    public JFrame frame;
    public GamePlayInterface gamePlayInterface;
    ControllerGamePlay controller;
    JLabel label;

    int widthFrame, heightFrame, tileSize;
    public int boardSize;
    Dimension size;
    public Hashtable<String, JLabel> labels;
    public String[] tileName;
    public ArrayList<Icon> originalImages;

    public HashSet<Tile> tiles;

    public boolean orientated = false, removeHint = false, isHint = false;

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
        this.addMouseWheelListener(new WheelEvent(controller));
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
        if (controller.hintsActivated && isHint){
            //System.out.println("Hint activated and is hint");
            if (removeHint)
                return "tiles/boardTile.png";
            switch(controller.currentColor){
                case RED:
                    return "tiles/RedHighlight.png";
                case YELLOW:
                    return "tiles/YellowHighlight.png";
                case GREEN:
                    return "tiles/GreenHighlight.png";
                case BLUE:
                    return "tiles/BlueHighlight.png";
            }
        } else if (controller.hintsActivated && !isHint){
            //System.out.println("Not Hint");
            switch(controller.game.getCurrentColor()) {
                case RED:
                    return "tiles/RedBloc.png";
                case YELLOW:
                    return "tiles/YellowBloc.png";
                case GREEN:
                    return "tiles/GreenBloc.png";
                case BLUE:
                    return "tiles/BlueBloc.png";
            }
        }
        //System.out.println("Not Hint");
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

    public void showPositions(){
        isHint = true;
        if (controller.hoveredPiece != null){
            tiles = controller.game.getBoard().fullcheck(controller.hoveredPiece, controller.currentColor);
            for (Tile tile : tiles){
                labels.get(tile.getY() + " " + tile.getX()).setIcon(new ImageIcon(getImage(tile.getY(), tile.getX())));
            }
        }
    }

    public void removePositions(){
        removeHint = true;
        //System.out.println("Hovered Piece : " + controller.hoveredPiece.getName().name());
        if (controller.hoveredPiece != null){
            //System.out.println("Remove Positions");
            for (Tile tile : tiles){
                isHint = true;
                if (tileName != null){
                    if (tile.getY() == Integer.parseInt(tileName[0]) && tile.getX() == Integer.parseInt(tileName[1])){
                        isHint = false;
                    }
                }
                System.out.println(tile.getY() + " " + tile.getX());
                labels.get(tile.getY() + " " + tile.getX()).setIcon(new ImageIcon(getImage(tile.getY(), tile.getX())));
            }
        }
        isHint = false;
        removeHint = false;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
