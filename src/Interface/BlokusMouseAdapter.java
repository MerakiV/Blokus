package Interface;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class BlokusMouseAdapter implements MouseListener {

    HoverButton current;
    MenuInterface menuUi;
    GamePlay game;
    TutorialInterface tuto;

    BlokusMouseAdapter(HoverButton b, MenuInterface m) {
        current = b;
        menuUi = m;
    }
    BlokusMouseAdapter(HoverButton b, GamePlay g) {
        current = b;
        game = g;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Name:"+current.name);
        if(current.name == "Tutorial"){
            menuUi.frame.getContentPane().remove(menuUi);
            try {
                tuto = new TutorialInterface(menuUi.frame, menuUi);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            menuUi.frame.getContentPane().add(tuto, BorderLayout.CENTER);
            menuUi.frame.getContentPane().invalidate();
            menuUi.frame.getContentPane().validate();
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
        menuUi.repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        current.currentImage = current.normalImage;
        menuUi.repaint();
    }


}