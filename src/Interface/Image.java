package Interface;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Image {
    BufferedImage image;
    public JFrame frame;

    public Image (JFrame f, String name) throws IOException {
        frame = f;
        image = chargeImg(name);
    }

    public BufferedImage chargeImg(String nom){
        BufferedImage img = null;
        try { //on récupère l'image à l'adresse où on l’a mise…
            InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(nom);
            assert in != null;
            img = ImageIO.read(in);
            //System.out.println("chargement des images ok");
            //fin chargement des images.
        } catch (Exception e) {
            System.out.println("erreur dans le chargement des images:" + e);
        }
        return img;
    }

    public int getHeight(){
        return image.getHeight();
    }

    public int getWidth(){
        return image.getWidth();
    }

    public void drawImg(Graphics g, int i1, int i2, int i3, int i4){
        g.drawImage(image, i1, i2, i3, i4, null);
    }

}
