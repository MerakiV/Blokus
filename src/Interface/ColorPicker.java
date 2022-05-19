package Interface;

import java.awt.Image;

import java.io.IOException;
import javax.swing.*;

public class ColorPicker extends JPanel {

    private HoverButton red, blue, green, yellow;
    private int buttonSize;
    private boolean visible;

    public ColorPicker(GameSelection selectMenu, int x, int y) throws IOException {
        yellow = new HoverButton(selectMenu, "YellowButton", x + 10, y + 10);
        selectMenu.add(yellow);
        buttonSize = yellow.getCurrentImageHeight();
        green = new HoverButton(selectMenu, "GreenButton", x + buttonSize + 20, y + 10);
        selectMenu.add(green);
        red = new HoverButton(selectMenu, "RedButton", x + 10, y + buttonSize + 20);
        selectMenu.add(red);
        blue = new HoverButton(selectMenu, "BlueButton", x + buttonSize + 20, y + buttonSize + 20);
        selectMenu.add(blue);
        setVisible(false);

    }

    @Override
    public void setVisible(boolean b) {
        yellow.setVisible(b);
        green.setVisible(b);
        red.setVisible(b);
        blue.setVisible(b);
        visible = b;
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    public void setButtonBound(int x, int y) {
        yellow.setButtonBound(x, y);
        green.setButtonBound(x + buttonSize + 10, y);
        red.setButtonBound(x, y + buttonSize + 10);
        blue.setButtonBound(x + buttonSize + 10, y + buttonSize + 10);
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
