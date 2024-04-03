package entity;

/**
 * Classe représentant un type spécifique de boss.
 */
public class BossType1 extends Boss {
    private int hitCount;
    private final int maxHits = 3;

    /**
     * Constructeur de la classe BossType1.
     * Initialise un boss de type 1.
     */
    public BossType1(int x, int y) {
        super(TypeCible.CIBLE_BOSS1, 3, x, y, 0);
        this.hitCount = 0;
    }
    
    @Override
    public void attacked() {
        this.hitCount++;
    }

    public boolean isDead() {
        return hitCount >= maxHits;
    }

    @Override
    public void jump(){
        isInTheAir = true;
        velocite = 10;
    }

    @Override
    public void sideWalk() {
    }

}
