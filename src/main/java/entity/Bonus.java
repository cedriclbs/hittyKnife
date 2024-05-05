package entity;

import config.Game;

import static entity.TypeCible.CIBLE_BONUS;

public class Bonus extends Cible{

    private TypeBonus typeBonus;

    public Bonus(double x,double y,TypeBonus typeBonnus){
        super (CIBLE_BONUS,x,y);
        this.typeBonus = typeBonnus;
    }
    public TypeBonus getTypeBonus(){
        return typeBonus;
    }

    public enum TypeBonus{
        BONUS_GEL,
        BONUS_POWER,        //traverse les cibles
        BONUS_GOLD,
        BONUS_XP,
        BONUS_TRAJ;         //change la trajectoire pour pouvoir toucher plusieurs cibles (traj circulaire)

    }

}
