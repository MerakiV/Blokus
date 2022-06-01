package Controller;

import GamePanels.BoardPanel;
import GamePanels.ColorPanel;
import GamePanels.PiecePanel;
import Interface.EventController;
import Interface.GamePlayInterface;
import Players.Player;
import Players.PlayerAI;
import Structures.Color;
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
    Thread t;

    Save saveGame;
    private PlayerTurn turn;

    public boolean pausedAI = false;

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

    /**
     * startGame:
     *      starts a new thread for the controller that controls the game flow
     * */
    public void startGame() {
        t = new Thread(this);
        t.start();
    }

    public PlayerTurn getCurrentPlayerTurn () {
        return this.turn;
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
        while (!game.hasEnded()){
            Player player = game.getCurrentPlayer();
            player.checkForMoves(game.getBoard());
//            game.getBoard().printBoard(-1);
            turn = new PlayerTurn(player, this.game, this);
            turn.startTurn();

            if (!turn.canExecuteMove()) {
                // TODO : Define what to do if the current player cannot play
                errorMessage = "No more moves for Player " + currentPlayer.getColor();
                System.out.println(errorMessage);
            }
            else{
                while (!turn.hasTerminated()) {
                    // wait the gamer has generated a move

                    long remainingDuration = turn.getRemainingTimeOfMinimalMillisecondTurnDuration();
                    if (remainingDuration > 0)
                    {
                        Thread.sleep(remainingDuration);
                    }
                    else {
                        // TODO : Remove it once the IHM refresh bug is resolved
                        Thread.sleep(32);
                    }
                }

                System.out.println("Player turn is terminated");

                Move currentMove = turn.getSelectedMove();
                //System.out.println("Selected Move = " + currentMove.getPieceType().name());
                put(currentMove, currentPlayer.getColor());
                //System.out.println("Player Color line 155 :" + currentPlayer.getColor().name());
            }

            //game.getBoard().printBoard(-1);
            turn.terminateTurn();
            nextTurn();
            game.updateEnd();
            gamePlayInterface.repaint();
        }
        game.endScoreCheck();
        gamePlayInterface.repaint();
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

    public boolean put(Move m, Color c) {
        piece = new Piece(m.getShape());
        piece.setName(m.getPieceType());
        this.color = c;
        if (piece != null) {
            boardPanel.removePositions();
            if (game.put(piece, color, m.getTile().getX(), m.getTile().getY())) {
                piece = null;
                color = null;
                // System.out.println("Works");
                // System.out.println("It's " + currentColor);
                return true;
            } else{
                System.out.println("Invalid : " +  m.getTile().getX() + " " + m.getTile().getY());
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

    /**
     *  Paint Image
     *      Changes the tiles on the board to match the piece manipulation
     * */
    public void paintImage() {
        Image image = getImage(this.color);
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

    /**
     *  Paint Image
     *      Changes the tiles on the board to match the piece manipulation
     * */
    public void paintImage(Move move, Color color) {
        int x = move.getTile().getY();
        int y = move.getTile().getX();
        Image image = getImage(color);
        boolean[][] shape = move.getShape().shape;
        x -= move.getShape().anchorY;
        y -= move.getShape().anchorX;
        if (x >= 0 && x + move.getShape().Ncol - 1 <= 19 && y >= 0 && y + move.getShape().Nlin - 1 <= 19) {
            for (int i = 0; i < move.getShape().Nlin; i++) {
                int temp = x;
                for (int j = 0; j < move.getShape().Ncol; j++) {
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
    private Image getImage(Color color) {
        BufferedImage img;
        int tileSize = boardPanel.boardSize / 20;
        Image resizedImage = null;
        try {
            InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(getPath(color));
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
    private String getPath(Color color) {
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

    /**
     *  Replace Original Image
     *      Replaces the tiles painted during the hover by the original tiles of the board
     * */
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

    /**
     *  New Game
     *      Starts a new game using the same settings as the current game
     * */
    void newGame() {
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

    /**
     *  Redo
     *      Verifies if redo is possible, if so, redoes to turn of player
     *      else an error message is given
     * */
    void redo() {
        if (canRedo()){
            game.redo();
            boardPanel.redo(game.next);
            game.redo();
            boardPanel.redo(game.next);
            piece = hoveredPiece = null;
            currentPlayer = game.getCurrentPlayer();
            currentColor = game.getCurrentColor();
            game.getBoard().printBoard(-1);
            turn = new PlayerTurn(currentPlayer, this.game, this);
            System.out.println("Current player : " + currentPlayer.getColor().name());
        } else {
            errorMessage = "Unable to Redo";
        }
        gamePlayInterface.repaint();
        // updateInterface();
    }

    /**
     *  Can Redo
     *      Verifies if redo is possible
     * */
    public boolean canRedo() {
        return game.history.future.size() >= 2;
    }

    /**
     *  Undo
     *      Verifies if undo is possible, if so, undoes to turn of player
     *      else an error message is given
     * */
    void undo() {
        if (canUndo()){
            game.undo();
            boardPanel.undo(game.previous);
            ColorPanel colorPanel = gamePlayInterface.getColorPanel();
            colorPanel.piecePanels.get(game.previous.nextMove.getPieceType() + " " + game.getCurrentColor()).isClicked = false;
            game.undo();
            boardPanel.undo(game.previous);
            color = game.getCurrentColor();
            colorPanel = gamePlayInterface.getColorPanel();
            colorPanel.piecePanels.get(game.previous.nextMove.getPieceType() + " " + game.getCurrentColor()).isClicked = false;
            gamePlayInterface.g2p = (Game2P) game;
            piece = hoveredPiece = null;
            currentPlayer = game.getCurrentPlayer();
            currentColor = game.getCurrentColor();
            game.getBoard().printBoard(-1);
            turn = new PlayerTurn(currentPlayer, this.game, this);
            turn.startTurn();
        } else{
            errorMessage = "Unable to Undo";
        }
        gamePlayInterface.repaint();
    }

    /**
     *  Undo
     *      Verifies if undo is possible
     * */
    public boolean canUndo() {
        return game.history.past.size() >= 2;
    }

    /**
     *  Command
     *      Performs (if verified) the different possible actions :
     *      - clockwise : turns the piece clockwise
     *      - counterclockwise : turns the piece counterclockwise
     *      - horizontal : flips the piece horizontally
     *      - vertical : flips the piece vertically
     *      - quit : exits the game
     *      - undo : undoes to the previous turn of player
     *      - redo : redoes to the previous turn of player
     *      - newgame : restarts the game with same settings
     *      - hints : activates/deactivaes hints
     *      - save : saves the game
     * */
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
                errorMessage = "Piece flipped vertically";
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
                if(pausedAI) {
                    resumeTurn();
                    System.out.println("Game unpaused");
                    errorMessage = "Game unpaused";
                    gamePlayInterface.repaint();
                    pausedAI = false;
                }
                else {
                    pauseTurn();
                    System.out.println("Game paused, can't move");
                    errorMessage = "Game paused, can't move";
                    gamePlayInterface.repaint();
                    pausedAI = true;
                }
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

    //public void resume()

    /**
     * resumeTurn :
     *      resumes (restarts) the game flow thread after pause
     */
    public void resumeTurn() {
        this.t = new Thread(this);
        this.t.start();

        System.out.println("Play turn resumed");
    }

    /**
     * pauseTurn :
     *      pauses the game flow (interrupts the game flow thread) and interrupts the current player turn
     */
    public void pauseTurn() {
        this.t.interrupt();
        if(this.turn != null){
            this.turn.pause();
        }
        System.out.println("Play turn paused");
    }
}
