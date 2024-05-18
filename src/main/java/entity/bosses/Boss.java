package entity.bosses;

import entity.*;

/**
 * Classe abstraite représentant un boss du jeu.
 */
public abstract class Boss extends Cible {
    int hitCount;
    private TypeCible bosstype;

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
     * @param x        Coordonnée x du boss.
     * @param y        Coordonnée y du boss.
     */
    public Boss(TypeCible bosstype, double x, double y) {
        super(x, y);
        this.bosstype = bosstype;
    }

    /**
     * Obtient le nombre de coups reçus par le boss.
     *
     * @return Le nombre de coups reçus.
     */
    public int getHitCount() {
        return hitCount;
    }

    /**
     * Méthode pour obtenir le type de boss.
     *
     * @return Le type de boss.
     */
    public TypeCible getBosstype(){
        return bosstype;
    }

    /**
     * Vérifie si le boss est mort.
     *
     * @return true si le boss est mort (a atteint 0 de santé), sinon false.
     */
    public boolean isDead() {
        int maxHits = 3;
        return hitCount >= maxHits;
    }


    /**
     * Méthode pour gérer une attaque contre le boss avec un couteau.
     * Cette méthode doit être implémentée dans les classes concrètes.
     */
    public void attacked() {
        this.hitCount++;
    }

    /**
     * Méthode pour mettre à jour le mouvement du boss.
     * Cette méthode doit être implémentée dans les classes concrètes.
     *
     * @param delta Le temps écoulé depuis la dernière mise à jour en secondes.
     */
    public void updateMovement(double delta) {
    }
}
