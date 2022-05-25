package Interface;

import Structures.Color;
import Structures.Game;
import Structures.Game2P;
import Structures.GameSettings2P;

import javax.swing.*;
import java.awt.*;

import java.io.File;
import java.io.IOException;

public class GameSelection extends JComponent {
    public JFrame frame;
    JPanel panel;
    Image backGround, logo, board, colorSelectbg, yellow, green, blue, red, p1c1, p1c2, p2c1, p2c2;
    HoverButton playButton, backButton;
    ColorPicker selectC1P1, selectC2P1, selectC1P2, selectC2P2;
    String currentPlayerPicking;
    boolean errorPlay;
    JComboBox selectP1, selectP2;
    int height, width, boardHeight, boardWidth, imageSize;

    DrawString selectColor, player11, player21, player12, player22, errorPlayText;

    GameSettings2P gs2p; // TBI: connect with the game/game2P classes

    public GameSelection(JFrame f) throws IOException {
        frame = f;
        width = frame.getWidth();
        height = frame.getHeight();
        errorPlay = false;
        backGround = new Image(frame, "images/border.png");
        board = new Image(frame, "images/board.png");
        logo = new Image(frame, "images/LogoBlokus.png");
        colorSelectbg = new Image(frame, "images/colorSelectBackground.png");
        yellow = new Image(frame, "tiles/yellowBloc.png");
        green = new Image(frame, "tiles/greenBloc.png");
        blue = new Image(frame, "tiles/blueBloc.png");
        red = new Image(frame, "tiles/redBloc.png");
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
        initUIButton();
    }

    private void initUIButton() throws IOException {
        String[] players = { "Human", "AI Easy", "AI Medium", "AI Hard" };
        // selectP1, selectP2;

        // Combo boxes
        selectP1 = new JComboBox(players);
        add(selectP1);
        selectP2 = new JComboBox(players);
        add(selectP2);

        // Listeners for combo boxes (to modify)
        selectP1.addActionListener(e -> {
            JComboBox comboBox = (JComboBox) e.getSource();
            String p1 = (String) comboBox.getSelectedItem();
            switch (p1) {
                case "Human":
                    gs2p.setP1Human();
                    break;
                case "AI Easy":
                    gs2p.setP1AI(0);
                    break;
                case "AI Medium":
                    System.out.println("P1 AI Medium");
                    // gs2p.setP1AI(1);
                    break;
                case "AI Hard":
                    System.out.println("P1 AI Hard");
                    // gs2p.setP1AI(2);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid player 1");
            }
            System.out.println("Player 1 set to " + p1);
        });

        selectP2.addActionListener(e -> {
            JComboBox comboBox = (JComboBox) e.getSource();
            String p2 = (String) comboBox.getSelectedItem();
            // TBI: modify when more AI difficulty made
            switch (p2) {
                case "Human":
                    gs2p.setP2Human();
                    break;
                case "AI Easy":
                    gs2p.setP2AI(0);
                    break;
                case "AI Medium":
                    System.out.println("P2 AI Medium");
                    // gs2p.setP2AI(1);
                    break;
                case "AI Hard":
                    System.out.println("P2 AI Hard");
                    // gs2p.setP2AI(2);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid player 2");
            }
            System.out.println("Player 2 set to " + p2);
        });

        // buttons to pick a color for a player
        selectC1P1 = new ColorPicker(this, "C1P1");
        gs2p.setP1Color1(Color.YELLOW);
        p1c1 = yellow;
        add(selectC1P1);
        selectC1P2 = new ColorPicker(this, "C1P2");
        gs2p.setP2Color1(Color.GREEN);
        p2c1 = green;
        add(selectC1P2);
        selectC2P1 = new ColorPicker(this, "C2P1");
        gs2p.setP1Color2(Color.BLUE);
        p1c2 = blue;
        add(selectC2P1);
        selectC2P2 = new ColorPicker(this, "C2P2");
        gs2p.setP2Color2(Color.RED);
        p2c2 = red;
        add(selectC2P2);

        // back and play buttons
        backButton = new HoverButton(this, "Back", 0, 0);
        add(backButton);
        playButton = new HoverButton(this, "Play", 0, 0);
        add(playButton);

        // player selection text
        player11 = new DrawString("Player 1");
        player21 = new DrawString("Player 2");
        player12 = new DrawString("Player 1");
        player22 = new DrawString("Player 2");
    }

    private void resetBound() {
        // getting frame width and height
        height = frame.getHeight();
        width = frame.getWidth();

        // resizing the play and back buttons
        playButton.setButtonBound((width - playButton.getWidth()) / 2, (int) (height * 0.82));
        backButton.setButtonBound((int) (width * 0.05), (int) (height * 0.08));

        // resizing the player selection combo boxes
        int offsetPlayerY = (height - boardHeight) / 2 + imageSize + 20;
        int offsetPlayer1X = (width - boardWidth) / 2 * 7 / 9 - (width / 8) / 2;
        int offsetPlayer2X = (width + boardWidth) / 2;
        offsetPlayer2X += (width - offsetPlayer2X) * 2 / 9 - (width / 8) / 2;
        selectP1.setBounds(offsetPlayer1X, offsetPlayerY, width / 8, height / 17);
        selectP2.setBounds(offsetPlayer2X, offsetPlayerY, width / 8, height / 17);
        selectP1.setFont(new Font("ABeeZee-Regular", Font.PLAIN, (int) (width * 0.015)));
        selectP2.setFont(new Font("ABeeZee-Regular", Font.PLAIN, (int) (width * 0.015)));

        // resizing the color selection buttons
        int topBorder = offsetPlayerY + selectP1.getHeight() + 20;
        int bottomBorder = (height + boardHeight) / 2 - imageSize - 20 - selectC1P1.getHeight();
        selectC1P1.setButtonBound(offsetPlayer1X, topBorder + (bottomBorder - topBorder) / 4, width / 8);
        selectC1P2.setButtonBound(offsetPlayer2X, topBorder + (bottomBorder - topBorder) / 4, width / 8);
        selectC2P1.setButtonBound(offsetPlayer2X, bottomBorder, width / 8);
        selectC2P2.setButtonBound(offsetPlayer1X, bottomBorder, width / 8);

        // resizing the player selection text
        int offsetPlayerText1Y = (height - boardHeight) / 2 + (imageSize + (int) (0.015 * width)) / 2;
        int offsetPlayerText2Y = (height + boardHeight) / 2 - (imageSize - (int) (0.015 * width)) / 2;
        int offsetPlayerText1X = (int) ((width - boardWidth) / 2 * 7 / 9 - 0.075 * width / 2);
        int offsetPlayerText2X = (width + boardWidth) / 2;
        offsetPlayerText2X += (int) ((width - offsetPlayerText2X) * 2 / 9 - 0.075 * width / 2);
        player11.setCoords(offsetPlayerText1X, offsetPlayerText1Y);
        player21.setCoords(offsetPlayerText2X, offsetPlayerText1Y);
        player12.setCoords(offsetPlayerText1X, offsetPlayerText2Y);
        player22.setCoords(offsetPlayerText2X, offsetPlayerText2Y);
        player11.setFontSize((int) (width * 0.02));
        player21.setFontSize((int) (width * 0.02));
        player12.setFontSize((int) (width * 0.02));
        player22.setFontSize((int) (width * 0.02));
    }

    public void setCurrentPlayer(String player) {
        System.out.println("changing current player to " + player);
        currentPlayerPicking = player;
    }

    public void setColor(Color color) {
        switch (currentPlayerPicking) {
            case "C1P1":
                System.out.println("Changing C1P1 color to " + color);
                gs2p.setP1Color1(color);
                p1c1 = colorToImage(color);
                selectC1P1.setCurrentColor(color);
                break;
            case "C1P2":
                System.out.println("Changing C1P1 color to " + color);
                gs2p.setP2Color1(color);
                p2c1 = colorToImage(color);
                selectC1P2.setCurrentColor(color);
                break;
            case "C2P1":
                System.out.println("Changing C1P1 color to " + color);
                gs2p.setP1Color2(color);
                p1c2 = colorToImage(color);
                selectC2P1.setCurrentColor(color);
                break;
            case "C2P2":
                System.out.println("Changing C1P1 color to " + color);
                gs2p.setP2Color2(color);
                p2c2 = colorToImage(color);
                selectC2P2.setCurrentColor(color);
                break;
            default:
                throw new IllegalArgumentException("Invalid player");
        }
    }

    public Image colorToImage(Color color) {
        switch (color) {
            case YELLOW:
                return yellow;
            case GREEN:
                return green;
            case BLUE:
                return blue;
            case RED:
                return red;
            default:
                throw new IllegalArgumentException("Invalid color");
        }
    }

    public String getCurrentPlayerPicking() {
        return currentPlayerPicking;
    }

    public Game getGame() {
        Game g2p = new Game2P(gs2p);
        return g2p;
    }

    public boolean validColors() {
        Color[] colors = new Color[4];
        colors[0] = gs2p.p1c1;
        colors[1] = gs2p.p2c1;
        colors[2] = gs2p.p1c2;
        colors[3] = gs2p.p2c2;

        for (int i = 0; i < 3; i++) {
            System.out.println(colors[i]);
            for (int j = i + 1; j < 4; j++) {
                System.out.println(colors[j]);
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
        resetBound();
        drawColorPicker(g, selectC1P1);
        drawColorPicker(g, selectC1P2);
        drawColorPicker(g, selectC2P1);
        drawColorPicker(g, selectC2P2);

        // drawing back and play buttons
        g.drawImage(backButton.getCurrentImage(), backButton.getX(), backButton.getY(), null);
        g.drawImage(playButton.getCurrentImage(), playButton.getX(), playButton.getY(), this);
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
        int buttonSize = p.getButtonSize();
        g.drawImage(colorSelectbg.image, p.getX(), p.getY(), p.getWidth(), p.getHeight(), null);
        g.drawImage(p.getCurrentImage("red"), redX, redY, buttonSize, buttonSize, null);
        g.drawImage(p.getCurrentImage("blue"), blueX, blueY, buttonSize, buttonSize, null);
        g.drawImage(p.getCurrentImage("green"), greenX, greenY, buttonSize, buttonSize, null);
        g.drawImage(p.getCurrentImage("yellow"), yellowX, yellowY, buttonSize, buttonSize, null);
    }

    public void drawText(Graphics g) {
        // drawing text for player selection
        player11.setGraphics(g);
        player21.setGraphics(g);
        player12.setGraphics(g);
        player22.setGraphics(g);
        player11.paint(g);
        player21.paint(g);
        player12.paint(g);
        player22.paint(g);

        // TBI : "color 1" and "color 2" on top of the color selection boxes
        // colorText11.paint(g);
        // colorText12.paint(g);
        // colorText21.paint(g);
        // colorText22.paint(g);

        // error message when players pick the same color
        if (errorPlay) {
            errorPlayText = new DrawString(g, "Each player must pick a unique color.", (width - 420) / 2,
                    playButton.getY() - 20, 24);
            errorPlayText.paint(g);
        }
    }

    public void drawBg(Graphics g) {
        backGround.drawImg(g, 0, 0, width, height);
        logo.drawImg(g, (int) (width * 0.88) / 2, (height - boardHeight) / 2 * 2 / 5, (int) (0.12 * width),
                (int) (0.06 * width));
    }

    public void drawBoard(Graphics g) {
        board.drawImg(g, (width - boardWidth) / 2, (height - boardHeight) / 2, boardWidth, boardHeight);
        p1c1.drawImg(g, (width - boardWidth) / 2, (height - boardHeight) / 2, imageSize, imageSize);
        p2c1.drawImg(g, (width + boardWidth) / 2 - imageSize, (height - boardHeight) / 2, imageSize, imageSize);
        p1c2.drawImg(g, (width + boardWidth) / 2 - imageSize, (height + boardHeight) / 2 - imageSize, imageSize,
                imageSize);
        p2c2.drawImg(g, (width - boardWidth) / 2, (height + boardHeight) / 2 - imageSize, imageSize, imageSize);
    }

    public void paintComponent(Graphics g) {
        width = frame.getWidth();
        height = frame.getHeight();
        boardWidth = width / 4;
        boardHeight = width / 4;
        imageSize = boardWidth / 9;

        drawBg(g);
        drawButtons(g);
        drawBoard(g);
        drawText(g);
        frame.setVisible(true);
    }
}