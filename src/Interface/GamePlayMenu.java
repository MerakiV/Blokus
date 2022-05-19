package Interface;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class GamePlayMenu extends JPanel {
    JFrame frame;
    JPanel panel;
    ImageIcon ii;
    Image backGround;

    public GamePlayMenu(JFrame f) throws IOException {
        System.out.println("GamePlayMenu");
        frame = f;
//        panel=new JPanel();//创建一个普通面板
//        panel.setBounds(frame.getWidth(),frame.getHeight(), frame.getWidth(), frame.getHeight());//设置普通面板位置和大小，不能省略
//        panel.setLayout(new BorderLayout());
//        ii = new ImageIcon("resources/images/tuto.png");
        String bg = "images/background.png";
        backGround = new Image(frame, bg);


    }


    public void paintComponent(Graphics g) {
//        g.drawImage(ii.getImage(),200,200, this);
        backGround.drawImg(g, 100, 100, 400, 400);
    }

}
