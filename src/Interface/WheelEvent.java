package Interface;

import Controller.ControllerGamePlay;

import javax.swing.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class WheelEvent implements MouseWheelListener {

    ControllerGamePlay controller;

    public WheelEvent(ControllerGamePlay c){
        this.controller = c;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (controller.piece != null){
            if (controller.hintsActivated){
                controller.boardPanel.removePositions();
                controller.boardPanel.showPositions();
                controller.boardPanel.repaint();
            }
            if(e.getWheelRotation() < 0){
                System.out.println("Mouse moved UP");
                controller.command("counterclockwise");
            } else {
                System.out.println("Mouse moved DOWN");
                controller.command("clockwise");
            }
        } else{
            System.out.println("No piece selected");
        }
    }
}
