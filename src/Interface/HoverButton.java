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

    /**
     *  HoverButton on MenuInterface
     * */
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

    /**
     *  HoverButton on GamePlayInterface
     * */
    public HoverButton(GamePlayInterface gameMenu, String name, int x, int y, int width, int height) throws IOException {
        this.name = name;
        this.img = createButtonImg(name);
        // Original Image
        this.normalImage = this.img[0];
        this.rolloverImage = this.img[1];
        this.currentImage = normalImage;
        this.setBounds(x, y, width, height);

    }

    public HoverButton(String name, int x, int y) throws IOException {
        this.name = name;
        this.img = createButtonImg(name);
        // Original Image
        this.normalImage = this.img[0];
        this.rolloverImage = this.img[1];
        this.currentImage = normalImage;
        this.setBounds(x, y, normalImage.getWidth(null), normalImage.getHeight(null));
    }

    /**
     *  HoverButton on GameSelection
     * */
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

    /**
     *  HoverButton on GamePlayMenu
     * */
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

    /**
     *  HoverButton on TutorialInterface
     * */
    public HoverButton(TutorialInterface tutoUI, String name, int x, int y) throws IOException {
        this.name = name;
        this.img = createButtonImg(name);
        this.normalImage = this.img[0];
        this.rolloverImage = this.img[1];
        this.currentImage = normalImage;
        this.setBounds(x, y, this.img[0].getWidth(null), this.img[0].getHeight(null));
        this.addMouseListener(new MenuMouseAdapter(this, tutoUI));
    }

    /**
     *  HoverButton on TutorialInterface2 (current one)
     * */
    public HoverButton(TutorialInterface2 tutoUI, String name, int x, int y) throws IOException {
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
