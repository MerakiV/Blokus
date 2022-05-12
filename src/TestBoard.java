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
                case "help":
                case "h":
                    System.out.println("help             : prints this help");
                    System.out.println("board            : prints board");
                    System.out.println("board <x>        : prints board with player x' avaliable corners");
                    System.out.println("printp           : prints current piece");
                    System.out.println("genp             : generates a new current piece");
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
                    cp = lp.get(r.nextInt(lp.size()));
                    break;
                case "rotatep":
                case "rp":
                    cp.rotateClockwise();
                    break;
                case "flipvp":
                case "fvp":
                    cp.flipV();
                    break;
                case "fliphp":
                case "fhp":
                    cp.flipH();
                    break;
                case "put":
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
                case "fullcheck":
                case "fc":
                    pl = ((arg.length>1) ? (Integer.decode(arg[1])) : 0);
                    for (x=0; x<20; x++) {
                        for (y=0; y<20; y++) {
                            if (bo.canPut(cp, pl, x, y)) {
                                System.out.println("Can put current piece on ("+x+","+y+")");
                            }
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