package entity.bosses;

import entity.*;

/**
 * Classe abstraite représentant un boss du jeu.
 */
public class Boss extends Cible {
    private int health;
    private TypeCible bosstype;
    protected boolean isInTheAir;

    /**
     * Constructeur de la classe Boss prenant les coordonnées x et y du boss.
     *
     * @param x Coordonnée x du boss.
     * @param y Coordonnée y du boss.
     */
    public Boss(Double x, Double y) {
        super(x,y);
    }

    /**
     * Constructeur de la classe Boss prenant le type de boss, sa santé, ses coordonnées x et y, et sa vitesse.
     *
     * @param bosstype Type de boss.
     * @param health   Santé du boss.
     * @param x        Coordonnée x du boss.
     * @param y        Coordonnée y du boss.
     */
    public Boss(TypeCible bosstype, int health, double x, double y) {
        super(bosstype, health, x, y);
    }

    /**
     * Méthode pour obtenir le type de boss.
     *
     * @return Le type de boss.
     */
    public TypeCible getBosstype(){
        return bosstype;
    }

    public void takeDamage(int damage) {
        health -= damage;
    }

    /**
     * Méthode pour gérer une attaque contre le boss avec un couteau.
     */
    public void attacked() {

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
