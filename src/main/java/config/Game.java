package config;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import debug.Debug;
import entity.*;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Classe principale du jeu gérant l'état du jeu, y compris le couteau, les cibles et les vies.
 * Permet la sauvegarde et le chargement de l'état du jeu.
 */
public class Game {
    Knife knife;
    List<Cible> listeCible;
    int life;
    int argent; // Ajout de l'argent comme attribut du jeu
    String nomUtilisateur;

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

    public Game(String nomUtilisateur){
        super();
        this.nomUtilisateur = nomUtilisateur;
    }



    // Getters et setters pour la sérialisation/désérialisation
    public Knife getKnife() { return knife; }
    public void setKnife(Knife knife) { this.knife = knife; }

    public List<Cible> getListeCible() { return listeCible; }
    public void setListeCible(List<Cible> listeCible) { this.listeCible = listeCible; }

    public int getArgent() { return argent; }
    public void setArgent(int argent) { this.argent = argent; }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    public String getNomUtilisateur() {
        return this.nomUtilisateur;
    }

    /**
     * Met à jour l'état du jeu en fonction du temps écoulé depuis la dernière mise à jour.
     *
     * @param delta Le temps écoulé depuis la dernière mise à jour, utilisé pour calculer les mouvements.
     */
     public void update(double delta){
         knife.updateMovement();
         Debug.affichage(knife);
     }

    /**
     * Sauvegarde l'état actuel du jeu dans un fichier spécifié.
     */
    public void sauvegarderEtat() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File( "src/main/saves/sauvegarde_"+ nomUtilisateur + ".json"), this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Game chargerEtat(String cheminFichier) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(cheminFichier), Game.class);
    }



}
