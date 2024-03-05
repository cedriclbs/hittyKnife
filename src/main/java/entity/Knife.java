package entity;
import geometry.Coordinate;
import geometry.Geometry;
import gui.KnifeDisplay;
import javax.swing.*;
import java.awt.*;

import java.io.Serializable;

public class Knife implements Serializable {
    private final Coordinate coordinate;
    private int angle = 0;
    private double velocite=0;
    public boolean isInTheAir = false;
    public boolean redescend = false;
    public boolean throwing = false;





    public Knife(){
        this.coordinate = new Coordinate(10,0);
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

    public int getAngle(){
        return angle;
    }


    public void startThrowing() {
        throwing = true;
    }

    public void resetThrowing() {
        this.throwing = false;
    }

    public void setAngle(int angle){
        this.angle=angle;
    }

    public double getVelocite() {
        return velocite;
    }

    public void setVelocite(double velocite) {
        this.velocite = velocite;
    }

    public void addAngle(int value){
        angle+=value;
        if (angle>360) angle-=360;
        if (angle<0) angle+=360;
    }


    public void jump(){
        isInTheAir = true;
        velocite = 10;
    }

    public void throwKnife(){
        if (throwing) {
            Geometry.forwardMovement(coordinate, angle);
        }
    }


    public void updateMovement() {
        if (isInTheAir) {
            if (throwing) {
                throwKnife();
            } else {
                updateJump();
                addAngle(4);
            }
        }
    }

    /**
     * Met à jour la position verticale et la vélocité du saut du personnage.
     * Cette méthode utilise la classe Geometry pour effectuer le mouvement ascendant
     * et descendant en fonction des paramètres spécifiés.
     *
     * @implNote La méthode met à jour la position y et la vélocité du personnage en fonction
     *           du mouvement ascendant et descendant du saut.
     *           Lorsque le personnage atteint le sommet du saut et commence à redescendre,
     *           la variable isInTheAir est mise à false, indiquant que le personnage n'est plus en l'air.
     *           Si le personnage a redescendu suffisamment bas et la vélocité est faible,
     *           la variable redescend est mise à false, la vélocité est réinitialisée à zéro,
     *           indiquant que le personnage a atterri.
     */
    public void updateJump(){
        double gravity = 0.4;
        double[] result = Geometry.upAndDownMovement(coordinate.getY(),10,velocite, gravity,50,this);
        coordinate.setY(result[0]);
        velocite=result[1];
            if (coordinate.getY()<1 && velocite<1 && redescend){
                isInTheAir = false;
                redescend = false;
                velocite = 0;
                angle = 0;
            }
    }


    public void updatePositionTest() {
        this.coordinate.setX(this.coordinate.getX()+1);
    }
}
