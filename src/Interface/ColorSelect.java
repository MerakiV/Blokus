package Interface;

import java.io.IOException;

public class ColorSelect extends HoverButton {
    private String player;

    public ColorSelect(GameSelection selectMenu, String name, String player, int x, int y) throws IOException {
        super(selectMenu, name, x, y);
        this.player = player;
    }

    public String getPlayer(){
        return player;
    } 
}
