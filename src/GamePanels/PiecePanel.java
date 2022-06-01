package GamePanels;

import Structures.Color;
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

    public boolean isClicked = false;

    public PiecePanel(ColorPanel c, Piece p){
        this.colorPanel = c;
        this.piece = colorPanel.player.getPiece(p);
        setTileSize();
        this.setName(piece.getName().name() + " " + colorPanel.color);
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
                    String path = tilePath();
                    resizedImage = getImage(path);
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

    String tilePath(){
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


    public void setTileSize() {
        pieceWidth = piece.getShape().Nlin;
        pieceHeight = piece.getShape().Ncol;
        pieceSize = colorPanel.piecePanelSize.height;
        tileSize = new Dimension(pieceSize/5, pieceSize/5);
    }

    private String getPath(){
        int col = colorPanel.controller.game.getBoard().getCorner(colorPanel.color);
        piece = colorPanel.controller.game.getPlayerList().get(col).getPiece(piece);
        if (colorPanel.controller.game.getPlayerList().get(col).getPieces().contains(piece)){
            //System.out.println("Piece in list : " + this.getName());
            String path = null;
            switch(colorPanel.player.getColor()){
                case RED:
                    path = "tiles/RedBloc.png";
                    break;
                case YELLOW:
                    path = "tiles/YellowBloc.png";
                    break;
                case GREEN:
                    path = "tiles/GreenBloc.png";
                    break;
                case BLUE:
                    path = "tiles/BlueBloc.png";
                    break;
                case WHITE:
                    path = "tiles/boardTile.png";
                    break;
            }
            if (colorPanel.controller.game.getBoard().sumPiecePlacements(piece, colorPanel.color) == 0){
                return "tiles/GreyBloc.png";
            }
            return path;
        } else {
            System.out.println("Piece not in list : " + this.getName() + " " + piece);
            switch(colorPanel.player.getColor()){
                case RED:
                    return "tiles/RedHighlight.png";
                case YELLOW:
                    return "tiles/YellowHighlight.png";
                case GREEN:
                    return "tiles/GreenHighlight.png";
                case BLUE:
                    return "tiles/BlueHighlight.png";
                case WHITE:
                    return "tiles/boardTile.png";
            }
        }
        return "tiles/GreyBloc.png";
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

    private Image getImage(String path) {
        BufferedImage img;
        Image resizedImage = null;
        try {
            InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(path);
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
        //System.out.println("Repainting : " + this.getName());
        Image image = getImage();
        g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);

        int col = colorPanel.controller.game.getBoard().getCorner(colorPanel.color);
        if (!isClicked && colorPanel.controller.game.getPlayerList().get(col).getPieces().contains(piece) &&colorPanel.controller.game.getBoard().sumPiecePlacements(piece, colorPanel.color) != 0) {
            g.drawImage(colorPanel.colorPanelImage, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }
}
