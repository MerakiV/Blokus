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
    ImageIcon ii;

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

        JPanel panel=new JPanel();//创建一个普通面板
        panel.setBounds(240,120,400,200);//设置普通面板位置和大小，不能省略
        panel.setLayout(new BorderLayout());//让JTextArea平铺整个JPanel
        ii = new ImageIcon("resources/images/tuto.png");
        panel.add(new ScrollableLabel(ii));
        JScrollPane jsp = new JScrollPane();
        jsp.getViewport().add(panel);
//        jsp.setSize(800,600);
        jsp.setBounds(240,120,800,600);
        Container contentPane = frame.getContentPane();
        contentPane.add(jsp);
        contentPane.setVisible(true);

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