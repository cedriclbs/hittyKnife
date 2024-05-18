package entity.bosses;

import entity.*;

/**
 * Classe abstraite représentant un boss du jeu.
 */
public class Boss extends Cible {
    private int health;

    /**
     * Constructeur de la classe Boss prenant les coordonnées x et y du boss.
     *
     * @param x Coordonnée x du boss.
     * @param y Coordonnée y du boss.
     */
    public Boss(Double x, Double y, TypeCible bosstype) {
        super(bosstype,x,y);
    }

    /**
     * Constructeur de la classe Boss prenant le type de boss, sa santé, ses coordonnées x et y, et sa vitesse.
     *
     * @param bosstype Type de boss.
     * @param x        Coordonnée x du boss.
     * @param y        Coordonnée y du boss.
     */
    public Boss(TypeCible bosstype, double x, double y) {
        super(bosstype, x, y);
    }


    /**
     * Méthode pour gérer une attaque contre le boss avec un couteau.
     */
    public void attacked() {
        health--;
    }

    /**
     * Méthode pour mettre à jour le mouvement du boss.
     */
    public void updateMovement(double delta) {
    }

    public boolean isDead() {
        return health <= 0;
    }

}
