package config;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import entity.*;
import entity.bosses.*;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Classe principale du jeu gérant l'état du jeu, y compris le couteau, les cibles et les vies.
 * Permet la sauvegarde et le chargement de l'état du jeu.
 */
public class Game {
    transient public Knife knife1;
    transient public Knife knife2;
    transient boolean isSolo;
    transient private List<Cible> listeCible1 = new ArrayList<>();
    transient private List<Cible> listeCible2 = new ArrayList<>();
    transient private int life;
    private RoundManagement roundManagement;
    private int currentRoundIndex;

    //Attribut du User pour JSON
    @JsonProperty("nomUtilisateur")
    private String nomUtilisateur;
    @JsonProperty("motDePasse")
    private String motDePasse;
    @JsonProperty("cheminSauvegarde")
    private String cheminSauvegarde;
    @JsonProperty("argent")
    private int argent;


    @JsonCreator
    public Game() {
        // Constructeur sans arguments pour la désérialisation JSON
    }

    /**
     * Constructeur qui initialise le jeu avec un couteau, une liste de cibles vide, et un nombre initial de vies.
     * Fait également sauter le couteau dès le début.
     */
    public Game(boolean isSolo){
        this.isSolo = isSolo;
        System.out.println("creation game");
        this.knife1 = new Knife();
        if (!isSolo){
            this.knife2 = new Knife();
        }
        this.roundManagement = new RoundManagement();
        initGame();

        //knife.addAngle(90);
        // Cible c1 = new Cible(20,20);
        // Cible c2 = new Cible(-15,30);
        // MovingTarget m1 = new MovingTarget(-20,10);
        // Cible c12 = new Cible(20,20);
        // Cible c22 = new Cible(-15,30);
        // MovingTarget m12 = new MovingTarget(-20,10);
        // listeCible1.add(c1);listeCible1.add(c2);
        // listeCible1.add(m1);
        // listeCible2.add(c12);listeCible2.add(c22);
        // listeCible2.add(m12);
        // life = 3;
    }

    /**
     * Constructeur qui initialise le jeu avec un nom d'utilisateur spécifié.
     * @param nomUtilisateur Le nom de l'utilisateur.
     */
    public Game(String nomUtilisateur, String motDePasse, String cheminSauvegarde, int argent){
        super();
        this.nomUtilisateur = nomUtilisateur;
        this.motDePasse = motDePasse;
        this.cheminSauvegarde = cheminSauvegarde;
        this.argent = argent;
    }


    // Getters et setters pour la sérialisation/désérialisation
    public Knife getKnife() { return knife1; }
    public Knife getKnife2(){ return knife2;}
    //public void setKnife(Knife knife) { this.knife = knife; }

    public List<Cible> getListeCible() { return listeCible1; }
    public List<Cible> getListeCible2() { return listeCible2; }
    //public void setListeCible(List<Cible> listeCible) { this.listeCible = listeCible; }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    public String getNomUtilisateur() {
        return this.nomUtilisateur;
    }

    private void initGame() {
        ChargerRound(currentRoundIndex);
    }

    private void ChargerRound(int roundIndex) {
        Round currentRound = roundManagement.getListeRounds().get(roundIndex);
        this.listeCible1.clear();
        this.listeCible1.addAll(currentRound.getListeCibles());

        if (!isSolo) {
            this.listeCible2.clear();
            this.listeCible2.addAll(currentRound.getListeCibles());
        }
    }


    /**
     * Met à jour l'état du jeu en fonction du temps écoulé depuis la dernière mise à jour.
     *
     * @param delta Le temps écoulé depuis la dernière mise à jour, utilisé pour calculer les mouvements.
     */
    public void update(double delta){
        //System.out.println("dqdqzsqzdqsqdqzdqz");
        double adjustedDelta = delta / 3;
        knife1.updateMovement();
        for(Cible c : this.listeCible1){
            if (c instanceof MovingTarget){
                ((MovingTarget) c).updateMovement();
            } else if (c instanceof BossType1){
                ((BossType1) c).updateMovement(adjustedDelta);
                //System.out.println(c.getX());
            }
        }
        if (!isSolo){
            knife2.updateMovement();
            for(Cible c : this.listeCible2){
                if (c instanceof MovingTarget){
                    ((MovingTarget) c).updateMovement();
                } else if (c instanceof BossType1){
                    ((BossType1) c).updateMovement(adjustedDelta);
                }
            }
        }
        if(listeCible1.isEmpty()){
            currentRoundIndex++;
            if (currentRoundIndex < roundManagement.getListeRounds().size()) {
                ChargerRound(currentRoundIndex);
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

    public int getCurrentRoundIndex() {
        return currentRoundIndex;
    }

}
