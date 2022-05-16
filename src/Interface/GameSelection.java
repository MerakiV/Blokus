package Interface;

import Structures.GameSettings2P;

import javax.swing.*;

import java.awt.*;

import java.io.File;
import java.io.IOException;

public class GameSelection extends JComponent {
    public JFrame frame;
    JPanel panel;
    Image backGround, logo, red, blue, green, yellow, blank, board;
    HoverButton playButton, backButton;
    JComboBox selectP1, selectP2;
    ColorSelect selectC1P1, selectC2P1, selectC1P2, selectC2P2;
    int height, width, boardHeight, boardWidth, colorWidth;

    DrawString selectColor, player1, player2;

    GameSettings2P gs2p; // TBI: connect with the game/game2P classes

    public GameSelection(JFrame f) throws IOException {
        frame = f;
        width = frame.getWidth();
        height = frame.getHeight();
        panel = new JPanel();
        // add(panel);
        backGround = new Image(frame, "images/border.png");
        board = new Image(frame, "images/board.png");
        logo = new Image(frame, "images/LogoBlokus.png");
        initUIButton();
        gs2p = new GameSettings2P();
        try {
            // create the font to use
            Font font = Font.createFont(Font.TRUETYPE_FONT, new File("resources/font/ABeeZee-Regular.otf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            // register the font
            ge.registerFont(font);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

    }

    private void initUIButton() throws IOException {
        String[] players = { "Human", "AI Easy", "AI Medium", "AI Hard" };
        // selectP1, selectP2;
        selectC1P1 = new ColorSelect(this, "C1P1", (width - boardWidth) / 2, (height - boardHeight) / 2);
        add(this.selectC1P1);
        colorWidth = selectC1P1.getCurrentImageWidth();
        selectC2P1 = new ColorSelect(this, "C1P2", (width + boardWidth) / 2 - colorWidth,
                (height - boardHeight) / 2);
        add(this.selectC2P1);
        selectC1P2 = new ColorSelect(this, "C2P1", (width - boardWidth) / 2,
                (height + boardHeight) / 2 - colorWidth);
        add(this.selectC1P2);
        selectC2P2 = new ColorSelect(this, "C2P2", (width + boardWidth) / 2 - colorWidth,
                (height + boardHeight) / 2 - colorWidth);
        add(this.selectC2P2);

        // Combo boxes
        selectP1 = new JComboBox(players);
        selectP1.setBounds((width - 300) / 4, (int) (height * 0.3), 130, 25);
        add(selectP1);

        // Listeners for combo boxes (to modify)

        selectP1.addActionListener(e -> {
            JComboBox comboBox = (JComboBox) e.getSource();
            String p1 = (String) comboBox.getSelectedItem();
            // TBI: modify when more AI difficulty made
            if (p1 == "Human") {
                gs2p.setP1Human();
            } else {
                gs2p.setP1AI(0);
            }
            System.out.println("Player 1 set to " + p1);
        });

        selectP2 = new JComboBox(players);
        selectP2.setBounds((int) ((width - 300) / 1.2), (int) (height * 0.3), 90, 20);
        add(selectP2);

        selectP2.addActionListener(e -> {
            JComboBox comboBox = (JComboBox) e.getSource();
            String p2 = (String) comboBox.getSelectedItem();
            // TBI: modify when more AI difficulty made
            if (p2 == "Human") {
                gs2p.setP2Human();
            } else {
                gs2p.setP2AI(0);
            }
            System.out.println("Player 2 set to " + p2);
        });

        backButton = new HoverButton(this, "Back", (int) (width * 0.05), (int) (height * 0.08));
        add(this.backButton);
        playButton = new HoverButton(this, "Play", (width - 270) / 2, (int) (height * 0.8));
        add(this.playButton);
    }

    private void resetBound(int width, int height) {
        this.playButton.setButtonBound((width - 270) / 2, (int) (height * 0.8));
        this.selectC1P1.setButtonBound((width - boardWidth) / 2, (height - boardHeight) / 2);
        this.selectC1P2.setButtonBound((width + boardWidth) / 2 - colorWidth, (height - boardHeight) / 2);
        this.selectC2P1.setButtonBound((width - boardWidth) / 2, (height + boardHeight) / 2 - colorWidth);
        this.selectC2P2.setButtonBound((width + boardWidth) / 2 - colorWidth,
                (height + boardHeight) / 2 - colorWidth);
    }

    public void drawButtons(Graphics g) {
        int playWidth = playButton.getCurrentImageWidth();
        colorWidth = selectC1P1.getCurrentImageWidth();

        resetBound(width, height);
        g.drawImage(this.backButton.getCurrentImage(), (int) (width * 0.05), (int) (height * 0.08), null);
        g.drawImage(this.playButton.getCurrentImage(), (width - playWidth) / 2, (int) (height * 0.8), this);

        g.drawImage(this.selectC1P1.getCurrentImage(), (width - boardWidth) / 2, (height - boardHeight) / 2, null);
        g.drawImage(this.selectC1P2.getCurrentImage(), (width + boardWidth) / 2 - colorWidth,
                (height - boardHeight) / 2, null);
        g.drawImage(this.selectC2P1.getCurrentImage(), (width - boardWidth) / 2,
                (height + boardHeight) / 2 - colorWidth, null);
        g.drawImage(this.selectC2P2.getCurrentImage(), (width + boardWidth) / 2 - colorWidth,
                (height + boardHeight) / 2 - colorWidth, null);

        /*
         * TODO : - resize font when window is resized
         * - Put the font loading is another class (should be executed when the game
         * window is started)
         */

        // TEXT

        selectColor = new DrawString(g, "Press the squares to select your color.", (width - 365) / 2,
                (int) (height * 0.25), 20f);
        selectColor.paint(g);
        player1 = new DrawString(g, "Player 1", width / 6, (int) (height * 0.25), 40f);
        player1.paint(g);
        player2 = new DrawString(g, "Player 2", (int) (width / 1.38), (int) (height * 0.25), 40f);
        player2.paint(g);

    }

    public void drawBg(Graphics g) {
        backGround.drawImg(g, 0, 0, width, height);
        logo.drawImg(g, (width - logo.getWidth()) / 2, (int) (height * 0.07), logo.getWidth(),
                logo.getHeight());
    }

    public void drawBoard(Graphics g) {
        board.drawImg(g, (width - boardWidth) / 2, (height - boardHeight) / 2, boardWidth, boardHeight);
    }

    public void paintComponent(Graphics g) {
        width = frame.getWidth();
        height = frame.getHeight();
        boardWidth = width / 4;
        boardHeight = width / 4;

        drawBg(g);
        drawBoard(g);
        drawButtons(g);

    }
}