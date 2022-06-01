package Interface;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class MenuInterface extends JComponent {
    Image backGround, whiteBack, logo;
    public HoverButton newGame, conti ,tutorial,  exit;
    public JFrame frame;
    int x, y, height, width;

    Boolean error = false;
    DrawString errorMessage;

    public MenuInterface(JFrame f) throws IOException {
        frame = f;
        frame.setResizable(true);
        // Charge images
        String bg = "images/background.png";
        backGround = new Image(frame, bg);
        whiteBack = new Image(frame, "images/whiteBackground.png");
        logo = new Image(frame, "images/logo.png");
        x = frame.getWidth() / 2;
        try {
            // create the font to use
            InputStream in = ClassLoader.getSystemResourceAsStream("font/ABeeZee-Regular.otf");
            Font font = Font.createFont(Font.TRUETYPE_FONT, in);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            // register the font
            ge.registerFont(font);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        inItUIButton();
    }

    /**
     *  Create and Initialise buttons
     * */
    private void inItUIButton() throws IOException {
        x = frame.getWidth() / 2;
        y = frame.getHeight();
        this.newGame = new HoverButton(this, "NG", 0, 0);
        add(this.newGame);
        this.conti = new HoverButton(this, "Continue", 0, 0);
        add(this.conti);
        this.tutorial = new HoverButton(this, "Tutorial", 0, 0);
        add(this.tutorial);
        this.exit = new HoverButton(this, "Exit", 0, 0);
        add(this.exit);
        errorMessage = new DrawString("Could not initialise the game", new Color(207, 14, 17));
        add(this.errorMessage);
    }

    /**
     *  Reset the bounds for those created buttons in order to put them in a correct position
     * */
    private void resetBound() {
        x = frame.getWidth() / 2;
        y = frame.getHeight();
        int widthImage = this.newGame.getWidth() / 2;
        int textPosition = (int)(13.75 * (int) (width * 0.02)/2);
        this.newGame.setBounds(x - widthImage, (int) (y * 0.35), (int) (frame.getWidth()*0.3), (int) (frame.getHeight()* 0.116));
        this.conti.setBounds(x - widthImage, (int) (y * 0.47),(int) (frame.getWidth()*0.3), (int) (frame.getHeight()* 0.116));
        this.tutorial.setBounds(x - widthImage, (int) (y * 0.59), (int) (frame.getWidth()*0.3), (int) (frame.getHeight()* 0.116));
        this.exit.setBounds(x - widthImage, (int) (y * 0.71), (int) (frame.getWidth()*0.3), (int) (frame.getHeight()* 0.116));
        this.errorMessage.setCoords(x - textPosition, (int) (y * 0.33));
        this.errorMessage.setFontSize((int) (width * 0.02));
    }

    public void drawButtons(Graphics g) throws IOException {
        resetBound();
        // g.drawImage(image, x, y, width, height)
        g.drawImage(this.newGame.getCurrentImage(), newGame.getX(), newGame.getY(), newGame.getWidth(), newGame.getHeight(),this);
        g.drawImage(this.conti.getCurrentImage(), conti.getX(), conti.getY(), conti.getWidth(), conti.getHeight(),this);
        g.drawImage(this.tutorial.getCurrentImage(), tutorial.getX(), tutorial.getY(), tutorial.getWidth(), tutorial.getHeight(),this);
        g.drawImage(this.exit.getCurrentImage(), exit.getX(), exit.getY(), exit.getWidth(), exit.getHeight(),this);
    }

    public void errorMessage(Graphics g){
        errorMessage.paint(g);
    }

    public void paintComponent(Graphics g) {
        height = frame.getHeight();
        width = frame.getWidth();
        backGround.drawImg(g, 0, 0, width, height);
        whiteBack.drawImg(g, (int) (width * 0.01), (int) (height * 0.01), (int) (width * 0.98), (int) (height * 0.98));
        logo.drawImg(g, (int) (width * 0.375), (int) (height * 0.05), (int) (width * 0.25), (int) (height * 0.25));
        if (error){
            errorMessage(g);
        }
        try {
            drawButtons(g);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}