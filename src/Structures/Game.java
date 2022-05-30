package Structures;

import Players.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import java.io.Serializable;

public abstract class Game implements Serializable, Cloneable {
    Board board;
    ArrayList<Player> players;
    Player currentPlayer;
    Color currentColor;

    History history;

    public boolean end;

    public boolean put(Piece p, Color c, int x, int y) {
        boolean ok = put(p.getShape(), p.getName(), c, x, y);
        return ok;
    }

    public boolean put(Shape s, PieceType pt, Color c, int x, int y) {
        int i = board.getCorner(c);
        if (board.canPut(s, i, x, y)) {
            pushToPast();
            board.put(s, i, x, y);
            currentPlayer.removePiece(pt);
            return true;
        } else {
            return false;
        }
    }

    public void updateEnd() {
        boolean e = false;
        for (Player p : players) {
            e = e || p.hasMoves();
        }
        if (!e)
            System.out.println("No more moves for any player, game ending");
        setEnd(!e);
    }

    public boolean hasEnded() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Color getCurrentColor() {
        return currentColor;
    }

    public List<Player> getPlayerList() {
        return players;
    }

    public Board getBoard() {
        return board;
    }

    public void undo() {
        pushToFuture();
        GameState previous = history.undo();
        board = previous.board;
        players = previous.players;
        currentPlayer = previous.getCurrentPlayer();
        currentColor = currentPlayer.getColor();
        updatePlayer2P();
    }

    public void redo() {
        pushToPast();
        GameState next = history.redo();
        board = next.board;
        players = next.players;
        currentPlayer = next.getCurrentPlayer();
        currentColor = currentPlayer.getColor();
        updatePlayer2P();
    }

    void updatePlayer2P(){
        Game2P g2p = (Game2P) this;
        g2p.p1.setPcol1(g2p.getPlayerList().get(0));
        g2p.p1.setPcol2(g2p.getPlayerList().get(2));
        g2p.p2.setPcol1(g2p.getPlayerList().get(1));
        g2p.p2.setPcol2(g2p.getPlayerList().get(3));
    }

    void pushToPast() {
//        int pl = -1;
//        for (int i = 0; i < players.size() && pl == -1; i++) {
//            if (currentPlayer == players.get(i)) {
//                pl = i;
//            }
//        }
        GameState gs = new GameState((Game2P) this);
        history.pushToPast(gs);
    }

    void pushToFuture() {
//        int pl = -1;
//        for (int i = 0; i < players.size() && pl == -1; i++) {
//            if (currentPlayer == players.get(i)) {
//                pl = i;
//            }
//        }
        GameState gs = new GameState((Game2P) this);
        history.pushToFuture(gs);
    }

    public abstract void nextTurn();

    @Override
    abstract public Game clone();

    // Can be used in subclasses' clone method.
    public void cloneFields(Game g2) {
        this.board = (Board) g2.board.clone();
        this.currentPlayer = null;

        this.players = new ArrayList<>(g2.players.size());
        for (int i = 0; i < g2.players.size(); i++) {
            this.players.add((Player) g2.players.get(i).clone());
            if (g2.players.get(i) == g2.currentPlayer) {
                this.currentPlayer = this.players.get(i);
            }
        }

        this.currentColor = g2.currentColor;
        this.history = g2.history;
    }

    public void printGame(boolean boardOnly) {
        System.out.println("**** Gameboard :");
        board.printBoard(-1);

        if (!boardOnly) {
            System.out.println();

            int p = 1;
            Iterator<Player> it1 = players.iterator();
            Player pl;
            Iterator<Piece> it2;
            Piece pi;

            while (it1.hasNext()) {
                pl = it1.next();
                System.out.println("**** Color " + p + " pieces :");
                p++;

                it2 = pl.getPieces().iterator();
                while (it2.hasNext()) {
                    pi = it2.next();
                    pi.getShape().printShape();
                    System.out.println();
                }
            }
        }
    }

}
