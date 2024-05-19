package config;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import javax.swing.Timer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import entity.*;
import entity.bosses.*;
import geometry.Coordinate;
import gui.*;

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
    transient public Knife knife3;

    @JsonIgnore
    transient boolean isSolo = false;
    @JsonIgnore
    transient private List<Cible> listeCible1 = new ArrayList<>();
    @JsonIgnore
    transient private List<Cible> listeCible2 = new ArrayList<>();
    @JsonIgnore
    private GameView gameView;

    @JsonIgnore
    public boolean[] gel = {false,false}; //[0] = solo et [1] = versus
    @JsonIgnore
    public boolean powered[] = {false,false,false}; //joueur solo et les deux versus
    @JsonIgnore
    public BonusManager bonusManager = new BonusManager(this);
    @JsonIgnore 
    private int vies = 4;
    @JsonIgnore
    private RoundManagement roundManagementVERSUS;
    @JsonIgnore
    public int scoreJoueur1 = 0;
    @JsonIgnore
    public int scoreJoueur2 = 0;
    @JsonIgnore
    public final int MAX_SCORE = 5;
    @JsonIgnore
    public boolean gameOver = false;
    @JsonIgnore
    private Timer gameOverTimer;
    @JsonIgnore
    public float gameOverOpacity = 1.0f;  


    private static final int xpThreshold = 10;

    private RoundManagement roundManagement;

    private List<GameObserver> observers = new ArrayList<>();
    private List<LibraryObserver> libraryObservers = new ArrayList<>();

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
    @JsonProperty("inventaire")
    private List<ShopItem> inventaire = new ArrayList<>();

    @JsonProperty("level")
    private int level;
    @JsonProperty("xp")
    private int xp;
    @JsonProperty("currentlevel")
    private int currentLevel = 1;
    @JsonProperty("currentBackgroundPath")
    private String currentBackgroundPath;

    @JsonCreator
    public Game() {
        // Constructeur sans arguments pour la désérialisation JSON
    }

    /**
     * Constructeur qui initialise le jeu avec un couteau, une liste de cibles vide, et un nombre initial de vies.
     * Fait également sauter le couteau dès le début.
     *
     * @param isSolo           Indique si le jeu est en mode solo.
     * @param cheminSauvegarde Le chemin vers le fichier de sauvegarde.
     */
    public Game(boolean isSolo, String cheminSauvegarde){
        this.isSolo = false;
        this.cheminSauvegarde = cheminSauvegarde;
        System.out.println("creation game");
        this.knife1 = new Knife(new Coordinate(0,0));
        this.knife2 = new Knife(new Coordinate(15,0));
        this.knife3 = new Knife(new Coordinate(-15,0));
        initInventaire();
        this.roundManagement = new RoundManagement(true);
        this.roundManagementVERSUS = new RoundManagement(false);
        this.gameView = new GameView(isSolo,this);
        this.currentLevel = 1;
        this.xp = 0;
        this.level = 0;
        this.currentBackgroundPath = "";
        loadGameState();
        initGame();
    }

    @JsonIgnore
    public List<Cible> getListeCible() { return listeCible1; }
    @JsonIgnore
    public List<Cible> getListeCible2() { return listeCible2; }

    public int getXp() {
        return xp;
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

    public List<ShopItem> getInventaire() {
        return this.inventaire;
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

    public void setIsSOlo(boolean b){
        this.isSolo =b;
    }

    private void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }
    @JsonProperty("currentlevel")
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
        if (currentBackgroundPath == null) {
            return; // Avoid setting null
        }
        this.currentBackgroundPath = currentBackgroundPath;

        // Extrait l'indice du chemin de l'image de fond
        String indexStr = currentBackgroundPath.replaceAll("[^0-9]", ""); // Enlève tout sauf les chiffres
        if (!indexStr.isEmpty()) {
            lastBackgroundIndex = Integer.parseInt(indexStr);
        }
    }

    @JsonIgnore
    public int getVies() {
        return vies;
    }
    public void perdreVie() {
        vies--;
        if (vies <= 0) {
            resetRoundAndRestoreLives();
        }
    }

    /**
     * Met à jour l'inventaire du jeu avec la liste spécifiée d'articles de boutique.
     * Cette méthode ajoute les nouveaux articles à l'inventaire existant, en s'assurant
     * qu'il n'y a pas de doublons.
     *
     * @param list La liste d'articles de boutique à ajouter à l'inventaire.
     */
    private void setInventaire(List<ShopItem> list) {
        if (this.inventaire == null){
            this.inventaire = new ArrayList<>();
        }
        if (list != null){
            for (ShopItem item : list) {
                if (!inventaire.contains(item)) {
                    inventaire.add(item);
                }
            }
        }
    }

    /**
     * Méthode pour initialiser l'inventaire avec des articles par défaut
     */
    public void initInventaire () {
        //Articles par défaut :
        ShopItem defaultKnife = new ShopItem("Sword 1", 15, RessourcesPaths.knifePath + "knife#1.png");
        ShopItem defaultMusic = new ShopItem("Music 1", 30, RessourcesPaths.buttonPath + "music.png");

        if (!this.inventaire.contains(defaultKnife)){
            this.inventaire.add(defaultKnife);
        }
        if (!this.inventaire.contains(defaultMusic)){
            this.inventaire.add(defaultMusic);
        }
    }

    public void resetScore(){
        this.scoreJoueur2=0;
        this.scoreJoueur1=0;
    }


    /**
    * Charge l'état du jeu à partir du fichier de sauvegarde spécifié dans cheminSauvegarde.
    * Cette méthode lit les données sauvegardées comme le niveau actuel, les points d'expérience,
    * le niveau, et l'argent, puis les affecte aux attributs correspondants de l'instance en cours.
    * Le chemin du fichier de sauvegarde est déterminé par l'attribut cheminSauvegarde de l'instance.
    * Si une erreur se produit lors du chargement, l'erreur est enregistrée dans la console.
    */
    private void loadGameState() {
        try {
            Game loadedGame = chargerEtat(this.cheminSauvegarde);
            this.currentLevel = loadedGame.getCurrentLevel();
            this.nomUtilisateur = loadedGame.getNomUtilisateur();
            this.xp = loadedGame.getXp();
            this.level = loadedGame.getLevel();
            this.argent = loadedGame.getArgent();
            this.inventaire = loadedGame.getInventaire();
            initInventaire();
            this.currentBackgroundPath = loadedGame.getCurrentBackgroundPath();
            roundManagement.setCurrentRoundIndex(0);
            roundManagementVERSUS.setCurrentRoundIndex(0);
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

    /**
     *  Méthode pour initialiser le jeu
     */
    private synchronized void initGame() {
        chargerRound(roundManagement.getCurrentRoundIndex(),true);
        chargerRound(roundManagementVERSUS.getCurrentRoundIndex(),false);
    }

    private synchronized void chargerRound(int roundIndex,boolean isSolo) {
        if (isSolo) {
            Round currentRound = roundManagement.getListeRounds().get(roundIndex);
            listeCible1.clear();
            listeCible1.addAll(currentRound.getListeCibles());
            roundManagement.setCurrentRoundIndex(roundIndex);
        }
        else{
            Round currentRound = roundManagementVERSUS.getListeRounds().get(roundIndex);
            listeCible2.clear();
            listeCible2.addAll(currentRound.getListeCibles());
            roundManagementVERSUS.setCurrentRoundIndex(roundIndex);
        }
    }


    /**
     * Méthode pour ajouter un observateur de jeu
     *
     * @param observer = l'observateur à ajouter
     */
    public synchronized void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    /**
     * Méthode pour notifier les observateur du jeu
     */
    public synchronized void notifyLevelObservers() {
        for (GameObserver observer : observers) {
            observer.onLevelChange();
        }
    }

    /**
     * Ajoute un observateur de bibliothèque à la liste des observateurs dans le but de l'affichage en temps réel de l'inventaire.
     *
     * @param observer L'observateur de bibliothèque à ajouter.
     */
    public synchronized void addLibraryObserver(LibraryObserver observer) {
        libraryObservers.add(observer);
    }

    /**
     * Notifie tous les observateurs de bibliothèque enregistrés lorsqu'un changement dans l'inventaire est détecté.
     * Cette méthode est appelée pour informer les observateurs que l'inventaire a été mis à jour et qu'ils doivent
     * rafraîchir leur affichage en conséquence pour l'affichage en temps réel.
     */
    public synchronized void notifyLibraryObservers() {
        for (LibraryObserver observer : libraryObservers) {
            observer.updateInventaire();
        }
    }



    /**
     * Met à jour l'état du jeu en fonction du temps écoulé depuis la dernière mise à jour.
     * Cette méthode gère le mouvement des couteaux et des cibles, vérifie la complétion des rounds,
     * et met à jour les effets des bonus en cours.
     *
     * @param delta Le temps écoulé depuis la dernière mise à jour, en millisecondes.
     */
    public synchronized void update(double delta) {
        double adjustedDelta = delta / 3;
        knife1.updateMovement();

        synchronized (listeCible1) {
            for (Cible c : new ArrayList<>(listeCible1)) {
                updateCible(c, adjustedDelta,true);
            }
        }
        if (!isSolo) {
            knife2.updateMovement();
            knife3.updateMovement();
            synchronized (listeCible2) {
                for (Cible c : new ArrayList<>(listeCible2)) {
                    updateCible(c, adjustedDelta,false);
                }
            }
        }
        checkRoundCompletion();
        bonusManager.updateBonusEffect();
    }



    /**
     * Met à jour le mouvement de la cible spécifiée en fonction du temps écoulé depuis la dernière mise à jour.
     *
     * @param c La cible à mettre à jour.
     * @param adjustedDelta Le temps écoulé depuis la dernière mise à jour, ajusté en fonction de la vitesse du jeu.
     */
    private void updateCible(Cible c, double adjustedDelta,boolean isSolo) {
        if(c instanceof MovingTarget) {
            if (isSolo) {
                if (!gel[0]) {
                    ((MovingTarget) c).updateMovement();
                }
            }
            else{
                if (!gel[1]) {
                    ((MovingTarget) c).updateMovement();
                }
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
                chargerRound(roundManagement.getCurrentRoundIndex(),true); // Chargement du round suivant
                //System.out.println(roundManagement.getCurrentRoundIndex());

            }
            else {
                currentLevel++; // Incrémentation du niveau
                notifyBackgroundChange();
                System.out.println("Level : " + currentLevel);
                roundManagement.resetRounds(); // Réinitialisation des rounds pour le nouveau niveau
                resetLives();
                chargerRound(roundManagement.getCurrentRoundIndex(),true); // Recharge le premier round du nouveau niveau
            }
        }

        if (listeCible2.isEmpty()) {
            roundManagementVERSUS.setCurrentRoundIndex(roundManagementVERSUS.getCurrentRoundIndex() + 1);
            if (roundManagementVERSUS.getCurrentRoundIndex() < roundManagementVERSUS.getListeRounds().size()) {
                chargerRound(roundManagementVERSUS.getCurrentRoundIndex(),false); // Chargement du round suivant
                //System.out.println(roundManagement.getCurrentRoundIndex());

            }
            else {
                //notifyBackgroundChange();
                roundManagementVERSUS.resetRounds(); // Réinitialisation des rounds pour le nouveau niveau
                chargerRound(roundManagementVERSUS.getCurrentRoundIndex(),false); // Recharge le premier round du nouveau niveau
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
            bgIndex = rand.nextInt(16); // Génère un indice aléatoire
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
        if (recentBackgrounds.size() > 8) {
            recentBackgrounds.poll(); // Retire l'indice le plus ancien
        }
    }

    /**
     * Méthode pour vérifier si un niveau a été atteint et attribuer les récompenses
     */
     private void checkLevelUp() {
        if(this.xp %xpThreshold==0){
            this.addLevel(1);
            notifyLevelObservers();
            giveRewards(); // Appel à une méthode pour attribuer les récompenses du niveau
            System.out.println("+1 Niveau");
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
            savedGame.setInventaire(this.inventaire);
            savedGame.setCurrentLevel(this.currentLevel);
            savedGame.setCurrentBackgroundPath(currentBackgroundPath);

            // Sauvegarde l'état mis à jour dans le fichier de sauvegarde
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File(cheminSauvegarde), savedGame);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Met à jour l'inventaire du jeu avec la fonction setInventaire, appelant les observateurs de Library pour ainsi
     * mettre à jour l'inventaire, le but étant de sauvegarder l'inventaire dans le fichier JSON.
     *
     * @param list La liste d'articles de boutique à ajouter à l'inventaire.
     */
    public void updateLibrary(List<ShopItem> list) {
        setInventaire(list);
        notifyLibraryObservers();
        sauvegarderEtat();
    }

    /**
     * Charge l'état du jeu à partir d'un fichier spécifié.
     * @param cheminFichier Le chemin du fichier à charger.
     * @return L'état du jeu chargé.
     * @throws IOException En cas d'erreur de lecture du fichier.
     */
    public Game chargerEtat(String cheminFichier) throws IOException {
        try {
            if (cheminFichier == null || cheminFichier.isEmpty()) {
                throw new IllegalArgumentException("Chemin de fichier invalide");
            }
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(new File(cheminFichier), Game.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Méthode pour attribuer les récompenses en fonction du niveau
    private void giveRewards() {
        switch (level) {
            case 1, 2, 4, 5, 7, 8, 10, 11, 13, 14-> this.argent += 10;
            case 3 -> {
                inventaire.add(new ShopItem("Sword 4", 0, "src/main/ressources/knifes/knife#4.png"));
                updateLibrary(inventaire);
            }
            case 6 -> {
                inventaire.add(new ShopItem("src/main/ressources/music/Battle_Theme.wav", 30, "src/main/ressources/button/music.png"));
                updateLibrary(inventaire);
            }
            case 9 -> {
                inventaire.add(new ShopItem("Sword 5", 0, "src/main/ressources/knifes/knife#5.png"));
                updateLibrary(inventaire);
            }
            case 12 -> {
                inventaire.add(new ShopItem("src/main/ressources/music/Main_Theme_2.wav", 0, "src/main/ressources/button/music.png"));
                updateLibrary(inventaire);
            }
            case 15 -> {
                inventaire.add(new ShopItem("src/main/ressources/music/Battle_Theme_2.wav", 0, "src/main/ressources/button/music.png"));
                updateLibrary(inventaire);
            }
            default -> {}
        }
    }

    /**
     * Déclenche l'animation de fin de jeu lorsque le joueur perd toutes ses vies.
     * Cette méthode configure la visibilité de l'écran de fin de jeu en réinitialisant
     * l'opacité à pleine, puis lance un Timer qui réduit progressivement cette opacité.
     * Lorsque l'opacité atteint zéro, le Timer s'arrête et l'état de fin de jeu est désactivé,
     * permettant potentiellement une réinitialisation pour une nouvelle partie.
     *
     * La méthode s'assure également que si un Timer de fin de jeu est déjà en cours, il est arrêté
     * avant d'en commencer un nouveau, évitant ainsi les conflits ou les multiples instances du Timer.
     */
    public void triggerGameOverAnimation() {
        gameOver = true;
        gameOverOpacity = 1.0f;
    
        if (gameOverTimer != null) {
            gameOverTimer.stop();
        }
    
        gameOverTimer = new Timer(30, e -> {
            gameOverOpacity -= 0.01f; 
            if (gameOverOpacity <= 0) {
                gameOverTimer.stop();
                gameOver = false; 
                gameOverOpacity = 0; 
            }
            gameView.repaint();  
        });
        gameOverTimer.start();
    }
    
    /**
    * Réinitialise le round actuel et restaure les vies à 4.
    */
    private void resetRoundAndRestoreLives() {
        vies = 4;  // Restaure les vies à 4
        if (roundManagement != null) {
            roundManagement.resetRounds();  // Réinitialise le round actuel
            listeCible1.clear();
            listeCible1.addAll(roundManagement.getListeRounds().get(roundManagement.getCurrentRoundIndex()).getListeCibles());
            triggerGameOverAnimation();
        }
    }

    /**
    * Réinitialise les points de vie à 4.
    */
    private void resetLives() {
        vies = 4;
    }

}
