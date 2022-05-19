package GamePanels;

import Structures.Board;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;

public class BoardTilePanel extends JPanel {

    BoardPanel boardPanel;
    int tileSize;
    Image resizedImage;
    public BoardTilePanel(BoardPanel b, int i, int j){
        boardPanel = b;
        tileSize = boardPanel.tileSize;
        resizedImage = getImage(i,j);
        this.setSize(tileSize, tileSize);
    }

    private Image getImage(int i, int j) {
        BufferedImage img = null;
        try {
            InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(getPath(i,j));
            assert in != null;
            img = ImageIO.read(in);
        } catch (Exception e) {
            System.out.println("erreur dans le chargement des images:" + e);
        }
        return img;
    }

    private String getPath(int i, int j){
        //System.out.println(board.getColor(i,j));
        switch(boardPanel.controller.game.getBoard().getColor(i,j)){
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

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(resizedImage, 0,0, tileSize, tileSize, null);
    }
}
