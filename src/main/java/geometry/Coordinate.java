package geometry;

/**
 * Classe représentant une paire de coordonnées (x, y).
 */
public class Coordinate {
    private double x,y;

    /**
     * Constructeur de la classe Coordinate.
     *
     * @param x Coordonnée x.
     * @param y Coordonnée y.
     */
    public Coordinate(double x,double y){
        this.x =x;
        this.y = y;
    }

    /**
     * Méthode pour obtenir la coordonnée x.
     *
     * @return La coordonnée x.
     */
    public double getX() {
        return x;
    }

    /**
     * Méthode pour obtenir la coordonnée y.
     *
     * @return La coordonnée y.
     */
    public double getY() {
        return y;
    }

    /**
     * Méthode pour définir la coordonnée y.
     *
     * @param y Nouvelle coordonnée y.
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Méthode pour définir la coordonnée x.
     *
     * @param x Nouvelle coordonnée x.
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Méthode pour définir les coordonnées (x, y).
     *
     * @param x Nouvelle coordonnée x.
     * @param y Nouvelle coordonnée y.
     */
    public void setCoordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

}
