package entity.bosses;

import entity.*;

/**
 * Classe représentant un type spécifique de boss.
 */
public class BossType2 extends Boss{

    /**
     * Constructeur de la classe BossType2.
     * Initialise un boss de type 2.
     */
    public BossType2() {
        super(TypeCible.CIBLE_BOSS2, 4, 10, 0, 0);
    }

    @Override
    public void attacked() {
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
