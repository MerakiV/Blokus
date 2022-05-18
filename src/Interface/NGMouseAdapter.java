package Interface;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import Structures.Color;

public class NGMouseAdapter implements MouseListener {

    HoverButton hover;
    ColorSelect drop;
    GameSelection selectMenu;
    MenuInterface menu;
    GamePlay gamePlay;

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
            switch (hover.name) {
                case "Back":
                    selectMenu.frame.getContentPane().remove(selectMenu);
                    try {
                        menu = new MenuInterface(selectMenu.frame);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    selectMenu.frame.getContentPane().add(menu, BorderLayout.CENTER);
                    selectMenu.frame.getContentPane().invalidate();
                    selectMenu.frame.getContentPane().validate();
                    break;
                case "Play":
                    if (selectMenu.validColors()) {
                        selectMenu.frame.getContentPane().remove(selectMenu);
                        try {
                            gamePlay = new GamePlay(selectMenu.frame);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        selectMenu.frame.getContentPane().add(gamePlay, BorderLayout.CENTER);
                        selectMenu.frame.getContentPane().invalidate();
                        selectMenu.frame.getContentPane().validate();
                    } else {
                        selectMenu.errorPlay();
                        selectMenu.repaint();
                    }
                    break;
                case "C1P1":
                    selectMenu.showColorPicker(1);
                    selectMenu.repaint();
                    break;
                case "C1P2":
                    selectMenu.showColorPicker(2);
                    selectMenu.repaint();
                    break;
                case "C2P1":
                    selectMenu.showColorPicker(3);
                    selectMenu.repaint();
                    break;
                case "C2P2":
                    selectMenu.showColorPicker(4);
                    selectMenu.repaint();
                    break;
                case "RedButton":
                    selectMenu.setColor(Color.RED);
                    selectMenu.showColorPicker(0);
                    selectMenu.repaint();
                    break;
                case "GreenButton":
                    selectMenu.setColor(Color.GREEN);
                    selectMenu.showColorPicker(0);
                    selectMenu.repaint();
                    break;
                case "BlueButton":
                    selectMenu.setColor(Color.BLUE);
                    selectMenu.showColorPicker(0);
                    selectMenu.repaint();
                    break;
                case "YellowButton":
                    selectMenu.setColor(Color.YELLOW);
                    selectMenu.showColorPicker(0);
                    selectMenu.repaint();
                    break;
                default:
                    throw new RuntimeException("Unknown button: " + hover.name);
            }
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
        selectMenu.repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (hover != null) {
            hover.currentImage = hover.normalImage;
        }
        selectMenu.repaint();
    }

}