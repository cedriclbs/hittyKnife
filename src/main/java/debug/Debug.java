package debug;

import entity.Knife;
import geometry.Geometry;

import static java.lang.Math.round;

/**
 * Classe utilitaire pour le débogage.
 */
public class Debug {

    static int lastPos;
    final static int RATIO_AFFICHE = 1;
    final static int LONGUEUR_VECT = 5;

    /**
     * Efface l'écran du terminal.
     */
    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Affiche un couteau sur le terminal.
     * "A" représente le couteau et "o" le vecteur de longueur LONGUEUR_VECT.
     * Note : Pour un meilleur rendu, il est préférable d'exécuter cette méthode dans le terminal plutôt que dans l'IDE.
     *
     * @param knife Couteau à afficher.
     */
    public static void affichage(Knife knife){
        lastPos = (int) round(knife.getY()/RATIO_AFFICHE);
        clearScreen();
        System.out.println(" y : "+knife.getY()+" velocite : "+knife.getVelocite());
        for (int i = 0; i < 40; i++) {
            for (int j = 0; j < 20; j++) {
                if (j == knife.getX() && i == 40-round(knife.getY() / RATIO_AFFICHE)) {
                    System.out.print(" A ");
                }
                else if (Geometry.estDansVecteur(LONGUEUR_VECT,(int) knife.getAngle(),knife.getX(),knife.getY(),j,40-i)) System.out.print(" o ");
                else {
                    System.out.print("   ");
                }
            }
            System.out.println();
        }
        lastPos = (int) round(knife.getY()/RATIO_AFFICHE);

    }

}
