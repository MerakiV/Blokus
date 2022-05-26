package Interface;

import java.awt.Image;

import java.io.IOException;
import javax.swing.*;

import Structures.Color;

public class ColorPicker extends JPanel {

    private ColorSelect red, blue, green, yellow;
    private int buttonSize, sepSize;
    private String player;

    public ColorPicker(GameSelection selectMenu, String player) throws IOException {
        yellow = new ColorSelect(selectMenu, "YellowButton");
        selectMenu.add(yellow);
        buttonSize = yellow.getCurrentImageHeight();
        green = new ColorSelect(selectMenu, "GreenButton");
        selectMenu.add(green);
        red = new ColorSelect(selectMenu, "RedButton");
        selectMenu.add(red);
        blue = new ColorSelect(selectMenu, "BlueButton");
        selectMenu.add(blue);
        this.player = player;
        this.addMouseListener(new NGMouseAdapter(this, selectMenu));
        setOpaque(false);
    }

    public void setButtonBound(int x, int y, int width) {
        buttonSize = (int) (width / (4 + 5 / 4));
        sepSize = (int) (buttonSize / 4.5);
        setBounds(x, y, width, buttonSize + sepSize * 2);
        yellow.setBounds(x + sepSize, y + sepSize, buttonSize, buttonSize);
        green.setBounds(x + buttonSize + sepSize * 2, y + sepSize, buttonSize, buttonSize);
        red.setBounds(x + buttonSize * 2 + sepSize * 3, y + sepSize, buttonSize, buttonSize);
        blue.setBounds(x + buttonSize * 3 + sepSize * 4, y + sepSize, buttonSize, buttonSize);
    }

    public void setCurrentColor(Color color) {
        switch (color) {
            case RED:
                red.setSelected(true);
                blue.setSelected(false);
                green.setSelected(false);
                yellow.setSelected(false);
                break;
            case BLUE:
                red.setSelected(false);
                blue.setSelected(true);
                green.setSelected(false);
                yellow.setSelected(false);
                break;
            case GREEN:
                red.setSelected(false);
                blue.setSelected(false);
                green.setSelected(true);
                yellow.setSelected(false);
                break;
            case YELLOW:
                red.setSelected(false);
                blue.setSelected(false);
                green.setSelected(false);
                yellow.setSelected(true);
                break;
            default:
                throw new IllegalArgumentException("Invalid color");
        }
    }

    public String getPlayer() {
        return player;
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

    public int getCurrentImageWidth() {
        return red.getCurrentImageWidth();
    }

    public int getCurrentImageHeight() {
        return red.getCurrentImageHeight();
    }

}
