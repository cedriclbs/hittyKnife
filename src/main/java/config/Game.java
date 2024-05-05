package config;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import entity.*;
import entity.bosses.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import gui.Library;

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
    transient boolean isSolo = false;
    @JsonIgnore
    transient private List<Cible> listeCible1 = new ArrayList<>();
    @JsonIgnore
    transient private List<Cible> listeCible2 = new ArrayList<>();
    transient private int xpThreshold;
    private RoundManagement roundManagement;

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
    Library library;
    @JsonProperty("level")
    private int level;
    @JsonProperty("xp")
    private int xp;
    private int currentLevel;
    private List<GameObserver> observers = new ArrayList<>();



    @JsonCreator
    public Game() {
        // Constructeur sans arguments pour la désérialisation JSON
    }


    /**
     * Constructeur qui initialise le jeu avec un couteau, une liste de cibles vide, et un nombre initial de vies.
     * Fait également sauter le couteau dès le début.
     */
    public Game(boolean isSolo, String cheminSauvegarde){
        this.isSolo = false;
        this.cheminSauvegarde = cheminSauvegarde;
        System.out.println("creation game");
        this.knife1 = new Knife();
        this.knife2 = new Knife();
        this.roundManagement = new RoundManagement();
        this.currentLevel = 1;
        this.xpThreshold = 100;
        this.xp = 0;
        this.level = 1;
        initGame();
    }


    // Getters et setters pour la sérialisation/désérialisation
    //public Knife getKnife() { return knife1; }
    //public Knife getKnife2(){ return knife2;}
    //public void setKnife(Knife knife) { this.knife = knife; }

    @JsonIgnore
    public List<Cible> getListeCible() { return listeCible1; }
    @JsonIgnore

    public List<Cible> getListeCible2() { return listeCible2; }
    //public void setListeCible(List<Cible> listeCible) { this.listeCible = listeCible; }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    public int getXp() {
        return xp;
    }

    public String getCheminSauvegarde(){
        return cheminSauvegarde;
    }

    @JsonProperty("level")
    public int getLevel() {
        return level;
    }

    public void setArgent(int argent) {
        this.argent = argent;
    }

    public void addArgent(int i) {
        this.setArgent(this.argent + i);
    }


    public void setXp(int xp) {
        this.xp = xp;
    }

    // Méthode pour ajouter de l'XP au jeu et vérifier si un niveau a été atteint
    public void addXP(int xp) {
        this.setXp(this.xp + xp);
        checkLevelUp();
    }
    @JsonProperty("level")
    public void setLevel(int level) {
        this.level = level;
    }

    public void addLevel(int i) {
        this.setLevel(this.level + i);
    }

    public String getNomUtilisateur() {
        return this.nomUtilisateur;
    }

    public Library getLibrary () {
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

    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(GameObserver observer) {
        observers.remove(observer);
    }
    public void setIsSOlo(boolean b){
        this.isSolo =b;
    }

    private void notifyObservers() {
        for (GameObserver observer : observers) {
            observer.onLevelChange();
            System.out.println("Nouveau niveau");
        }
    }

    // Méthode pour vérifier si un niveau a été atteint et attribuer les récompenses
    private void checkLevelUp() {
        this.addLevel(1);
        notifyObservers();
        giveRewards(); // Appel à une méthode pour attribuer les récompenses du niveau
        System.out.println("+1 Niveau");
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
            }
            else if (c instanceof BossType1){
                ((BossType1) c).updateMovement(adjustedDelta);
                //System.out.println(c.getX());
                //System.out.println(c.getY());
            } else if (c instanceof BossType2){
                ((BossType2) c).updateMovement(adjustedDelta);
                //System.out.println(c.getX());
                //System.out.println(c.getY());
            } else if (c instanceof BossType3) {
                ((BossType3) c).updateMovement(adjustedDelta);
                //System.out.println(c.getX());
                //System.out.println(c.getY());
            }
        }
        if (!isSolo) {
            knife2.updateMovement();
            System.out.println(listeCible2.size());
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
            ChargerRound(roundManagement.getCurrentRoundIndex()); // Recharge le premier round du nouveau niveau
        }
    }

    /**
     * Sauvegarde l'état actuel du jeu dans un fichier spécifié.
     */

    public void sauvegarderEtat() {
        try {
            Game savedGame = chargerEtat(cheminSauvegarde);

            // Met à jour les informations sauvegardées avec les informations actuelles
            savedGame.setArgent(this.argent);
            savedGame.setXp(this.xp);
            savedGame.setLevel(this.level);
            savedGame.setLibrary(this.library);
            savedGame.setCurrentLevel(this.currentLevel);

            // Sauvegarde l'état mis à jour dans le fichier de sauvegarde
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File(cheminSauvegarde), savedGame);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    private void setLibrary(Library library) {
        this.library = library;
    }


    /**
     * Charge l'état du jeu à partir d'un fichier spécifié.
     * @param cheminFichier Le chemin du fichier à charger.
     * @return L'état du jeu chargé.
     * @throws IOException En cas d'erreur de lecture du fichier.
     */
    public Game chargerEtat(String cheminFichier) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(cheminFichier), Game.class);
    }


    public void updateLibrary(ShopCart cart) {
        if (cart != null) {
            if (library == null) {
                library = new Library(cheminSauvegarde);
            }
            for (ShopItem item : cart.getCart()) {
                if (!library.getLibraryItems().contains(item)) {
                    library.getLibraryItems().add(item);
                }
            }
            //library.charger();
        }

        sauvegarderEtat();
    }

    // Méthode pour attribuer les récompenses en fonction du niveau
    private void giveRewards() {
        switch (level) {
            case 2:
                this.argent += 10;
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;
            case 10:
                break;
            case 11:
                break;
            case 12:
                break;
            case 13:
                break;
            case 14:
                break;
            case 15:
                break;
            case 16:
                break;
            case 17:
                break;
            case 18:
                break;
            case 19:
                break;
            case 20:
                break;
            default:
                break;
        }
    }




    public int getCurrentLevel() {
        return currentLevel;
    }

}
