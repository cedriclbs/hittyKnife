package config;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import entity.*;
import entity.bosses.*;
import gui.*;

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
    @JsonIgnore 
    private GameView gameView;
    @JsonIgnore
    public boolean gel = false;
    @JsonIgnore
    public boolean powered = false;
    @JsonIgnore
    public BonusManager bonusManager = new BonusManager(this);


    transient private int xpThreshold;
    private RoundManagement roundManagement;
    private List<GameObserver> observers = new ArrayList<>();
    private int lastBackgroundIndex = -1; // Dernier indice utilisé
    private Queue<Integer> recentBackgrounds = new LinkedList<>(); // Indices récents pour éviter la répétition
    private Random rand = new Random();


    //Attribut du User pour JSON
    @JsonProperty("nomUtilisateur")
    private String nomUtilisateur;
    @JsonProperty("motDePasse")
    private String motDePasse;
    @JsonProperty("cheminSauvegarde")
    private String cheminSauvegarde;
    @JsonProperty("argent")
    private int argent;
    @JsonIgnore()
    Library library;
    @JsonProperty("level")
    private int level;
    @JsonProperty("xp")
    private int xp;
    private int currentLevel;
    @JsonProperty("currentBackgroundPath")
    private String currentBackgroundPath; 





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
        this.gameView = new GameView(isSolo,this);
        this.currentLevel = 1;
        this.xpThreshold = 100;
        this.xp = 0;
        this.level = 1;
        this.currentBackgroundPath = null;
        loadGameState();
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

    public int getArgent() {
        return argent;
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

    /**
    * Charge l'état du jeu à partir du fichier de sauvegarde spécifié dans cheminSauvegarde.
    * Cette méthode lit les données sauvegardées comme le niveau actuel, les points d'expérience,
    * le niveau, et l'argent, puis les affecte aux attributs correspondants de l'instance en cours.
    * 
    * Le chemin du fichier de sauvegarde est déterminé par l'attribut cheminSauvegarde de l'instance.
    * Si une erreur se produit lors du chargement, l'erreur est enregistrée dans la console.
    */
    private void loadGameState() {
        try {
            Game loadedGame = chargerEtat(this.cheminSauvegarde);
            this.currentLevel = loadedGame.getCurrentLevel();  
            this.xp = loadedGame.getXp();
            this.level = loadedGame.getLevel();
            this.argent = loadedGame.getArgent();
            this.currentBackgroundPath = loadedGame.getCurrentBackgroundPath();
            roundManagement.setCurrentRoundIndex(0);
            updateBackground();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }   
    /**
     * Met à jour l'arrière-plan de l'interface graphique du jeu si les conditions sont remplies.
     * Cette méthode vérifie que l'objet gameView et le chemin de l'arrière-plan (currentBackgroundPath) ne sont pas null.
     * Si les deux conditions sont remplies, l'arrière-plan de l'interface graphique est mis à jour avec la nouvelle image spécifiée.
     * Ceci est utilisé pour refléter les changements visuels dans l'arrière-plan au cours du jeu.
     */
    private void updateBackground() {
        if (gameView != null && currentBackgroundPath != null) {
            gameView.updateBackgroundImage(currentBackgroundPath); 
        }
    }
    
    private synchronized void initGame() {
        ChargerRound(roundManagement.getCurrentRoundIndex());
    }

    private synchronized void ChargerRound(int roundIndex) {
            Round currentRound = roundManagement.getListeRounds().get(roundIndex);
            listeCible1.clear();
            listeCible1.addAll(currentRound.getListeCibles());
            roundManagement.setCurrentRoundIndex(roundIndex);

            if (!isSolo) {
                listeCible2.clear();
                listeCible2.addAll(currentRound.getListeCibles());
            }
        
    }

    public synchronized void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    public synchronized void removeObserver(GameObserver observer) {
        observers.remove(observer);
    }
    public void setIsSOlo(boolean b){
        this.isSolo =b;
    }

    private synchronized void notifyObservers() {
        for (GameObserver observer : observers) {
            observer.onLevelChange();
        }
    }

    public synchronized void update(double delta) {
        double adjustedDelta = delta / 3;
        knife1.updateMovement();

        synchronized (listeCible1) {
            for (Cible c : new ArrayList<>(listeCible1)) {
                updateCible(c, adjustedDelta);
            }
        }
        if (!isSolo) {
            knife2.updateMovement();
            synchronized (listeCible2) {
                for (Cible c : new ArrayList<>(listeCible2)) {
                    updateCible(c, adjustedDelta);
                }
            }
        }

        checkRoundCompletion();
        bonusManager.updateBonusEffect();
    }

    private void updateCible(Cible c, double adjustedDelta) {

            if (c instanceof MovingTarget) {
                if (!gel) {
                    ((MovingTarget) c).updateMovement();
                }

            } else if (c instanceof BossType1) {
                ((BossType1) c).updateMovement(adjustedDelta);
            } else if (c instanceof BossType2) {
                ((BossType2) c).updateMovement(adjustedDelta);
            } else if (c instanceof BossType3) {
                ((BossType3) c).updateMovement(adjustedDelta);
            } else if (c instanceof BossType4) {
                ((BossType4) c).updateMovement(adjustedDelta);
            } 

    }
    /**
     * Vérifie si le round actuel est terminé en examinant si la liste des cibles du premier joueur est vide.
     * Si toutes les cibles sont éliminées, la méthode avance le jeu au round suivant ou, si tous les rounds sont terminés,
     * augmente le niveau du jeu, réinitialise les rounds et charge le premier round du nouveau niveau.
     * Cette méthode synchronisée assure que le contrôle du jeu reste cohérent lors de l'actualisation des états des rounds.
     */
    private synchronized void checkRoundCompletion() {
        if (listeCible1.isEmpty()) {
            roundManagement.setCurrentRoundIndex(roundManagement.getCurrentRoundIndex() + 1);
            if (roundManagement.getCurrentRoundIndex() < roundManagement.getListeRounds().size()) {
                ChargerRound(roundManagement.getCurrentRoundIndex()); // Chargement du round suivant
                //System.out.println(roundManagement.getCurrentRoundIndex());

            }  
            else {
                currentLevel++; // Incrémentation du niveau
                notifyBackgroundChange();
                System.out.println("Level : " + currentLevel);
                roundManagement.resetRounds(); // Réinitialisation des rounds pour le nouveau niveau
                ChargerRound(roundManagement.getCurrentRoundIndex()); // Recharge le premier round du nouveau niveau
            }
    
        }
    }

    /**
     * Met à jour l'image de fond du jeu. Cette méthode sélectionne un nouvel arrière-plan
     * aléatoire et met à jour l'interface graphique si elle est initialisée.
     * Assure que l'arrière-plan affiché est toujours rafraîchi en fonction du contexte du jeu.
     */
    private void notifyBackgroundChange() {
        String newPath = selectRandomBackground();
        setCurrentBackgroundPath(newPath);
        if (gameView != null) {
            gameView.updateBackgroundImage(currentBackgroundPath);
        }
    }
    
    
    /**
     * Sélectionne un chemin d'image de fond aléatoire à partir d'un ensemble prédéfini de ressources.
     * Utilise un système pour éviter de répéter les arrière-plans récemment utilisés,
     * assurant une variété visuelle.
     *
     * @return Le chemin d'accès au fichier de l'image de fond sélectionnée.
     */
    private String selectRandomBackground() {
        int bgIndex;
        do {
            bgIndex = rand.nextInt(11); // Génère un indice aléatoire
        } while (bgIndex == lastBackgroundIndex || recentBackgrounds.contains(bgIndex)); // Vérifie les conditions

        updateRecentBackgrounds(bgIndex);
        return "src/main/ressources/background/bgJap" + bgIndex + ".gif";
    }
    
    /**
     * Met à jour la file d'indices des arrière-plans utilisés récemment pour assurer
     * que les arrière-plans ne se répètent pas trop fréquemment.
     * Conserve une file des indices des 5 derniers arrière-plans utilisés.
     *
     * @param newIndex Le nouvel indice à ajouter à la file.
     */
    private void updateRecentBackgrounds(int newIndex) {
        lastBackgroundIndex = newIndex;
        recentBackgrounds.offer(newIndex); // Ajoute le nouvel indice à la file
        if (recentBackgrounds.size() > 5) {
            recentBackgrounds.poll(); // Retire l'indice le plus ancien
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
            savedGame.setCurrentBackgroundPath(currentBackgroundPath);

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


    public RoundManagement getRoundManagement() {
        return roundManagement;
    }

    public boolean getIsSolo(){
        return isSolo;
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
        updateBackground();
    }

    @JsonProperty("currentBackgroundPath")
    public String getCurrentBackgroundPath() {
        return currentBackgroundPath;
    }

    @JsonProperty("currentBackgroundPath")
    public void setCurrentBackgroundPath(String currentBackgroundPath) {
        this.currentBackgroundPath = currentBackgroundPath;
    }

    
}
