package entity;

import geometry.Coordinate;
import geometry.Geometry;

public class MovingTarget extends Cible{
    private double angle = 90;
    public MovingTarget(double x, double y){
        super(x,y);

    }
    /**
     * Met à jour le mouvement de l'objet en fonction de sa position actuelle et de son angle.
     * Utilise la géométrie pour calculer la nouvelle position en avant.
     * Si la nouvelle position dépasse les limites verticales définies, ajuste l'angle en conséquence.
     */
    public void updateMovement(){
        double[] result = Geometry.forwardMovement(getCoord(),angle,45);
        setX(result[0]);
        setY(result[1]);

        if (getY()<10) angle = 90;
        if (getY()>30) angle = 270;
    }


}
