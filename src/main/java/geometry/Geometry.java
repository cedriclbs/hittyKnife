package geometry;
import App.Loop;
import entity.Knife;

public class Geometry{

    /**
     * Modifie la position verticale d'un objet en simulant un mouvement de haut en bas.
     * La fonction prend en compte la vélocité actuelle, la gravité et une hauteur maximale.
     *
     * @param y La position verticale actuelle de l'objet.
     * @param height La hauteur maximale à laquelle l'objet peut monter.
     * @param ratio Le ratio utilisé pour ajuster la vélocité du mouvement.
     * @param knife éventuellement le couteau pour indiquer s'il redescend.
     * @return La nouvelle position verticale de l'objet après le mouvement.
     */
    public static double[] upAndDownMovement(double y, int height, double velocite, double gravity, int ratio, Knife knife){
        y += (velocite * Loop.delta)/ratio;
        if (y<height) {
            velocite += gravity * Loop.delta;
        }
        else {
            velocite -= gravity * Loop.delta;
            if (knife != null) knife.redescend = true;
        }
        if (y < 0) {
            y = 0;
            velocite = 0;
        }
        if (knife != null) {
            knife.setVelocite(velocite);
        }
        return new double[]{y,velocite};
    }

    /**
     * Déplace les coordonnées spécifiées vers l'avant selon l'angle donné.
     * @param coordinate Les coordonnées à déplacer.
     * @param angle L'angle en degrés.
     */
    public static double[] forwardMovement(Coordinate coordinate, double angle,int ratio){
        double deltaX;
        double deltaY;
        double angleRad = angle * Math.PI / 180.0;
        deltaX = Math.cos(angleRad);
        deltaY = Math.sin(angleRad);
        double[] result = new double[2];
        result[0] =  coordinate.getX()+deltaX/ratio;
        result[1] =  coordinate.getY()+deltaY/ratio;
        return result;
    }

    /*public static void forwardMovementSoloMode(Coordinate coordinate, double angle, double velocite) {
        double angleRad = Math.toRadians(angle);

        double deltaX = Math.cos(angleRad);
        double deltaY = Math.sin(angleRad);


        coordinate.setX(coordinate.getX() + deltaX * 2);
        coordinate.setY(coordinate.getY() + deltaY * 2);
    }*/

    /**
     * Vérifie si un point défini par ses coordonnées (x2, y2) est situé dans un vecteur défini par sa longueur et son angle.
     * Utilise les coordonnées du point de départ (this.x, this.y), la longueur du vecteur et l'angle du vecteur.
     * @param longueur La longueur du vecteur.
     * @param x2       La coordonnée x du point à vérifier.
     * @param y2       La coordonnée y du point à vérifier.
     * @return true si le point (x2, y2) est situé dans le vecteur, sinon false.
     */
    public static boolean estDansVecteur(int longueur, int angle, double x, double y,int x2, int y2) {
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
    private static boolean estSurLigne(int x1, int y1, int x2, int y2, int px, int py) {
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
    private static double distanceEntrePoints(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    /**
     * Calcule le mouvement vertical avec ajustement horizontal en fonction des limites données.
     *
     * @param x                La position horizontale actuelle.
     * @param y                La position verticale actuelle.
     * @param delta            Le delta de temps.
     * @param speed            La vitesse du mouvement en pixels par unité de temps.
     * @param directionPositive Si la direction verticale est positive (vers le haut).
     * @param limitTop         La limite supérieure du mouvement vertical.
     * @param limitBottom      La limite inférieure du mouvement vertical.
     * @param xChangePositive  La nouvelle position horizontale si la limite supérieure est atteinte.
     * @param xChangeNegative  La nouvelle position horizontale si la limite inférieure est atteinte.
     * @return Un tableau de doubles contenant la nouvelle position horizontale, la nouvelle position verticale et la direction verticale sous forme de 1 (positive) ou 0 (négative).
     */
    public static double[] verticalMovementWithHorizontalAdjustment(double x, double y, double delta, double speed, boolean directionPositive, double limitTop, double limitBottom, double xChangePositive, double xChangeNegative) {
        double movement = speed * delta; // Déplacement en pixels
        double newY = y + (directionPositive ? movement : -movement);

        if (newY >= limitTop) {
            directionPositive = false;
            newY = limitTop;
            x = xChangePositive;
        } else if (newY <= limitBottom) {
            directionPositive = true;
            newY = limitBottom;
            x = xChangeNegative;
        }

        return new double[]{x, newY, directionPositive ? 1 : 0};
    }

    /**
     * Calcule le mouvement circulaire en fonction du centre, du rayon et de la vitesse angulaire.
     *
     * @param centerX      La position horizontale du centre du cercle.
     * @param centerY      La position verticale du centre du cercle.
     * @param radius       Le rayon du cercle.
     * @param angularSpeed La vitesse angulaire en radians par unité de temps.
     * @param currentAngle L'angle actuel en radians.
     * @param delta        Le delta de temps.
     * @return Un tableau de doubles contenant la nouvelle position horizontale, la nouvelle position verticale et le nouvel angle en radians.
     */
    public static double[] moveInCircle(double centerX, double centerY, double radius, double angularSpeed, double currentAngle, double delta) {
        double movement = angularSpeed * delta;
        double newAngle = currentAngle + movement;
        double newX = centerX + radius * Math.cos(newAngle);
        double newY = centerY + radius * Math.sin(newAngle);
        return new double[]{newX, newY, newAngle};
    }

    /**
     * Calcule le mouvement horizontal avec ajustement vertical en fonction des limites données.
     *
     * @param x                La position horizontale actuelle.
     * @param y                La position verticale actuelle.
     * @param delta            Le delta de temps.
     * @param speed            La vitesse du mouvement en pixels par unité de temps.
     * @param directionPositive Si la direction horizontale est positive (vers la droite).
     * @param limitLeft        La limite gauche du mouvement horizontal.
     * @param limitRight       La limite droite du mouvement horizontal.
     * @param limitTop         La limite supérieure de réinitialisation verticale.
     * @param limitBottom      La limite inférieure de réinitialisation verticale.
     * @return Un tableau de doubles contenant la nouvelle position horizontale, la nouvelle position verticale et la direction horizontale sous forme de 1 (positive) ou 0 (négative).
     */
    public static double[] horizontalMovementWithVerticalAdjustment(double x, double y, double delta, double speed, boolean directionPositive, double limitLeft, double limitRight, double limitTop, double limitBottom) {
        double movement = speed * delta;

        double newX = x;
        double newY = y;

        if (directionPositive) {
            newX += movement;
            if (newX >= limitRight) {
                newY -= 2;
                directionPositive = false;
            }
        } else {
            newX -= movement;
            if (newX <= limitLeft) {
                newY -= 2;
                directionPositive = true;
            }
        }

        if (newY <= limitBottom) {
            newX = limitLeft;
            newY = limitTop;
        }

        return new double[]{newX, newY, directionPositive ? 1 : 0};
    }

    /**
     * Calcule le mouvement selon un modèle prédéfini en fonction de la phase actuelle.
     *
     * @param x      La position horizontale actuelle.
     * @param y      La position verticale actuelle.
     * @param speed  La vitesse du mouvement en pixels par unité de temps.
     * @param delta  Le delta de temps.
     * @param phase  La phase actuelle du modèle de mouvement.
     * @return Un tableau de doubles contenant la nouvelle position horizontale, la nouvelle position verticale et la nouvelle phase.
     */
    public static double[] moveInPattern(double x, double y, double speed, double delta, int phase) {
        double deplacement = speed * delta;
        double newX = x;
        double newY = y;
        switch (phase) {
            case 1:
                newX += deplacement;
                if (newX >= -25) {
                    phase = 2;
                }
                break;
            case 2:
                newY -= deplacement;
                if (newY <= 5) {
                    phase = 3;
                }
                break;
            case 3:
                newX -= deplacement;
                if (newX <= -56) {
                    newX = 56;
                    phase = 4;
                }
                break;
            case 4:
                newX -= deplacement;
                if (newX <= 25) {
                    phase = 5;
                }
                break;
            case 5:
                newY += deplacement;
                if (newY >= 25) {
                    phase = 6;
                }
                break;
            case 6:
                newX += deplacement;
                if (newX >= 56) {
                    newX = -56;
                    phase = 1;
                }
                break;
        }
        if (newX <= -57 || newY <= -50) {
            newX = -56;
            newY = 25;
        }
        return new double[]{newX, newY, phase};
    }

}