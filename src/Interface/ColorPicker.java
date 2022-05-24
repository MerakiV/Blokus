package Interface;

import java.awt.Image;

import java.io.IOException;
import javax.swing.*;

public class ColorPicker extends JPanel {

    private ColorSelect red, blue, green, yellow;
    private int buttonSize;

    public ColorPicker(GameSelection selectMenu, String player, int x, int y) throws IOException {
        yellow = new ColorSelect(selectMenu, "YellowButton", player, x + 10, y + 10);
        selectMenu.add(yellow);
        buttonSize = yellow.getCurrentImageHeight();
        green = new ColorSelect(selectMenu, "GreenButton", player, x + buttonSize + 10*2, y + 10);
        selectMenu.add(green);
        red = new ColorSelect(selectMenu, "RedButton", player, x + buttonSize * 2 + 10 * 3, y + 10);
        selectMenu.add(red);
        blue = new ColorSelect(selectMenu, "BlueButton", player, x + buttonSize * 3 + 10 * 4, y + 10);
        selectMenu.add(blue);

    }

    public void setButtonBound(int x, int y) {
        yellow.setButtonBound(x + 10, y + 10);
        green.setButtonBound(x + buttonSize + 10*2, y + 10);
        red.setButtonBound(x + buttonSize * 2 + 10 * 3, y + 10);
        blue.setButtonBound(x + buttonSize * 3 + 10 * 4, y + 10);
    }

    public int getX(String color) {
        switch (color) {
            case "red":
                return red.getX();
            case "blue":
                return blue.getX();
            case "green":
                return green.getX();
            case "yellow":
                return yellow.getX();
            default:
                throw new IllegalArgumentException("Color not found");
        }
    }

    public int getY(String color) {
        switch (color) {
            case "red":
                return red.getY();
            case "blue":
                return blue.getY();
            case "green":
                return green.getY();
            case "yellow":
                return yellow.getY();
            default:
                throw new IllegalArgumentException("Color not found");
        }
    }

    public int getButtonSize() {
        return buttonSize;
    }

    public Image getCurrentImage(String color) {
        switch (color) {
            case "red":
                return red.getCurrentImage();
            case "blue":
                return blue.getCurrentImage();
            case "green":
                return green.getCurrentImage();
            case "yellow":
                return yellow.getCurrentImage();
            default:
                throw new IllegalArgumentException("Color not found");
        }
    }

    public int getCurrentImageWidth(String color) {
        switch (color) {
            case "red":
                return red.getCurrentImageWidth();
            case "blue":
                return blue.getCurrentImageWidth();
            case "green":
                return green.getCurrentImageWidth();
            case "yellow":
                return yellow.getCurrentImageWidth();
            default:
                throw new IllegalArgumentException("Color not found");
        }
    }

    public int getCurrentImageHeight(String color) {
        switch (color) {
            case "red":
                return red.getCurrentImageHeight();
            case "blue":
                return blue.getCurrentImageHeight();
            case "green":
                return green.getCurrentImageHeight();
            case "yellow":
                return yellow.getCurrentImageHeight();
            default:
                throw new IllegalArgumentException("Color not found");
        }
    }

}
