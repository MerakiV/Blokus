package Interface;

import Structures.Game;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GameInterface implements Runnable {
    public Game game;
    private MenuInterface menu;

    public void run(){
        JFrame frame = new JFrame("Blokus");
        frame.setName("Blokus");
        frame.setTitle("Blokus");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(1344, 756);
        frame.getContentPane().setBackground(Color.white);
        frame.setVisible(true);
        frame.setFocusable(true);

        try {
            menu = new MenuInterface(frame);
            menu.setLayout(new BorderLayout());
        } catch (IOException e) {
            e.printStackTrace();
        }
        frame.getContentPane().add(menu, BorderLayout.CENTER);
    }

}