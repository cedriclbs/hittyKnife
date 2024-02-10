package entity;
import geometry.Geometry;
public class Knife {
    private double x,y;
    private int angle = 0;
    private double velocite=0;
    private final double GRAVITY=0.4;
    public boolean isInTheAir = false;
    public boolean redescend = false;

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
       double[] result = Geometry.upAndDownMovement(y,10,velocite,GRAVITY,50,this);
       y=result[0];
       velocite=result[1];
       if (y<1 && velocite<1 && redescend){
           isInTheAir = false;
           redescend = false;
           velocite = 0;

       }
    }

}
