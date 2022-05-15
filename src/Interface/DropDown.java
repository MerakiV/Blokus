package Interface;

import java.awt.Image;

import java.io.IOException;

import javax.swing.*;

public class DropDown extends JPanel {
    private MenuInterface menuUI;
    private GamePlayInterface gameMenu;

    private Image[] img;
    public Image normalImage;
    public Image rolloverImage;
    public Image currentImage;

    private boolean enabled = true;

    public Object[] items;

    public DropDown(MenuInterface menuUI, Object[] items, int x, int y) throws IOException {
        this.menuUI = menuUI;
        this.items = items;
        // this.img = createButtonImg(name);
        // this.normalImage = this.img[0];
        // this.rolloverImage = this.img[1];
        // this.currentImage = normalImage;
        this.setBounds(x, y, this.img[0].getWidth(null), this.img[0].getHeight(null));
        System.out.println(x + " " + y);
        // this.addMouseListener(new BlokusMouseAdapter(this, this.menuUI));
    }

}
