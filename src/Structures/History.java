package Structures;

import java.io.Serializable;
import java.util.Stack;

public class History implements Serializable {
    Stack<GameState> past, future;

    public History() {
        past = new Stack<GameState>();
        future = new Stack<GameState>();
    }

    public boolean canUndo() {
        return !past.empty();
    }

    public GameState undo() {
        if (canUndo()) {
            GameState gs = past.pop();
            return gs;
        } else {
            return null;
        }
    }

    public boolean canRedo() {
        return !future.empty();
    }

    public GameState redo() {
        if (canRedo()) {
            GameState gs = future.pop();
            return gs;
        } else {
            return null;
        }
    }

    public void pushToPast(GameState gs){past.push(gs);}
    public void pushToFuture(GameState gs){future.push(gs);}
}
