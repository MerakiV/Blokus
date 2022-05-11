package Interface;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class NGMouseAdapter implements MouseListener {

    HoverButton current;
    GameSelection game;

    NGMouseAdapter(HoverButton b, GameSelection g) {
        current = b;
        game = g;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Name:"+current.name);
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