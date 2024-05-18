package entity;

import static entity.TypeCible.CIBLE_BONUS;

/**
 * Classe représentant un bonus dans le jeu.
 */
public class Bonus extends Cible {

    private TypeBonus typeBonus;

    /**
     * Constructeur de la classe Bonus.
     *
     * @param x          La coordonnée x du bonus.
     * @param y          La coordonnée y du bonus.
     * @param typeBonus  Le type de bonus.
     */
    public Bonus(double x,double y,TypeBonus typeBonus){
        super (CIBLE_BONUS,x,y);
        this.typeBonus = typeBonus;
    }

    /**
     * Obtient le type de bonus.
     *
     * @return Le type de bonus.
     */
    public TypeBonus getTypeBonus() {
        return typeBonus;
    }

    /**
     * Enumération des différents types de bonus.
     */
    public enum TypeBonus {
        BONUS_GEL,      // Ralentit les cibles
        BONUS_POWER,    // Traverse les cibles
        BONUS_GOLD,     // Donne de l'argent
        BONUS_XP,       // Donne de l'expérience
        BONUS_TNT       // Explose
    }
}