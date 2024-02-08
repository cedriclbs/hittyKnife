package geometry;
import config.*;
public class Geometry{

    private double velocite;
    private double gravity;


    public Geometry(double velocite, double gravity){
        this.velocite = velocite;
        this.gravity = gravity;
    }

    public double upAndDownMovement(double y, int height, int ratio){ //15 //50
        y += (velocite * Loop.delta)/ratio;
        if (y<height) {
            velocite += gravity * Loop.delta;
        }
        else {
            velocite -= gravity * Loop.delta;
        }

        if (y < 0) {
            y = 0;
            velocite = 0;
        }
        return y;


    }







}