package GamePanels;

import Controller.ControllerGamePlay;
import Controller.PlayerTurn;
import Interface.GamePlayInterface;
import Structures.Move;
import Structures.Tile;

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
        System.out.println("Board Mouse Adapter entered");
        if (e.getButton() == MouseEvent.BUTTON3){
            System.out.println("Right Clicked!");
            if (controller.piece != null){
                controller.command("vertical");
            } else{
                System.out.println("No piece Selected");
                controller.errorMessage = "No piece selected";
                gamePlayInterface.repaint();
            }
        } else if (e.getButton() == MouseEvent.BUTTON2){
            System.out.println("Middle Clicked!");
            if (controller.piece != null){
                controller.command("horizontal");
            } else{
                System.out.println("No piece Selected");
                controller.errorMessage = "No piece selected";
                gamePlayInterface.repaint();
            }
        } else {
            PlayerTurn playerTurn = this.controller.getCurrentPlayerTurn();
            if (playerTurn == null){
                // TODO : Define what to do on null player turn
            }

            System.out.println("Board tile " + tile.getName());
            String[] split = tile.getName().split(" ");
            controller.boardPanel.tileName = split;
            int x = Integer.parseInt(split[0]);
            int y = Integer.parseInt(split[1]);

            Move currentMove = new Move(this.controller.piece, new Tile(y, x));
            playerTurn.setMove(currentMove);
            playerTurn.game.getBoard().printBoard(-1);
        }
    }

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
        controller.tile = tile;
        if (controller.piece != null){
            paintImage();
        }
    }

    public void paintImage(){
        if (gamePlayInterface.boardPanel.orientated){
            System.out.println("Not Equal");
        }
        Image image = getImage();
        String[] split = tile.getName().split(" ");
        int x = Integer.parseInt(split[0]);
        int y = Integer.parseInt(split[1]);
        boolean [][] shape = controller.piece.getShape().shape;
        x -= controller.piece.getShape().anchorY ;
        y -= controller.piece.getShape().anchorX;
        if (x >=0 && x + controller.piece.getShape().Ncol - 1 <=19 && y >= 0 && y + controller.piece.getShape().Nlin - 1 <= 19){
            if (!controller.originalImages.isEmpty()){
                controller.originalImages.clear();
            }
            for (int i=0; i<controller.piece.getShape().Nlin; i++) {
                int temp = x;
                for (int j=0; j<controller.piece.getShape().Ncol; j++) {
                    originalImages.add(gamePlayInterface.boardPanel.labels.get(temp + " " + y).getIcon());
                    if (shape[i][j]) {
                        gamePlayInterface.boardPanel.labels.get(temp + " " + y).setIcon(new ImageIcon(image));
                        gamePlayInterface.boardPanel.labels.get(temp + " " + y).repaint();
                    }
                    temp++;
                }
                y++;
            }
            gamePlayInterface.boardPanel.originalImages = originalImages;
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
            replaceOriginal();
        }
        originalImages.clear();
        clicked = false;

    }

    public void replaceOriginal(){
        if (gamePlayInterface.boardPanel.orientated){
            if (!controller.originalImages.isEmpty())
            //System.out.println("Not Equal");
                originalImages = controller.originalImages;
            gamePlayInterface.boardPanel.orientated = false;
        }
        String name =  tile.getName();
        String[] split = name.split(" ");
        int x = Integer.parseInt(split[0]);
        int y = Integer.parseInt(split[1]);
        x -= controller.piece.getShape().anchorY ;
        y -= controller.piece.getShape().anchorX;
        if (x >=0 && x + controller.piece.getShape().Ncol - 1 <=19 && y >= 0 && y + controller.piece.getShape().Nlin - 1 <= 19){
            for (int i=0; i<controller.piece.getShape().Nlin; i++) {
                int temp = x;
                for (int j=0; j<controller.piece.getShape().Ncol; j++) {
                    gamePlayInterface.boardPanel.labels.get(temp + " " + y).setIcon(originalImages.get(0));
                    originalImages.remove(0);
                    gamePlayInterface.boardPanel.labels.get(temp + " " + y).repaint();
                    temp++;
                }
                y++;
            }
            gamePlayInterface.boardPanel.originalImages = originalImages;
        }
    }
}
