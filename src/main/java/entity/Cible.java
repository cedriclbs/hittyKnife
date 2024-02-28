package entity;
import geometry.Coordinate;

import java.io.Serializable;

public class Cible implements Serializable {

    private String name;
    private int health;

    private Coordinate coordinate;
    protected double velocite;
    protected boolean isInTheAir;
    protected boolean redescend;

    public Cible(double x,double y){
        coordinate = new Coordinate(x,y);
    }

    public Cible(String name, int health, double x, double y, double velocite) {
        this.name = name;
        this.health = health;
        this.coordinate = new Coordinate(x,y);
        this.velocite = velocite;
        this.isInTheAir = false;
        this.redescend = false;  
    }


    public double getY(){
        return coordinate.getY();
    }
    public double getX() {
        return coordinate.getX();
    }
    public void setX(double x){
        coordinate.setX(x);
    }

    public void setY(double y) {
        coordinate.setY(y);
    }

}
