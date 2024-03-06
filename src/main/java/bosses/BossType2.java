package bosses;

import entity.Knife;

public class BossType2 extends Boss{

    public BossType2() {
        super("Boss 2", 4, 10, 0, 0);
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

    @Override
    public void updateMovement() {
        updateLeftRight();
    }
    
}
