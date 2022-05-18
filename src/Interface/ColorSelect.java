package Interface;

import java.awt.Graphics;
import java.awt.Image;

import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.*;

import Structures.Color;

public class ColorSelect extends HoverButton {
    private GameSelection selectMenu;

    private Image blank, blankHover, red, redHover, blue, blueHover, green, greenHover, yellow, yellowHover;

    public ColorSelect(GameSelection selectMenu, String name, int x, int y) throws IOException {
        super(selectMenu, name, x, y);
        chargeImages();
    }

    public void chargeImages() throws IOException {
        blank = normalImage;
        blankHover = rolloverImage;

        red = charge("RedBloc");
        redHover = charge("RedHighlight");

        blue = charge("BlueBloc");
        blueHover = charge("BlueHighlight");

        green = charge("GreenBloc");
        greenHover = charge("GreenHighlight");

        yellow = charge("YellowBloc");
        yellowHover = charge("YellowHighlight");
    }

    public Image charge(String imageName) throws IOException {
        InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream("tiles/" + imageName + ".png");
        Image img = ImageIO.read(in);
        return img;
    }

    public void changeCurrentImage(Color color) {
        switch (color) {
            case RED:
                normalImage = red;
                rolloverImage = redHover;
                currentImage = normalImage;
                break;
            case BLUE:
                normalImage = blue;
                rolloverImage = blueHover;
                currentImage = normalImage;
                break;
            case GREEN:
                normalImage = green;
                rolloverImage = greenHover;
                currentImage = normalImage;
                break;
            case YELLOW:
                normalImage = yellow;
                rolloverImage = yellowHover;
                currentImage = normalImage;
                break;
            case WHITE:
                normalImage = blank;
                rolloverImage = blankHover;
                currentImage = normalImage;
                break;
            default:
                throw new IllegalArgumentException("Invalid color");
        }

    }
}
