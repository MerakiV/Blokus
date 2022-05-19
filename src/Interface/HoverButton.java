package Interface;

import java.awt.Graphics;
import java.awt.Image;

import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.*;

public class HoverButton extends JPanel {
    private MenuInterface menuUI;
    private GamePlay gameMenu;
    private GameSelection selectMenu;
    private TutorialInterface tutoUI;

    private Image[] img;
    public Image normalImage;
    public Image rolloverImage;
    public Image currentImage;

    private boolean enabled = true;

    public String name = null;

    public HoverButton(MenuInterface menuUI, String name, int x, int y) throws IOException {
        this.menuUI = menuUI;
        this.name = name;
        this.img = createButtonImg(name);
        this.normalImage = this.img[0];
        this.rolloverImage = this.img[1];
        this.currentImage = normalImage;
        this.setBounds(x - 202, y, this.img[0].getWidth(null), this.img[0].getHeight(null));
        System.out.println(x + " " + y);
        this.addMouseListener(new MenuMouseAdapter(this, this.menuUI));
    }

    public HoverButton(GamePlay gameMenu, String name, int x, int y) throws IOException {
        this.gameMenu = gameMenu;
        this.name = name;
        this.img = createButtonImg(name);
        // Original Image
        int newS = (int) (gameMenu.frame.getWidth() * 0.03);
        Image newNormal = this.img[0].getScaledInstance(newS, newS, Image.SCALE_DEFAULT);
        this.normalImage = newNormal;
        // Hovered Image
        this.rolloverImage = newNormal.getScaledInstance((int) (newS * 1.1), (int) (newS * 1.1), Image.SCALE_DEFAULT);
        this.currentImage = normalImage;
        this.setBounds(x, y, newS, newS);
        this.addMouseListener(new GameMouseAdapter(this, this.gameMenu));
    }

    // Hover Button for Game Selection
    public HoverButton(GameSelection selectMenu, String name, int x, int y) throws IOException {
        this.selectMenu = selectMenu;
        this.name = name;
        this.img = createButtonImg(name);
        // Original Image
        this.normalImage = this.img[0];
        // Hovered Image
        this.rolloverImage = this.img[1];
        this.currentImage = normalImage;
        this.setBounds(x, y, normalImage.getWidth(null), normalImage.getHeight(null));
        this.addMouseListener(new NGMouseAdapter(this, this.selectMenu));
    }

    public HoverButton(TutorialInterface tutoUI, String name, int x, int y) throws IOException {
        this.tutoUI = tutoUI;
        this.name = name;
        this.img = createButtonImg(name);
        this.normalImage = this.img[0];
        this.rolloverImage = this.img[1];
        this.currentImage = normalImage;
        this.setBounds(x, y, this.img[0].getWidth(null), this.img[0].getHeight(null));
        this.addMouseListener(new MenuMouseAdapter(this, this.tutoUI));
    }

    public void setButtonBound(Integer x, Integer y) {
        this.setBounds(x, y, normalImage.getWidth(null), normalImage.getHeight(null));
    }

    public void paint(Graphics g) {
        this.setOpaque(false);
        if (enabled) {
            g.drawImage(currentImage, this.getX(), this.getY(), this.getWidth(),
                    this.getHeight(), this);
        }
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
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
