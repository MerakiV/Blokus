package Interface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;


public class BackButton extends JPanel implements MouseListener {
    private TutorialInterface tutoUI;
    private Image[] img;

    public Image normalImage;
    public Image rolloverImage;

    public Image currentImage;

    private boolean enabled = true;

    public String name = null;
    MenuInterface menu;

    public BackButton(TutorialInterface tutoUI, String name, Integer x, Integer y) throws IOException {
        this.tutoUI = tutoUI;
        this.name = name;//设置名称
        this.img = this.tutoUI.menuUi.createButtonImg(name);
        this.normalImage = this.img[0];
        this.rolloverImage = this.img[1];
        this.currentImage = normalImage;
        this.setBounds(x, y, this.img[0].getWidth(null), this.img[0].getHeight(null));
        this.addMouseListener(this);
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
    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Remove");
        tutoUI.frame.getContentPane().remove(tutoUI);
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
