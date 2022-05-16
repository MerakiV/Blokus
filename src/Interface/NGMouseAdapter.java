package Interface;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class NGMouseAdapter implements MouseListener {

    HoverButton hover;
    ColorSelect drop;
    GameSelection selectMenu;
    MenuInterface menu;
    GamePlay gamePlay;
    boolean colorPicked;

    NGMouseAdapter(HoverButton b, GameSelection g) {
        hover = b;
        selectMenu = g;
    }

    NGMouseAdapter(ColorSelect b, GameSelection g) {
        drop = b;
        selectMenu = g;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (hover != null) {
            System.out.println("Name:" + hover.name);
            if (hover.name == "Back") {
                selectMenu.frame.getContentPane().remove(selectMenu);
                try {
                    menu = new MenuInterface(selectMenu.frame);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                selectMenu.frame.getContentPane().add(menu, BorderLayout.CENTER);
                selectMenu.frame.getContentPane().invalidate();
                selectMenu.frame.getContentPane().validate();
            } else if (hover.name == "Play") {
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
        if (drop != null) {
            if (!colorPicked) {
                colorPicked = true;
                drop.changeCurrentImage("red");
            }
            selectMenu.repaint();

        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (hover != null) {
            hover.currentImage = hover.rolloverImage;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (hover != null) {
            hover.currentImage = hover.rolloverImage;
        }
        if (drop != null) {
            if (!colorPicked) {
                drop.changeCurrentImage("hover");
            }
        }
        selectMenu.repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (hover != null) {
            hover.currentImage = hover.normalImage;
        }
        if (drop != null) {
            if (!colorPicked) {
                drop.changeCurrentImage("blank");
            }
        }
        selectMenu.repaint();
    }

}