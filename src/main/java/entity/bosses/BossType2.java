package entity.bosses;

import entity.*;
import geometry.Geometry;

/**
 * Classe représentant un type spécifique de boss.
 */
public class BossType2 extends Boss{
    private double currentAngle = 0;

    /**
     * Constructeur de la classe BossType2.
     * Initialise un boss de type 2.
     */
    public BossType2(double x, double y) {
        super(TypeCible.CIBLE_BOSS2, x, y);
    }

    @Override
    public void updateMovement(double delta) {
        double centerX = 0;
        double centerY = 13;
        double radius = 30;
        double angularSpeed = 0.03;

        double[] newPosition = Geometry.moveInCircle(centerX, centerY, radius, angularSpeed, currentAngle, delta);
        setX(newPosition[0]);
        setY(newPosition[1]);
        currentAngle = newPosition[2];
    }

}
