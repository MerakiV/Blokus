package GamePanels;

import Controller.ControllerGamePlay;
import Interface.EventController;
import Interface.GameMouseAdapter;
import Interface.GamePlayInterface;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PieceMouseAdapter implements MouseListener {
    GamePlayInterface gamePlayInterface;
    PiecePanel piecePanel;

    ControllerGamePlay controller;
    public PieceMouseAdapter(GamePlayInterface g){
        gamePlayInterface = g;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        piecePanel = (PiecePanel) e.getSource();
        System.out.println("Piece Type : " + piecePanel.piece.getName().name());
        controller.piece = piecePanel.piece;
        controller.color = piecePanel.colorPanel.color;
        System.out.println(controller.piece.getName().name());
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
