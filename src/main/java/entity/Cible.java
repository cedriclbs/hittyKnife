package entity;

import geometry.Coordinate;
import java.io.Serializable;

public class Cible {

    public enum TypeCible {
        CIBLE_NORMALE,
        CIBLE_ARGENT,
        CIBLE_BONUSGEL,
        CIBLE_BOSS1,
        CIBLE_BOSS2,
        CIBLE_BOSS3
    }

    private TypeCible typeCible;
    private int health;

    private Coordinate coordinate;
    protected double velocite;
    protected boolean isInTheAir;
    protected boolean redescend;
    protected boolean estPasTouche = true;

    public Cible(double x, double y) {
        coordinate = new Coordinate(x, y);
    }

    public Cible(TypeCible typeCible, int health, double x, double y, double velocite) {
        this.typeCible = typeCible;
        this.health = health;
        this.coordinate = new Coordinate(x, y);
        this.velocite = velocite;
        this.isInTheAir = false;
        this.redescend = false;
    }

    public double getY() {
        return coordinate.getY();
    }

    public double getX() {
        return coordinate.getX();
    }

    public void setX(double x) {
        coordinate.setX(x);
    }

    public void setY(double y) {
        coordinate.setY(y);
    }

    public TypeCible getTypeCible() {
        return typeCible;
    }

    public int getArgentCible() {
        switch (typeCible) {
            case CIBLE_ARGENT:
                return 10;
            default:
                return 0;
        }
    }

    public void estTouche() {
        this.estPasTouche = false;
    }

    public boolean estPasTouche() {
        return estPasTouche;
    }
}
