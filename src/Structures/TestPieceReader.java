package Structures;

public class TestPieceReader {
    static PieceReader pr;

    public static void main(String[] args) throws Exception
    {
        PieceReader pr = new PieceReader();
        pr.readPieces();

        pr.printPiecesList();
    }


}