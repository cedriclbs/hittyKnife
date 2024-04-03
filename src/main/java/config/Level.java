package config;

/**
 * Représente un niveau dans le jeu. Gère l'état actuel du niveau,
 * incluant le round en cours et la sauvegarde du jeu.
 */
public class Level {
    private int level;
    private int argent;
    private Round round;
    //private GameSave gameSave;

    /**
     * Constructeur pour initialiser un nouveau niveau.
     * Démarre le jeu au niveau 1 avec un nouveau round et une sauvegarde de jeu initiale.
     */
    public Level() {
        this.level = 1;
        this.argent = 0; 
        this.round = new Round(); 
        //this.gameSave = new GameSave(new Knife(), new ArrayList<Cible>(),argent,level);
    }

    /**
     * Méthode appelée pour gérer la progression des cibles.
     * Vérifie si tous les rounds et le boss ont été terminés pour passer au niveau suivant.
     */
    public void nextTargetAndUpdateLevel() {
        round.nextTarget();

        // Vérifie si tous les rounds et le boss ont été terminés
        if (round.isAllRoundCompleted()) {
            bossDefeated(); // Méthode pour gérer la fin du boss et passer au niveau suivant
        }
    }

    /**
     * Gère les actions à effectuer lorsque le boss est vaincu, comme incrémenter le niveau,
     * réinitialiser le round et sauvegarder le jeu.
     */
    public void bossDefeated() {
        level++; 
        round.reset(); 
        saveGame();
    }

    /**
     * Sauvegarde l'état actuel du jeu, incluant le couteau en cours, la liste des cibles,l'argent
     * et le niveau actuel.
     */
    public void saveGame() {
        //gameSave = new GameSave(gameSave.getKnife(), gameSave.getListeCible(),gameSave.getArgent(),gameSave.getLevel());
    }

    /**
     * Retourne le niveau actuel.
     *
     * @return Le niveau actuel du jeu.
     */
    public int getLevel() {
        return level;
    }

}