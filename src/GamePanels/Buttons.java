package GamePanels;

import Interface.GamePlayInterface;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;

public class Buttons extends JButton {

    GamePlayInterface gamePlayInterface;
    JFrame frame;
    Image image;
    String name;

    public Buttons(GamePlayInterface g, String s){
        gamePlayInterface = g;
        frame = g.frame;
        name = s;
        initialiseButton();
        setSize();
        this.setBounds(0,0,50,50);
    }

    public void initialiseButton(){
        image = getImage(name);
    }

    public void setSize(){

    }

    private Image getImage(String name) {
        BufferedImage img;
        Image resizedImage = null;
        try {
            InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(getPath(name));
            assert in != null;
            img = ImageIO.read(in);
            resizedImage = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        } catch (Exception e) {
            System.out.println("erreur dans le chargement des images:" + e);
        }
        return resizedImage;
    }

    private String getPath(String name){
        switch(name){
            case "clockwise":
                return "buttons/Clockwise/normal.png";
        }
        return null;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(this.image, 0,0, null);
    }

}
