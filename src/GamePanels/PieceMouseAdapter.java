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

    /**
     *  Mouse Clicked
     *   - If the selected piece has not been played and is the color of the current HUMAN player's turn,
     *      the piece will be selected
     *   - If there is an error (piece has been played, or it's not the player's turn)
     *      a message will be written under the board with the corresponding problem
     **/
    @Override
    public void mouseClicked(MouseEvent e) {
        piecePanel = (PiecePanel) e.getSource();
        int col = piecePanel.colorPanel.controller.game.getBoard().getCorner(piecePanel.colorPanel.color);
        if (!piecePanel.colorPanel.controller.game.getPlayerList().get(col).getPieces().contains(piecePanel.piece) || (piecePanel.colorPanel.controller.game.getBoard().sumPiecePlacements(piecePanel.piece, piecePanel.colorPanel.color) == 0) || controller.currentPlayer.isAI()){
            // TODO: add label on the bottom of board
            System.out.println("You have already placed " + piecePanel.piece.getName().name() + " on the board or it is not playable");
        } else{
            if (piecePanel.colorPanel.color == controller.currentColor){
                if (controller.piecePanel != null) {
                    if (controller.game.getPlayerList().get(col).getPieces().contains(controller.piecePanel.piece)){
                        controller.piecePanel.isClicked = false;
                        controller.piecePanel.repaint();
                    }
                }
                piecePanel.isClicked = true;
                //System.out.println("Piece Type : " + piecePanel.piece.getName().name());
                controller.piece = piecePanel.piece;
                controller.color = piecePanel.colorPanel.color;
                controller.piecePanel = piecePanel;
                System.out.println(controller.piece.getName().name());
                piecePanel.repaint();
            }
            else {
                // TODO: add label on the bottom of board
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

    /**
     *  Mouse Entered
     *   - If hints are activated :
     *      If the mouse hovers over a piece, the positions where the piece can be placed
     *      will be shown on the board
     * */
    @Override
    public void mouseEntered(MouseEvent e) {
        //System.out.println("Mouse Entered" + e.getX() + " " + e.getY());
    }


    /**
     *  Mouse Exited
     *   - If hints are activated :
     *      the shown positions on the board will be reverted to the original board tile
     * */
    @Override
    public void mouseExited(MouseEvent e) {
        //System.out.println("Mouse Exited" + e.getX() + " " + e.getY());

    }
}
