package entity;
import geometry.Coordinate;
import gui.KnifeDisplay;
import geometry.Geometry;
import javax.swing.*;
import java.awt.*;


public class Knife {
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

    public Coordinate getCoordinate() {
        return coordinate;
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
        throwing = true;
        Geometry.forwardMovementSoloMode(coordinate, angle, velocite);
    }


    public void updateMovement() {
        if (isInTheAir) {
            if (throwing) {
                throwKnife();

                // Le couteau sort de l'écran ? Si oui, reset
                if (getX() < 0 || getX() > KnifeDisplay.getBgImgWidth() || getY() < 0 || getY() > KnifeDisplay.getBgImgHeight()) {
                    resetKnife();
                }

            } else {
                updateJump();
                addAngle(4);
            }
        }
    }

    // Ajoutez cette méthode pour réinitialiser le couteau au milieu de l'écran
    public void resetKnife() {
        setX((double) KnifeDisplay.getBgImgWidth() / 2);
        setY((double) KnifeDisplay.getBgImgHeight() / 2);

        isInTheAir = false;
        redescend = false;
        throwing = false;
        velocite = 0;
        angle = 0;
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


}
