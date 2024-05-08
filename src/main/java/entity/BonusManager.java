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

    public void appliquerBonus(Bonus.TypeBonus typeBonus){
        switch(typeBonus){
            case BONUS_GEL : game.gel=true;activateGel();break;

            case BONUS_POWER : game.powered=true;activatePower();break;
            //case BONUS_GOLD -> ;
            //case BONUS_XP -> ;
            //case BONUS_TRAJ -> ;
        }
    }

    public void activateGel() {
        activeGel = true;
        activationTimeGel = System.currentTimeMillis();
    }
    public void activatePower() {
        activePower = true;
        activationTimePower = System.currentTimeMillis();
    }

    public void deactivateGel() {
        activeGel = false;
        game.gel=false;
    }
    public void deactivatePower() {
        activePower = false;
        game.powered=false;
    }
    public void updateBonusEffect() {
        if (activeGel && System.currentTimeMillis() - activationTimeGel >= 10000) {
            deactivateGel();
        }
        if (activePower && System.currentTimeMillis() - activationTimePower >= 10000) {
            deactivatePower();
        }
    }






}
