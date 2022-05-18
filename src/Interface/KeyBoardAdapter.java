package Interface;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyBoardAdapter extends KeyAdapter {

    EventController controller;

    KeyBoardAdapter(EventController c) {
        controller = c;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_C:
                controller.command("clockwise");
                break;
            case KeyEvent.VK_Z:
            case KeyEvent.VK_W:
                controller.command("counterclockwise");
            case KeyEvent.VK_UP:
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                controller.command("horizontal");
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                controller.command("vertical");
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_Q:
                controller.command("quit");
                break;
            case KeyEvent.VK_P:
                controller.command("pause");
                break;
            case KeyEvent.VK_U:
                controller.command("undo");
                break;
            case KeyEvent.VK_R:
                controller.command("redo");
                break;
            case KeyEvent.VK_I:
                controller.command("ia");
                break;
            case KeyEvent.VK_ESCAPE:
                controller.command("fullscreen");
                break;
            case KeyEvent.VK_N:
                controller.command("newGame");
                break;
        }
    }
}

