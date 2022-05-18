package GamePanels;

import Interface.GamePlayInterface;
import Structures.Board;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.Hashtable;

public class BoardPanel extends JPanel{
    public JFrame frame;
    GamePlayInterface gamePlayInterface;
    JLabel label;

    int widthFrame;
    int heightFrame;
    public int boardSize;
    int tileSize;
    Dimension size;

    // Board Variables
    Board board;

    Hashtable<String, JLabel> labels;

    public BoardPanel(GamePlayInterface g) {
        gamePlayInterface = g;
        frame = g.frame;
        initialiseBoard();
        setSize();
        this.setLayout(new GridLayout(20,20));
        this.setBounds(0, 0, tileSize*20,tileSize*20);
        labels = new Hashtable<>();
        //this.addMouseListener(new BoardMouseAdapter(gamePlayInterface));
        addBoardTiles();
    }

    private void initialiseBoard(){
        board = gamePlayInterface.board;
    }

    private void addBoardTiles(){
        for (int y = 0; y < 20 ; y++){
            for (int x = 0; x < 20 ; x++){
                Image resizedImage = getImage(y,x);
                ImageIcon iconImage = new ImageIcon(resizedImage);
                label = new JLabel(iconImage);
                label.setSize(tileSize,tileSize);
                label.setName(x + " " + y);
                label.addMouseListener(new BoardMouseAdapter(gamePlayInterface, label));
                add(label);
                labels.put(label.getName(), label);
            }
        }
    }

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

    private String getPath(int i, int j){
        //System.out.println(board.getColor(i,j));
        switch(board.getColor(i,j)){
            case RED:
                return "tiles/RedBloc.png";
            case YELLOW:
                return "tiles/YellowBloc.png";
            case GREEN:
                return "tiles/GreenBloc.png";
            case BLUE:
                return "tiles/BlueBloc.png";
            case WHITE:
                return "tiles/boardTile.png";
        }
        return null;
    }

    private void setSize(){
        widthFrame = frame.getWidth();
        heightFrame = frame.getHeight();
        boardSize = (int) (Math.min(heightFrame, widthFrame) * 0.6);
        size = new Dimension(boardSize, boardSize);
        tileSize = boardSize/20;
        boardSize = tileSize*20;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
