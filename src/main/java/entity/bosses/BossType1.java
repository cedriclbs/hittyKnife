package entity.bosses;

import entity.Knife;
import entity.TypeCible;

/**
 * Classe représentant un type spécifique de boss.
 */
public class BossType1 extends Boss {

    /**
     * Constructeur de la classe BossType1.
     * Initialise un boss de type 1.
     */
    public BossType1(TypeCible bosstype, int health, double x, double y, double velocite) {
        super(bosstype, health, x, y, velocite);
    }
    
    @Override
    public void attacked(Knife knife) {
        /*
        if (knife.hitBoss(this)) {
            takeDamage(1);
        }
        */
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
