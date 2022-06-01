package Interface;

import Structures.Game;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class GameInterface implements Runnable {
    public Game game;
    private MenuInterface menu;

    public void run() {
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

            // loading background music
            File in = new File("resources/sounds/bgm.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(in);
            Mixer mixer = AudioSystem.getMixer(null);
            Clip clip = AudioSystem.getClip(mixer.getMixerInfo());
            clip.open(audioIn);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
        frame.getContentPane().add(menu, BorderLayout.CENTER);
    }

}