package Interface;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.InputStream;

import java.awt.Image;


public class BackButton extends JPanel implements MouseListener {
    private TutorialInterface tutoUI;
    private Image[] img;

    public Image normalImage;
    public Image rolloverImage;

    public Image currentImage;

    private boolean enabled = true;

    public String name = null;
    MenuInterface menu;

    public BackButton(String name, Integer x, Integer y) throws IOException {
        this.tutoUI = tutoUI;
        this.name = name;
        this.img = createButtonImg(name);
        this.normalImage = this.img[0];
        this.rolloverImage = this.img[1];
        this.currentImage = normalImage;
        this.setBounds(x, y, this.img[0].getWidth(null), this.img[0].getHeight(null));
        this.addMouseListener(this);
    }

    public Image[] createButtonImg(String name) throws IOException {
        Image img1, img2;
        InputStream in1 = ClassLoader.getSystemClassLoader().getResourceAsStream("buttons/"+ name + "/normal.png");
        img1 = ImageIO.read(in1);
        InputStream in2 = ClassLoader.getSystemClassLoader().getResourceAsStream("buttons/"+ name + "/mouseOver.png");
        img2 = ImageIO.read(in2);
        return new java.awt.Image[] {
                img1, img2
        };
    }

    public void paint(Graphics g) {
        this.setOpaque(false); // 背景透明
        if (enabled){
            g.drawImage(currentImage, this.getX(), this.getY(), this.getWidth(), this.getHeight(), null);
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
    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Remove");
        tutoUI.frame.getContentPane().removeAll();
        try {
            menu = new MenuInterface(tutoUI.frame);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        tutoUI.frame.getContentPane().add(menu, BorderLayout.CENTER);
        tutoUI.frame.getContentPane().invalidate();
        tutoUI.frame.getContentPane().validate();


    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("Enter");
        currentImage = rolloverImage;
        tutoUI.repaint();

    }

    @Override
    public void mouseExited(MouseEvent e) {
        currentImage = normalImage;
        tutoUI.repaint();
    }
}
