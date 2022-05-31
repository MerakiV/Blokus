package Interface;

import java.io.IOException;

import java.awt.Image;

public class ColorSelect extends HoverButton {

    private Image normalImage, rolloverImage, selectedImage, rolloverSelectedImage;

    public ColorSelect(GameSelection selectMenu, String name) throws IOException {
        super(selectMenu, name, 0, 0);
        this.normalImage = super.normalImage;
        this.rolloverImage = super.rolloverImage;
        selectedImage = rolloverImage;
        rolloverSelectedImage = rolloverImage;
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
