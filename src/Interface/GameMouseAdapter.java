package Interface;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameMouseAdapter implements MouseListener {

    GamePlayInterface game;
    component currentComponent;

    HoverButton menu, hint, redo, undo, save;
    int positionX,positionY;
    boolean clicked = false;

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
        SAVE,
        NONE
    }

    GameMouseAdapter(GamePlayInterface g, HoverButton b1, HoverButton b2, HoverButton b3, HoverButton b4, HoverButton b5){
        game = g;
        menu = b1;
        hint = b2;
        redo = b3;
        undo = b4;
        save = b5;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        positionX = e.getX();
        positionY = e.getY();
        currentComponent = checkComponent(e);
        System.out.println("Mouse Clicked On Board " + positionX + " " + positionY);
        System.out.println(currentComponent.name());
        switch(currentComponent){
            case BOARD:
                // System.out.println("Mouse Clicked On Board " + positionX + " " + positionY);
                break;
            case TOPLEFT:
                // System.out.println("Mouse Clicked On Top Left Color Panel " + positionX + " " + positionY);
                break;
            case TOPRIGHT:
                // System.out.println("Mouse Clicked On Top Right Color Panel " + positionX + " " + positionY);
                break;
            case BOTTOMLEFT:
                // System.out.println("Mouse Clicked On Bottom Left Color Panel" + positionX + " " + positionY);
                break;
            case BOTTOMRIGHT:
                // System.out.println("Mouse Clicked On Bottom right Color Panel " + positionX + " " + positionY);
                break;
            case MENU:
                // System.out.println("Mouse Clicked On Board " + positionX + " " + positionY);
                // System.out.println("Menu button Clicked");
                game.play = true;
                game.controller.piece = null;
                game.repaint();
                break;
            case HINTS:
                // System.out.println("Mouse Clicked On Board " + positionX + " " + positionY);
                System.out.println("Hint button Clicked");
                clicked = !clicked;
                if (clicked) {
                    game.hint.currentImage = game.hint.rolloverImage;
                } else {
                    game.hint.currentImage = game.hint.normalImage;
                }
                game.controller.hintsActivated = (!game.controller.hintsActivated);
                game.repaint();
                break;
            case REDO:
                 System.out.println("REDO button Clicked");
                game.controller.command("redo");
                break;
            case UNDO:
                 System.out.println("UNDO button Clicked");
                game.controller.command("undo");
                break;
            case SAVE:
                 System.out.println("Save button Clicked");

                break;
            default:
                break;
        }
    }

    public component checkComponent(MouseEvent e){
        Integer width = game.frame.getWidth();
        Integer height = game.frame.getHeight();
        System.out.println(((int) (width * 0.55) - 50) + " "+ (((int) (width * 0.55) - 50 + (int) (height * 0.055))));
        System.out.println((int) (height * 0.85) + " "+ ((int) (height * 0.85)+(int) (height * 0.055)));
        // BOARD
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
        else if (1223 <= e.getX() && (1223+menu.getCurrentImageWidth()) >= e.getX()
                && 60 <= e.getY() && (60 + menu.getCurrentImageHeight())>= e.getY()){
            return component.MENU;
        } // HINT BUTTON
        else if (67 <= e.getX() && (67+hint.getCurrentImageWidth()) >= e.getX()
                && 60 <= e.getY() && (60 + hint.getCurrentImageHeight())>= e.getY()){
            return component.HINTS;
        } // REDO BUTTON
        else if ((int) (width * 0.6 - 50) <= e.getX() && ((int) (width * 0.6 - 50) + (int) (height * 0.055)) >= e.getX()
                && (int) (height * 0.85) <= e.getY() && ((int) (height * 0.85)+(int) (height * 0.055))>= e.getY()){
            return component.REDO;
        } // UNDO BUTTON
        else if ((int) (width * 0.45) - 50 <= e.getX() && ((int) (width * 0.45) - 50 + (int) (height * 0.055)) >= e.getX()
                && (int) (height * 0.85) <= e.getY() && ((int) (height * 0.85)+(int) (height * 0.055))>= e.getY()){
            return component.UNDO;
        } // SAVE BUTTON
        else if (((int) (width * 0.5 - 40)) <= e.getX() &&  ((int) (width * 0.5 + 40)) >= e.getX()
                && ((int) (height * 0.85)) <= e.getY() && ((int) (height * 0.85)+ 39) >= e.getY()){
            System.out.println("Save");
            return component.SAVE;
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