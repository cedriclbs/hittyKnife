package entity;

import geometry.Coordinate;

/**
 * Classe représentant une cible dans le jeu.
 */
public class Cible {
    private TypeCible typeCible;
    private final Coordinate coordinate;

    /**
     * Constructeur de la classe Cible.
     *
     * @param x Coordonnée x de la cible.
     * @param y Coordonnée y de la cible.
     */
    public Cible(double x, double y) {
        coordinate = new Coordinate(x, y);
    }

    /**
     * Constructeur de la classe Cible.
     *
     * @param typeCible Type de la cible.
     * @param x         Coordonnée x de la cible.
     * @param y         Coordonnée y de la cible.
     */
    public Cible(TypeCible typeCible, double x, double y) {
        this.typeCible = typeCible;
        coordinate = new Coordinate(x, y);
    }

    /**
     * Méthode pour obtenir la coordonnée y de la cible.
     *
     * @return La coordonnée y de la cible.
     */
    public double getY() {
        return coordinate.getY();
    }

    /**
     * Méthode pour obtenir la coordonnée x de la cible.
     *
     * @return La coordonnée x de la cible.
     */
    public double getX() {
        return coordinate.getX();
    }

    /**
     * Méthode pour définir la coordonnée x de la cible.
     *
     * @param x Nouvelle coordonnée x de la cible.
     */
    public void setX(double x) {
        coordinate.setX(x);
    }

    /**
     * Méthode pour définir la coordonnée y de la cible.
     *
     * @param y Nouvelle coordonnée y de la cible.
     */
    public void setY(double y) {
        coordinate.setY(y);
    }

    /**
     * Méthode pour obtenir les coordonnées de la cible.
     *
     * @return Les coordonnées de la cible
     */
    public Coordinate getCoord(){return coordinate;}

}
