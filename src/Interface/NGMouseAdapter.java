package Interface;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import Controller.ControllerGamePlay;
import Structures.Color;
import Structures.Game;

public class NGMouseAdapter implements MouseListener {

    HoverButton hover;
    ColorPicker picker;
    GameSelection selectMenu;
    MenuInterface menu;
    GamePlayInterface gamePlay;

    NGMouseAdapter(HoverButton b, GameSelection g) {
        hover = b;
        selectMenu = g;
    }

    NGMouseAdapter(ColorPicker b, GameSelection g) {
        picker = b;
        selectMenu = g;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // handling mouse clicks for the buttons
        if (hover != null) {
            System.out.println("Name:" + hover.name);
            switch (hover.name) {
                case "Back":
                    // goes back to the main menu
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
                    // creates a new game and starts the gameplay interface
                    if (selectMenu.validColors()) {
                        Game g2p = selectMenu.getGame();
                        selectMenu.gs2p.printSettings();
                        ControllerGamePlay controller = new ControllerGamePlay(g2p, selectMenu.frame);
                        selectMenu.frame.addKeyListener(new KeyBoardAdapter(controller));
                        selectMenu.frame.getContentPane().remove(selectMenu);
                        try {
                            gamePlay = new GamePlayInterface(selectMenu.frame, controller, selectMenu.gs2p);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        // TODO : init game loop somewhere here?
                        // create new class containing controller and interface?
                        selectMenu.frame.getContentPane().add(gamePlay, BorderLayout.CENTER);
                        selectMenu.frame.getContentPane().invalidate();
                        selectMenu.frame.getContentPane().validate();
                        // Begins the game
                        controller.startGame();
                    } else {
                        selectMenu.errorPlay();
                        selectMenu.repaint();
                    }
                    break;
                case "RedButton":
                    // sets the current player color to red
                    selectMenu.setColor(Color.RED);
                    selectMenu.repaint();
                    break;
                case "GreenButton":
                    // sets the current player color to green
                    selectMenu.setColor(Color.GREEN);
                    selectMenu.repaint();
                    break;
                case "BlueButton":
                    // sets the current player color to blue
                    selectMenu.setColor(Color.BLUE);
                    selectMenu.repaint();
                    break;
                case "YellowButton":
                    // sets the current player color to yellow
                    selectMenu.setColor(Color.YELLOW);
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
        if (picker != null) {
            // changes the current player when entering a color picker
            selectMenu.setCurrentPlayer(picker.getPlayer());
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