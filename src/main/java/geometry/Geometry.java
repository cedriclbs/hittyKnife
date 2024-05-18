package geometry;
import App.Loop;
import entity.Knife;
import entity.bosses.Boss;

public class Geometry{

    /**
     * Modifie la position verticale d'un objet en simulant un mouvement de haut en bas.
     * La fonction prend en compte la vélocité actuelle, la gravité et une hauteur maximale.
     *
     * @param y La position verticale actuelle de l'objet.
     * @param height La hauteur maximale à laquelle l'objet peut monter.
     * @param ratio Le ratio utilisé pour ajuster la vélocité du mouvement.
     * @param knife éventuellement le couteau pour indiquer s'il redescend.
     * @return La nouvelle position verticale de l'objet après le mouvement.
     */
    public static double[] upAndDownMovement(double y, int height, double velocite, double gravity, int ratio, Knife knife){
        y += (velocite * Loop.delta)/ratio;
        if (y<height) {
            velocite += gravity * Loop.delta;
        }
        else {
            velocite -= gravity * Loop.delta;
            if (knife != null) knife.redescend = true;
        }
        if (y < 0) {
            y = 0;
            velocite = 0;
        }
        if (knife != null) {
            knife.setVelocite(velocite);
        }
        return new double[]{y,velocite};
    }

    /**
     * Déplace les coordonnées spécifiées vers l'avant selon l'angle donné.
     * @param coordinate Les coordonnées à déplacer.
     * @param angle L'angle en degrés.
     */
    public static double[] forwardMovement(Coordinate coordinate, double angle,int ratio){
        double deltaX;
        double deltaY;
        double angleRad = angle * Math.PI / 180.0;
        deltaX = Math.cos(angleRad);
        deltaY = Math.sin(angleRad);
        double[] result = new double[2];
        result[0] =  coordinate.getX()+deltaX/ratio;
        result[1] =  coordinate.getY()+deltaY/ratio;
        return result;
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

    /**
     * Modifie la position horizontale d'un objet en simulant un mouvement de gauche à droite.
     * La fonction prend en compte la vélocité horizontale actuelle, l'accélération horizontale et une largeur maximale.
     *
     * @param x La position horizontale actuelle de l'objet.
     * @param width La largeur maximale à laquelle l'objet peut se déplacer.
     * @param velocityX La vélocité horizontale actuelle de l'objet.
     * @param accelerationX L'accélération horizontale appliquée à l'objet.
     * @param ratio Le ratio utilisé pour ajuster la vélocité du mouvement.
     * @param boss Le boss associé à l'objet, ou null s'il n'y en a pas.
     * @return Un tableau contenant la nouvelle position horizontale et la nouvelle vélocité horizontale de l'objet après le mouvement.
     */
    public static double[] leftAndRightMovement(double x, int width, double velocityX, double accelerationX, int ratio, Boss boss) {
        return new double[]{5.5, 3.3};
    }
}