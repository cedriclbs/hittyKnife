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
        // Définir les limites de déplacement sur l'axe vertical
        double limitTop = 40; // Position Y la plus haute
        double limitBottom = -5; // Position Y la plus basse

        // Calculer le déplacement en fonction de la vitesse et du temps écoulé depuis la dernière mise à jour
        double speed = 1; // Vitesse du mouvement en pixels par seconde
        double movement = speed * delta; // Déplacement en pixels
        double newY = getY() + (directionPositive ? movement : -movement);

        // Vérifier si le mouvement dépasse la limite supérieure
        if (newY >= limitTop) {
            // Inverser la direction du mouvement vertical
            directionPositive = false;
            // Corriger la position verticale pour rester dans les limites
            newY = limitTop;
            // Changer la position horizontale
            setX(40); // Changer la valeur selon les besoins
        }
        // Vérifier si le mouvement dépasse la limite inférieure
        else if (newY <= limitBottom) {
            // Inverser la direction du mouvement vertical
            directionPositive = true;
            // Corriger la position verticale pour rester dans les limites
            newY = limitBottom;
            // Changer la position horizontale
            setX(-40); // Changer la valeur selon les besoins
        }

        double newX = getX(); // Garder la position horizontale inchangée si le mouvement est vertical
        setY(newY);
        setX(newX);
    }

}
