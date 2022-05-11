import java.util.List;
import java.util.Random;
import java.util.Scanner;

import Structures.*;




class TestBoard {
    public static void main(String [] args) {
        System.out.println("Type h for help.");
        System.out.println("Note : this console is very NOT robust. Be careful what you type.");
        Boolean cont = true;

        Integer x, y, pl;
        String line;
        String [] arg;
        Scanner sc = new Scanner(System.in);

        Board bo = new Board(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
        // Color [] col = { Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW };

        Random r = new Random();
        PieceReader pr = null;
        try { pr = new PieceReader(); } catch(Exception e) { System.out.println(e.toString()); System.exit(0); }
        List<Piece> lp = pr.getPiecesList();
        Piece cp = lp.get(r.nextInt(lp.size()));

        System.out.print("[TestBoard] ");
        while(sc.hasNext() && cont) {
            line = sc.nextLine();
            arg = line.split(" ");
            switch(arg[0]) {
                case "h":
                    System.out.println("h : prints this help");
                    System.out.println("b : prints board");
                    System.out.println("b <x> : prints board with player x' avaliable corners");
                    System.out.println("pp : prints current piece");
                    System.out.println("gp : generates a new current piece");
                    System.out.println("rp : rotates current piece 90° clockwise");
                    System.out.println("fvp : flips current piece vertically");
                    System.out.println("fhp : flips current piece horizontally");
                    System.out.println("p <player> <x> <y> : tries to put the current piece for the given player ([0,4[) at coordinates (x,y)");
                    System.out.println("q : quits");
                    break;
                case "b":
                    if (arg.length>1) {
                        bo.printBoard(Integer.decode(arg[1]));
                    } else {
                        bo.printBoard(-1);
                    }
                    break;
                case "pp":
                    cp.printPiece();
                    break;
                case "gp":
                    cp = lp.get(r.nextInt(lp.size()));
                    break;
                case "rp":
                    cp.rotateClockwise();
                    break;
                case "fvp":
                    cp.flipV();
                    break;
                case "fhp":
                    cp.flipH();
                    break;
                case "p":
                    pl = Integer.decode(arg[1]);
                    x  = Integer.decode(arg[2]);
                    y  = Integer.decode(arg[3]);
                    if (bo.checkAndPut(cp, pl, x, y)) {
                        System.out.println("Succesfully put.");
                    } else {
                        System.out.println("Couldn't put.");
                    }
                    break;
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