package Interface;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class TutorialInterface extends JComponent {
    Image backGround;
    Image logo;
    BackButton back;
    public JFrame frame;
    public MenuInterface menuUi;
    Tutorial tuto;

    public TutorialInterface(JFrame f, MenuInterface m) throws IOException {
        System.out.println("Tuto");
        frame = f;
        menuUi = m;
        String bg = "images/boarder.png";
        String lg = "images/LogoBlokus.png";
        backGround = chargeImg(bg);
        logo = chargeImg(lg);
        this.back = new BackButton(this, "back", 60, 60);
        add(this.back);

        this.tuto = new Tutorial();
        add(this.tuto);
    }

    public Image chargeImg(String nom){
        Image img = null;
        try { //on récupère l'image à l'adresse où on l’a mise…
            InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(nom);
            img = ImageIO.read(in);
            //fin chargement des images.
        } catch (Exception e) {
            System.out.println("erreur dans le chargement des images:" + e);
        }
        System.out.println("chargement des images ok");
        return img;
    }


    public void paintComponent(Graphics g) {
        g.drawImage(backGround, 0, 0, 1280,800, null);
        g.drawImage(logo, 640- (logo.getWidth(null)/2), 15,  null);
        g.drawImage(this.back.getCurrentImage(), 60, 60, this);

    }
}