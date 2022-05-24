package Interface;

import GamePanels.ColorPanel;
import Structures.PieceType;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Hashtable;

public class GameMouseAdapter implements MouseListener {

    GamePlayInterface game;
    component currentComponent;

    int positionX,positionY;

    enum component {
        BOARD,
        TOPLEFT,
        TOPRIGHT,
        BOTTOMLEFT,
        BOTTOMRIGHT,
        NONE
    }

    GameMouseAdapter(GamePlayInterface g) {
        game = g;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        positionX = e.getX();
        positionY = e.getY();
        currentComponent = checkComponent(e);
        switch(currentComponent){
            case BOARD:
                System.out.println("Mouse Clicked On Board " + positionX + " " + positionY);
                break;
            case TOPLEFT:
                topLeftClick();
                break;
            case TOPRIGHT:
                System.out.println("Mouse Clicked On Top Right Color Panel " + positionX + " " + positionY);
                break;
            case BOTTOMLEFT:
                System.out.println("Mouse Clicked On Bottom Left Color Panel" + positionX + " " + positionY);
                break;
            case BOTTOMRIGHT:
                System.out.println("Mouse Clicked On Bottom right Color Panel " + positionX + " " + positionY);
                break;
            default:
                break;
        }
    }

    public void topLeftClick(){
        int piecePanelSize = game.topLeftPanel.piecePanelSize.height;
        int x = positionX - game.topLeftX;
        int y = positionY - game.topLeftY;

    }

    public component checkComponent(MouseEvent e){
        if (game.boardX <= e.getX() && (game.boardX + game.boardPanel.boardSize) >= e.getX()
                && game.boardY <= e.getY() && (game.boardY + game.boardPanel.boardSize) >= e.getY()){
            return component.BOARD;
        } // TOP LEFT PANEL
        else if (game.topLeftX <= e.getX() && (game.topLeftX + game.colorPanelSize.width) >= e.getX()
                && game.topLeftY <= e.getY() && (game.topLeftY + game.colorPanelSize.height) >= e.getY()){
            return component.TOPLEFT;
        } // BOTTOM LEFT PANEL
        else if (game.bottomLeftX <= e.getX() && (game.bottomLeftX + game.colorPanelSize.width) >= e.getX()
                && game.bottomLeftY <= e.getY() && (game.bottomLeftY + game.colorPanelSize.height) >= e.getY()){
            return component.BOTTOMLEFT;
        } // TOP RIGHT PANEL
        else if (game.topRightX <= e.getX() && (game.topRightX + game.colorPanelSize.width) >= e.getX()
                && game.topRightY <= e.getY() && (game.topRightY + game.colorPanelSize.height) >= e.getY()){
            return component.TOPRIGHT;
        } // BOTTOM RIGHT PANEL
        else if (game.bottomRightX <= e.getX() && (game.bottomRightX + game.colorPanelSize.width) >= e.getX()
                && game.bottomRightY <= e.getY() && (game.bottomRightY + game.colorPanelSize.height) >= e.getY()){
            return component.BOTTOMRIGHT;
        }
        return component.NONE;
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