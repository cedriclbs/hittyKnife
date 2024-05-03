package config;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entity.*;
import entity.bosses.*;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Classe principale du jeu gérant l'état du jeu, y compris le couteau, les cibles et les vies.
 * Permet la sauvegarde et le chargement de l'état du jeu.
 */
public class Game {
    @JsonIgnore
    transient public Knife knife1;
    @JsonIgnore
    transient public Knife knife2;
    @JsonIgnore
    transient boolean isSolo;
    @JsonIgnore
    transient private List<Cible> listeCible1 = new ArrayList<>();
    @JsonIgnore
    transient private List<Cible> listeCible2 = new ArrayList<>();
    transient private int life;
    private RoundManagement roundManagement;  
    private int currentLevel;

    //Attribut du User pour JSON
    @JsonProperty("nomUtilisateur")
    private String nomUtilisateur;
    @JsonProperty("motDePasse")
    private String motDePasse;
    @JsonProperty("cheminSauvegarde")
    private String cheminSauvegarde;
    @JsonProperty("argent")
    private int argent;
    @JsonProperty("library")
    List<ShopItem> library;


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
        this.currentLevel = 1;
        initGame();

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
    //public Knife getKnife() { return knife1; }
    //public Knife getKnife2(){ return knife2;}
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

    public List<ShopItem> getLibrary () {
        return library;
    }

    private void initGame() {
        ChargerRound(roundManagement.getCurrentRoundIndex());
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
        double adjustedDelta = delta / 3;
        knife1.updateMovement();
        for (Cible c : this.listeCible1) {
            if (c instanceof MovingTarget) {
                ((MovingTarget) c).updateMovement();
            } else if (c instanceof BossType1){
                ((BossType1) c).updateMovement(adjustedDelta);
                //System.out.println(c.getX());
            } else if (c instanceof BossType2){
                ((BossType2) c).updateMovement(adjustedDelta);
                //System.out.println(c.getX());
                //System.out.println(c.getY());
            } else if (c instanceof BossType3) {
                ((BossType3) c).updateMovement(adjustedDelta);
                //System.out.println(c.getX());
                System.out.println(c.getY());
            }
        }
        if (!isSolo) {
            knife2.updateMovement();
            for (Cible c : this.listeCible2) {
                if (c instanceof MovingTarget) {
                    ((MovingTarget) c).updateMovement();
                } else if (c instanceof BossType1){
                    ((BossType1) c).updateMovement(adjustedDelta);
                }
            }
        }

        if (listeCible1.isEmpty()) {
            // Incrémentation de l'indice de round dans RoundManagement
            roundManagement.setCurrentRoundIndex(roundManagement.getCurrentRoundIndex() + 1);
            if (roundManagement.getCurrentRoundIndex() < roundManagement.getListeRounds().size()) {
                ChargerRound(roundManagement.getCurrentRoundIndex()); // Chargement du round suivant
                //System.out.println(roundManagement.getCurrentRoundIndex());
            }
        }
        if (roundManagement.isAllRoundsCompleted()) {
            currentLevel++; // Incrémentation du niveau
            System.out.println("Level : " + currentLevel);
            roundManagement.resetRounds(); // Réinitialisation des rounds pour le nouveau niveau
            roundManagement.setCurrentRoundIndex(0); // Réinitialisation de l'indice de round
            ChargerRound(roundManagement.getCurrentRoundIndex()); // Recharge le premier round du nouveau niveau
        }
    }




    /**
     * Sauvegarde l'état actuel du jeu dans un fichier spécifié.
     */

    public void sauvegarderEtat() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File("src/main/saves/sauvegarde_"+ nomUtilisateur + ".json"), this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void updateLibrary(ShopCart cart) {
        if (cart != null) {
            if (library == null) {
                library = new ArrayList<>();
            }
            for (ShopItem item : cart.getCart()) {
                if (!library.contains(item)) {
                    library.add(item);
                }
            }
            //library.charger();
        }

        sauvegarderEtat();
    }




    /**
     * Sauvegarde le panier du ShopMenu dans un fichier spécifié pour l'afficher dans la bibliothèque du joueur
     */





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

    public int getCurrentLevel() {
        return currentLevel;
    }

}
