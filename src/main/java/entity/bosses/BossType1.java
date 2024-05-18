package entity.bosses;

import entity.*;

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
        this.hitCount = 0;
    }

    @Override
    public void updateMovement(double delta) {
        double limitTop = 55; // Position Y la plus haute
        double limitBottom = -15; // Position Y la plus basse

        // Calculer le déplacement en fonction de la vitesse et du temps écoulé depuis la dernière mise à jour, en pixels par seconde
        double speed = 0.6;
        double movement = speed * delta;
        double newY = getY() + (directionPositive ? movement : -movement);


        if (newY >= limitTop) {
            // Inverser la direction du mouvement vertical
            directionPositive = false;
            newY = limitTop;
            setX(30);
        }

        else if (newY <= limitBottom) {
            directionPositive = true;
            newY = limitBottom;
            setX(-30);
        }

        double newX = getX(); // Garder la position horizontale inchangée si le mouvement est vertical
        setY(newY);
        setX(newX);
    }

}
