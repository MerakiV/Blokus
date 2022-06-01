package Structures;

import Players.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import java.io.Serializable;
import java.util.Stack;

public abstract class Game implements Serializable, Cloneable {
    Board board;
    ArrayList<Player> players;
    Player currentPlayer;
    Color currentColor;

    public History history;

    public GameState previous, next;

    public boolean end;

    public boolean put(Piece p, Color c, int x, int y) {
        System.out.println("Put line 25 color : " + c.name());
        boolean ok = put(p.getShape(), p.getName(), c, x, y);
        return ok;
    }

    public boolean put(Shape s, PieceType pt, Color c, int x, int y) {
        int i = board.getCorner(c);
        if (board.canPut(s, i, x, y)) {
            System.out.println("Can Place piece :" + pt.name() + " " + c.name());
            history.future.clear(); // clear future history
            Move last = (history.past.isEmpty() ? null : history.past.peek().nextMove);
            Move next = new Move(s,pt, new Tile(x,y));
            pushToPast(last,next);
            board.put(s, i, x, y);
            currentPlayer.removePiece(pt);
            return true;
        } else {
            System.out.println("Can't Place piece :" + pt.name() + " " + c.name());
            board.printBoard(-1);
            return false;
        }
    }

    //for IA MinMax
    public void directPut(Shape s, PieceType pt, Color c, int x, int y) {
        int i = board.getCorner(c);
        board.put(s, i, x, y);
        currentPlayer.removePiece(pt);
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
        previous = history.undo();
        Move lm = (previous == null ? null : previous.nextMove);
        Move nm = (history.future.isEmpty() ? null : history.future.peek().lastMove);
        pushToFuture(lm,nm);
        board = previous.board;
        players = previous.players;
        System.out.println("List of pieces of Player 0: " + players.get(0).getPieces());
        System.out.println("List of pieces of Player 1: " + players.get(1).getPieces());
        System.out.println("List of pieces of Player 2: " + players.get(2).getPieces());
        System.out.println("List of pieces of Player 3: " + players.get(3).getPieces());
        currentPlayer = previous.getCurrentPlayer();
        currentColor = currentPlayer.getColor();
        update2P();
    }

    public void redo() {
        next = history.redo();
        Move lm = (history.past.isEmpty() ? null : history.past.peek().nextMove);
        Move nm = (next == null ? null : next.lastMove);
        pushToPast(lm,nm);
        board = next.board;
        players = next.players;
        currentPlayer = next.getCurrentPlayer();
        currentColor = currentPlayer.getColor();
        update2P();
    }

    private void update2P(){
        Game2P g2p = (Game2P) this;

        g2p.p1.setPcol1(players.get(0));
        g2p.p1.setPcol2(players.get(2));
        g2p.p2.setPcol1(players.get(1));
        g2p.p2.setPcol2(players.get(3));

        g2p.p1.updateScore();
        g2p.p2.updateScore();

        System.out.println("P1 Color 1 Score : "+players.get(0).getScore());
        System.out.println("P1 Color 2 Score : "+players.get(2).getScore());
        System.out.println("P2 Color 1 Score : "+players.get(1).getScore());
        System.out.println("P2 Color 2 Score : "+players.get(3).getScore());

    }

    void pushToPast(Move lm, Move nm) {
        GameState gs = new GameState(this, lm, nm);
        history.pushToPast(gs);
    }

    void pushToFuture(Move lm, Move nm) {
        GameState gs = new GameState(this, lm, nm);
        history.pushToFuture(gs);
    }

    public abstract void nextTurn();

    @Override
    public Game clone() {
        return null;
    }

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
        this.end = g2.end;
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
