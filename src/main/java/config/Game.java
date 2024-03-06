package config;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import debug.Debug;
import entity.*;

/**
 * Classe principale du jeu gérant l'état du jeu, y compris le couteau, les cibles et les vies.
 * Permet la sauvegarde et le chargement de l'état du jeu.
 */
public class Game {
    Knife knife;
    List<Cible> listeCible;
    int life;
    int argent; // Ajout de l'argent comme attribut du jeu

    /**
     * Constructeur qui initialise le jeu avec un couteau, une liste de cibles vide, et un nombre initial de vies.
     * Fait également sauter le couteau dès le début.
     */
    public Game(){
        knife = new Knife();
        listeCible = new ArrayList<>();
        life = 3;
        argent = 0;
        knife.jump();
    }

    /**
     * Met à jour l'état du jeu en fonction du temps écoulé depuis la dernière mise à jour.
     *
     * @param delta Le temps écoulé depuis la dernière mise à jour, utilisé pour calculer les mouvements.
     */
     public void update(double delta){
         knife.updateMovement();
         //Debug.affichage(knife);
         //System.out.println("Knife X: " + knife.getX() + ", Knife Y: " + knife.getY());
         //System.out.println("Rotation Angle: " + knife.getAngle());

     }

    /**
     * Sauvegarde l'état actuel du jeu dans un fichier spécifié.
     *
     * @param cheminFichier Le chemin vers le fichier où l'état du jeu sera sauvegardé.
     */
    public void sauvegarderEtat(String cheminFichier) {
        GameSave gameSave = new GameSave(knife, listeCible, argent);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("save.ser"))) {
            oos.writeObject(gameSave);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Charge l'état du jeu à partir d'un fichier spécifié, remplaçant l'état actuel par celui chargé.
     *
     * @param cheminFichier Le chemin vers le fichier contenant l'état du jeu sauvegardé.
     */
    public void chargerEtat(String cheminFichier) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(cheminFichier))) {
            GameSave gameSave = (GameSave) ois.readObject();
            this.knife = gameSave.getKnife();
            this.listeCible = gameSave.getListeCible();
            this.argent = gameSave.getArgent();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void ajouterArgent(int montant) {
        this.argent += montant;
    }

    public void retirerArgent(int montant) {
        this.argent -= montant;
    }

    public Knife getKnife () {
        return knife;
    }


}
