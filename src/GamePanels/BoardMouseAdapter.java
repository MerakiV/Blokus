package GamePanels;

import Controller.ControllerGamePlay;
import Interface.GamePlayInterface;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;

public class BoardMouseAdapter implements MouseListener {
    ControllerGamePlay controller;
    GamePlayInterface gamePlayInterface;
    JLabel tile;
    int tileSize;
    ArrayList<Icon> originalImages;
    boolean clicked = false;

    public BoardMouseAdapter (ControllerGamePlay c, GamePlayInterface g, JLabel label){
        gamePlayInterface = g;
        controller = c;
        tile = label;
        tileSize = label.getHeight();
        originalImages = new ArrayList<>();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Board tile " + tile.getName());

        String[] split = tile.getName().split(" ");
        int x = Integer.parseInt(split[0]);
        int y = Integer.parseInt(split[1]);
        clicked = controller.put(y,x);
        gamePlayInterface.repaint();
    }

    // TODO : change the color values to match corners
    public int getColor(){
        switch(controller.color){
            case WHITE:
                return -1;
            case BLUE:
                return 2;
            case RED:
                return 3;
            case GREEN:
                return 1;
            case YELLOW:
                return 0;
        }
        return -1;
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (controller.piece != null){
            Image image = getImage();
            String[] split = tile.getName().split(" ");
            int x = Integer.parseInt(split[0]);
            int y = Integer.parseInt(split[1]);
            boolean [][] shape = controller.piece.getShape().shape;
            x -= controller.piece.getShape().anchorY ;
            y -= controller.piece.getShape().anchorX;
            if (x >=0 && x + controller.piece.getShape().Ncol - 1 <=19 && y >= 0 && y + controller.piece.getShape().Nlin - 1 <= 19){
                for (int i=0; i<controller.piece.getShape().Nlin; i++) {
                    int temp = x;
                    for (int j=0; j<controller.piece.getShape().Ncol; j++) {
                        originalImages.add(gamePlayInterface.boardPanel.labels.get(temp + " " + y).getIcon());
                        if (shape[i][j]) {
                            gamePlayInterface.boardPanel.labels.get(temp + " " + y).setIcon(new ImageIcon(image));
                        }
                        temp++;
                    }
                    y++;
                }
            }
        }
    }

    private Image getImage() {
        BufferedImage img;
        Image resizedImage = null;
        try {
            InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(getPath());
            assert in != null;
            img = ImageIO.read(in);
            resizedImage = img.getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH);
        } catch (Exception e) {
            System.out.println("erreur dans le chargement des images:" + e);
        }
        return resizedImage;
    }

    private String getPath(){
        switch(controller.color){
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
    public void mouseExited(MouseEvent e) {
        if (controller.piece != null && !clicked){
            Image image = getImage();
            String name =  tile.getName();
            String[] split = name.split(" ");
            int x = Integer.parseInt(split[0]);
            int y = Integer.parseInt(split[1]);
            boolean [][] shape = controller.piece.getShape().shape;
            x -= controller.piece.getShape().anchorY ;
            y -= controller.piece.getShape().anchorX;
            if (x >=0 && x + controller.piece.getShape().Ncol - 1 <=19 && y >= 0 && y + controller.piece.getShape().Nlin - 1 <= 19){
                int num = 0;
                for (int i=0; i<controller.piece.getShape().Nlin; i++) {
                    int temp = x;
                    for (int j=0; j<controller.piece.getShape().Ncol; j++) {
                        gamePlayInterface.boardPanel.labels.get(temp + " " + y).setIcon(originalImages.get(num));
                        num++;
                        temp++;
                    }
                    y++;
                }
            }
        }
        originalImages.clear();
        clicked = false;

    }
}
