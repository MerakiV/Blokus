import Structures.*;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class TestShapeHash {
    public static void main(String[] args){
        //manually set game settings
        GameSettings2P settings = new GameSettings2P();
        //settings.setP1AI(0);
        settings.setP1Human();
        settings.setP2Human();
        //settings.setP2AI(0);
        settings.setP1Color1(Color.RED);
        settings.setP1Color2(Color.BLUE);
        settings.setP2Color1(Color.GREEN);
        settings.setP2Color2(Color.YELLOW);

        //create new game
        Game g = new Game2P(settings);
        Game2P g2p = (Game2P) g;
        System.out.println("Current player color : "+g2p.getCurrentColor());
        //g2p.getBoard().printBoard(0);

        String line;
        String [] arg;
        Scanner sc = new Scanner(System.in);
        boolean cont = true;
        int index, x,y, pl;
        List<Piece> lp;
        Piece cp;
        Random r = new Random();
        boolean playerChanged = false;

        Board bo = g2p.getBoard();

        lp = g.getCurrentPlayer().getPieces();
        index = r.nextInt(lp.size());
        cp = lp.get(index);
        while(cont) {
            if(g.getCurrentPlayer().isAI()){
                System.out.println("AI is playing... ");
                g.getCurrentPlayer().playPiece(bo);
                bo.printBoard(-1);
                g.nextTurn();
                System.out.print("[TestGame] ");
                sc.nextLine();
            }
            else {
                System.out.println("Current player color : " + g2p.getCurrentColor());
                System.out.print("[TestGame] ");
                index = r.nextInt(lp.size());
                cp = lp.get(index);
                lp = g.getCurrentPlayer().getPieces();
                playerChanged = false;
                while (sc.hasNext() && !playerChanged) {
                    line = sc.nextLine();
                    arg = line.split(" ");
                    switch (arg[0]) {
                        case "help":
                        case "h":
                            System.out.println("help             : prints this help");
                            System.out.println("board            : prints board");
                            System.out.println("board <x>        : prints board with player x' avaliable corners");
                            System.out.println("printp           : prints current piece");
                            System.out.println("takep <name>     : takes a piece from player's list according to PieceType name");
                            System.out.println("genp             : takes a random piece from player's list");
                            System.out.println("rotatep          : rotates current piece 90° clockwise");
                            System.out.println("flipvp           : flips current piece vertically");
                            System.out.println("fliphp           : flips current piece horizontally");
                            System.out.println("put <x> <y>      : tries to put the current piece for the player at coordinates (x,y)");
                            //System.out.println("fullcheck        : tries to put the current piece on ever tile and prints where it can");
                            System.out.println("quit             : quits");
                            break;
                        case "board":
                        case "b":
                            if (arg.length > 1) {
                                bo.printBoard(Integer.decode(arg[1]));
                            } else {
                                bo.printBoard(-1);
                            }
                            break;
                        case "bp":
                            pl = bo.getCorner(g.getCurrentColor());
                            System.out.println("Color code : " + pl);
                            bo.printBoard(pl);
                            break;
                        case "printp":
                        case "pp":
                            cp.printPiece();
                            break;
                        case "genp":
                        case "gp":
                            index = r.nextInt(lp.size());
                            cp = lp.get(index);
                            break;
                        case "takep":
                        case "tp":
                            cp = g.getCurrentPlayer().takePiece(PieceType.valueOf(arg[1]));
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
                            pl = bo.getCorner(g.getCurrentColor());
                            x = Integer.decode(arg[1]);
                            y = Integer.decode(arg[2]);
                            if (bo.checkAndPut(cp, pl, x, y)) {
                                System.out.println("Succesfully put.");
                                g.nextTurn();
                                playerChanged = true;
                            } else {
                                System.out.println("Couldn't put.");
                            }
                            break;
                        case "quit":
                        case "q":
                            cont = false;
                            playerChanged = true;
                            break;
                        case "plist":
                            g.getCurrentPlayer().printPlayer();
                            break;
                        case "hash":
                            System.out.println("Shape hash : "+cp.getShape().hashCode());
                            break;
                        default:
                            System.out.println("Couldn't recognize command.");
                    }
                    if (playerChanged) {
                        break;
                    }
                    System.out.println("Current player color : " + g2p.getCurrentColor());
                    System.out.print("[TestGame] ");
                }
            }
        }
        sc.close();

    }
}
