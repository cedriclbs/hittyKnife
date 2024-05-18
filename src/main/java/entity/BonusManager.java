package entity;

import config.Game;

public class BonusManager {
    Game game;
    private boolean activeGel[] = new boolean[2];
    private long activationTimeGel[] = new long[2];
    private boolean activePower[] = new boolean[3];
    private long activationTimePower[] = new long[3];

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
    public void appliquerBonus(Bonus.TypeBonus typeBonus,int player){
        switch(typeBonus){
            case BONUS_GEL : if (player<1) game.gel[0]=true; else game.gel[1]=true;activateGel(player);break;
            case BONUS_POWER : game.powered[player]=true;activatePower(player);break;
            case BONUS_GOLD : game.addArgent(10);break;
            case BONUS_XP : game.addXP(5);break;
            //bonus TNT géré dans entityDisplay
        }
    }
    /**
     * Cette méthode active le gel dans le jeu en mettant à jour le statut du gel
     * et en enregistrant le moment de son activation.
     */
    public void activateGel(int player) {
        if (player<1)activeGel[0] = true; else activeGel[1] = true;
        if (player<1)activationTimeGel[0] = System.currentTimeMillis();else activationTimeGel[1] = System.currentTimeMillis();
    }
    public void activatePower(int player) {
        activePower[player] = true;
        activationTimePower[player] = System.currentTimeMillis();
    }

    /**
     * Cette méthode désactive le gel dans le jeu en mettant à jour le statut du gel
     * et en réinitialisant le gel dans le jeu.
     */
    public void deactivateGel(int player) {
        if (player<1)activeGel[0] = false;else activeGel[1] = false;
        if (player<1) game.gel[0]=false; else game.gel[1] = false;
    }
    public void deactivatePower(int player) {
        activePower[player] = false;
        game.powered[player]=false;
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
        for (int i =0 ; i<3 ; i++) {
            if (activePower[i] && System.currentTimeMillis() - activationTimePower[i] >= 10000) {
                deactivatePower(i);
            }
        }
    }






}
