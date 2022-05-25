package Interface;

import java.io.IOException;

import java.awt.Image;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ColorSelect extends HoverButton {

    private Image normalImage, rolloverImage, selectedImage, rolloverSelectedImage;

    public ColorSelect(GameSelection selectMenu, String name) throws IOException {
        super(selectMenu, name, 0, 0);
        this.normalImage = super.normalImage;
        this.rolloverImage = super.rolloverImage;
        InputStream in1 = ClassLoader.getSystemClassLoader().getResourceAsStream("buttons/" + name + "/selected.png");
        selectedImage = ImageIO.read(in1);
        InputStream in2 = ClassLoader.getSystemClassLoader()
                .getResourceAsStream("buttons/" + name + "/selectedMouseOver.png");
        rolloverSelectedImage = ImageIO.read(in2);
    }

    public void setSelected(boolean selected) {
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
