package Interface;

import Structures.Color;
import Structures.GameSettings2P;

import javax.swing.*;

import java.awt.*;

import java.io.File;
import java.io.IOException;

public class GameSelection extends JComponent {
    public JFrame frame;
    JPanel panel;
    Image backGround, logo, board;
    HoverButton playButton, backButton;
    ColorSelect selectC1P1, selectC2P1, selectC1P2, selectC2P2;
    ColorPicker colorPicker1, colorPicker2, colorPicker3, colorPicker4;
    String currentPlayerPicking;
    boolean errorPlay;
    JComboBox selectP1, selectP2;
    int height, width, boardHeight, boardWidth, buttonSize;

    DrawString selectColor, player1, player2, errorPlayText;

    GameSettings2P gs2p; // TBI: connect with the game/game2P classes

    public GameSelection(JFrame f) throws IOException {
        frame = f;
        width = frame.getWidth();
        height = frame.getHeight();
        errorPlay = false;
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

        // Combo boxes
        selectP1 = new JComboBox(players);
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

        // buttons to pick a color for a player
        selectC1P1 = new ColorSelect(this, "C1P1", (width - boardWidth) / 2, (height - boardHeight) / 2);
        add(selectC1P1);
        buttonSize = selectC1P1.getCurrentImageWidth();
        selectC1P2 = new ColorSelect(this, "C1P2", (width + boardWidth) / 2 - buttonSize,
                (height - boardHeight) / 2);
        add(selectC1P2);
        selectC2P1 = new ColorSelect(this, "C2P1", (width - boardWidth) / 2,
                (height + boardHeight) / 2 - buttonSize);
        add(selectC2P1);
        selectC2P2 = new ColorSelect(this, "C2P2", (width + boardWidth) / 2 - buttonSize,
                (height + boardHeight) / 2 - buttonSize);
        add(selectC2P2);

        // color pickers
        colorPicker1 = new ColorPicker(this, 0, 0);
        add(colorPicker1);
        colorPicker1.setVisible(false);
        colorPicker2 = new ColorPicker(this, 0, 0);
        add(colorPicker2);
        colorPicker2.setVisible(false);
        colorPicker3 = new ColorPicker(this, 0, 0);
        add(colorPicker3);
        colorPicker3.setVisible(false);
        colorPicker4 = new ColorPicker(this, 0, 0);
        add(colorPicker4);
        colorPicker4.setVisible(false);

        // back and play buttons
        backButton = new HoverButton(this, "Back", (int) (width * 0.05), (int) (height * 0.08));
        add(backButton);
        playButton = new HoverButton(this, "Play", (width - 270) / 2, (int) (height * 0.8));
        add(playButton);
    }

    private void resetBound(int width, int height) {
        int offsetRight = (buttonSize * 3 + 10);
        int offsetBottom = (buttonSize * 2 + 10);

        playButton.setButtonBound((width - playButton.getWidth()) / 2, (int) (height * 0.82));
        backButton.setButtonBound((int) (width * 0.05), (int) (height * 0.08));

        selectC1P1.setButtonBound((width - boardWidth) / 2, (height - boardHeight) / 2);
        selectC1P2.setButtonBound((width + boardWidth) / 2 - buttonSize, (height - boardHeight) / 2);
        selectC2P1.setButtonBound((width - boardWidth) / 2, (height + boardHeight) / 2 - buttonSize);
        selectC2P2.setButtonBound((width + boardWidth) / 2 - buttonSize, (height + boardHeight) / 2 - buttonSize);

        colorPicker1.setButtonBound((width - boardWidth) / 2 + buttonSize + 25,
                (height - boardHeight) / 2 + 25);
        colorPicker2.setButtonBound((width + boardWidth) / 2 - offsetRight - 25,
                (height - boardHeight) / 2 + 25);
        colorPicker3.setButtonBound((width - boardWidth) / 2 + buttonSize + 25,
                (height + boardHeight) / 2 - offsetBottom - 25);
        colorPicker4.setButtonBound((width + boardWidth) / 2 - offsetRight - 25,
                (height + boardHeight) / 2 - offsetBottom - 25);

        int offsetPlayerY = selectC1P1.getY() + selectC1P1.getHeight();
        int offsetPlayer1X = (width - boardWidth) / 2 * 2 / 3 - (width / 10) / 2;
        int offsetPlayer2X = (width + boardWidth) / 2;
        offsetPlayer2X += (width - offsetPlayer2X) / 3 - (width / 10) / 2;
        selectP1.setBounds(offsetPlayer1X, offsetPlayerY + 30, width / 10, height / 20);
        selectP2.setBounds(offsetPlayer2X, offsetPlayerY + 30, width / 10, height / 20);
    }

    public void showColorPicker(int i) {
        switch (i) {
            case 0:
                System.out.println("Hide all color pickers");
                currentPlayerPicking = null;
                colorPicker1.setVisible(false);
                colorPicker2.setVisible(false);
                colorPicker3.setVisible(false);
                colorPicker4.setVisible(false);
            case 1:
                System.out.println("show color picker 1");
                currentPlayerPicking = "C1P1";
                colorPicker1.setVisible(true);
                colorPicker2.setVisible(false);
                colorPicker3.setVisible(false);
                colorPicker4.setVisible(false);
                break;
            case 2:
                System.out.println("show color picker 2");
                currentPlayerPicking = "C1P2";
                colorPicker1.setVisible(false);
                colorPicker2.setVisible(true);
                colorPicker3.setVisible(false);
                colorPicker4.setVisible(false);
                break;
            case 3:
                System.out.println("show color picker 3");
                currentPlayerPicking = "C2P1";
                colorPicker1.setVisible(false);
                colorPicker2.setVisible(false);
                colorPicker3.setVisible(true);
                colorPicker4.setVisible(false);
                break;
            case 4:
                System.out.println("show color picker 4");
                currentPlayerPicking = "C2P2";
                colorPicker1.setVisible(false);
                colorPicker2.setVisible(false);
                colorPicker3.setVisible(false);
                colorPicker4.setVisible(true);
                break;
            default:
                throw new IllegalArgumentException("Invalid color picker index");
        }
    }

    public void setColor(Color color) {
        switch (currentPlayerPicking) {
            case "C1P1":
                System.out.println("Changing C1P1 color to " + color);
                selectC1P1.changeCurrentImage(color);
                gs2p.setP1Color1(color);
                break;
            case "C1P2":
                System.out.println("Changing C1P1 color to " + color);
                selectC1P2.changeCurrentImage(color);
                gs2p.setP2Color1(color);
                break;
            case "C2P1":
                System.out.println("Changing C1P1 color to " + color);
                selectC2P1.changeCurrentImage(color);
                gs2p.setP1Color2(color);
                break;
            case "C2P2":
                System.out.println("Changing C1P1 color to " + color);
                selectC2P2.changeCurrentImage(color);
                gs2p.setP2Color2(color);
                break;
            default:
                throw new IllegalArgumentException("Invalid player");
        }
    }

    public String getCurrentPlayerPicking() {
        return currentPlayerPicking;
    }

    public boolean validColors() {
        Color[] colors = new Color[4];
        colors[0] = gs2p.p1c1;
        colors[1] = gs2p.p2c1;
        colors[2] = gs2p.p1c2;
        colors[3] = gs2p.p2c2;

        for (int i = 0; i < 3; i++) {
            for (int j = i + 1; j < 4; j++) {
                if (colors[i] == null || colors[j] == null || colors[i] == colors[j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public void errorPlay() {
        errorPlay = true;
    }

    public void drawButtons(Graphics g) {
        // drawing buttons to pick a color for a player
        resetBound(width, height);
        g.drawImage(backButton.getCurrentImage(), backButton.getX(), backButton.getY(), null);
        g.drawImage(playButton.getCurrentImage(), playButton.getX(), playButton.getY(), this);

        g.drawImage(selectC1P1.getCurrentImage(), selectC1P1.getX(), selectC1P1.getY(), null);

        g.drawImage(selectC1P2.getCurrentImage(), selectC1P2.getX(), selectC1P2.getY(), null);

        g.drawImage(selectC2P1.getCurrentImage(), selectC2P1.getX(), selectC2P1.getY(), null);

        g.drawImage(selectC2P2.getCurrentImage(), selectC2P2.getX(), selectC2P2.getY(), null);

        // drawing color pickers
        if (colorPicker1.isVisible()) {
            drawColorPicker(g, colorPicker1);
        }
        if (colorPicker2.isVisible()) {
            drawColorPicker(g, colorPicker2);
        }
        if (colorPicker3.isVisible()) {
            drawColorPicker(g, colorPicker3);
        }
        if (colorPicker4.isVisible()) {
            drawColorPicker(g, colorPicker4);
        }
    }

    public void drawColorPicker(Graphics g, ColorPicker p) {
        int redX = p.getX("red");
        int redY = p.getY("red");
        int blueX = p.getX("blue");
        int blueY = p.getY("blue");
        int greenX = p.getX("green");
        int greenY = p.getY("green");
        int yellowX = p.getX("yellow");
        int yellowY = p.getY("yellow");
        g.drawImage(p.getCurrentImage("red"), redX, redY, null);
        g.drawImage(p.getCurrentImage("blue"), blueX, blueY, null);
        g.drawImage(p.getCurrentImage("green"), greenX, greenY, null);
        g.drawImage(p.getCurrentImage("yellow"), yellowX, yellowY, null);
    }

    public void drawText(Graphics g) {
        /*
         * TODO : - resize font when window is resized
         * - Put the font loading is another class (should be executed when the game
         * window is started)
         */

        // TEXT

        selectColor = new DrawString(g, "Press the squares to select your color.", (width - 420) / 2,
                (int) (height * 0.25), 24);
        selectColor.paint(g);
        int offsetPlayerTextY = selectC1P1.getY() + (selectC1P1.getHeight() + 30) / 2;
        int offsetPlayerText1X = (width - boardWidth) / 2 * 2 / 3 - 110 / 2;
        int offsetPlayerText2X = (width + boardWidth) / 2;
        offsetPlayerText2X += (width - offsetPlayerText2X) / 3 - 110 / 2;

        player1 = new DrawString(g, "Player 1", offsetPlayerText1X, offsetPlayerTextY, 30);
        player1.paint(g);
        player2 = new DrawString(g, "Player 2", offsetPlayerText2X, offsetPlayerTextY, 30);
        player2.paint(g);
        if (errorPlay) {
            errorPlayText = new DrawString(g, "Each player must pick a unique color.", (width - 420) / 2,
                    playButton.getY() - 20, 24);
            errorPlayText.paint(g);
        }
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
        drawText(g);

        // printDebug();
    }

    private void printDebug() {
        System.out.println("\n--------------------------------------");
        System.out.println("P1 human : " + gs2p.p1Human);
        System.out.println("P1 AI : " + gs2p.p1AIdiff);
        System.out.println("P2 human : " + gs2p.p2Human);
        System.out.println("P2 AI : " + gs2p.p2AIdiff);
        System.out.println("P1 color 1 : " + gs2p.p1c1);
        System.out.println("P1 color 2 : " + gs2p.p1c2);
        System.out.println("P2 color 1 : " + gs2p.p2c1);
        System.out.println("P2 color 2 : " + gs2p.p2c2);
        System.out.println("--------------------------------------");
    }
}