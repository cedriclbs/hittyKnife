package entity.bosses;

import entity.*;
import geometry.Geometry;

/**
 * Classe représentant un type spécifique de boss.
 */
public class BossType3 extends Boss{
    private int hitCount;
    private boolean directionPositive = true; // Indique si le boss se déplace vers la droite (+x) ou vers la gauche (-x)

    /**
     * Constructeur de la classe BossType3.
     * Initialise un boss de type 3.
     */
    public BossType3(double x, double y) {
        super(TypeCible.CIBLE_BOSS3, x, y);
        this.hitCount = 0;
    }
    @Override
    public void attacked() {
        this.hitCount++;
    }

    public boolean isDead() {
        int health = 3;
        return hitCount >= health;
    }

    public int getHitCount() {
        return hitCount;
    }

    @Override
    public void updateMovement(double delta) {
        // Définir les limites de déplacement sur l'axe horizontal
        double limitLeft = -56;
        double limitRight = 56;

        // Définir les limites de déplacement sur l'axe vertical
        double limitTop = 40; // Position Y la plus haute
        double limitBottom = 25; // Position Y la plus basse

        // Calculer le déplacement en fonction de la vitesse et du temps écoulé depuis la dernière mise à jour
        double speed = 0.6; // Vitesse du mouvement en pixels par seconde

        // Utiliser la méthode centralisée pour obtenir la nouvelle position et la direction
        double[] newPosition = Geometry.horizontalMovementWithVerticalAdjustment(getX(), getY(), delta, speed, directionPositive, limitLeft, limitRight, limitTop, limitBottom);

        setX(newPosition[0]);
        setY(newPosition[1]);
        directionPositive = newPosition[2] == 1;
    }

}
