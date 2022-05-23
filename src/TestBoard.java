import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import Structures.*;




class TestBoard {
    public static void main(String [] args) {
        System.out.println("Type h for help.");
        System.out.println("Note : this console is very NOT robust. Be careful what you type.");
        Boolean cont = true;

        Integer x, y, pl, index;
        String line;
        String [] arg;
        Scanner sc = new Scanner(System.in);

        Tile t;
        Iterator<Tile> it;
        Set<Tile> li;

        Board bo = new Board(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
        // Color [] col = { Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW };

        Random r = new Random();
        PieceReader pr = null;
        try { pr = new PieceReader(); } catch(Exception e) { System.out.println(e.toString()); System.exit(0); }
        List<Piece> lp = pr.getPiecesList();
        index = 0;
        Piece cp = lp.get(index);

        System.out.print("[TestBoard] ");
        while(sc.hasNext() && cont) {
            line = sc.nextLine();
            arg = line.split(" ");
            switch(arg[0]) {
                case "help":
                case "h":
                    System.out.println("help             : prints this help");
                    System.out.println("board            : prints board");
                    System.out.println("board <x>        : prints board with player x' avaliable corners");
                    System.out.println("printp           : prints current piece");
                    System.out.println("nextp            : generates a new current piece (next in the list)");
                    System.out.println("genp             : generates a new current piece (random)");
                    System.out.println("rotatep          : rotates current piece 90° clockwise");
                    System.out.println("flipvp           : flips current piece vertically");
                    System.out.println("fliphp           : flips current piece horizontally");
                    System.out.println("put <pl> <x> <y> : tries to put the current piece for the given player ([0,4[) at coordinates (x,y)");
                    System.out.println("fullcheck <pl>   : tries to put the current piece on ever tile and prints where it can");
                    System.out.println("quit             : quits");
                    break;
                case "board":
                case "b":
                    if (arg.length>1) {
                        bo.printBoard(Integer.decode(arg[1]));
                    } else {
                        bo.printBoard(-1);
                    }
                    break;
                case "printp":
                case "pp":
                    cp.printPiece();
                    break;
                case "genp":
                case "gp":
                    index = r.nextInt(lp.size());
                    cp = lp.get(index);
                    cp.printPiece();
                    break;
                case "nextp":
                case "np":
                    index = (index + 1) % lp.size();
                    cp = lp.get(index);
                    cp.printPiece();
                    break;
                case "rotatep":
                case "rp":
                    cp.rotateClockwise();
                    cp.printPiece();
                    break;
                case "flipvp":
                case "fvp":
                    cp.flipV();
                    cp.printPiece();
                    break;
                case "fliphp":
                case "fhp":
                    cp.flipH();
                    cp.printPiece();
                    break;
                case "put":
                case "p":
                    try {
                        pl = Integer.decode(arg[1]);
                        x  = Integer.decode(arg[2]);
                        y  = Integer.decode(arg[3]);
                    } catch(Exception e) {
                        System.out.println("Unable to recognize arguments as numbers.");
                        break;
                    }
                    if (bo.checkAndPut(cp, pl, x, y)) {
                        System.out.println("Succesfully put.");
                    } else {
                        System.out.println("Couldn't put.");
                    }
                    break;
                case "fullcheck":
                case "fc":
                    pl = ((arg.length>1) ? (Integer.decode(arg[1])) : 0);
                    li = bo.fullcheck(cp, pl);
                    if (li.isEmpty()) {
                        System.out.println("This current piece can't be put anywhere.");
                    } else {
                        it = li.iterator();
                        while(it.hasNext()) {
                            t = it.next();
                            System.out.println("This current piece can be put on ("+t.getX()+","+t.getY()+")");
                        }
                    }
                    break;
                case "quit":
                case "q":
                    cont = false;
                    break;
                default:
                    System.out.println("Couldn't recognize command.");
            }
            if (!cont) { break; }
            System.out.print("[TestBoard] ");
        }
        sc.close();
    }
}
