package geometry;
import config.*;
public class Geometry{



    /**
     * Modifie la position verticale d'un objet en simulant un mouvement de haut en bas.
     * La fonction prend en compte la vélocité actuelle, la gravité et une hauteur maximale.
     *
     * @param y La position verticale actuelle de l'objet.
     * @param height La hauteur maximale à laquelle l'objet peut monter.
     * @param ratio Le ratio utilisé pour ajuster la vélocité du mouvement.
     * @return La nouvelle position verticale de l'objet après le mouvement.
     */
    public static double[] upAndDownMovement(double y, int height, double velocite, double gravity, int ratio){
        y += (velocite * Loop.delta)/ratio;
        if (y<height) {
            velocite += gravity * Loop.delta;
        }
        else {
            velocite -= gravity * Loop.delta;
        }
        if (y < 0) {
            y = 0;
            velocite = 0;
        }
        return new double[]{y,velocite};
    }

    /**
     * Vérifie si un point défini par ses coordonnées (x2, y2) est situé dans un vecteur défini par sa longueur et son angle.
     * Utilise les coordonnées du point de départ (this.x, this.y), la longueur du vecteur et l'angle du vecteur.
     * @param longueur La longueur du vecteur.
     * @param x2       La coordonnée x du point à vérifier.
     * @param y2       La coordonnée y du point à vérifier.
     * @return true si le point (x2, y2) est situé dans le vecteur, sinon false.
     */
    public static boolean estDansVecteur(int longueur, int angle, double x, double y,int x2, int y2) {
        double angleEnRadians = Math.toRadians(angle);

        int xFinale = (int) (x + (int) (longueur * Math.cos(angleEnRadians)));
        int yFinale = (int) (y + (int) (longueur * Math.sin(angleEnRadians)));
        return estSurLigne((int) x, (int) y, xFinale, yFinale, x2, y2);
    }

    /**
     * Vérifie si un point défini par ses coordonnées (px, py) est situé sur une ligne définie par deux points (x1, y1) et (x2, y2).
     * Utilise la méthode de comparaison des distances par rapport à la longueur de la ligne.
     * @param x1 La coordonnée x du premier point de la ligne.
     * @param y1 La coordonnée y du premier point de la ligne.
     * @param x2 La coordonnée x du deuxième point de la ligne.
     * @param y2 La coordonnée y du deuxième point de la ligne.
     * @param px La coordonnée x du point à vérifier.
     * @param py La coordonnée y du point à vérifier.
     * @return true si le point (px, py) est situé sur la ligne, sinon false.
     */
    private static boolean estSurLigne(int x1, int y1, int x2, int y2, int px, int py) {
        double d1 = distanceEntrePoints(px, py, x1, y1);
        double d2 = distanceEntrePoints(px, py, x2, y2);
        double longueurVecteur = distanceEntrePoints(x1, y1, x2, y2);

        return Math.abs(d1 + d2 - longueurVecteur) < 0.1;
    }

    /**
     * Calcule et retourne la distance euclidienne entre deux points définis par leurs coordonnées (x1, y1) et (x2, y2).
     * Utilise la formule de la distance euclidienne : sqrt((x2 - x1)^2 + (y2 - y1)^2).
     * @param x1 La coordonnée x du premier point.
     * @param y1 La coordonnée y du premier point.
     * @param x2 La coordonnée x du deuxième point.
     * @param y2 La coordonnée y du deuxième point.
     * @return La distance euclidienne entre les deux points.
     */
    private static double distanceEntrePoints(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }




}