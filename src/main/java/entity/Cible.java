package entity;

import geometry.Coordinate;

/**
 * Classe représentant une cible dans le jeu.
 */
public class Cible {

    private TypeCible typeCible;
    private int health;

    private Coordinate coordinate;
    protected double velocite;
    protected boolean isInTheAir;
    protected boolean redescend;
    protected boolean estPasTouche = true;

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
     * @param x Coordonnée x de la cible.
     * @param y Coordonnée y de la cible.
     */
    public Cible(TypeCible type, double x, double y) {
        this.typeCible = type;
        coordinate = new Coordinate(x, y);
    }

 /**
     * Constructeur de la classe Cible.
     *
     * @param typeCible Type de la cible.
     * @param x         Coordonnée x de la cible.
     * @param y         Coordonnée y de la cible.
     * @param velocite  Vitesse de la cible.
     */
    public Cible(TypeCible typeCible,double x, double y, double velocite) {
        this.typeCible = typeCible;
        this.coordinate = new Coordinate(x, y);
        this.velocite = velocite;
        this.isInTheAir = false;
        this.redescend = false;
    }

    /**
     * Constructeur de la classe Cible.
     *
     * @param typeCible Type de la cible.
     * @param health    Santé de la cible.
     * @param x         Coordonnée x de la cible.
     * @param y         Coordonnée y de la cible.
     * @param velocite  Vitesse de la cible.
     */
    public Cible(TypeCible typeCible, int health, double x, double y, double velocite) {
        this.typeCible = typeCible;
        this.health = health;
        this.coordinate = new Coordinate(x, y);
        this.velocite = velocite;
        this.isInTheAir = false;
        this.redescend = false;
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
     * Méthode pour obtenir le type de la cible.
     *
     * @return Le type de la cible.
     */
    public TypeCible getTypeCible() {
        return typeCible;
    }

    public Coordinate getCoord(){return coordinate;}

    /**
     * Méthode pour obtenir la valeur d'argent de la cible.
     *
     * @return La valeur d'argent de la cible.
     */
    public int getArgentCible() {
        return switch (typeCible) {
            case CIBLE_ARGENT -> 10;
            default -> 0;
        };
    }

    /**
     * Méthode pour marquer la cible comme touchée.
     */
    public void estTouche() {
        this.estPasTouche = false;
    }

    /**
     * Méthode pour vérifier si la cible n'a pas été touchée.
     *
     * @return true si la cible n'a pas été touchée, sinon false.
     */
    public boolean estPasTouche() {
        return estPasTouche;
    }

}
