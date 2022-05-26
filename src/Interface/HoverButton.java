package Interface;

import java.awt.Graphics;
import java.awt.Image;

import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.*;

public class HoverButton extends JPanel {

    private Image[] img;
    public Image normalImage, rolloverImage, currentImage;
    private boolean enabled = true;

    public String name;

    public HoverButton(MenuInterface menuUI, String name, int x, int y) throws IOException {
        this.name = name;
        this.img = createButtonImg(name);
        this.normalImage = this.img[0];
        this.rolloverImage = this.img[1];
        this.currentImage = normalImage;
        this.setBounds(x - 202, y, this.img[0].getWidth(null), this.img[0].getHeight(null));
        //System.out.println(x + " " + y);
        this.addMouseListener(new MenuMouseAdapter(this, menuUI));
    }

    public HoverButton(GamePlayInterface gameMenu, String name, int x, int y) throws IOException {
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
        // this.addMouseListener(new GameMouseAdapter(gameMenu, this));
        //System.out.println(x + " " + y);
    }

    // Hover Button for Game Selection
    public HoverButton(GameSelection selectMenu, String name, int x, int y) throws IOException {
        this.name = name;
        this.img = createButtonImg(name);
        // Original Image
        this.normalImage = this.img[0];
        // Hovered Image
        this.rolloverImage = this.img[1];
        this.currentImage = normalImage;
        this.setBounds(x, y, normalImage.getWidth(null), normalImage.getHeight(null));
        this.addMouseListener(new NGMouseAdapter(this, selectMenu));
    }

    public HoverButton(GamePlayMenu gameMenu, String name, int x, int y) throws IOException {
        //System.out.println(x + " " + y);
        this.name = name;
        this.img = createButtonImg(name);
        // Original Image
        this.normalImage = this.img[0];
        // Hovered Image
        this.rolloverImage = this.img[1];
        this.currentImage = normalImage;
        this.setBounds(x, y, normalImage.getWidth(null), normalImage.getHeight(null));
        this.addMouseListener(new MenuMouseAdapter(this, gameMenu.game, gameMenu));
    }

    public HoverButton(TutorialInterface tutoUI, String name, int x, int y) throws IOException {
        this.name = name;
        this.img = createButtonImg(name);
        this.normalImage = this.img[0];
        this.rolloverImage = this.img[1];
        this.currentImage = normalImage;
        this.setBounds(x, y, this.img[0].getWidth(null), this.img[0].getHeight(null));
        this.addMouseListener(new MenuMouseAdapter(this, tutoUI));
    }

    public void setButtonBound(Integer x, Integer y) {
        this.setBounds(x, y, normalImage.getWidth(null), normalImage.getHeight(null));
    }

    public void setButtonBound(Integer x, Integer y, Integer width, Integer height) {
        this.setBounds(x, y, width, height);
    }

    public void paint(Graphics g) {
        this.setOpaque(false);
        if (enabled) {
            g.drawImage(this.currentImage, this.getX(), this.getY(), this.getWidth(),
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
