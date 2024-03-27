package entity;
import geometry.Coordinate;
import geometry.Geometry;

/**
 * Classe représentant un couteau dans le jeu.
 */
public class Knife {
    private final Coordinate coordinate;
    private final double DEFAULT_ANGLE = 90;
    private double angle = DEFAULT_ANGLE;
    private double velocite=0;
    public boolean isInTheAir = false;
    public boolean redescend = false;
    public boolean throwing = false;

    /**
     * Constructeur de la classe Knife.
     * Initialise un couteau avec des coordonnées par défaut.
     */
    public Knife(){
        this.coordinate = new Coordinate(0,0);
    }

    /**
     * Méthode pour obtenir la coordonnée y du couteau.
     *
     * @return La coordonnée y du couteau.
     */
    public double getY() {
        return coordinate.getY();
    }

    /**
     * Méthode pour obtenir la coordonnée x du couteau.
     *
     * @return La coordonnée x du couteau.
     */
    public double getX() {
        return coordinate.getX();
    }

    /**
     * Méthode pour définir la coordonnée x du couteau.
     *
     * @param x Nouvelle coordonnée x du couteau.
     */
    public void setX(double x) {
        coordinate.setX(x);
    }

    /**
     * Méthode pour définir la coordonnée y du couteau.
     *
     * @param y Nouvelle coordonnée y du couteau.
     */
    public void setY(double y) {
        coordinate.setY(y);
    }

    /**
     * Méthode pour obtenir les coordonnées du couteau.
     *
     * @return Les coordonnées du couteau.
     */
    public Coordinate getCoordinate() {
        return coordinate;
    }

    /**
     * Méthode pour obtenir l'angle du couteau.
     *
     * @return L'angle du couteau.
     */
    public double getAngle() {
        return angle;
    }

    /**
     * Méthode pour démarrer le lancer du couteau.
     */
    public void startThrowing() {
        throwing = true;
    }

    /**
     * Méthode pour réinitialiser l'état de lancer du couteau.
     */
    public void resetThrowing() {
        this.throwing = false;
    }

    /**
     * Méthode pour définir l'angle du couteau.
     *
     * @param angle Nouvel angle du couteau.
     */
    public void setAngle(int angle) {
        this.angle = angle;
    }

    /**
     * Méthode pour obtenir la vélocité du couteau.
     *
     * @return La vélocité du couteau.
     */
    public double getVelocite() {
        return velocite;
    }

    /**
     * Méthode pour définir la vélocité du couteau.
     *
     * @param velocite Nouvelle vélocité du couteau.
     */
    public void setVelocite(double velocite) {
        this.velocite = velocite;
    }

    /**
     * Méthode pour ajouter un angle au couteau.
     *
     * @param value Angle à ajouter.
     */
    public void addAngle(double value){
        angle+=value;
        if (angle > 360) angle -= 360;
        if (angle < 0) angle += 360;
    }

    /**
     * Méthode pour faire sauter le couteau.
     * Met le couteau en l'air avec une vélocité verticale de 10.
     */
    public void jump(){
        isInTheAir = true;
        velocite = 10;
    }

    /**
     * Méthode pour lancer le couteau.
     */
    public void throwKnife(){
        throwing = true;
        Geometry.forwardMovement(coordinate, angle);
    }

    /**
     * Méthode pour mettre à jour le mouvement du couteau.
     * Si le couteau est en l'air, il soit lance, soit il saute.
     */
    public void updateMovement() {
        if (isInTheAir) {
            if (throwing) {
                throwKnife();

            } else {
                updateJump();
                addAngle(0.4);
            }
        }
    }

    // Ajoutez cette méthode pour réinitialiser le couteau au milieu de l'écran
    public void resetKnife() {
        //setX((double) KnifeDisplay.getBgImgWidth() / 2);
        //setY((double) KnifeDisplay.getBgImgHeight() / 2);
        setX(0);setY(0);

        isInTheAir = false;
        redescend = false;
        throwing = false;
        velocite = 0;
        angle = DEFAULT_ANGLE;
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
            angle = DEFAULT_ANGLE;
        }
    }

}
