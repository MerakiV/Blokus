package Players;

import Structures.Board;
import Structures.Piece;

import

import java.util.ArrayList;

public abstract class Player {
    protected int score;
    protected ArrayList<Piece> pieces;
    protected Board board;

    public abstract Board play();
    public abstract boolean canPlay();

}
