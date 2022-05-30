package Controller;

import GamePanels.BoardPanel;
import GamePanels.PiecePanel;
import Interface.DrawString;
import Interface.EventController;
import Interface.GamePlayInterface;
import Players.Player;
import Players.PlayerAI;
import Structures.Color;
import Structures.Shape;
import Structures.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;

public class ControllerGamePlay implements EventController, Runnable {
    public Game game, originalGame;
    public Piece piece, hoveredPiece;
    public Color color, currentColor;
    public GamePlayInterface gamePlayInterface;

    public Player currentPlayer;

    public PiecePanel piecePanel;
    public ArrayList<Piece> pieces;

    public BoardPanel boardPanel;

    public JLabel tile;
    public ArrayList<Icon> originalImages;

    public boolean hintsActivated = false;

    public String errorMessage = "";

    JFrame frame;
    public Thread t;
    Shape shape;

    Save saveGame;

    /**
     *  Controller Game Play
     *      Version before color selection
     * */
    public ControllerGamePlay() {
        initialiseGame();
        piece = null;
        color = null;
        currentPlayer = game.getCurrentPlayer();
        currentColor = game.getCurrentColor();
        originalImages = new ArrayList<>();
    }

    public ControllerGamePlay(Game g, JFrame f) {
        frame = f;
        game = g;
        originalGame = g;
        piece = null;
        color = null;
        initPieces();
        currentPlayer = game.getCurrentPlayer();
        currentColor = game.getCurrentColor();
        originalImages = new ArrayList<>();
        saveGame = new Save(g,"save.dat");
    }

    /**
     *  Initialise Pieces
     *      creates a list of the pieces to be used to show all pieces in the color panel
     *      when a game is continued
     * */
    void initPieces() {
        PieceReader pRead;
        try {
            pRead = new PieceReader();
            pieces = pRead.getPiecesList();
        } catch (Exception e) {
            System.err.println("FATAL ERROR : missing piece file");
            System.exit(1);
        }
    }

    public void startGame() {
        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        try {
            endRun();
            //noEndRun();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    long refreshTime(boolean ai) {
        return ai ? 500 : 32;
    }

    synchronized Move generateMove(){
        Move m = ((PlayerAI) currentPlayer).generateMove(game);
        //System.out.println("Move calculated");
        return m;
    }

    public void endRun() throws InterruptedException {
        boolean allAI = true;
        for (Player p : game.getPlayerList()) {
            allAI = allAI && p.isAI();
        }
        long lag = refreshTime(allAI);
        while (!game.hasEnded()) {
            // System.out.println(currentPlayer.getColor() + "'s turn");
            try {
                t.sleep(lag);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (currentPlayer.isAI()) {
                if (currentPlayer.checkForMoves(game.getBoard())) {
                    Move m = generateMove();
                    if (m != null) {
                        shape = m.getShape();
                        piece = new Piece(shape);
                        piece.setName(m.getPieceType());
                        color = currentColor;
                        int x = m.getTile().getX();
                        int y = m.getTile().getY();
                        // piece.printPiece();
                        // System.out.println("Board tile " + x + " " + y);
                        paintImage(y, x);
                        put(x, y);
                        boardPanel.repaint();
                    }
                    else{
                        errorMessage = currentPlayer.getColor()+": Move generated is null";
                        System.out.println(errorMessage);
                        gamePlayInterface.repaint();
                    }
                } else {
                    errorMessage = "No more moves for AI " + currentPlayer.getColor();
                    System.out.println(errorMessage);
                    gamePlayInterface.repaint();
                }
                nextTurn();
                frame.repaint();
                game.updateEnd();
            } else { // not AI
                if (!currentPlayer.checkForMoves(game.getBoard())) {
                    errorMessage = "No more moves for Player " + currentPlayer.getColor();
                    System.out.println(errorMessage);
                    gamePlayInterface.repaint();
                    nextTurn();
                    frame.repaint();
                    game.updateEnd();
                }
            }
        }
        // game has ended
        errorMessage = "Game over";
        System.out.println(errorMessage);
        gamePlayInterface.repaint();
    }

    public void noEndRun() {
        boolean allAI = true;
        for (Player p : game.getPlayerList()) {
            allAI = allAI && p.isAI();
        }
        long lag = refreshTime(allAI);
        while (true) {
            // System.out.println(currentPlayer.getColor() + "'s turn");
            try {
                t.sleep(lag);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (currentPlayer.isAI()) {
                System.out.println("AI playing");
                Move m = ((PlayerAI) currentPlayer).generateMove(game);
                if (m != null) {
                    shape = m.getShape();
                    piece = new Piece(shape);
                    piece.setName(m.getPieceType());
                    color = currentColor;
                    int x = m.getTile().getX();
                    int y = m.getTile().getY();
                    piece.printPiece();
                    System.out.println("Board tile " + x + " " + y);
                    paintImage(y, x);
                    put(x, y);
                    boardPanel.repaint();
                } else {
                    System.out.println("No more moves for AI");
                }
                nextTurn();
                frame.repaint();
            }
        }
    }

    private void initialiseGame() {
        // Game Settings + Create Game
        setUpGameSettings();
        System.out.println("Finished InitialiseGame");
    }

    public void setUpGameSettings() {
        GameSettings2P gameSettings2P = new GameSettings2P();
        gameSettings2P.setP1Color1(Structures.Color.YELLOW);
        gameSettings2P.setP1Color2(Structures.Color.RED);
        gameSettings2P.setP2Color1(Structures.Color.GREEN);
        gameSettings2P.setP2Color2(Structures.Color.BLUE);
        System.out.println("Finished Color");
        game = new Game2P(gameSettings2P);
        System.out.println("Finished SetUpGameSettings");
    }

    public boolean put(int y, int x) {
        if (piece != null) {
            boardPanel.removePositions();
            if (game.put(piece, color, y, x)) {
                piece = null;
                color = null;
                // System.out.println("Works");
                // System.out.println("It's " + currentColor);
                return true;
            } else{
                System.out.println("Invalid");
                errorMessage = "Invalid piece placement";
                gamePlayInterface.repaint();
            }

        }
        return false;
    }

    /**
     *  Next Turn
     *      Goes to the next Player
     **/
    public void nextTurn() {
        game.nextTurn();
        currentPlayer = game.getCurrentPlayer();
        currentColor = game.getCurrentColor();
        errorMessage = "";
        // System.out.println("Game Next turn :" +currentPlayer.getColor());
        // System.out.println("Game Next turn :" +currentColor);
        // game.getBoard().printBoard(game.getBoard().getCorner(currentColor));
    }

    public void paintImage() {
        Image image = getImage();
        String[] split = tile.getName().split(" ");
        int x = Integer.parseInt(split[0]);
        int y = Integer.parseInt(split[1]);
        boolean[][] shape = piece.getShape().shape;
        x -= piece.getShape().anchorY;
        y -= piece.getShape().anchorX;
        if (x >= 0 && x + piece.getShape().Ncol - 1 <= 19 && y >= 0 && y + piece.getShape().Nlin - 1 <= 19) {
            for (int i = 0; i < piece.getShape().Nlin; i++) {
                int temp = x;
                for (int j = 0; j < piece.getShape().Ncol; j++) {
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

    public void paintImage(int x, int y) {
        Image image = getImage();
        boolean[][] shape = piece.getShape().shape;
        x -= piece.getShape().anchorY;
        y -= piece.getShape().anchorX;
        if (x >= 0 && x + piece.getShape().Ncol - 1 <= 19 && y >= 0 && y + piece.getShape().Nlin - 1 <= 19) {
            for (int i = 0; i < piece.getShape().Nlin; i++) {
                int temp = x;
                for (int j = 0; j < piece.getShape().Ncol; j++) {
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

    /**
     *  Get Image
     *      Returns the corresponding image of the tile at the position given in input
     **/
    private Image getImage() {
        BufferedImage img;
        int tileSize = boardPanel.boardSize / 20;
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

    /**
     *  Get Path
     *      Returns the path to the corresponding image in resources
     * */
    private String getPath() {
        switch (color) {
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

    public void replaceOriginal() {
        if (originalImages.isEmpty()) {
            originalImages = boardPanel.originalImages;
        }
        String name = tile.getName();
        System.out.println(name);
        String[] split = name.split(" ");
        int x = Integer.parseInt(split[0]);
        int y = Integer.parseInt(split[1]);
        x -= piece.getShape().anchorY;
        y -= piece.getShape().anchorX;
        if (x >= 0 && x + piece.getShape().Ncol - 1 <= 19 && y >= 0 && y + piece.getShape().Nlin - 1 <= 19) {
            for (int i = 0; i < piece.getShape().Nlin; i++) {
                int temp = x;
                for (int j = 0; j < piece.getShape().Ncol; j++) {
                    boardPanel.labels.get(temp + " " + y).setIcon(originalImages.get(0));
                    originalImages.remove(0);
                    boardPanel.labels.get(temp + " " + y).repaint();
                    temp++;
                }
                y++;
            }
            System.out.println("replace original");
        } else {
            System.out.println("replace original failed : " + x + " " + y);
        }

    }

    void newGame() {
        // TODO: reset board and player turn to original
        game = originalGame;
        piece = null;
        color = null;
        initPieces();
        currentPlayer = game.getCurrentPlayer();
        currentColor = game.getCurrentColor();
        originalImages = new ArrayList<>();
        piece = hoveredPiece = null;
        hintsActivated = false;
    }

    void redo() {
        game.redo();
        piece = hoveredPiece = null;
        currentPlayer = game.getCurrentPlayer();
        currentColor = game.getCurrentColor();
        game.getBoard().printBoard(-1);
        gamePlayInterface.repaint();
        // updateInterface();
    }

    void undo() {
        game.undo();
        piece = hoveredPiece = null;
        currentPlayer = game.getCurrentPlayer();
        currentColor = game.getCurrentColor();
        Game2P g2p = (Game2P) game;
        game.getBoard().printBoard(-1);
        currentPlayer.printPlayer();
//        System.out.println("P1C1 Score: "+g2p.p1.pcol1.getScore());
//        System.out.println("P1C2 Score: "+g2p.p1.pcol2.getScore());
//        System.out.println("P1 Score: "+g2p.p1.get2PScore());
//        System.out.println("P2C1 Score: "+g2p.p2.pcol1.getScore());
//        System.out.println("P2C2 Score: "+g2p.p2.pcol2.getScore());
//        System.out.println("P2 Score: "+g2p.p2.get2PScore());
//        System.out.println("Current player: "+game.getCurrentColor());
        // updateInterface();
        gamePlayInterface.repaint();
    }

    @Override
    public boolean command(String c) {
        switch (c) {
            case "clockwise":
                boardPanel.orientated = true;
                System.out.println("clockwise");
                errorMessage = "Piece rotated clockwise";
                replaceOriginal();
                piece.rotateClockwise();
                paintImage();
                gamePlayInterface.repaint();
                break;
            case "counterclockwise":
                boardPanel.orientated = true;
                System.out.println("counterclockwise");
                errorMessage = "Piece rotated counterclockwise";
                replaceOriginal();
                piece.rotateCounterclockwise();
                paintImage();
                gamePlayInterface.repaint();
                break;
            case "horizontal":
                boardPanel.orientated = true;
                System.out.println("horizontal");
                errorMessage = "Piece flipped horizontally";
                replaceOriginal();
                piece.flipH();
                paintImage();
                gamePlayInterface.repaint();
                break;
            case "vertical":
                boardPanel.orientated = true;
                System.out.println("vertical");
                errorMessage = "Piece flipped horizontally";
                replaceOriginal();
                piece.flipV();
                paintImage();
                gamePlayInterface.repaint();
                break;
            case "quit":
                System.out.println("quit");
                System.exit(0);
                break;
            case "undo":
                System.out.println("undo");
                errorMessage = "Undo previous move";
                undo();
                break;
            case "redo":
                System.out.println("redo");
                errorMessage = "Redo previous move";
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
                newGame();
                boardPanel.repaint();
                break;
            case "hints":
                System.out.println("hints");
                hintsActivated = !hintsActivated;
                boardPanel.repaint();
                break;
            case "save":
                System.out.println("Saving game...");
                errorMessage = "Saving game...";
                gamePlayInterface.repaint();
                saveGame.writeSave();
                System.out.println("Game saved...");
                errorMessage = "Game saved...";
                gamePlayInterface.repaint();
                break;
            default:
                return false;
        }
        return true;
    }

    public void setBoardPanel(BoardPanel b) {
        if (b == null) {
            throw new RuntimeException("Panel null");
        }
        this.boardPanel = b;

    }

}
