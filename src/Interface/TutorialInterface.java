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
    ImageIcon ii;

    public TutorialInterface(JFrame f, MenuInterface m) throws IOException {
        System.out.println("Tuto");
        frame = f;
        menuUi = m;
        String bg = "images/border.png";
        String lg = "images/LogoBlokus.png";
        backGround = new Image(frame, bg);
        logo = new Image(frame, lg);
        this.back = new BackButton("back", (int) (width*0.05), (int) (height*0.08));
        add(this.back);

        //this.tuto = new Tutorial();
        //add(this.tuto);
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


    public void paintComponent(Graphics g) {
        height = frame.getHeight();
        width = frame.getWidth();
        backGround.drawImg(g,0, 0, width,height);
        logo.drawImg(g,(width/2)- (logo.getWidth()/2), 15, logo.getWidth(), logo.getHeight() );
        g.drawImage(this.back.getCurrentImage(), (int) (width*0.05), (int) (height*0.08), this);


    }
}