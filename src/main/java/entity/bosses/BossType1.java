package entity.bosses;

import entity.Knife;
import entity.Cible;

/**
 * Classe représentant un type spécifique de boss.
 */
public class BossType1 extends Boss {

    /**
     * Constructeur de la classe BossType1.
     * Initialise un boss de type 1.
     */
    public BossType1() {
        super(Cible.TypeCible.CIBLE_BOSS1, 3, 5, 0, 0);
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
