package Structures;

import java.io.*;

public class Save {

    Game game;
    String filename;

    public Save(Game g, String fn){
        game = g;
        filename = fn;
    }

    public Save(String fn){
        filename = fn;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public void writeSave(){
        try (FileOutputStream fos = new FileOutputStream(filename);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(game);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void loadSave(){
        try (FileInputStream fileIn = new FileInputStream(filename);
             ObjectInputStream ois = new ObjectInputStream(fileIn)){
            game = (Game) ois.readObject();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
