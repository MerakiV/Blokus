package Interface;

import javax.swing.*;
import javax.swing.JFrame;
import java.awt.*;


public class Tutorial extends JPanel{
    TextField text1;
    public Tutorial() {
        text1 = new TextField(20);//设置文本框大小
        text1.setText("hello,Blooming!");//设置文本框内容
        text1.setLocation(100,100);
        text1.setVisible(true);

    }


}
