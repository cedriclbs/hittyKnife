package entity;

import geometry.Geometry;

/**
 * Classe représentant une cible mobile dans le jeu.
 */
public class MovingTarget extends Cible {
    private double angle = 90;

    /**
     * Constructeur de la classe MovingTarget.
     *
     * @param x Coordonnée x initiale de la cible mobile.
     * @param y Coordonnée y initiale de la cible mobile.
     */
    public MovingTarget(double x, double y) {
        super(x, y);
    }

    /**
     * Met à jour le mouvement de l'objet en fonction de sa position actuelle et de son angle.
     * Utilise la géométrie pour calculer la nouvelle position en avant.
     * Si la nouvelle position dépasse les limites verticales définies, ajuste l'angle en conséquence.
     */
    public void updateMovement() {
        // Calculer la nouvelle position de la cible en fonction de sa position actuelle et de l'angle
        double[] result = Geometry.forwardMovement(getCoord(), angle, 65);
        setX(result[0]);
        setY(result[1]);

        // Ajuster l'angle si la cible atteint les limites verticales définies
        if (getY() < 10) {
            angle = 90; // Changer l'angle pour que la cible remonte
        }
        if (getY() > 30) {
            angle = 270; // Changer l'angle pour que la cible redescende
        }
    }
}
