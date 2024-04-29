package entity.bosses;

import entity.*;

/**
 * Classe représentant un type spécifique de boss.
 */
public class BossType2 extends Boss{
    private int hitCount;
    private boolean directionPositive = true; // Indique si le boss se déplace vers la droite (+x) ou vers la gauche (-x)

    /**
     * Constructeur de la classe BossType2.
     * Initialise un boss de type 2.
     */
    public BossType2(double x, double y) {
        super(TypeCible.CIBLE_BOSS2, 3, x, y);
        this.hitCount = 0;
    }
    @Override
    public void attacked() {
        this.hitCount++;
    }

    public boolean isDead() {
        int maxHits = 3;
        return hitCount >= maxHits;
    }

    @Override
    public void updateMovement(double delta) {
        double a = 5.5;
    }

}
