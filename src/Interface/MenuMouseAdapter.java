package Interface;

import Controller.ControllerGamePlay;
import Structures.Game;
import Structures.Game2P;
import Structures.Save;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.InvalidClassException;

import static java.lang.System.exit;

public class MenuMouseAdapter implements MouseListener {

    HoverButton current;
    MenuInterface menuUi;
    MenuInterface menu;
    GamePlayInterface game;
    GameSelection selectMenu;
    TutorialInterface tutoUi;
    TutorialInterface tuto;
    TutorialInterface2 tuto2;
    GameSelection gameSelec;

    GamePlayInterface gamePlay;
    GamePlayInterface gamePlay2;

    GamePlayMenu playMenu;

    MenuMouseAdapter(HoverButton b, MenuInterface m) {
        current = b;
        menuUi = m;
    }

    MenuMouseAdapter(HoverButton b, TutorialInterface g) {
        current = b;
        tutoUi = g;
    }

    MenuMouseAdapter(HoverButton b, TutorialInterface2 g) {
        current = b;
        tuto2 = g;
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
//                        tuto = new TutorialInterface(menuUi.frame);
//                        changePanel(tuto);
                        tuto2 = new TutorialInterface2(menuUi.frame);
                        changePanel(tuto2);
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
                if(tutoUi != null) {
                    tutoUi.frame.getContentPane().removeAll();
                    try {
                        menu = new MenuInterface(tutoUi.frame);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    tutoUi.frame.getContentPane().add(menu, BorderLayout.CENTER);
                    tutoUi.frame.getContentPane().invalidate();
                    tutoUi.frame.getContentPane().validate();
                } else if (tuto2 != null){
                    tuto2.frame.getContentPane().removeAll();
                    try {
                        menu = new MenuInterface(tuto2.frame);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    tuto2.frame.getContentPane().add(menu, BorderLayout.CENTER);
                    tuto2.frame.getContentPane().invalidate();
                    tuto2.frame.getContentPane().validate();
                }
                break;
            case "Resume":
                System.out.println("Remove");
                gamePlay.removeMenu(gamePlay.playMenu);
                this.game.controller.resumeTurn();
                break;
            case "Continue":
                Save savegame = new Save("save.dat");
                try {
                    savegame.loadSave();
                    Game g = savegame.getGame();
                    if (g !=null){
                        ControllerGamePlay control = new ControllerGamePlay(g, menuUi.frame);
                        menuUi.frame.addKeyListener(new KeyBoardAdapter(control));
                        game = new GamePlayInterface(menuUi.frame, control);
                        changePanel(game);
                    } else {
                        System.out.println("Could not initialise the game");
                        menuUi.error = true;
                        menuUi.repaint();
                    }
                } catch (InvalidClassException ex) {
                    System.out.println("There was an invalid class exception");
                    //throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                break;
            case "Restart":
                Game g2p = new Game2P(gamePlay.gs2p);
                ControllerGamePlay controller = new ControllerGamePlay(g2p, gamePlay.frame);
                gamePlay.frame.getContentPane().remove(gamePlay);

                try {
                    gamePlay2 = new GamePlayInterface(gamePlay.frame, controller, gamePlay.gs2p);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                gamePlay.frame.getContentPane().add(gamePlay2, BorderLayout.CENTER);
                gamePlay.frame.getContentPane().invalidate();
                gamePlay.frame.getContentPane().validate();
                // Begins the game
                controller.startGame();
                break;
            case "Exit":
                exit(0);
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
            }else if (tuto2 != null){
                tuto2.repaint();
            }
        } else if (menuUi != null) {
            menuUi.repaint();
//        }
        } else if (playMenu != null){
            System.out.println("Repaint Enter");
            playMenu.repaint();
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
            }else if (tuto2 != null){
                tuto2.repaint();
            }
        } else if (menuUi != null) {
            menuUi.repaint();
//        }
        } else if (playMenu != null){
            System.out.println("Repaint Exit");
//            switch (current.name){
//                case "NG":
//                    HoverButton b = playMenu.newgame;
//                    b.repaint();
//                    break;
//                case "Tutorial":
//                    HoverButton b2 = playMenu.tutorial;
//                    b2.repaint();
//                    break;
//                default:
//                    break;
//            }
            playMenu.repaint();
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