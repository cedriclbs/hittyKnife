package entity;

import config.Game;

/**
 * Classe responsable de la gestion des bonus dans le jeu.
 */
public class BonusManager {
    private Game game;
    private boolean activeGel[] = new boolean[2];
    private long activationTimeGel[] = new long[2];
    private boolean activePower[] = new boolean[3];
    private long activationTimePower[] = new long[3];

    /**
     * Constructeur de la classe BonusManager.
     *
     * @param game Instance du jeu pour laquelle les bonus sont gérés.
     */
    public BonusManager(Game game){
        this.game = game;
    }

    /**
     * Cette méthode applique un bonus spécifique au jeu en fonction de son type.
     * Les différents types de bonus affectent le jeu de différentes manières :
     * - BONUS_GEL : Active le gel dans le jeu.
     * - BONUS_POWER : Active une puissance spéciale dans le jeu.
     * - BONUS_GOLD : Ajoute une quantité spécifique d'argent au jeu.
     * - BONUS_XP : Ajoute une quantité spécifique d'expérience au jeu.
     *
     * @param typeBonus Le type de bonus à appliquer au jeu.
     * @param player Le joueur qui reçoit le bonus (0 ou 1).
     */
    public void appliquerBonus(Bonus.TypeBonus typeBonus, int player){
        switch(typeBonus){
            case BONUS_GEL:
                if (player < 1) game.gel[0] = true;
                else game.gel[1] = true;
                activateGel(player);
                break;
            case BONUS_POWER:
                game.powered[player] = true;
                activatePower(player);
                break;
            case BONUS_GOLD:
                game.addArgent(10);
                break;
            case BONUS_XP:
                game.addXP(5);
                break;
            //Bonus TNT géré dans entityDisplay
        }
    }

    /**
     * Cette méthode active le gel dans le jeu pour un joueur spécifique.
     *
     * @param player Le joueur pour lequel le gel est activé (0 ou 1).
     */
    public void activateGel(int player) {
        activeGel[player] = true;
        activationTimeGel[player] = System.currentTimeMillis();
    }

    /**
     * Cette méthode active une puissance spéciale dans le jeu pour un joueur spécifique.
     *
     * @param player Le joueur pour lequel la puissance spéciale est activée (0 à 2).
     */
    public void activatePower(int player) {
        activePower[player] = true;
        activationTimePower[player] = System.currentTimeMillis();
    }

    /**
     * Cette méthode désactive le gel dans le jeu pour un joueur spécifique.
     *
     * @param player Le joueur pour lequel le gel est désactivé (0 ou 1).
     */
    public void deactivateGel(int player) {
        activeGel[player] = false;
        game.gel[player] = false;
    }

    /**
     * Cette méthode désactive une puissance spéciale dans le jeu pour un joueur spécifique.
     *
     * @param player Le joueur pour lequel la puissance spéciale est désactivée (0 à 2).
     */
    public void deactivatePower(int player) {
        activePower[player] = false;
        game.powered[player] = false;
    }

    /**
     * Cette méthode met à jour les effets des bonus actifs dans le jeu.
     * Si le gel est actif et que le temps écoulé depuis son activation dépasse
     * 10 secondes, le gel est désactivé.
     * De même, si la puissance spéciale est active et que le temps écoulé depuis
     * son activation dépasse 10 secondes, la puissance spéciale est désactivée.
     */
    public void updateBonusEffect() {
        if (activeGel[0] && System.currentTimeMillis() - activationTimeGel[0] >= 10000) {
            deactivateGel(0);
        }
        if (activeGel[1] && System.currentTimeMillis() - activationTimeGel[1] >= 10000) {
            deactivateGel(1);
        }
        for (int i = 0; i < 3; i++) {
            if (activePower[i] && System.currentTimeMillis() - activationTimePower[i] >= 10000) {
                deactivatePower(i);
            }
        }
    }
}
