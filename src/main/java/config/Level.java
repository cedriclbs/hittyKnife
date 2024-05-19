package config;

/**
 * Représente un niveau dans le jeu. Gère l'état actuel du niveau,
 * incluant le round en cours et la sauvegarde du jeu.
 */
public class Level {
    private int level;
    private int argent;
    private RoundManagement round;

    /**
     * Constructeur pour initialiser un nouveau niveau.
     * Démarre le jeu au niveau 1 avec un nouveau round et une sauvegarde de jeu initiale.
     */
    public Level() {
        this.level = 1;
        this.argent = 0; 
        this.round = new RoundManagement(true);
        //this.gameSave = new GameSave(new Knife(), new ArrayList<Cible>(), argent, level);
    }

    /**
     * Méthode appelée pour gérer la progression des cibles.
     * Vérifie si tous les rounds sont terminés pour passer au niveau suivant.
     */
    public void nextTargetAndUpdateLevel() {
        if (round.isAllRoundsCompleted()) {
            level++;           // Incrémente le niveau
            round.resetRounds();  // Réinitialise les rounds
        }
    }

    /**
     * Sauvegarde l'état actuel du jeu, incluant le couteau en cours, la liste des cibles, l'argent
     * et le niveau actuel.
     */
    public void saveGame() {
        //gameSave = new GameSave(gameSave.getKnife(), gameSave.getListeCible(), gameSave.getArgent(), gameSave.getLevel());
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
