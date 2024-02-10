package config;

import entity.Knife;
import geometry.Geometry;

import static java.lang.Math.round;

public class Debug {

    static int lastPos;
    final static int RATIO_AFFICHE = 1;
    final static int LONGUEUR_VECT = 5;




    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Affiche le knife sur le terminal où "A" représente le knife et "o" le vecteur de longueur LONGUEUR_VECT.
     * À noter : pour un meilleur rendu mieux vaux l'exécuter dans le cmd plutôt que dans l'IDE.
     */
    public static void affichage(Knife knife){
        lastPos = (int) round(knife.getY()/RATIO_AFFICHE);
        clearScreen();
        //System.out.println(" y : "+knife.getY()+" velocite : "+knife.getVelocite());
        for (int i = 0; i < 40; i++) {
            for (int j = 0; j < 20; j++) {
                if (j == 10 && i == 40-round(knife.getY() / RATIO_AFFICHE)) {
                    System.out.print(" A ");
                }
                else if (Geometry.estDansVecteur(LONGUEUR_VECT,knife.getAngle(),knife.getX(),knife.getY(),j,40-i)) System.out.print(" o ");
                else {
                    System.out.print("   ");
                }
            }
            System.out.println();
        }
        lastPos = (int) round(knife.getY()/RATIO_AFFICHE);

    }
}
