package entity;
import geometry.Geometry;


public class Knife {
    private double x,y;
    private int angle = 0;
    private double velocite=0;
    public boolean isInTheAir = false;
    public boolean redescend = false;
    public boolean throwing = false;

    public Knife(){
        this.x=10;
        this.y=0;
    }

    public double getY(){
        return y;
    }
    public double getX() {
        return x;
    }
    public void setX(double x){
        this.x=x;
    }
    public void setY(double y) {
        this.y = y;
    }

    public int getAngle(){
        return angle;
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


    public void updateMovement(){
        if (isInTheAir){
            if (throwing){
                throwKnife();
            }
            else {
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
        double[] result = Geometry.upAndDownMovement(y,10,velocite, gravity,50,this);
       y=result[0];
       velocite=result[1];
       if (y<1 && velocite<1 && redescend){
           isInTheAir = false;
           redescend = false;
           velocite = 0;

       }
    }

    public void throwKnife(){
        double deltaX;
        double deltaY;
        double angleRad = angle * Math.PI / 180.0;
        deltaX = Math.cos(angleRad);
        deltaY = Math.sin(angleRad);
        x+=deltaX/2;
        y+=deltaY/2;
    }

}
