package entity.bosses;

import entity.*;
import geometry.Geometry;

/**
 * Classe représentant un type spécifique de boss.
 */
public class BossType1 extends Boss {
    private boolean directionPositive = true; // Indique si le boss se déplace vers la droite (+x) ou vers la gauche (-x)

    /**
     * Constructeur de la classe BossType1.
     * Initialise un boss de type 1.
     */
    public BossType1(double  x, double y) {
        super(TypeCible.CIBLE_BOSS1, x, y);
    }

    @Override
    public void updateMovement(double delta) {
        double limitTop = 55;
        double limitBottom = -15;

        double speed = 0.6;

        double[] newPosition = Geometry.verticalMovementWithHorizontalAdjustment(getX(), getY(), delta, speed, directionPositive, limitTop, limitBottom, 30, -30);

        setX(newPosition[0]);
        setY(newPosition[1]);
        directionPositive = newPosition[2] == 1;
    }

}
