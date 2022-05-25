package Interface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class MenuMouseAdapter implements MouseListener {

    HoverButton current;
    MenuInterface menuUi;
    MenuInterface menu;
    GamePlayInterface game;
    GameSelection selectMenu;
    TutorialInterface tutoUi;
    TutorialInterface tuto;
    GameSelection gameSelec;

    GamePlayInterface gamePlay;

    GamePlayMenu playMenu;

    MenuMouseAdapter(HoverButton b, MenuInterface m) {
        current = b;
        menuUi = m;
    }

    MenuMouseAdapter(HoverButton b, TutorialInterface g) {
        current = b;
        tutoUi = g;
    }

    MenuMouseAdapter(HoverButton b, GamePlayInterface g, GamePlayMenu gameMenu) {
        current = b;
        gamePlay = g;
        playMenu = gameMenu;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Name:" + current.name);
        switch (current.name){
            case "Tutorial":
                try {
                    if (menuUi != null) {
                        tuto = new TutorialInterface(menuUi.frame);
                        changePanel(tuto);
                    } else if (playMenu != null){
                        tuto = new TutorialInterface(gamePlay.frame);

                        gamePlay.frame.getContentPane().remove(gamePlay);
                        gamePlay.frame.getContentPane().add(tuto, BorderLayout.CENTER);
                        gamePlay.frame.getContentPane().invalidate();
                        gamePlay.frame.getContentPane().validate();

                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                break;
            case "NG":
                try {
                    if(menuUi != null) {
                        gameSelec = new GameSelection(menuUi.frame);
                        changePanel(gameSelec);
                    } else if (playMenu != null){
                        gameSelec = new GameSelection(gamePlay.frame);

                        gamePlay.frame.getContentPane().removeAll();
                        gamePlay.frame.getContentPane().add(gameSelec, BorderLayout.CENTER);
                        gamePlay.frame.getContentPane().invalidate();
                        gamePlay.frame.getContentPane().validate();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                break;
            case "Back":
                tutoUi.frame.getContentPane().removeAll();
                try {
                    menu = new MenuInterface(tutoUi.frame);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                tutoUi.frame.getContentPane().add(menu, BorderLayout.CENTER);
                tutoUi.frame.getContentPane().invalidate();
                tutoUi.frame.getContentPane().validate();
                break;
            case "Resume":
                System.out.println("Remove");
                gamePlay.removeMenu(gamePlay.playMenu);
                break;
            default:
                break;
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
//        System.out.println("Entered"+current.name);
        current.currentImage = current.rolloverImage;
        if (current.name == "Back") {
            if (tutoUi != null) {
                tutoUi.repaint();
            }
        } else if (menuUi != null) {
            menuUi.repaint();
//        }
        } else if (playMenu != null){
            System.out.println("Repaint");
//            playMenu.repaint();
//            playMenu.playMenu.tutorial.repaint();
//            switch (current.name){
//                case "NG":
//                    playMenu.newgame.repaint();
//                    break;
//                case "Tutorial":
//                    playMenu.tutorial.repaint();
//                    break;
//                default:
//                    break;
//            }
        }

    }

    @Override
    public void mouseExited(MouseEvent e) {
//        System.out.println("Exited"+current.name);
        current.currentImage = current.normalImage;
        if (current.name == "Back") {
            if (tutoUi != null) {
                tutoUi.repaint();
            }
        } else if (menuUi != null) {
            menuUi.repaint();
//        }
        } else if (playMenu != null){
            System.out.println("Repaint");
//            switch (current.name){
//                case "NG":
//                    playMenu.newgame.repaint();
//                    break;
//                case "Tutorial":
//                    playMenu.tutorial.repaint();
//                    break;
//                default:
//                    break;
//            }
//            playMenu.repaint();
            //playMenu.playMenu.tutorial.repaint();
        }
    }

    private void changePanel(JComponent page){
        menuUi.frame.getContentPane().remove(menuUi);
        menuUi.frame.getContentPane().add(page, BorderLayout.CENTER);
        menuUi.frame.getContentPane().invalidate();
        menuUi.frame.getContentPane().validate();
    }

}