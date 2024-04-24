package entity.bosses;

import entity.*;
import geometry.Coordinate;

/**
 * Classe abstraite représentant un boss du jeu.
 */
public class Boss extends Cible {
    private int health;
    private TypeCible bosstype;
    protected double velocite;
    protected boolean isInTheAir;
    public boolean redescend;

    /**
     * Constructeur de la classe Boss prenant les coordonnées x et y du boss.
     *
     * @param x Coordonnée x du boss.
     * @param y Coordonnée y du boss.
     */
    public Boss(Double x, Double y) {
        super(x,y);
    }

    /**
     * Constructeur de la classe Boss prenant le type de boss, sa santé, ses coordonnées x et y, et sa vitesse.
     *
     * @param bosstype Type de boss.
     * @param health   Santé du boss.
     * @param x        Coordonnée x du boss.
     * @param y        Coordonnée y du boss.
     * @param velocite Vitesse du boss.
     */
    public Boss(TypeCible bosstype, int health, double x, double y, double velocite) {
        super(bosstype, health, x, y, velocite);        
    }

    /**
     * Méthode pour obtenir le type de boss.
     *
     * @return Le type de boss.
     */
    public TypeCible getBosstype(){
        return bosstype;
    }

    /**
     * Méthode pour obtenir la santé du boss.
     *
     * @return La santé du boss.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Méthode pour obtenir la vitesse du boss.
     *
     * @return La vitesse du boss.
     */
    public double getVelocite() {
        return this.velocite;
    }

    /**
     * Méthode pour définir la vitesse du boss.
     *
     * @param velocite La nouvelle vitesse du boss.
     */
    public void setVelocite(double velocite) {
        this.velocite = velocite;
    }

    public void takeDamage(int damage) {
        health -= damage;
    }

    /**
     * Méthode pour gérer une attaque contre le boss avec un couteau.
     */
    public void attacked() {

    }

    /**
     * Méthode pour faire sauter le boss.
     */
    public void jump() {

    }

    /**
     * Méthode pour déplacer le boss sur le côté.
     */
    public void sideWalk() {

    }

    /**
     * Méthode pour mettre à jour le mouvement du boss.
     */
    public void updateMovement(double delta) {

    }

    protected void setHealth(int x) { health = x;
    }

    public boolean isDead() {
        return health <= 0;
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
