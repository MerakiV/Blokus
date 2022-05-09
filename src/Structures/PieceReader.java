package Structures;

import java.io.FileNotFoundException;
import java.util.*;
import java.io.File;

public class PieceReader {
    Piece piece;
    Shape shape;
    List<Piece> pieceList;
    int Ncol;
    int Nlin;
    int anchorX;
    int anchorY;
    boolean [][] baseShape;

    boolean anchorFound = false;


    Scanner sc;
    File f;
    String token = null;
    String [] tokenArgs;
    int i = 0; //number of pieces
    int j; //counter of lines
    int k; //counter of columns
    char currentChar;

    PieceReader() throws FileNotFoundException {
        f = new File("resources/pieces/normal.txt");
        sc = new Scanner(f);
    }

    public void readPieces(){

        pieceList = new ArrayList<>();

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
            sc.useDelimiter(";");
            while (i < 21){ //number of pieces
                //System.out.println("\n");
                token = sc.next();
                //System.out.println(token);
                tokenArgs = token.split("\n");
                Nlin = Character.getNumericValue(tokenArgs[0].charAt(0));
                //System.out.println(Nlin);
                Ncol = Character.getNumericValue(tokenArgs[1].charAt(0));
                //System.out.println(Ncol);
                baseShape = new boolean[Nlin][Ncol]; //everything is initialized to 0
                for (j = 2; j < tokenArgs.length - 1; j++) {
                    currentChar = tokenArgs[j].charAt(0);
                    if (currentChar == '#' || currentChar == 'X' || currentChar == '-') {
                        for (k = 0; k < tokenArgs[j].length()-1; k++) {
                            currentChar = tokenArgs[j].charAt(k);
                            if (currentChar == '#' || currentChar == 'X') {
                                //System.out.println(tokenargs[j].charAt(k));
                                baseShape[j-2][k] = true;
                                //System.out.println(Arrays.deepToString(baseShape));
                                if (currentChar == 'X') {
                                    if (anchorFound) {
                                        System.out.println("Error, the piece named " +tokenArgs[j + 1]+" has several anchors.");
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
                    System.out.println("Error, the piece named "+tokenArgs[j + 1]+" has no anchor.");
                    System.exit(0);
                }
                //System.out.println(Arrays.deepToString(baseShape));

                shape = new Shape(Nlin, Ncol, anchorX, anchorY);
                shape.setShape(baseShape);
                //System.out.println(Arrays.deepToString(shape.getShape()));

                //shape.printShape();

                shape.setName(tokenArgs[j]); //set enum type?

                //System.out.println(shape.getName()); //name (shape + piece)

                piece = new Piece(shape);
                piece.setName(tokenArgs[j]); //set enum type?

                //p.printPiece();

                pieceList.add(piece);

                i++;
                sc.nextLine(); //space
                sc.nextLine(); //space
                anchorFound = false;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public List<Piece> getPiecesList(){
        return pieceList;
    }

    public void printPiecesList(){
        Piece p;
        int i = 0;
        System.out.println("---------- ALL PIECES : ------------");
        for (Piece value : pieceList) {
            System.out.println();
            p = value;
            System.out.println("----- Piece " + p.getName() + " : -----");
            System.out.println();
            p.printPiece();
            System.out.println();
        }
    }
}
