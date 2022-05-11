package Interface;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class NGMouseAdapter implements MouseListener {

    HoverButton current;
    GameSelection selectMenu;
    MenuInterface menu;
    GamePlay gamePlay;

    NGMouseAdapter(HoverButton b, GameSelection g) {
        current = b;
        selectMenu = g;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Name:" + current.name);
        if (current.name == "Back") {
            selectMenu.frame.getContentPane().remove(selectMenu);
            try {
                menu = new MenuInterface(selectMenu.frame);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            selectMenu.frame.getContentPane().add(menu, BorderLayout.CENTER);
            selectMenu.frame.getContentPane().invalidate();
            selectMenu.frame.getContentPane().validate();
        } else if (current.name == "Play") {
            selectMenu.frame.getContentPane().remove(selectMenu);
            try {
                gamePlay = new GamePlay(selectMenu.frame);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            selectMenu.frame.getContentPane().add(gamePlay, BorderLayout.CENTER);
            selectMenu.frame.getContentPane().invalidate();
            selectMenu.frame.getContentPane().validate();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        current.currentImage = current.rolloverImage;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        current.currentImage = current.rolloverImage;
        selectMenu.repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        current.currentImage = current.normalImage;
        selectMenu.repaint();
    }

}