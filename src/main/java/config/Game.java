package config;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import entity.*;
import entity.bosses.*;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Classe principale du jeu gérant l'état du jeu, y compris le couteau, les cibles et les vies.
 * Permet la sauvegarde et le chargement de l'état du jeu.
 */
public class Game {
    public Knife knife;
    private List<Cible> listeCible = new ArrayList<>();
    private int life;
    private int argent; // Ajout de l'argent comme attribut du jeu
    private String nomUtilisateur;

    /**
     * Constructeur qui initialise le jeu avec un couteau, une liste de cibles vide, et un nombre initial de vies.
     * Fait également sauter le couteau dès le début.
     */
    public Game(){
        System.out.println("creation game");
        this.knife = new Knife();
        //knife.addAngle(90);
        Cible c1 = new Cible(20,20);
        Cible c2 = new Cible(-15,30);
        MovingTarget m1 = new MovingTarget(-20,10);
        BossType1 b1 = new BossType1(30, 15); //x changera entre -30 et 30  et  y deplacera de -21 à 49 pour sortir de l'écran et apparaitre de l'autre côté
        listeCible.add(c1);listeCible.add(c2);
        listeCible.add(m1);
        listeCible.add(b1);
        life = 3;
        argent = 100;
    }

    /**
     * Constructeur qui initialise le jeu avec un nom d'utilisateur spécifié.
     * @param nomUtilisateur Le nom de l'utilisateur.
     */
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
         //System.out.println("dqdqzsqzdqsqdqzdqz");
         knife.updateMovement();
         for(Cible c : this.listeCible){
             if (c instanceof MovingTarget){
                 ((MovingTarget) c).updateMovement();
             } else if (c instanceof BossType1){
                 //((BossType1) c).updateMovement();
             }
         }
         //Debug.affichage(knife);
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

    /**
     * Charge l'état du jeu à partir d'un fichier spécifié.
     * @param cheminFichier Le chemin du fichier à charger.
     * @return L'état du jeu chargé.
     * @throws IOException En cas d'erreur de lecture du fichier.
     */
    public static Game chargerEtat(String cheminFichier) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(cheminFichier), Game.class);
    }

}