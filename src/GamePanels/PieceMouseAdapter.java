package GamePanels;

import Controller.ControllerGamePlay;
import Interface.EventController;
import Interface.GameMouseAdapter;
import Interface.GamePlayInterface;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PieceMouseAdapter implements MouseListener {
    PiecePanel piecePanel;

    ControllerGamePlay controller;
    public PieceMouseAdapter(ControllerGamePlay c){
        controller = c;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        piecePanel = (PiecePanel) e.getSource();
        int col = piecePanel.colorPanel.controller.game.getBoard().getCorner(piecePanel.colorPanel.color);
        if (!piecePanel.colorPanel.controller.game.getPlayerList().get(col).getPieces().contains(piecePanel.piece)){
            System.out.println("You have already placed " + piecePanel.piece.getName().name() + " on the board");
        } else{
            if (piecePanel.colorPanel.color == controller.currentColor){
                if (controller.piecePanel == null){
                    controller.piecePanel = piecePanel;
                } else {
                    if (controller.game.getPlayerList().get(col).getPieces().contains(controller.piecePanel.piece)){
                        controller.piecePanel.isClicked = false;
                        controller.piecePanel.repaint();
                    }
                }
                piecePanel.isClicked = true;
                System.out.println("Piece Type : " + piecePanel.piece.getName().name());
                controller.piece = piecePanel.piece;
                controller.color = piecePanel.colorPanel.color;
                controller.piecePanel = piecePanel;
                System.out.println(controller.piece.getName().name());
                piecePanel.repaint();
            }
            else {
                System.out.println("it's " +controller.currentColor +"'s turn to play,  not " + piecePanel.colorPanel.color);
            }
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
        //System.out.println("Mouse Entered" + e.getX() + " " + e.getY());
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //System.out.println("Mouse Exited" + e.getX() + " " + e.getY());
    }
}
