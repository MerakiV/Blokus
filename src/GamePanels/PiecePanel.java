package GamePanels;

import Structures.Piece;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;

public class PiecePanel extends JPanel {

    ColorPanel colorPanel;
    Piece piece;

    int pieceSize, pieceWidth,pieceHeight;
    Dimension tileSize;
    Image resizedImage;

    final static boolean shouldFill = true;
    GridBagConstraints gridBagConstraints;

    public PiecePanel(ColorPanel c, Piece p){
        colorPanel = c;
        piece = p;
        setTileSize();
        this.setLayout(new GridBagLayout());
        gridBagConstraints = new GridBagConstraints();
        if (shouldFill){
            gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        }
        addPieceTiles();
    }

    private void addPieceTiles(){
        //piece.printPiece();
        for (int i = 0; i < pieceWidth; i++){
            for (int j = 0; j < pieceHeight; j++){
                if (! piece.getShape().isEmpty(i,j)){
                    resizedImage = getImage();
                    JLabel label = new JLabel(new ImageIcon(resizedImage));
                    label.setPreferredSize(tileSize);
                    gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
                    gridBagConstraints.gridx = j;
                    gridBagConstraints.gridy = i;
                    add(label, gridBagConstraints);
                } else{
                    JLabel label = new JLabel();
                    add(label);
                }
            }
        }
    }

    public void setTileSize() {
        pieceWidth = piece.getShape().Nlin;
        pieceHeight = piece.getShape().Ncol;
        pieceSize = colorPanel.piecePanelSize.height;
        tileSize = new Dimension(pieceSize/5, pieceSize/5);
    }

    private String getPath(){
        switch(colorPanel.player.getColor()){
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

    private Image getImage() {
        BufferedImage img;
        Image resizedImage = null;
        try {
            InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(getPath());
            assert in != null;
            img = ImageIO.read(in);
            resizedImage = img.getScaledInstance(tileSize.width, tileSize.height, Image.SCALE_SMOOTH);
        } catch (Exception e) {
            System.out.println("erreur dans le chargement des images:" + e);
        }
        return resizedImage;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(colorPanel.colorPanelImage, 0, 0, this.getWidth() , this.getHeight(), this);
    }
}
