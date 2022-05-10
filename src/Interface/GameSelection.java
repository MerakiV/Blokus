package Interface;

import javax.imageio.ImageIO;
import javax.swing.*;

import javafx.scene.effect.Blend;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class GameSelection extends JComponent {
    public JFrame frame;
    Image backGround, red, blue, green, yellow;
    JComboBox selectP1, selectP2, selectC1P1, selectC2P1, selectC1P2, selectC2P2;
    JButton PlayButton;
    JLabel selectColor, player1, player2;

    public GameSelection(JFrame f) throws IOException {
        frame = f;
        System.out.println("bg :");
        backGround = chargeImg("images/boarder.png");
        System.out.println("bg fin :" + backGround);
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

        selectP1 = createComboBox(players);
        selectP2 = createComboBox(players);
        selectC1P1 = createComboBox(colors);
        selectC2P1 = createComboBox(colors);
        selectC1P2 = createComboBox(colors);
        selectC2P2 = createComboBox(colors);
        // PlayButton = new HoverButton(this, "Play", frame.getWidth() / 2,
        // frame.getHeight() * 9 / 10);
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

    public Image[] createButtonImg(String name) throws IOException {
        Image img1, img2;
        InputStream in1 = ClassLoader.getSystemClassLoader().getResourceAsStream("buttons/" + name + "/normal.png");
        img1 = ImageIO.read(in1);
        InputStream in2 = ClassLoader.getSystemClassLoader().getResourceAsStream("buttons/" + name + "/mouseOver.png");
        img2 = ImageIO.read(in2);
        return new Image[] {
                img1, img2
        };
    }

    public void drawButtons(Graphics g) {

    }

    public void drawBg(Graphics g) {
        g.drawImage(backGround, 0, 0, frame.getWidth(), frame.getHeight(), null);
    }

    public void drawBoard(Graphics g) {
        // Image board = chargeImg("");
        int boardHeight = 100;
        int boardWidth = 100;
        // g.drawImage(board, (frame.getWidth() - boardWidth) / 2, (frame.getHeight() -
        // boardHeight) / 2,
        // boardWidth, boardHeight, null);
    }

    public void paintComponent(Graphics g) {
        drawBg(g);
        System.out.println("Height : " + frame.getHeight() + ", Width : " + frame.getWidth());
        drawBoard(g);
        drawButtons(g);

    }

}