package Interface;

import javax.imageio.ImageIO;
import javax.swing.*;

//import javafx.scene.effect.Blend;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class GameSelection extends JComponent {
    public JFrame frame;
    Image backGround, logo, red, blue, green, yellow, board;
    JComboBox selectP1, selectP2, selectC1P1, selectC2P1, selectC1P2, selectC2P2;
    HoverButton PlayButton;
    BackButton back;
    int height, width;

    JLabel selectColor, player1, player2;

    public GameSelection(JFrame f) throws IOException {
        frame = f;
        backGround = new Image(frame,"images/border.png");
        logo = new Image(frame,"images/LogoBlokus.png");
        red = new Image(frame,"tiles/RedBloc.png");
        blue = new Image(frame,"tiles/BlueBloc.png");
        green = new Image(frame,"tiles/GreenBloc.png");
        yellow = new Image(frame,"tiles/YellowBloc.png");
        board = new Image(frame,"images/board.png");
        initUIButton();
    }

    private JLabel createLabel(String s) {
        JLabel lab = new JLabel(s);
        return lab;
    }

    private JComboBox createComboBox(Object[] items) {
        JComboBox comboBox = new JComboBox(items);
        // comboBox.addActionListener();
        return comboBox;
    }

    private void initUIButton() throws IOException {
        Image[] colors = { red, blue, green, yellow };
        String[] players = { "Human", "AI Easy", "AI Medium", "AI Hard" };

        player1 = createLabel("Player 1");
        player2 = createLabel("Player 2");
        selectColor = createLabel("press the squares to select your color.");
        selectP1 = createComboBox(players);
        selectP2 = createComboBox(players);
        selectC1P1 = createComboBox(colors);
        selectC2P1 = createComboBox(colors);
        selectC1P2 = createComboBox(colors);
        selectC2P2 = createComboBox(colors);
        // PlayButton = new HoverButton(this, "Play", frame.getWidth() / 2,
        // frame.getHeight() * 0.9);
    }

    public void drawButtons(Graphics g) {

    }

    public void drawBg(Graphics g) {
        height = frame.getHeight();
        width = frame.getWidth();

        backGround.drawImg(g,0, 0, width,height);
        logo.drawImg(g, (int)(width * 0.375) ,(int)(height * 0.05), (int)(width * 0.25), (int)(height * 0.25));
    }

    public void drawBoard(Graphics g) {
        height = frame.getHeight();
        width = frame.getWidth();
        int boardWidth = frame.getWidth() / 4;
        int boardHeight = frame.getWidth() / 4;

        board.drawImg(g,(width - boardWidth) / 2, (height - boardHeight) / 2, boardWidth, boardHeight);
    }

    public void paintComponent(Graphics g) {
        drawBg(g);
        drawBoard(g);
        drawButtons(g);

    }

}