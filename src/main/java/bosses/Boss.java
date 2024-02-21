package bosses;

import entity.Knife;
import geometry.Geometry;

public abstract class Boss {
    private String name;
    private int health;
    
    private boolean isBuffed = false;
    private boolean isDebuffed = false;
    
    private double x;
    private double y;
    protected double velocite;
    protected boolean isInTheAir;
    protected boolean redescend;    

    public Boss(String name, int health, double x, double y, double velocite) {
        this.name = name;
        this.health = health;
        this.x = x;
        this.y = y;
        this.velocite = velocite;
        this.isInTheAir = false;
        this.redescend = false;     
        
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

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
    
    public double getVelocite() {
        return this.velocite;
    }
    
    public void setVelocite(double velocite) {
        this.velocite = velocite;
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
