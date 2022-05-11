import Players.Player2P;
import Players.PlayerHuman2P;
import Structures.Color;
import Structures.PieceType;

public class TestPlayer {
    public static void main(String[] args){
        Player2P pl = new PlayerHuman2P(Color.RED, Color.BLUE);

        System.out.println("Player Test:");
        pl.pcol1.takePiece(PieceType.ONE);
        pl.pcol2.takePiece(PieceType.FIVE_T);
        pl.pcol1.printPlayer();
        pl.pcol2.printPlayer();
    }
}
