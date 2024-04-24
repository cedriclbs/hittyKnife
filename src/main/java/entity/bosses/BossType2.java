package entity.bosses;

import entity.Knife;
import entity.TypeCible;

/**
 * Classe représentant un type spécifique de boss.
 */
public class BossType2 extends Boss{

    /**
     * Constructeur de la classe BossType2.
     * Initialise un boss de type 2.
     */
    public BossType2(TypeCible bosstype, int health, double x, double y, double velocite) {
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
    public void jump() {
        
    }

    @Override
    public void sideWalk() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sideWalk'");
    }
    
}
