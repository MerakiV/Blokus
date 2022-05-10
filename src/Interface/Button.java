package Interface;

import java.awt.Graphics;
import java.awt.Image;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.*;


public class Button extends JPanel{
    private MenuInterface menuUI;
    private Image[] img;

    public Image normalImage;
    public Image rolloverImage;

    public Image currentImage;

    private boolean enabled = true;

    public String name = null;

    public Button(MenuInterface menuUI,String name, Integer x, Integer y) throws IOException {
        this.menuUI = menuUI;
        this.name = name;//设置名称
        this.img = this.menuUI.createButtonImg(name);
        this.normalImage = this.img[0];
        this.rolloverImage = this.img[1];
        this.currentImage = normalImage;
        this.setBounds(x, y, this.img[0].getWidth(null), this.img[0].getHeight(null));
        this.addMouseListener(new BlokusMouseAdapter(this, this.menuUI));
    }

    public void paint(Graphics g) {
        this.setOpaque(false); // 背景透明
        if (enabled){
            g.drawImage(currentImage, this.getX(), this.getY(), this.getWidth(),
                    this.getHeight(), this);
        }
    }


    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Image getCurrentImage(){
        return currentImage;
    }


    public Integer getCurrentImageWidth(){
        return  currentImage.getWidth(null);
    }


}
