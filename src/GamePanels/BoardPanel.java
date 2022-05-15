package GamePanels;

import Interface.GamePlayInterface;
import Structures.Board;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;

public class BoardPanel extends JPanel{
    public JFrame frame;
    GamePlayInterface gamePlayInterface;
    JLabel label;

    int widthFrame, heightFrame, boardSize, tileSize;
    Dimension size;

    // Board Variables
    Board board;

    public BoardPanel(GamePlayInterface g) {
        gamePlayInterface = g;
        frame = g.frame;
        initialiseBoard();
        setSize();
        this.setLayout(new GridLayout(20,20));
        addBoardTiles();
    }

    private void initialiseBoard(){
        board = gamePlayInterface.board;
    }

    private void addBoardTiles(){
        for (int i = 0; i < 20 ; i++){
            for (int j = 0; j < 20 ; j++){
                Image resizedImage = getImage(i,j);
                label = new JLabel(new ImageIcon(resizedImage));
                label.setSize(tileSize,tileSize);
                add(label);
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
            //System.out.println("chargement des images ok");
            //fin chargement des images.
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
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBounds(0, 0, size.width, size.height);
    }
}
