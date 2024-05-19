package entity.bosses;

import entity.*;

import geometry.Geometry;

/**
 * Classe représentant un type spécifique de boss.
 */
public class BossType4 extends Boss {
    private int phase = 1;

    /**
     * Constructeur de la classe BossType4.
     * Initialise un boss de type 4.
     */
    public BossType4(double x, double y) {
        super(TypeCible.CIBLE_BOSS4, x, y);
    }

    @Override
    public void updateMovement(double delta) {
        double speed = 0.5;
        double[] newPosition = Geometry.moveInPattern(getX(), getY(), speed, delta, phase);
        setX(newPosition[0]);
        setY(newPosition[1]);
        phase = (int) newPosition[2];
    }
}
