package Interface;

import javax.imageio.ImageIO;
import javax.swing.*;

import javafx.scene.layout.Background;

import java.awt.*;

import java.io.IOException;
import java.io.InputStream;

public class GameSelection extends JComponent {
    public JFrame frame;
    Image backGround, logo, red, blue, green, yellow, board;
    JComboBox selectP1, selectP2, selectC1P1, selectC2P1, selectC1P2, selectC2P2;
    HoverButton playButton;
    BackButton back;
    int height, width;

    JLabel selectColor, player1, player2;

    public GameSelection(JFrame f) throws IOException {
        frame = f;
        backGround = new Image(frame, "images/border.png");
        board = new Image(frame, "images/board.png");
        logo = new Image(frame, "images/LogoBlokus.png");
        red = new Image(frame, "tiles/redBloc.png");
        blue = new Image(frame, "tiles/blueBloc.png");
        green = new Image(frame, "tiles/greenBloc.png");
        yellow = new Image(frame, "tiles/yellowBloc.png");
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
        playButton = new HoverButton(this, "Continue", (int) (frame.getWidth() * 0.91),
                (int) (frame.getHeight() * 0.8));
        add(playButton);
    }

    // TO DO : Position the button Properly
    public void drawButtons(Graphics g) {
        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();
        int imageWidth = playButton.getCurrentImageWidth();

        g.drawImage(this.playButton.getCurrentImage(), (frameWidth - imageWidth) / 2, (int) (frameHeight * 0.78), this);
    }

    public void drawBg(Graphics g) {
        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();

        backGround.drawImg(g, 0, 0, frameWidth, frameHeight);
        logo.drawImg(g, (frameWidth - logo.getWidth()) / 2, (int) (frameHeight * 0.07), logo.getWidth(),
                logo.getHeight());
    }

    public void drawBoard(Graphics g) {
        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();

        int boardWidth = frameWidth / 4;
        int boardHeight = frameWidth / 4;
        board.drawImg(g, (frameWidth - boardWidth) / 2, (frameHeight - boardHeight) / 2, boardWidth,
                boardHeight);
    }

    public void paintComponent(Graphics g) {
        drawBg(g);
        drawBoard(g);
        drawButtons(g);

    }

}