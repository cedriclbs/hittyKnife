package entity.bosses;

import entity.*;
import geometry.Coordinate;

/**
 * Classe abstraite représentant un boss du jeu.
 */
public abstract class Boss extends Cible {
    private int health;
    private TypeCible bosstype;
    private boolean isBuffed = false;
    private boolean isDebuffed = false;

    private Coordinate coordinate;
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

    /**
     * Méthode pour définir la coordonnée x du boss.
     *
     * @param x Nouvelle coordonnée x du boss.
     */
    public void setX(double x) {
        coordinate.setX(x);
    }

    /**
     * Méthode pour définir la coordonnée y du boss.
     *
     * @param y Nouvelle coordonnée y du boss.
     */
    public void setY(double y) {
        coordinate.setY(y);
    }

    /**
     * Méthode pour infliger des dégâts au boss.
     *
     * @param damage Dommages infligés.
     */
    public void takeDamage(int damage) {
        health -= damage;
    }

    /**
     * Méthode abstraite pour gérer une attaque contre le boss avec un couteau.
     *
     */
    public abstract void attacked();

    /**
     * Méthode abstraite pour faire sauter le boss.
     */
    public abstract void jump();

    /**
     * Méthode abstraite pour déplacer le boss sur le côté.
     */
    public abstract void sideWalk();

    /**
     * Méthode pour mettre à jour le mouvement du boss.
     */
    public abstract void updateMovement();

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
