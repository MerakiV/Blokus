package Interface;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class MenuInterface extends JComponent {
    Image backGround;
    public HoverButton newGame;
    public HoverButton conti;
    public HoverButton tutorial;
    public HoverButton exit;
    public JFrame frame;
    Integer x = 640;

    public MenuInterface(JFrame f) throws IOException {
        frame = f;
        String bg = "images/background.png";
        backGround = chargeImg(bg);
        inItUIButton();
    }


    private void inItUIButton() throws IOException {
        this.newGame = new HoverButton(this, "NG", x-217, 280);
        add(this.newGame);
        this.conti = new HoverButton(this, "Continue", x-217, 390);
        add(this.conti);
        this.tutorial = new HoverButton(this, "Tutorial",x-217, 500);
        add(this.tutorial);
        this.exit = new HoverButton(this, "Exit", x-217, 610);
        add(this.exit);
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

    public Image[] createButtonImg(String name) throws IOException {
        Image img1, img2;
        InputStream in1 = ClassLoader.getSystemClassLoader().getResourceAsStream("buttons/"+ name + "/normal.png");
        img1 = ImageIO.read(in1);
        InputStream in2 = ClassLoader.getSystemClassLoader().getResourceAsStream("buttons/"+ name + "/mouseOver.png");
        img2 = ImageIO.read(in2);
        return new Image[] {
                img1, img2
        };
    }

    public void drawButtons(Graphics g){
        g.drawImage(this.newGame.getCurrentImage(), x- (this.newGame.getCurrentImageWidth())/2, 280, this);
        g.drawImage(this.conti.getCurrentImage(), x- (this.conti.getCurrentImageWidth())/2, 390, this);
        g.drawImage(this.tutorial.getCurrentImage(), x- (this.tutorial.getCurrentImageWidth())/2, 500, this);
        g.drawImage(this.exit.getCurrentImage(), x- (this.exit.getCurrentImageWidth())/2,610, this);
    }

    public void drawBg(Graphics g){
        g.drawImage(backGround, 0, 0, 1280,800, null);
    }


    public void paintComponent(Graphics g) {
        drawBg(g);
        drawButtons(g);

    }

}