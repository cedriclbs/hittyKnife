package entity.bosses;

import entity.*;

/**
 * Classe représentant un type spécifique de boss.
 */
public class BossType2 extends Boss{
    private int hitCount;

    /**
     * Constructeur de la classe BossType2.
     * Initialise un boss de type 2.
     */
    public BossType2(double x, double y) {
        super(TypeCible.CIBLE_BOSS2, 3, x, y);
        this.hitCount = 0;
    }
    @Override
    public void attacked() {
        this.hitCount++;
    }

    public boolean isDead() {
        int maxHits = 3;
        return hitCount >= maxHits;
    }

    @Override
    public void updateMovement(double delta) {
        // Définir le centre du cercle
        double centerX = 0;
        double centerY = 13;

        // Définir le rayon du cercle
        double radius = 30;

        // Définir la vitesse de rotation
        double angularSpeed = 0.1; // en radians par seconde

        // Calculer l'angle de rotation en fonction du temps écoulé depuis la dernière mise à jour
        double movement = 0.28 * delta;
        double angle = angularSpeed * movement;

        // Ajouter l'angle à l'angle actuel
        double currentAngle = Math.atan2(getY() - centerY, getX() - centerX);
        double newAngle = currentAngle + angle;

        // Calculer la nouvelle position en utilisant la formule paramétrique du cercle
        double newX = centerX + radius * Math.cos(newAngle);
        double newY = centerY + radius * Math.sin(newAngle);

        setX(newX);
        setY(newY);
    }

}
