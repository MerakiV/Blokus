package Interface;

import javax.imageio.ImageIO;
import javax.swing.*;

import javafx.scene.effect.Blend;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class GameSelection extends JComponent {
    public JFrame frame;
    Image backGround, logo, red, blue, green, yellow;
    JComboBox selectP1, selectP2, selectC1P1, selectC2P1, selectC1P2, selectC2P2;
    HoverButton PlayButton;
    BackButton back;

    JLabel selectColor, player1, player2;

    public GameSelection(JFrame f) throws IOException {
        frame = f;
        backGround = chargeImg("images/boarder.png");
        logo = chargeImg("images/LogoBlokus.png");
        red = chargeImg("tiles/redBloc.png");
        blue = chargeImg("tiles/blueBloc.png");
        green = chargeImg("tiles/greenBloc.png");
        yellow = chargeImg("tiles/yellowBloc.png");
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

    public Image chargeImg(String nom) {
        Image img = null;
        try { // on récupère l'image à l'adresse où on l’a mise…
            InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(nom);
            img = ImageIO.read(in);
            System.out.println("chargement des images ok");
            // fin chargement des images.
        } catch (Exception e) {
            System.out.println("erreur dans le chargement des images:" + e);
        }
        return img;
    }

    public void drawButtons(Graphics g) {

    }

    public void drawBg(Graphics g) {
        g.drawImage(backGround, 0, 0, frame.getWidth(), frame.getHeight(), null);
        g.drawImage(logo, (frame.getWidth() - logo.getWidth(null)) / 2, (int) (frame.getHeight() * 0.07), null);
    }

    public void drawBoard(Graphics g) {
        Image board = chargeImg("images/board.png");
        int boardWidth = frame.getWidth() / 4;
        int boardHeight = frame.getWidth() / 4;

        g.drawImage(board, (frame.getWidth() - boardWidth) / 2, (frame.getHeight() - boardHeight) / 2, boardWidth,
                boardHeight, null);
    }

    public void paintComponent(Graphics g) {
        drawBg(g);
        drawBoard(g);
        drawButtons(g);

    }

}