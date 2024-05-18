package entity.bosses;

import entity.*;

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
        super(TypeCible.CIBLE_BOSS3, 3, x, y);
        this.hitCount = 0;
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
        double movement = speed * delta; // Déplacement en pixels

        double newX = getX();
        double newY = getY();

        // Vérifier la direction du mouvement actuel
        if (directionPositive) {
            // Si la direction est positive, le boss se déplace vers la droite sur l'axe horizontal
            newX += movement;
            // Vérifier si le mouvement dépasse la limite droite
            if (newX >= limitRight) {
                // Une fois arrivé à la limite droite, le boss se déplace vers le bas sur l'axe vertical
                newY -= 2;
                // Inverser la direction du mouvement horizontal pour que le boss se déplace vers la gauche
                directionPositive = false;
            }
        } else {
            // Si la direction est négative, le boss se déplace vers la gauche sur l'axe horizontal
            newX -= movement;
            // Vérifier si le mouvement dépasse la limite gauche
            if (newX <= limitLeft) {
                // Une fois arrivé à la limite gauche, le boss se déplace vers le bas sur l'axe vertical
                newY -= 2;
                // Inverser la direction du mouvement horizontal pour que le boss se déplace vers la droite
                directionPositive = true;
            }
        }

        // Vérifier si le mouvement dépasse la limite inférieure
        if (newY <= limitBottom) {
            // Si le boss atteint la limite basse, le ramener à la position initiale en haut
            newX = -56; // Position horizontale centrale
            newY = limitTop; // Position verticale la plus haute
        }

        setX(newX);
        setY(newY);
    }

}
