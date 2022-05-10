package Interface;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class GameInterface implements Runnable {
    private JFrame frame;
    private GameGraphic gameGraphics;
    private MenuInterface menu;


    public static void start(){
        SwingUtilities.invokeLater(new GameInterface());
    }

    public void run(){
        frame = new JFrame("Blokus");
        frame.setName("Blokus");
        frame.setTitle("Blokus");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(1280, 800);
        frame.getContentPane().setBackground(Color.white);
        frame.setVisible(true);

        try {
            menu = new MenuInterface(frame);
        } catch (IOException e) {
            e.printStackTrace();
        }
        frame.getContentPane().add(menu, BorderLayout.CENTER);

//        gameGraphics = new GameGraphic();
//        frame.getContentPane().add(gameGraphics, BorderLayout.CENTER);

    }

}