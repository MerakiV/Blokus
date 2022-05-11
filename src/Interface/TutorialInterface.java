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
    HoverButton back;
    public JFrame frame;
    public MenuInterface menuUi;
    //Tutorial tuto;
    int height;
    int width;
    ImageIcon ii;
    JScrollPane jsp;


    public TutorialInterface(JFrame f, MenuInterface m) throws IOException {
        System.out.println("Tuto");
        frame = f;
        menuUi = m;
        String bg = "images/border.png";
        String lg = "images/LogoBlokus.png";
        backGround = new Image(frame, bg);
        logo = new Image(frame, lg);
        this.back = new HoverButton(this,"Back", (int) (frame.getWidth()*0.05), (int) (frame.getHeight()*0.08));
        add(this.back);

        JPanel panel=new JPanel();//创建一个普通面板
        panel.setBounds((frame.getWidth()/2) - ( (int) (frame.getWidth()*0.6)/2),(frame.getHeight()/2) - ( (int) (frame.getHeight()*0.6)/2),(int) (frame.getWidth()*0.6),(int) (frame.getHeight()*0.6));//设置普通面板位置和大小，不能省略
        panel.setLayout(new BorderLayout());//让JTextArea平铺整个JPanel
        ii = new ImageIcon("resources/images/tuto.png");

        panel.add(new ScrollableLabel(ii));
        jsp = new JScrollPane();
        jsp.getViewport().add(panel);
//        jsp.setSize(800,600);
        jsp.setBounds((frame.getWidth()/2) - ( (int) (frame.getWidth()*0.6)/2),(frame.getHeight()/2) - ( (int) (frame.getHeight()*0.6)/2),(int) (frame.getWidth()*0.6),(int) (frame.getHeight()*0.6));
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

        jsp.setBounds((frame.getWidth()/2) - ( (int) (frame.getWidth()*0.6)/2),(frame.getHeight()/2) - ( (int) (frame.getHeight()*0.6)/2),(int) (frame.getWidth()*0.6),(int) (frame.getHeight()*0.6));

    }
}