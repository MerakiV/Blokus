package Interface;

import java.awt.Graphics;
import java.awt.Image;

import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.*;

public class ColorSelect extends JPanel {
    private GameSelection selectMenu;

    private Image blank, hover, red, blue, green, yellow, currentImage;
    private Image[] items;
    private String name;

    private boolean enabled = true;

    public ColorSelect(GameSelection selectMenu, String name, int x, int y) throws IOException {
        this.selectMenu = selectMenu;
        this.name = name;
        chargeImages();
        this.currentImage = blank;
        this.setBounds(x, y, this.blank.getWidth(null), this.blank.getHeight(null));
        System.out.println(x + " " + y);
        this.addMouseListener(new NGMouseAdapter(this, this.selectMenu));
    }

    public void chargeImages() throws IOException {
        InputStream inBlank = ClassLoader.getSystemClassLoader().getResourceAsStream("tiles/GreyBloc.png");
        blank = ImageIO.read(inBlank);
        InputStream inHover = ClassLoader.getSystemClassLoader().getResourceAsStream("tiles/YellowHighlight.png");
        hover = ImageIO.read(inHover);
        InputStream inRed = ClassLoader.getSystemClassLoader().getResourceAsStream("tiles/RedBloc.png");
        red = ImageIO.read(inRed);
        InputStream inBlue = ClassLoader.getSystemClassLoader().getResourceAsStream("tiles/BlueBloc.png");
        blue = ImageIO.read(inBlue);
        InputStream inGreen = ClassLoader.getSystemClassLoader().getResourceAsStream("tiles/GreenBloc.png");
        green = ImageIO.read(inGreen);
        InputStream inYellow = ClassLoader.getSystemClassLoader().getResourceAsStream("tiles/YellowBloc.png");
        yellow = ImageIO.read(inYellow);
    }

    public void setButtonBound(Integer x, Integer y) {
        this.setBounds(x, y, currentImage.getWidth(null), currentImage.getHeight(null));
    }

    public void paint(Graphics g) {
        this.setOpaque(false);
        if (enabled) {
            g.drawImage(currentImage, this.getX(), this.getY(), this.getWidth(),
                    this.getHeight(), this);
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void changeCurrentImage(String color) {
        switch (color) {
            case "red":
                this.currentImage = red;
                break;
            case "blue":
                this.currentImage = blue;
                break;
            case "green":
                this.currentImage = green;
                break;
            case "yellow":
                this.currentImage = yellow;
                break;
            case "blank":
                this.currentImage = blank;
                break;
            case "hover":
                this.currentImage = hover;
                break;
            default:
                throw new IllegalArgumentException("Invalid color");
        }

    }

    public Image getCurrentImage() {
        return currentImage;
    }

    public Integer getCurrentImageWidth() {
        return currentImage.getWidth(null);
    }

    public int getCurrentImageHeight() {
        return currentImage.getHeight(null);
    }

}
