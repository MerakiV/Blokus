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
    java.awt.Image img;
    JScrollPane jsp;
    ScrollableLabel sc;
    JPanel panel;

    public TutorialInterface(JFrame f) throws IOException {
        System.out.println("Tuto");
        frame = f;
        frame.setResizable(true);
//        menuUi = m;
        String bg = "images/border.png";
        String lg = "images/LogoBlokus.png";
        backGround = new Image(frame, bg);
        logo = new Image(frame, lg);
        this.back = new HoverButton(this,"Back", (int) (frame.getWidth()*0.05), (int) (frame.getHeight()*0.08));
        add(this.back);

        panel=new JPanel();
        panel.setBounds((frame.getWidth()/2) - ( (int) (frame.getWidth()*0.7)/2),(frame.getHeight()/2) - ( (int) (frame.getHeight()*0.7)/2),(int) (frame.getWidth()*0.7),(int) (frame.getHeight()*0.7));//设置普通面板位置和大小，不能省略
        panel.setLayout(new BorderLayout());
        ii = new ImageIcon("resources/images/tuto.png");

        sc = new ScrollableLabel(ii);


        jsp = new JScrollPane();
        jsp.getViewport().add(panel);
        jsp.setBounds((frame.getWidth()/2) - ( (int) (frame.getWidth()*0.7)/2),(frame.getHeight()/2) - ( (int) (frame.getHeight()*0.7)/2),(int) (frame.getWidth()*0.7),(int) (frame.getHeight()*0.7));
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
        //for tuto img adaptive
        int widthTuto = (int) (frame.getWidth()*0.7), heightTuto = -1;
        img = ii.getImage();
        img = img.getScaledInstance(widthTuto, heightTuto, java.awt.Image.SCALE_DEFAULT);
        ii.setImage(img);
        sc.setIcon(ii);
        panel.add(sc, BorderLayout.NORTH);

        jsp.setBounds((frame.getWidth()/2) - ( (int) (frame.getWidth()*0.7)/2),(frame.getHeight()/2) - ( (int) (frame.getHeight()*0.7)/2),(int) (frame.getWidth()*0.7),(int) (frame.getHeight()*0.7));

    }
}