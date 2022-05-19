package Structures;

import java.io.*;

public class Save {

    Game game;
    String filename;

    public Save(Game g, String fn){
        game = g;
        filename = fn;
    }

    public void WriteSave(){
        try (FileOutputStream fos = new FileOutputStream("Save/"+filename);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(game);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void LoadSave(){
        try (FileInputStream fileIn = new FileInputStream("Save/"+filename);
             ObjectInputStream ois = new ObjectInputStream(fileIn)){
            game = (Game) ois.readObject();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
