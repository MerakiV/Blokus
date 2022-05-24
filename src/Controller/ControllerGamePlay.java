package Controller;

import GamePanels.BoardPanel;
import GamePanels.PiecePanel;
import Interface.EventController;
import Players.Player;
import Players.PlayerAI;
import Structures.*;
import Structures.Color;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;

public class ControllerGamePlay implements EventController, Runnable {
    public Game game;
    public Piece piece;
    public Color color;

    public Player currentPlayer;
    public Color currentColor;

    public PiecePanel piecePanel;

    public BoardPanel boardPanel;

    public JLabel tile;
    public ArrayList<Icon> originalImages;

    JFrame frame;
    Thread t;

    public ControllerGamePlay(){
        initialiseGame();
        piece = null;
        color = null;
        currentPlayer = game.getCurrentPlayer();
        currentColor = game.getCurrentColor();
        originalImages = new ArrayList<>();
    }

    public ControllerGamePlay(Game g, JFrame f){
        frame = f;
        game = g;
        piece = null;
        color = null;
        currentPlayer = game.getCurrentPlayer();
        currentColor = game.getCurrentColor();
        originalImages = new ArrayList<>();
    }

    public void startGame(){
        t = new Thread(this);
        t.start();
    }

    @Override
    public void run(){
        while (true) {
            //System.out.println(currentPlayer.getColor() + "'s turn");
            try {
                t.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(currentPlayer.isAI()) {
                System.out.println("AI playing");
                Move m = ((PlayerAI) currentPlayer).generateMove(game.getBoard());
                if(m != null) {
                    piece = m.getPiece();
                    color = currentColor;
                    int x = m.getTile().getX();
                    int y = m.getTile().getY();
                    piece.printPiece();
                    System.out.println("Board tile " + x + " " + y);
                    paintImage(y, x);
                    //paintImage();
                    put(x, y);
                    boardPanel.repaint();
                    frame.repaint();
                }
                else{
                    System.out.println("No more moves for AI");
                }
            }
        }
    }

    private void initialiseGame(){
        // Game Settings + Create Game
        setUpGameSettings();
        System.out.println("Finished InitialiseGame");
    }

    // TODO : to change once game settings completed
    public void setUpGameSettings(){
        GameSettings2P gameSettings2P = new GameSettings2P();
        gameSettings2P.setP1Color1(Structures.Color.YELLOW);
        gameSettings2P.setP1Color2(Structures.Color.RED);
        gameSettings2P.setP2Color1(Structures.Color.GREEN);
        gameSettings2P.setP2Color2(Structures.Color.BLUE);
        System.out.println("Finished Color");
        game = new Game2P(gameSettings2P);
        System.out.println("Finished SetUpGameSettings");
    }

    public boolean put(int y, int x){
        if (piece != null){
            if (game.put(piece, color,y,x)){
                System.out.println("Works");
                piece = null;
                color = null;
                nextTurn();
                System.out.println("It's " + currentColor);
                return true;
            }else
                System.out.println("Invalid");
        }
        return false;
    }
    public void nextTurn(){
        game.nextTurn();
        currentPlayer = game.getCurrentPlayer();
        System.out.println("Game Next turn :" +currentPlayer.getColor());
        currentColor = game.getCurrentColor();
        System.out.println("Game Next turn :" +currentColor);
        game.getBoard().printBoard(0);
    }

    void modifyBoardPanel(){
        replaceOriginal();
        paintImage();
    }


    public void paintImage(){
        Image image = getImage();
        String[] split = tile.getName().split(" ");
        int x = Integer.parseInt(split[0]);
        int y = Integer.parseInt(split[1]);
        boolean [][] shape = piece.getShape().shape;
        x -= piece.getShape().anchorY ;
        y -= piece.getShape().anchorX;
        if (x >=0 && x + piece.getShape().Ncol - 1 <=19 && y >= 0 && y + piece.getShape().Nlin - 1 <= 19){
            for (int i=0; i<piece.getShape().Nlin; i++) {
                int temp = x;
                for (int j=0; j<piece.getShape().Ncol; j++) {
                    originalImages.add(boardPanel.labels.get(temp + " " + y).getIcon());
                    if (shape[i][j]) {
                        boardPanel.labels.get(temp + " " + y).setIcon(new ImageIcon(image));
                        boardPanel.labels.get(temp + " " + y).repaint();
                    }
                    temp++;
                }
                y++;
            }
        }
    }

    public void paintImage(int x, int y){
        Image image = getImage();
        boolean [][] shape = piece.getShape().shape;
        x -= piece.getShape().anchorY ;
        y -= piece.getShape().anchorX;
        if (x >=0 && x + piece.getShape().Ncol - 1 <=19 && y >= 0 && y + piece.getShape().Nlin - 1 <= 19){
            for (int i=0; i<piece.getShape().Nlin; i++) {
                int temp = x;
                for (int j=0; j<piece.getShape().Ncol; j++) {
                    if (shape[i][j]) {
                        boardPanel.labels.get(temp + " " + y).setIcon(new ImageIcon(image));
                        boardPanel.labels.get(temp + " " + y).repaint();
                    }
                    temp++;
                }
                y++;
            }
        }
    }

    private Image getImage() {
        BufferedImage img;
        int tileSize = boardPanel.boardSize/20;
        Image resizedImage = null;
        try {
            InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(getPath());
            assert in != null;
            img = ImageIO.read(in);
            resizedImage = img.getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH);
        } catch (Exception e) {
            System.out.println("erreur dans le chargement des images:" + e);
        }
        return resizedImage;
    }

    private String getPath(){
        switch(color){
            case RED:
                return "tiles/RedBloc.png";
            case YELLOW:
                return "tiles/YellowBloc.png";
            case GREEN:
                return "tiles/GreenBloc.png";
            case BLUE:
                return "tiles/BlueBloc.png";
            case WHITE:
                return "tiles/boardTile.png";
        }
        return null;
    }

    public void replaceOriginal(){
        if (originalImages.isEmpty()){
            originalImages = boardPanel.originalImages;
        }
        String name =  tile.getName();
        System.out.println(name);
        String[] split = name.split(" ");
        int x = Integer.parseInt(split[0]);
        int y = Integer.parseInt(split[1]);
        boolean [][] shape = piece.getShape().shape;
        x -= piece.getShape().anchorY ;
        y -= piece.getShape().anchorX;
        if (x >=0 && x + piece.getShape().Ncol - 1 <=19 && y >= 0 && y + piece.getShape().Nlin - 1 <= 19){
            for (int i=0; i<piece.getShape().Nlin; i++) {
                int temp = x;
                for (int j=0; j<piece.getShape().Ncol; j++) {
                    boardPanel.labels.get(temp + " " + y).setIcon(originalImages.get(0));
                    originalImages.remove(0);
                    boardPanel.labels.get(temp + " " + y).repaint();
                    temp++;
                }
                y++;
            }
            System.out.println("replace original");
        }
        else {
            System.out.println("replace original failed : " + x + " " + y);
        }

    }

    void newGame(){
        // TODO: reset board and player turn to original
    }

    // TODO : fix redo / undo since it doesn't work even for the text board
    void redo(){
        game.redo();
        game.getBoard().printBoard(-1);
        //updateInterface();
    }


    void undo(){
        game.undo();
        game.getBoard().printBoard(-1);
        // updateInterface();
    }

    @Override
    public boolean command(String c) {
        switch (c) {
            case "clockwise":
                boardPanel.orientated = true;
                System.out.println("clockwise");
                replaceOriginal();
                piece.rotateClockwise();
                paintImage();
                boardPanel.repaint();
                break;
            case "counterclockwise":
                boardPanel.orientated = true;
                System.out.println("counterclockwise");
                replaceOriginal();
                piece.rotateCounterclockwise();
                paintImage();
                boardPanel.repaint();
                break;
            case "horizontal":
                boardPanel.orientated = true;
                System.out.println("horizontal");
                replaceOriginal();
                piece.flipH();
                paintImage();
                boardPanel.repaint();
                break;
            case "vertical":
                boardPanel.orientated = true;
                System.out.println("vertical");
                replaceOriginal();
                piece.flipV();
                paintImage();
                boardPanel.repaint();
                break;
            case "quit":
                System.out.println("quit");
                System.exit(0);
                break;
            case "undo":
                System.out.println("undo");
                undo();
                break;
            case "redo":
                System.out.println("redo");
                redo();
                break;
            case "pause":
                System.out.println("pause");
                break;
            case "fullscreen":
                System.out.println("fullscreen");
                break;
            case "ia":
                System.out.println("ia");
                break;
            case "next":
                System.out.println("next");
                break;
            case "newGame":
                System.out.println("newGame");
                break;
            case "hints":
                System.out.println("hints");
                break;
            default:
                return false;
        }
        return true;
    }

    public void setBoardPanel(BoardPanel b){
        if( b == null){
            throw new RuntimeException("Panel null");
        }
        this.boardPanel = b;

    }

}
