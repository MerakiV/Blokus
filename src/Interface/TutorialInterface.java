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
    //Tutorial tuto;
    int height;
    int width;

    public TutorialInterface(JFrame f, MenuInterface m) throws IOException {
        System.out.println("Tuto");
        frame = f;
        menuUi = m;
        String bg = "images/border.png";
        String lg = "images/LogoBlokus.png";
        backGround = new Image(frame, bg);
        logo = new Image(frame, lg);
        this.back = new BackButton("back", 60, 60);
        add(this.back);

        //this.tuto = new Tutorial();
        //add(this.tuto);
        JPanel panel=new JPanel();//创建一个普通面板
        panel.setBounds(240,120,400,200);//设置普通面板位置和大小，不能省略
        panel.setLayout(new BorderLayout());//让JTextArea平铺整个JPanel
        //ii = new ImageIcon("resources/images/tuto.png");
        //panel.add(new ScrollableLabel(ii));
        JScrollPane jsp = new JScrollPane();
        jsp.getViewport().add(panel);
//        jsp.setSize(800,600);
        jsp.setBounds(240,120,800,600);
        Container contentPane = frame.getContentPane();
        contentPane.add(jsp);
        contentPane.setVisible(true);

        height = frame.getHeight();
        width = frame.getWidth();
    }


    public void paintComponent(Graphics g) {
        backGround.drawImg(g,0, 0, width,height);
        logo.drawImg(g,0, 0, width,height);
        g.drawImage(this.back.getCurrentImage(), 60, 60, this);

    }
}