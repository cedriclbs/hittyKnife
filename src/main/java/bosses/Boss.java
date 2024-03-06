package bosses;

import entity.Cible;
import entity.Knife;
import geometry.Coordinate;
import geometry.Geometry;

public abstract class Boss extends Cible {
    private String name;
    private int health;
    
    private boolean isBuffed = false;
    private boolean isDebuffed = false;

    private Coordinate coordinate;
    protected double velocite;
    protected boolean isInTheAir;
    protected boolean redescend;

    public Boss(Double x, Double y) {
        super(x,y);
    }

    public Boss(String name, int health, double x, double y, double velocite) {
        super(name, health, x, y, velocite);        
    }

    public void takeDamage(int damage) {
        if (isDebuffed) {
            health -= 2 * damage;
        }
        if (isBuffed) {
            health -= 2 / damage;
        }
        if (health <= 0) {
            //System.out.println(name + " is dead");
        }
    }

    public abstract void attacked(Knife knife);

    public abstract void jump();

    public abstract void sideWalk();

    public void updateMovement(){
        if (isInTheAir){
            //updateJump();
            }
    }    

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }
    
    public double getVelocite() {
        return this.velocite;
    }
    
    public void setVelocite(double velocite) {
        this.velocite = velocite;
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
    
    /*
    public void updateJump(){
        double gravity = 0.4;
        //double[] result = Geometry.upAndDownMovement(y,10,velocite, gravity,50, this);
       y=result[0];
       velocite=result[1];
       if (y<1 && velocite<1 && redescend){
           isInTheAir = false;
           redescend = false;
           velocite = 0;

       }
    }
    */
    
}
