package GamePanels;

import Interface.GameMouseAdapter;
import Interface.GamePlayInterface;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ColorMouseAdapter implements MouseListener {
    GamePlayInterface gamePlayInterface;
    ColorPanel colorPanel;
    public ColorMouseAdapter(GamePlayInterface g){
        gamePlayInterface = g;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        colorPanel = (ColorPanel) e.getSource();
        System.out.println("Color : " + colorPanel.color);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {


    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //System.out.println("Mouse Entered" + e.getX() + " " + e.getY());
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //System.out.println("Mouse Exited" + e.getX() + " " + e.getY());
    }
}
