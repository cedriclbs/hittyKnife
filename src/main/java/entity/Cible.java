package entity;
import geometry.Coordinate;

import java.io.Serializable;

public class Cible implements Serializable {


    private final Coordinate coordinate;

    Cible(double x,double y){
        coordinate = new Coordinate(x,y);
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
