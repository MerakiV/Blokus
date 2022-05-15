package Interface;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class GameMouseAdapter implements MouseListener {

    HoverButton current;
    GamePlayInterface game;
    PauseMenu pause;

    GameMouseAdapter(HoverButton b, GamePlayInterface g) {
        current = b;
        game = g;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Name:"+current.name);
        if (current.name == "Menu"){
            game.frame.getContentPane().remove(game);
            try {
                pause = new PauseMenu(game.frame);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            game.frame.getContentPane().add(pause, BorderLayout.CENTER);
            game.frame.getContentPane().invalidate();
            game.frame.getContentPane().validate();
        }

    }

    @Override
    public void mousePressed(MouseEvent e){

    }
    @Override
    public void mouseReleased(MouseEvent e) {
        current.currentImage = current.rolloverImage;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        current.currentImage = current.rolloverImage;
        game.repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        current.currentImage = current.normalImage;
        game.repaint();
    }


}