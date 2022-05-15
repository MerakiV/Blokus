package Interface;

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

        try {
            menu = new GamePlayInterface(frame);
            //menu.setLayout(new BorderLayout());
            //menu = new MenuInterface(frame);
            // menu = new GameSelection(frame);
        } catch (IOException e) {
            e.printStackTrace();
        }
        frame.getContentPane().add(menu, BorderLayout.CENTER);
    }

}