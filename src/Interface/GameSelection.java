package Interface;

import javax.imageio.ImageIO;
import javax.swing.*;

import javafx.scene.layout.Background;

import java.awt.*;

import java.io.IOException;
import java.io.InputStream;

public class GameSelection extends JComponent {
    public JFrame frame;
    JPanel panel;
    Image backGround, logo, red, blue, green, yellow, board;
    JComboBox selectP1, selectP2, selectC1P1, selectC2P1, selectC1P2, selectC2P2;
    HoverButton playButton, backButton;
    int height, width;

    JLabel selectColor, player1, player2;

    public GameSelection(JFrame f) throws IOException {
        frame = f;
        width = frame.getWidth();
        height = frame.getHeight();
        panel = new JPanel();
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
        backButton = new HoverButton(this, "Back", (int) (width * 0.05), (int) (height * 0.08));
        add(this.backButton);
        playButton = new HoverButton(this, "Play", (width - 270) / 2, (int) (height * 0.8));
        add(this.playButton);
    }

    private void resetBound(int width, int height) {
        this.playButton.setButtonBound((width - 270) / 2, (int) (height * 0.8));
    }

    public void drawButtons(Graphics g) {
        int imageWidth = playButton.getCurrentImageWidth();

        resetBound(width, height);
        g.drawImage(this.backButton.getCurrentImage(), (int) (width * 0.05), (int) (height * 0.08), null);
        g.drawImage(this.playButton.getCurrentImage(), (width - imageWidth) / 2, (int) (height * 0.8), this);
    }

    public void drawBg(Graphics g) {
        backGround.drawImg(g, 0, 0, width, height);
        logo.drawImg(g, (width - logo.getWidth()) / 2, (int) (height * 0.07), logo.getWidth(),
                logo.getHeight());
    }

    public void drawBoard(Graphics g) {
        int boardWidth = width / 4;
        int boardHeight = width / 4;
        board.drawImg(g, (width - boardWidth) / 2, (height - boardHeight) / 2, boardWidth,
                boardHeight);

        // panel.add(selectColor, (frameWidth - selectColor.getWidth()) / 2, (int)
        // (frameHeight * 0.4));
    }

    public void paintComponent(Graphics g) {
        width = frame.getWidth();
        height = frame.getHeight();

        drawBg(g);
        drawBoard(g);
        drawButtons(g);

    }

}