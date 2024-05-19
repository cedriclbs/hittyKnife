package entity.bosses;

import entity.*;
import geometry.Geometry;

/**
 * Classe représentant un type spécifique de boss.
 */
public class BossType3 extends Boss{
    private boolean directionPositive = true; // Indique si le boss se déplace vers la droite (+x) ou vers la gauche (-x)

    /**
     * Constructeur de la classe BossType3.
     * Initialise un boss de type 3.
     */
    public BossType3(double x, double y) {
        super(TypeCible.CIBLE_BOSS3, x, y);
    }

    @Override
    public void updateMovement(double delta) {
        double limitLeft = -56;
        double limitRight = 56;

        double limitTop = 35;
        double limitBottom = 25;


        double speed = 0.6;


        double[] newPosition = Geometry.horizontalMovementWithVerticalAdjustment(getX(), getY(), delta, speed, directionPositive, limitLeft, limitRight, limitTop, limitBottom);

        setX(newPosition[0]);
        setY(newPosition[1]);
        directionPositive = newPosition[2] == 1;
    }

}
