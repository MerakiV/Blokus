package Structures;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;
import java.io.File;

public class PieceReader {
    Piece piece;
    Shape shape;
    ArrayList<Piece> pieceList;
    PieceType pt;
    TileType tt;
    int Ncol;
    int Nlin;
    int anchorX;
    int anchorY;

    int value = 0;
    boolean [][] baseShape;

    boolean anchorFound = false;


    Scanner sc;
    String token = null;
    String [] tokenArgs;

    int nbPieces;
    int i = 0; //counter of piece
    int j; //counter of lines
    int k; //counter of columns
    char currentChar;

    public PieceReader() throws FileNotFoundException {
        InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream("pieces/normal.txt");
        sc = new Scanner(in);
        readPieces();
    }

    public void readPieces(){

        pieceList = new ArrayList<>();

        /* Model of the file:
         *
         * At the beginning :
         * NUMBER OF PIECES (int)
         *
         * And then for every piece:
         * HEIGHT (int)
         * LENGTH (int)
         * #---
         * #X##
         * NAME
         *
         * Empty lines separate the pieces
         * # : square of a piece
         * - : empty spaces
         * X : anchor part
         */

        try {
            nbPieces = Integer.parseInt(sc.nextLine());
            if(nbPieces < 21){
                System.out.println("The number of pieces is incorrect");
                System.exit(1);
            }
            sc.nextLine(); //empty line

            sc.useDelimiter(";");
            while (i < nbPieces){
                token = sc.next();
                tokenArgs = token.split("\n");
                Nlin = Character.getNumericValue(tokenArgs[0].charAt(0));
                Ncol = Character.getNumericValue(tokenArgs[1].charAt(0));
                baseShape = new boolean[Nlin][Ncol]; //everything is initialized to false
                for (j = 2; j < tokenArgs.length - 1; j++) {
                    currentChar = tokenArgs[j].charAt(0);
                    if (currentChar == '#' || currentChar == 'X' || currentChar == '-') {
                        for (k = 0; k < tokenArgs[j].length(); k++) {
                            currentChar = tokenArgs[j].charAt(k);
                            if (currentChar == '#' || currentChar == 'X') {
                                baseShape[j-2][k] = true;
                                value++;
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
                    }
                }
                if (!anchorFound) {
                    System.out.println("Error, the piece named "+tokenArgs[j + 1]+" has no anchor.");
                    System.exit(0);
                }

                shape = new Shape(Nlin, Ncol, anchorX, anchorY, baseShape);
                shape.setName(tt.valueOf(tokenArgs[j])); //TBI set enum type?

                piece = new Piece(shape);
                piece.setName(pt.valueOf(tokenArgs[j]));
                piece.setValue(value);

                pieceList.add(piece);

                i++;
                sc.nextLine();
                sc.nextLine();
                anchorFound = false;
                value = 0;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public ArrayList<Piece> getPiecesList(){
        return pieceList;
    }
    public void setPiecesList(ArrayList<Piece> p){ pieceList = p; }

    public void printPiecesList(){
        Piece p;
        System.out.println("---------- ALL PIECES : ------------");
        for (Piece value : pieceList) {
            System.out.println();
            p = value;
            System.out.println("----- Piece " + p.getName() + " : -----");
            System.out.println();
            p.printPieceFull();
            System.out.println();
        }
    }
}
