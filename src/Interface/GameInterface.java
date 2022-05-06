package Interface;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class GameInterface implements Runnable {
    private JFrame frame;
    //private GameGraphic gameGraphics;


    public GameInterface() {

    }

    public static void start(){
        SwingUtilities.invokeLater(new GameInterface());
    }
    public void run(){
        frame = new JFrame("Blokus");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 800);
        frame.setVisible(true);

    }
}