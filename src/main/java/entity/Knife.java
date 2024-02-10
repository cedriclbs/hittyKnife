package entity;
import geometry.Geometry;
public class Knife {
    private double x,y;
    private int angle = 0;
    private double velocite=0;
    private double gravity=0.2;

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

    public void addAngle(int value){
        angle+=value;
        if (angle>360) angle-=360;
        if (angle<0) angle+=360;
    }


    /**
     * Appel upAndDownMovement de Geometry pour simuler le saut du couteau
     */
    public void saut(){
       double[] result = Geometry.upAndDownMovement(y,15,velocite,gravity,50);
       y=result[0];
       velocite=result[1];
    }

}
