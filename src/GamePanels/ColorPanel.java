package GamePanels;

import Controller.ControllerGamePlay;
import Interface.GameMouseAdapter;
import Interface.GamePlayInterface;
import Players.Player;
import Structures.Color;
import Structures.Piece;
import Structures.PieceReader;
import Structures.PieceType;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Objects;

public class ColorPanel extends JPanel{
    public JFrame frame;
    GamePlayInterface gamePlayInterface;
    BufferedImage colorPanelImage;

    int widthFrame, heightFrame, boardSize;
    Dimension size;
    public Dimension piecePanelSize;

    // Color Panel Variables
    public Color color;
    Player player;

    ControllerGamePlay controller;

    public ColorPanel(GamePlayInterface g, ControllerGamePlay c, Player p) throws IOException {
        gamePlayInterface = g;
        controller = c;
        player = p;
        frame = g.frame;
        initialiseColorPanel();
        getColorSize();
        this.setLayout(new GridLayout(4,6));
        this.setBorder(BorderFactory.createLineBorder(java.awt.Color.black));
        this.addMouseListener(new ColorMouseAdapter(gamePlayInterface));
        getPieces();
    }

    private void initialiseColorPanel() throws IOException {
        color = player.getColor();
        colorPanelImage = ImageIO.read(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResourceAsStream(getPath())));
    }

    public String getPath(){
        switch(color){
            case BLUE:
                return "images/blueBackground.png";
            case RED:
                return "images/redBackground.png";
            case GREEN:
                return "images/greenBackground.png";
            case YELLOW:
                return "images/yellowBackground.png";
        }
        return null;
    }

    public void getColorSize() {
        widthFrame = gamePlayInterface.frame.getWidth();
        heightFrame = gamePlayInterface.frame.getHeight();
        boardSize = gamePlayInterface.boardSize;
        size= gamePlayInterface.colorPanelSize;
        piecePanelSize = new Dimension(Math.min(size.height/4, size.width/6), Math.min(size.height/4, size.width/6) );
    }

    private void getPieces(){
        for (int i = 0; i < controller.pieces.size(); i++){
            PiecePanel piece = new PiecePanel(this, controller.pieces.get(i));
            piece.setPreferredSize(piecePanelSize);
            this.add(piece);
            piece.setBorder(BorderFactory.createLineBorder(java.awt.Color.black));
            piece.addMouseListener(new PieceMouseAdapter(controller));
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(colorPanelImage, 0, 0, size.width , size.height, this);
    }
}
