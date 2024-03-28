package entity;

import geometry.Coordinate;
import geometry.Geometry;

public class MovingTarget extends Cible{
    private double angle = 90;
    public MovingTarget(double x, double y){
        super(x,y);

    }

    public void updateMovement(){
        double[] result = Geometry.forwardMovement(getCoord(),angle,15);
        setX(result[0]);
        setY(result[1]);

        if (getY()<10) angle = 90;
        if (getY()>30) angle = 270;
    }


}
