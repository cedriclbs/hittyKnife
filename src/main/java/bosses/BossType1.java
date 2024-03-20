package bosses;

import entity.Knife;
import entity.Cible;

public class BossType1 extends Boss {

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
