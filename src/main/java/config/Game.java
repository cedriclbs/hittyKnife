package config;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import entity.*;


public class Game {
    Knife knife;
    List<Cible> listeCible;
    int life;


    Game(){
        knife = new Knife();
        listeCible = new ArrayList<>();
        life = 3;
        knife.jump();
    }



    /**
     * Boucle principale du jeu
     * @param delta timer du jeu.
     */
     public void update(double delta){
         knife.updateMovement();

        Debug.affichage(knife);
     }

    // Méthode pour sauvegarder l'état du jeu
    public void sauvegarderEtat(String cheminFichier) {
        GameSave gameSave = new GameSave(knife, listeCible);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("save.ser"))) {
            oos.writeObject(gameSave);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour charger l'état du jeu
    public void chargerEtat(String cheminFichier) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(cheminFichier))) {
            GameSave gameSave = (GameSave) ois.readObject();
            this.knife = gameSave.getKnife();
            this.listeCible = gameSave.getListeCible();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
