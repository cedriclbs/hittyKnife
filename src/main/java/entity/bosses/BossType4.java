package entity.bosses;

import entity.*;

/**
 * Classe représentant un type spécifique de boss.
 */
public class BossType4 extends Boss{
    private int phase = 1;

    /**
     * Constructeur de la classe BossType4.
     * Initialise un boss de type 4.
     */
    public BossType4(double x, double y) {
        super(TypeCible.CIBLE_BOSS4, x, y);
        this.hitCount = 0;
    }

    @Override
    public void updateMovement(double delta) {
        double speed = 0.5;
        double deplacement = speed * delta;

        double newX = getX();
        double newY = getY();
        switch (phase) {
            case 1: // Gauche à droite jusqu'à une certaine X
                newX += deplacement;
                if (newX >= -25) {
                    phase = 2; // Passer à la phase suivante
                }
                break;
            case 2: // De bas en haut jusqu'à une certaine Y
                newY -= deplacement;
                if (newY <= 5) {
                    phase = 3; // Passer à la phase suivante
                }
                break;
            case 3: // De droite à gauche jusqu'à sortir de l'écran
                newX -= deplacement;
                if (newX <= -56) {
                    newX = 56;
                    phase = 4; // Passer à la phase suivante
                }
                break;
            case 4: // Continuer de droite à gauche jusqu'à une certaine -X (opposée à la première phase)
                newX -= deplacement;
                if (newX <= 25) {
                    phase = 5; // Passer à la phase suivante
                }
                break;
            case 5: // De haut en bas jusqu'à une certaine Y
                newY += deplacement;
                if (newY >= 25) {
                    phase = 6; // Passer à la phase suivante
                }
                break;
            case 6: // De gauche à droite jusqu'à sortir de l'écran et revenir à la position initiale
                newX += deplacement;
                if (newX >= 56) {
                    newX = -56; // Réinitialiser la position X
                    phase = 1; // Réinitialiser le motif
                }
                break;
        }
        // Vérifier si le mouvement dépasse la limite
        if (newY <= -50) {
            newX = -56;
            newY = 5;
        }
        setX(newX);
        setY(newY);
    }

}
