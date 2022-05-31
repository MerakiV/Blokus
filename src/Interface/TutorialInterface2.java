package Interface;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class TutorialInterface2 extends JComponent{
    JFrame frame;
    Image backGround;
    Image logo;
    Image tuto1;
    Image tuto2;
    Image tuto3;
    Image tuto4;
    HoverButton back;
    Integer height, width;
    public Boolean drawTuto1=true;
    public Boolean drawTuto2=false;
    public Boolean drawTuto3=false;
    public Boolean drawTuto4=false;


    public TutorialInterface2(JFrame j) throws IOException {
        frame = j;
        String bg = "images/border.png";
        String lg = "images/LogoBlokus.png";
        String tt1 = "images/tuto1.png";
        String tt2 = "images/tuto2.png";
        String tt3 = "images/tuto3.png";
        String tt4 = "images/tuto4.png";
        backGround = new Image(frame, bg);
        logo = new Image(frame, lg);
        tuto1 = new Image(frame, tt1);
        tuto2 = new Image(frame, tt2);
        tuto3 = new Image(frame, tt3);
        tuto4 = new Image(frame, tt4);
        this.back = new HoverButton(this,"Back", (int) (frame.getWidth()*0.05), (int) (frame.getHeight()*0.08));
        add(this.back);
        this.addMouseListener(new TutorialMouseAdapter(this));


    }

    public void drawTuto1(Graphics g){
        if (drawTuto1){
            tuto1.drawImg(g, (int) (frame.getWidth()*0.145),(int) (frame.getHeight()*0.172), (int) (frame.getWidth()*0.73), (int) (frame.getHeight()*0.74));
        }
    }

    public void drawTuto2(Graphics g){
        if (drawTuto2){
            tuto2.drawImg(g, (int) (frame.getWidth()*0.145),(int) (frame.getHeight()*0.172), (int) (frame.getWidth()*0.73), (int) (frame.getHeight()*0.74));
        }
    }

    public void drawTuto3(Graphics g){
        if (drawTuto3){
            tuto3.drawImg(g, (int) (frame.getWidth()*0.145),(int) (frame.getHeight()*0.172), (int) (frame.getWidth()*0.73), (int) (frame.getHeight()*0.74));
        }
    }

    public void drawTuto4(Graphics g){
        if (drawTuto4){
            tuto4.drawImg(g, (int) (frame.getWidth()*0.145),(int) (frame.getHeight()*0.172), (int) (frame.getWidth()*0.73), (int) (frame.getHeight()*0.74));
        }
    }

    public void paintComponent(Graphics g) {
        height = frame.getHeight();
        width = frame.getWidth();
        backGround.drawImg(g,0, 0, width,height);
        logo.drawImg(g,(width/2)- (logo.getWidth()/2), 15, logo.getWidth(), logo.getHeight() );
        g.drawImage(this.back.getCurrentImage(), (int) (width*0.05), (int) (height*0.08), this);

        drawTuto1(g);
        drawTuto2(g);
        drawTuto3(g);
        drawTuto4(g);

    }

}
