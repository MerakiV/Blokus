package Interface;

import Controller.ControllerGamePlay;
import Structures.Game;
import Structures.Game2P;
import Structures.GameSettings2P;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import javax.swing.*;

public class GameInterface implements Runnable {
    private JFrame frame;
    private GamePlayInterface menu;
    public Game game;
    //private MenuInterface menu;
    // private GameSelection menu;

    public static void start(){
        SwingUtilities.invokeLater(new GameInterface());
    }

    public void run(){
        frame = new JFrame("Blokus");
        frame.setName("Blokus");
        frame.setTitle("Blokus");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(1344, 756);
        frame.getContentPane().setBackground(Color.white);
        frame.setVisible(true);
        frame.setFocusable(true);

        try {
            // TODO : initialise game in controller
            initialiseGame();
            ControllerGamePlay controller = new ControllerGamePlay();
            frame.addKeyListener(new KeyBoardAdapter(controller));
            menu = new GamePlayInterface(frame, controller);
            //menu.setLayout(new BorderLayout());
            //menu = new MenuInterface(frame);
            // menu = new GameSelection(frame);
        } catch (IOException e) {
            e.printStackTrace();
        }
        frame.getContentPane().add(menu, BorderLayout.CENTER);
    }

    private void initialiseGame(){
        // Game Settings + Create Game
        setUpGameSettings();
        System.out.println("Finished InitialiseGame");
    }

    // TODO : to change once game settings completed
    public void setUpGameSettings(){
        GameSettings2P gameSettings2P = new GameSettings2P();
        gameSettings2P.setP1Color1(Structures.Color.YELLOW);
        gameSettings2P.setP1Color2(Structures.Color.RED);
        gameSettings2P.setP2Color1(Structures.Color.GREEN);
        gameSettings2P.setP2Color2(Structures.Color.BLUE);
        System.out.println("Finished Color");
        game = new Game2P(gameSettings2P);
        System.out.println("Finished SetUpGameSettings");
    }

}