package entity;
import geometry.Geometry;
public class Knife {
    private double x,y;
    private int angle = 0;
    Geometry geometry = new Geometry(0,0.2);

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
        y=geometry.upAndDownMovement(y,15,50);
    }


    /**
     * Vérifie si un point défini par ses coordonnées (x2, y2) est situé dans un vecteur défini par sa longueur et son angle.
     * Utilise les coordonnées du point de départ (this.x, this.y), la longueur du vecteur et l'angle du vecteur.
     * @param longueur La longueur du vecteur.
     * @param x2       La coordonnée x du point à vérifier.
     * @param y2       La coordonnée y du point à vérifier.
     * @return true si le point (x2, y2) est situé dans le vecteur, sinon false.
     */
    public boolean estDansVecteur(int longueur, int x2, int y2) {
        double angleEnRadians = Math.toRadians(angle);

        int xFinale = (int) (x + (int) (longueur * Math.cos(angleEnRadians)));
        int yFinale = (int) (y + (int) (longueur * Math.sin(angleEnRadians)));
        return estSurLigne((int) x, (int) y, xFinale, yFinale, x2, y2);
    }

    /**
     * Vérifie si un point défini par ses coordonnées (px, py) est situé sur une ligne définie par deux points (x1, y1) et (x2, y2).
     * Utilise la méthode de comparaison des distances par rapport à la longueur de la ligne.
     * @param x1 La coordonnée x du premier point de la ligne.
     * @param y1 La coordonnée y du premier point de la ligne.
     * @param x2 La coordonnée x du deuxième point de la ligne.
     * @param y2 La coordonnée y du deuxième point de la ligne.
     * @param px La coordonnée x du point à vérifier.
     * @param py La coordonnée y du point à vérifier.
     * @return true si le point (px, py) est situé sur la ligne, sinon false.
     */
    private boolean estSurLigne(int x1, int y1, int x2, int y2, int px, int py) {
        double d1 = distanceEntrePoints(px, py, x1, y1);
        double d2 = distanceEntrePoints(px, py, x2, y2);
        double longueurVecteur = distanceEntrePoints(x1, y1, x2, y2);

        return Math.abs(d1 + d2 - longueurVecteur) < 0.1;
    }

    /**
     * Calcule et retourne la distance euclidienne entre deux points définis par leurs coordonnées (x1, y1) et (x2, y2).
     * Utilise la formule de la distance euclidienne : sqrt((x2 - x1)^2 + (y2 - y1)^2).
     * @param x1 La coordonnée x du premier point.
     * @param y1 La coordonnée y du premier point.
     * @param x2 La coordonnée x du deuxième point.
     * @param y2 La coordonnée y du deuxième point.
     * @return La distance euclidienne entre les deux points.
     */
    private double distanceEntrePoints(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
}
