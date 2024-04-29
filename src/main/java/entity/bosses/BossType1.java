package entity.bosses;

import entity.*;

/**
 * Classe représentant un type spécifique de boss.
 */
public class BossType1 extends Boss {
    private int hitCount;
    private boolean directionPositive = true; // Indique si le boss se déplace vers la droite (+x) ou vers la gauche (-x)

    /**
     * Constructeur de la classe BossType1.
     * Initialise un boss de type 1.
     */
    public BossType1(double  x, double y) {
        super(TypeCible.CIBLE_BOSS1, 3, x, y);
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
        // Définir les limites de déplacement sur l'axe horizontal
        double limitLeft = -56;
        double limitRight = 56;

        // Définir les limites de déplacement sur l'axe vertical
        double limitTop = 20; // Changer la valeur selon les besoins
        double limitBottom = 40; // Changer la valeur selon les besoins

        // Calculer le déplacement en fonction de la vitesse et du temps écoulé depuis la dernière mise à jour
        double speed = 1; // Vitesse du mouvement en pixels par seconde
        double movement = speed * delta; // Déplacement en pixels
        double newX = getX() + (directionPositive ? movement : -movement);

        // Vérifier si le mouvement dépasse la limite gauche
        if (newX <= limitLeft) {
            // Inverser la direction du mouvement horizontal
            directionPositive = true;
            // Corriger la position horizontale pour rester dans les limites
            newX = limitLeft;
            // Changer la position verticale
            setY(limitTop);
        }
        // Vérifier si le mouvement dépasse la limite droite
        else if (newX >= limitRight) {
            // Inverser la direction du mouvement horizontal
            directionPositive = false;
            // Corriger la position horizontale pour rester dans les limites
            newX = limitRight;
            // Changer la position verticale
            setY(limitBottom);
        }

        double newY = getY(); // Garder la position verticale inchangée si le mouvement est horizontal
        setX(newX);
        setY(newY);
    }

}
