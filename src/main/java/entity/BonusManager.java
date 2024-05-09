package entity;

import config.Game;

public class BonusManager {
    Game game;
    private boolean activeGel;
    private long activationTimeGel;
    private boolean activePower;
    private long activationTimePower;

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
     */
    public void appliquerBonus(Bonus.TypeBonus typeBonus){
        switch(typeBonus){
            case BONUS_GEL : game.gel=true;activateGel();break;
            case BONUS_POWER : game.powered=true;activatePower();break;
            case BONUS_GOLD : game.addArgent(40);break;
            case BONUS_XP : game.addXP(20);break;
            //bonus TNT géré dans entityDisplay
        }
    }
    /**
     * Cette méthode active le gel dans le jeu en mettant à jour le statut du gel
     * et en enregistrant le moment de son activation.
     */
    public void activateGel() {
        activeGel = true;
        activationTimeGel = System.currentTimeMillis();
    }
    public void activatePower() {
        activePower = true;
        activationTimePower = System.currentTimeMillis();
    }

    /**
     * Cette méthode désactive le gel dans le jeu en mettant à jour le statut du gel
     * et en réinitialisant le gel dans le jeu.
     */
    public void deactivateGel() {
        activeGel = false;
        game.gel=false;
    }
    public void deactivatePower() {
        activePower = false;
        game.powered=false;
    }
    /**
     * Cette méthode met à jour les effets des bonus actifs dans le jeu.
     * Si le gel est actif et que le temps écoulé depuis son activation dépasse
     * 10 secondes, le gel est désactivé.
     * De même, si la puissance spéciale est active et que le temps écoulé depuis
     * son activation dépasse 10 secondes, la puissance spéciale est désactivée.
     */
    public void updateBonusEffect() {
        if (activeGel && System.currentTimeMillis() - activationTimeGel >= 10000) {
            deactivateGel();
        }
        if (activePower && System.currentTimeMillis() - activationTimePower >= 10000) {
            deactivatePower();
        }
    }






}
