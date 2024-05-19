package entity.bosses;

import entity.*;

/**
 * Classe abstraite représentant un boss du jeu.
 */
public class Boss extends Cible {
    private int hitCount;

    /**
     * Constructeur de la classe Boss prenant les coordonnées x et y du boss.
     *
     * @param x Coordonnée x du boss.
     * @param y Coordonnée y du boss.
     */
    public Boss(Double x, Double y, TypeCible bosstype) {
        super(bosstype,x,y);
        this.hitCount = 0;
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
        this.hitCount = 0;
    }


    /**
     * Méthode pour gérer une attaque contre le boss avec un couteau.
     * Cette méthode doit être implémentée dans les classes concrètes.
     */
    public void attacked() {
        hitCount++;
    }

    /**
     * Méthode pour mettre à jour le mouvement du boss.
     * Cette méthode doit être implémentée dans les classes concrètes.
     *
     * @param delta Le temps écoulé depuis la dernière mise à jour en secondes.
     */
    public void updateMovement(double delta) {
    }

    /**
     * Vérifie si le boss est mort.
     *
     * @return true si le boss est mort (a atteint 0 de santé), sinon false.
     */
    public boolean isDead() {
        return hitCount >=3;
    }

    public int getHitCount() {
        return hitCount;
    }

}
