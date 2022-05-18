package Interface;

import Controller.ControllerGamePlay;
import Structures.Game;
import Structures.Game2P;
import Structures.GameSettings2P;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Objects;

public class MenuMouseAdapter implements MouseListener {

    HoverButton current;
    MenuInterface menuUi;
    MenuInterface menu;
    GamePlayInterface game;
    TutorialInterface tutoUi;
    TutorialInterface tuto;
    GameSelection gameSelec;

    Game g;

    MenuMouseAdapter(HoverButton b, MenuInterface m) {
        current = b;
        menuUi = m;
    }

    MenuMouseAdapter(HoverButton b, TutorialInterface g) {
        current = b;
        tutoUi = g;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Name:" + current.name);
        if (Objects.equals(current.name, "Tutorial")) {
            menuUi.frame.getContentPane().remove(menuUi);
            try {
                tuto = new TutorialInterface(menuUi.frame, menuUi);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            menuUi.frame.getContentPane().add(tuto, BorderLayout.CENTER);
            menuUi.frame.getContentPane().invalidate();
            menuUi.frame.getContentPane().validate();
        } else if (Objects.equals(current.name, "NG")) {
            menuUi.frame.getContentPane().remove(menuUi);
            try {
                gameSelec = new GameSelection(menuUi.frame);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            menuUi.frame.getContentPane().add(gameSelec, BorderLayout.CENTER);
            menuUi.frame.getContentPane().invalidate();
            menuUi.frame.getContentPane().validate();
        } // TODO : change Continue to match history if a game has already been saved
        else if (Objects.equals(current.name, "Continue")) {
            menuUi.frame.getContentPane().remove(menuUi);
            try {
                game = new GamePlayInterface(menuUi.frame, new ControllerGamePlay());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            menuUi.frame.getContentPane().add(game, BorderLayout.CENTER);
            menuUi.frame.getContentPane().invalidate();
            menuUi.frame.getContentPane().validate();
        } else if (Objects.equals(current.name, "Back") && tutoUi != null) {
            System.out.println("Remove");
            tutoUi.frame.getContentPane().removeAll();
            try {
                menu = new MenuInterface(tutoUi.frame);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            tutoUi.frame.getContentPane().add(menu, BorderLayout.CENTER);
            tutoUi.frame.getContentPane().invalidate();
            tutoUi.frame.getContentPane().validate();
        } else if (Objects.equals(current.name, "Exit")){
            System.exit(0);
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
        if (Objects.equals(current.name, "Back")) {
            if (tutoUi != null) {
                tutoUi.repaint();
            }
        } else {
            menuUi.repaint();
        }

    }

    @Override
    public void mouseExited(MouseEvent e) {
        current.currentImage = current.normalImage;
        if (Objects.equals(current.name, "Back")) {
            if (tutoUi != null) {
                tutoUi.repaint();
            }
        } else {
            menuUi.repaint();
        }
    }

}