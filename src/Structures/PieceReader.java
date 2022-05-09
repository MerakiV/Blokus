package Structures;

import java.io.FileNotFoundException;
import java.util.*;
import java.io.File;

public class PieceReader {

    Scanner s;
    Piece p;
    Shape shape;
    List<Piece> l;
    File f;

    int Ncol;
    int Nlin;
    int anchorX;
    int anchorY;
    boolean [][] baseShape;

    int i = 0; //number of pieces
    int j; //counter of lines
    int k; //counter of columns
    char currentChar;
    String token = null;
    String [] tokenargs;
    boolean anchorFound = false;


    PieceReader() throws FileNotFoundException {
        f = new File("resources/pieces/normal.txt");
        s = new Scanner(f);
    }

    public void readPieces(){

        l = new ArrayList<>();

        /* Model of the file:
         * HEIGHT (int)
         * LENGTH (int)
         * #---
         * #X##
         * NAME
         *
         * Empty lines separate the pieces
         * # : pentamino square
         * - : empty spaces
         * X : anchor piece
         */

        try { //init
            s.useDelimiter(";");
            while (i < 21){ //number of pieces
                //System.out.println("\n");
                token = s.next();
                //System.out.println(token);
                tokenargs = token.split("\n");
                Nlin = Character.getNumericValue(tokenargs[0].charAt(0));
                //System.out.println(Nlin);
                Ncol = Character.getNumericValue(tokenargs[1].charAt(0));
                //System.out.println(Ncol);
                baseShape = new boolean[Nlin][Ncol]; //everything is initialized to 0
                for (j = 2; j < tokenargs.length - 1; j++) {
                    currentChar = tokenargs[j].charAt(0);
                    if (currentChar == '#' || currentChar == 'X' || currentChar == '-') {
                        for (k = 0; k < tokenargs[j].length()-1; k++) {
                            currentChar = tokenargs[j].charAt(k);
                            if (currentChar == '#' || currentChar == 'X') {
                                //System.out.println(tokenargs[j].charAt(k));
                                baseShape[j-2][k] = true;
                                //System.out.println(Arrays.deepToString(baseShape));
                                if (currentChar == 'X') {
                                    if (anchorFound) {
                                        System.out.println("Error, the piece named " +tokenargs[j + 1]+" has several anchors.");
                                        System.out.println("Anchors must be unique for every piece.");
                                        System.exit(0);
                                    }
                                    anchorFound = true;
                                    anchorX = j - 2;
                                    anchorY = k;
                                }
                            }
                        }
                        //System.out.println(tokenargs[j]);
                    }
                }
                if (!anchorFound) {
                    System.out.println("Error, the piece named "+tokenargs[j + 1]+" has no anchor.");
                    System.exit(0);
                }
                //System.out.println(Arrays.deepToString(baseShape));

                shape = new Shape(Nlin, Ncol, anchorX, anchorY);
                shape.setShape(baseShape);
                //System.out.println(Arrays.deepToString(shape.getShape()));

                //shape.printShape();

                shape.setName(tokenargs[j]); //set enum type?

                //System.out.println(shape.getName()); //name (shape + piece)

                p = new Piece(shape);
                p.setName(tokenargs[j]); //set enum type?

                //p.printPiece();

                l.add(p);

                i++;
                s.nextLine(); //space
                s.nextLine(); //space
                anchorFound = false;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public List<Piece> getPiecesList(){
        return l;
    }

    public void printPiecesList(){
        Piece p;
        int i = 0;
        System.out.println("---------- ALL PIECES : ------------");
        Iterator<Piece> it = l.iterator();
        while(it.hasNext()) {
            System.out.println();
            p = it.next();
            System.out.println("----- Piece " + p.getName() + " : -----");
            System.out.println();
            p.printPiece();
            System.out.println();
        }
    }
}
