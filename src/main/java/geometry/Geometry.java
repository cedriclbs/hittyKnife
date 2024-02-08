package geometry;
import config.*;
public class Geometry{

    private double velocite;
    private double gravity;


    public Geometry(double velocite, double gravity){
        this.velocite = velocite;
        this.gravity = gravity;
    }


    /**
     * Modifie la position verticale d'un objet en simulant un mouvement de haut en bas.
     * La fonction prend en compte la vélocité actuelle, la gravité et une hauteur maximale.
     *
     * @param y La position verticale actuelle de l'objet.
     * @param height La hauteur maximale à laquelle l'objet peut monter.
     * @param ratio Le ratio utilisé pour ajuster la vélocité du mouvement.
     * @return La nouvelle position verticale de l'objet après le mouvement.
     */
    public double upAndDownMovement(double y, int height, int ratio){
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