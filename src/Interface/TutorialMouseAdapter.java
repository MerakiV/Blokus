package Interface;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TutorialMouseAdapter implements MouseListener {
    TutorialInterface2 tuto2;
    public TutorialMouseAdapter(TutorialInterface2 tt2){
        tuto2 = tt2;
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        Integer borderWidth1 = (int) (tuto2.frame.getWidth()*0.145);
        Integer borderWidth2 = borderWidth1 + (int)((int) (tuto2.frame.getWidth()*0.73)*0.22);
        Integer borderWidth3 = borderWidth2 + (int)((int) (tuto2.frame.getWidth()*0.73)*0.22);
        Integer borderWidth4 = borderWidth3 + (int)((int) (tuto2.frame.getWidth()*0.73)*0.22);
        Integer borderWidth5 = borderWidth4 + (int)((int) (tuto2.frame.getWidth()*0.73)*0.22);
        Integer borderHeight1 = (int) (tuto2.frame.getHeight()*0.172);
        Integer borderHeight2 =  borderHeight1 + (int) ((int) (tuto2.frame.getHeight()*0.74)*0.0794);

        System.out.println(borderWidth4+" "+borderWidth5);
        System.out.println("Mouse Clicked On Board " + e.getX() + " " + e.getY());
        if (borderWidth1 <= e.getX() && borderWidth2 >= e.getX()
                && borderHeight1 <= e.getY() &&  borderHeight2 >= e.getY()){
            tuto2.drawTuto1 = true;
            tuto2.drawTuto2 = false;
            tuto2.drawTuto3 = false;
            tuto2.drawTuto4 = false;
            tuto2.repaint();
        } else if(borderWidth2 <= e.getX() && borderWidth3 >= e.getX()
                && borderHeight1 <= e.getY() && borderHeight2 >= e.getY()){
            tuto2.drawTuto2 = true;
            tuto2.drawTuto1 = false;
            tuto2.drawTuto3 = false;
            tuto2.drawTuto4 = false;
            tuto2.repaint();
        } else if(borderWidth3 <= e.getX() && borderWidth4 >= e.getX()
                && borderHeight1 <= e.getY() && borderHeight2 >= e.getY()){
            tuto2.drawTuto2 = false;
            tuto2.drawTuto1 = false;
            tuto2.drawTuto3 = true;
            tuto2.drawTuto4 = false;

            tuto2.repaint();
        } else if(borderWidth4 <= e.getX() && borderWidth5 >= e.getX()
                && borderHeight1 <= e.getY() && borderHeight2 >= e.getY()){
            tuto2.drawTuto2 = false;
            tuto2.drawTuto1 = false;
            tuto2.drawTuto3 = false;
            tuto2.drawTuto4 = true;
            tuto2.repaint();
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
