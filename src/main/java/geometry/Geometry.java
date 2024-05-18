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

    public static double[] verticalMovementWithHorizontalAdjustment(double x, double y, double delta, double speed, boolean directionPositive, double limitTop, double limitBottom, double xChangePositive, double xChangeNegative) {
        double movement = speed * delta; // Déplacement en pixels
        double newY = y + (directionPositive ? movement : -movement);

        // Vérifier si le mouvement dépasse la limite supérieure
        if (newY >= limitTop) {
            directionPositive = false;
            newY = limitTop;
            x = xChangePositive; // Changer la position horizontale
        }
        // Vérifier si le mouvement dépasse la limite inférieure
        else if (newY <= limitBottom) {
            directionPositive = true;
            newY = limitBottom;
            x = xChangeNegative; // Changer la position horizontale
        }

        return new double[]{x, newY, directionPositive ? 1 : 0}; // Retourner la nouvelle position et la direction
    }

    public static double[] horizontalMovementWithVerticalAdjustment(double x, double y, double delta, double speed, boolean directionPositive, double limitLeft, double limitRight, double limitTop, double limitBottom) {
        double movement = speed * delta; // Déplacement en pixels

        double newX = x;
        double newY = y;

        // Vérifier la direction du mouvement actuel
        if (directionPositive) {
            // Si la direction est positive, le boss se déplace vers la droite sur l'axe horizontal
            newX += movement;
            // Vérifier si le mouvement dépasse la limite droite
            if (newX >= limitRight) {
                // Une fois arrivé à la limite droite, le boss se déplace vers le bas sur l'axe vertical
                newY -= 2;
                // Inverser la direction du mouvement horizontal pour que le boss se déplace vers la gauche
                directionPositive = false;
            }
        } else {
            // Si la direction est négative, le boss se déplace vers la gauche sur l'axe horizontal
            newX -= movement;
            // Vérifier si le mouvement dépasse la limite gauche
            if (newX <= limitLeft) {
                // Une fois arrivé à la limite gauche, le boss se déplace vers le bas sur l'axe vertical
                newY -= 2;
                // Inverser la direction du mouvement horizontal pour que le boss se déplace vers la droite
                directionPositive = true;
            }
        }

        // Vérifier si le mouvement dépasse la limite inférieure
        if (newY <= limitBottom) {
            // Si le boss atteint la limite basse, le ramener à la position initiale en haut
            newX = limitLeft; // Position horizontale centrale
            newY = limitTop; // Position verticale la plus haute
        }

        return new double[]{newX, newY, directionPositive ? 1 : 0}; // Retourner la nouvelle position et la direction
    }

    public static double[] moveInCircle(double centerX, double centerY, double radius, double angularSpeed, double currentAngle, double delta) {
        double movement = angularSpeed * delta;
        double newAngle = currentAngle + movement;
        double newX = centerX + radius * Math.cos(newAngle);
        double newY = centerY + radius * Math.sin(newAngle);
        return new double[]{newX, newY, newAngle};
    }

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