import Interface.GameInterface;

import javax.swing.*;

class Blokus {
    public static void main(String[] args){
        GameInterface gameInterface = new GameInterface();
        SwingUtilities.invokeLater(gameInterface);
    }
}