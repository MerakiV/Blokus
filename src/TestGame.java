import Structures.Color;
import Structures.Game;
import Structures.Game2P;
import Structures.GameSettings2P;

public class TestGame {
    public static void main(String[] args){
        //manually set game settings
        GameSettings2P settings = new GameSettings2P();
        settings.setP1Human();
        settings.setP2AI();
        settings.setP1Color1(Color.RED);
        settings.setP1Color2(Color.BLUE);
        settings.setP2Color1(Color.GREEN);
        settings.setP2Color2(Color.YELLOW);

        //create new game
        Game g = new Game2P(settings);
        Game2P g2p = (Game2P) g;
        System.out.println("Current player color : "+g2p.getCurrentColor());
        g2p.getBoard().printBoard(0);

    }
}
