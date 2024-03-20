package bosses;

import entity.Knife;
import entity.Cible;

public class BossType2 extends Boss{

    public BossType2() {
        super(Cible.TypeCible.CIBLE_BOSS2, 4, 10, 0, 0);
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
