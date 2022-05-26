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

    HoverButton current;

    MenuInterface menu;

    int positionX,positionY;

    enum component {
        BOARD,
        TOPLEFT,
        TOPRIGHT,
        BOTTOMLEFT,
        BOTTOMRIGHT,
        MENU,
        HINTS,
        REDO,
        UNDO,
        NONE
    }

    GameMouseAdapter(GamePlayInterface g) {
        game = g;
    }

    GameMouseAdapter(GamePlayInterface g, HoverButton b){
        game = g;
        current = b;

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        positionX = e.getX();
        positionY = e.getY();
        currentComponent = checkComponent(e);
        System.out.println(currentComponent);
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
            case MENU:
                System.out.println("Mouse Clicked On Board " + positionX + " " + positionY);
                System.out.println("Menu button Clicked");
                game.play = true;
                game.repaint();
                break;
            case HINTS:
                System.out.println("Mouse Clicked On Board " + positionX + " " + positionY);
                System.out.println("Hint button Clicked");
                break;
            case REDO:
                System.out.println("Mouse Clicked On Board " + positionX + " " + positionY);
                System.out.println("REDO button Clicked");
                game.controller.command("redo");
                break;
            case UNDO:
                System.out.println("Mouse Clicked On Board " + positionX + " " + positionY);
                System.out.println("UNDO button Clicked");
                game.controller.command("undo");
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
        }// MENU BUTTON
        else if (1223 <= e.getX() && (1223+current.getCurrentImageWidth()) >= e.getX()
                && 60 <= e.getY() && (60 + current.getCurrentImageHeight())>= e.getY()){
            return component.MENU;
        } // HINT BUTTON
        else if (67 <= e.getX() && (67+current.getCurrentImageWidth()) >= e.getX()
                && 60 <= e.getY() && (60 + current.getCurrentImageHeight())>= e.getY()){
            return component.HINTS;
        } // UNDO BUTTON
        else if (554 <= e.getX() && (554+current.getCurrentImageWidth()) >= e.getX()
                && 642 <= e.getY() && (642 + current.getCurrentImageHeight())>= e.getY()){
            return component.UNDO;
        } // REDO BUTTON
        else if (689 <= e.getX() && (689+current.getCurrentImageWidth()) >= e.getX()
                && 642 <= e.getY() && (642 + current.getCurrentImageHeight())>= e.getY()){
            return component.REDO;
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
//        System.out.println("Mouse Entered" + e.getX() + " " + e.getY());
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //System.out.println("Mouse Exited" + e.getX() + " " + e.getY());
    }


}