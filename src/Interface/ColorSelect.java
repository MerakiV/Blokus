package Interface;

import java.io.*;

import javax.imageio.ImageIO;

import java.awt.Image;

public class ColorSelect extends HoverButton {

    private Image normalImage, rolloverImage, selectedImage, rolloverSelectedImage;

    public ColorSelect(GameSelection selectMenu, String name) throws IOException {
        super(selectMenu, name, 0, 0);
        this.normalImage = super.normalImage;
        this.rolloverImage = super.rolloverImage;
        InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream("buttons/" + name + "/selected.png");
        selectedImage = ImageIO.read(in);
        rolloverSelectedImage = selectedImage;
    }

    public void setSelected(boolean selected) {
        // changes the image to show it is selected
        if (selected) {
            super.normalImage = selectedImage;
            super.currentImage = selectedImage;
            super.rolloverImage = rolloverSelectedImage;
        } else {
            super.normalImage = normalImage;
            super.currentImage = normalImage;
            super.rolloverImage = rolloverImage;
        }
        repaint();
    }
}
